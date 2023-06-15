package team.free.freeway.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationResponseDto {

    private String date;
    private List<NotificationResponse> notifications;

    @Builder
    public NotificationResponseDto(String date, List<NotificationResponse> notifications) {
        this.date = date;
        this.notifications = notifications;
    }

    public static NotificationResponseDto of(String date, List<NotificationResponse> notificationResponseList) {
        return NotificationResponseDto.builder()
                .date(date)
                .notifications(notificationResponseList)
                .build();
    }
}
