package team.free.freeway.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicStationServiceTest {

    @Autowired
    StationService stationService;

    @Test
    void test() throws Exception {
        stationService.updateElevatorStatusAndStationStatus();
    }
}