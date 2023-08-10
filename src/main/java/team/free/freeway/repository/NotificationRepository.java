package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

//    @Query("SELECT n FROM Notification n WHERE n.dateTime >= :dateTime")
//    List<Notification> findRecentNotifications(@Param("dateTime") LocalDateTime dateTime);
}
