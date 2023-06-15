package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.Notification;

import java.time.LocalDateTime;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsByContentAndDateTime(String content, LocalDateTime dateTime);
}
