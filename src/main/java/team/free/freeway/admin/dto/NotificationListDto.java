package team.free.freeway.admin.dto;

import lombok.Builder;
import lombok.Getter;
import team.free.freeway.domain.Notification;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class NotificationListDto {

    private Long id;
    private String summary;
    private String content;
    private String dateTime;

    @Builder
    public NotificationListDto(Long id, String summary, String content, String dateTime) {
        this.id = id;
        this.summary = summary;
        this.content = content;
        this.dateTime = dateTime;
    }

    public static NotificationListDto of(Notification notification) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm EEEE", Locale.KOREA);
        return NotificationListDto.builder()
                .id(notification.getId())
                .summary(notification.getSummary())
                .content(notification.getContent())
                .dateTime(notification.getDateTime().format(dateTimeFormatter))
                .build();
    }
}
