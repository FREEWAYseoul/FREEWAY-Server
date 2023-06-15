package team.free.freeway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.free.freeway.api.OpenAIRequestManager;
import team.free.freeway.crawler.NotificationDto;
import team.free.freeway.crawler.SeoulMetroTwitterCrawler;
import team.free.freeway.domain.Notification;
import team.free.freeway.exception.JsonParseFaileException;
import team.free.freeway.repository.NotificationRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BasicNotificationService implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SeoulMetroTwitterCrawler crawler;
    private final OpenAIRequestManager openAIRequestManager;

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
        }
    }

    private boolean existsNotification(NotificationDto notificationDto) {
        return notificationRepository.existsByContentAndDateTime(notificationDto.getNotificationContent(),
                notificationDto.getNotificationDate());
    }
}
