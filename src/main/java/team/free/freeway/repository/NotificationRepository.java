package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.free.freeway.domain.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    boolean existsByContentAndDateTime(String content, LocalDateTime dateTime);

    @Query("SELECT n FROM Notification n WHERE n.dateTime >= :dateTime")
    List<Notification> findRecentNotifications(@Param("dateTime") LocalDateTime dateTime);
}
