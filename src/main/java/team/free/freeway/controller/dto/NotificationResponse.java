package team.free.freeway.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.domain.Notification;

import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationResponse {

    private String summary;
    private String content;
    private LocalTime time;

    @Builder
    public NotificationResponse(String summary, String content, LocalTime time) {
        this.summary = summary;
        this.content = content;
        this.time = time;
    }

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .summary(notification.getSummary())
                .content(notification.getContent())
                .time(notification.getDateTime().toLocalTime())
                .build();
    }
}
