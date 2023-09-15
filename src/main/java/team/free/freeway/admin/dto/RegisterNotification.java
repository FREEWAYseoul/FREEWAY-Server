package team.free.freeway.admin.dto;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
public class RegisterNotification {

    @NotBlank
    private String notificationSummary;

    @NotBlank
    private String notificationContents;

    @NotBlank
    private String notificationDate;

    public RegisterNotification(String notificationSummary, String notificationContents, String notificationDate) {
        this.notificationSummary = notificationSummary;
        this.notificationContents = notificationContents;
        this.notificationDate = notificationDate;
    }
}
