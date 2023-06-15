package team.free.freeway.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import team.free.freeway.annotation.Scheduler;
import team.free.freeway.service.NotificationService;
import team.free.freeway.service.StationService;

@RequiredArgsConstructor
@Scheduler
public class UpdateScheduler {

    private final StationService stationService;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 * * * *")
    public void periodicUpdateStation() {
        stationService.updateElevatorStatusAndStationStatus();
    }

    @Scheduled(cron = "30 */1 * * * *")
    public void periodicUpdateNotification() {
        notificationService.updateSubwayNotification();
    }
}
