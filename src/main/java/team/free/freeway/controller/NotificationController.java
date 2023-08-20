package team.free.freeway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.free.freeway.controller.dto.NotificationResponseDto;
import team.free.freeway.service.NotificationService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> notificationsWithinLast14Days() {
        List<NotificationResponseDto> notificationResponseDtolist = notificationService.getNotificationsWithinLast90Days();
        return ResponseEntity.ok().body(notificationResponseDtolist);
    }
}
