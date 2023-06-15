package team.free.freeway.crawler;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationDto {

    private String notificationContent;
    private LocalDateTime notificationDate;

    @Builder
    public NotificationDto(String notificationContent, LocalDateTime notificationDate) {
        this.notificationContent = notificationContent;
        this.notificationDate = notificationDate;
    }
}
