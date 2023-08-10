package team.free.freeway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.controller.dto.NotificationResponse;
import team.free.freeway.controller.dto.NotificationResponseDto;
import team.free.freeway.domain.Notification;
import team.free.freeway.repository.NotificationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BasicNotificationService implements NotificationService {

//    private static final int BEFORE_DATE = 1_000_000_000;

    private final NotificationRepository notificationRepository;

    @Override
    public List<NotificationResponseDto> getNotificationsWithinLast14Days() {
        List<NotificationResponseDto> notificationResponseDtoList = new ArrayList<>();
        Map<String, List<NotificationResponse>> notificationMap = new HashMap<>();

//        LocalDateTime pastDate = get14DaysAgoDate();
        List<Notification> notifications = notificationRepository.findAll();

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

    /*private LocalDateTime get14DaysAgoDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.minusDays(BEFORE_DATE);
    }*/
}
