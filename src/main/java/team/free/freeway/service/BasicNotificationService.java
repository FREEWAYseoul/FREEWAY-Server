package team.free.freeway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.api.OpenAIRequestManager;
import team.free.freeway.controller.dto.NotificationResponse;
import team.free.freeway.controller.dto.NotificationResponseDto;
import team.free.freeway.crawler.NotificationDto;
import team.free.freeway.crawler.SeoulMetroTwitterCrawler;
import team.free.freeway.domain.Notification;
import team.free.freeway.exception.JsonParseFaileException;
import team.free.freeway.repository.NotificationRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BasicNotificationService implements NotificationService {

    private static final List<NotificationDto> notificationCache = new ArrayList<>();
    private static final int BEFORE_DATE = 14;

    private final NotificationRepository notificationRepository;
    private final SeoulMetroTwitterCrawler crawler;
    private final OpenAIRequestManager openAIRequestManager;

    @PostConstruct
    private void init() {
        List<Notification> recentNotifications = notificationRepository.findRecentNotifications(LocalDateTime.now().minusDays(14));
        for (Notification recentNotification : recentNotifications) {
            notificationCache.add(NotificationDto.builder()
                    .notificationContent(recentNotification.getContent())
                    .notificationDate(recentNotification.getDateTime())
                    .build());
        }
    }

    @Transactional
    @Override
    public void updateSubwayNotification() {
        List<NotificationDto> notificationDtoList = crawler.crawlingSeoulMetroTwitter();
        for (NotificationDto notificationDto : notificationDtoList) {
            if (existsNotification(notificationDto)) {
                continue;
            }

            Notification notification = Notification.from(notificationDto);
            String notificationSummary;
            try {
                notificationSummary = openAIRequestManager.getNotificationSummary(notification);
            } catch (JsonProcessingException e) {
                throw new JsonParseFaileException();
            }

            notification.updateSummary(notificationSummary);
            notificationRepository.save(notification);

            notificationCache.add(NotificationDto.builder()
                    .notificationContent(notification.getContent())
                    .notificationDate(notification.getDateTime())
                    .build());
        }
    }

    private boolean existsNotification(NotificationDto notificationDto) {
        for (NotificationDto recentNotification : notificationCache) {
            if (recentNotification.equals(notificationDto)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<NotificationResponseDto> getNotificationsWithinLast14Days() {
        List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();
        Map<String, List<NotificationResponse>> notificationMap = new HashMap<>();

        LocalDateTime pastDate = get14DaysAgoDate();
        List<Notification> notifications = notificationRepository.findRecentNotifications(pastDate);

        setNotificationMap(notificationMap, notifications);
        for (String date : notificationMap.keySet()) {
            List<NotificationResponse> notificationResponseList = notificationMap.get(date);
            notificationResponseDtoList.add(NotificationResponseDto.of(date, notificationResponseList));
        }

        return notificationResponseDtoList;
    }

    private void setNotificationMap(Map<String, List<NotificationResponse>> notificationMap, List<Notification> notifications) {
        for (Notification notification : notifications) {
            String date = notification.getDateTime().toLocalDate().toString();
            notificationMap.computeIfAbsent(date, k -> new ArrayList<>());
            List<NotificationResponse> notificationResponseList = notificationMap.get(date);
            notificationResponseList.add(NotificationResponse.from(notification));
        }
    }

    private LocalDateTime get14DaysAgoDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.minusDays(BEFORE_DATE);
    }
}
