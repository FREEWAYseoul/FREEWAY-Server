package team.free.freeway.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.free.freeway.admin.dto.NotificationListDto;
import team.free.freeway.admin.dto.RegisterNotification;
import team.free.freeway.domain.Notification;
import team.free.freeway.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final NotificationRepository notificationRepository;

    public AdminController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/notifications")
    public String registerNotificationForm(Model model) {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(90);
        List<NotificationListDto> recentNotifications =
                notificationRepository.findNotificationByDateTimeAfterOrderByDateTimeDesc(dateTime).stream()
                        .map(NotificationListDto::of)
                        .collect(Collectors.toList());
        model.addAttribute("notifications", recentNotifications);
        return "register-notification";
    }

    @PostMapping("/notifications")
    public String registerNotification(@ModelAttribute RegisterNotification registerNotification) {
        Notification notification = Notification.of(registerNotification);
        notificationRepository.save(notification);
        return "redirect:/admin/notifications";
    }

    @DeleteMapping("/notifications/{notificationId}")
    public String delete(@PathVariable Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found!"));
        notificationRepository.delete(notification);
        return "redirect:/admin/notifications";
    }
}
