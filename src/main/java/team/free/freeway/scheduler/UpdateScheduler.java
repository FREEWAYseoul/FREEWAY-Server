package team.free.freeway.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import team.free.freeway.annotation.Scheduler;
import team.free.freeway.service.StationService;

@RequiredArgsConstructor
@Scheduler
public class UpdateScheduler {

    private final StationService stationService;

    @Scheduled(cron = "0 0 * * * *")
    public void periodicalUpdateStation() {
        stationService.updateElevatorStatusAndStationStatus();
    }
}
