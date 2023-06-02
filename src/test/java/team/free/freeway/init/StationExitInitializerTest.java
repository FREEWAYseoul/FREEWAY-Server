package team.free.freeway.init;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StationExitInitializerTest {

    @Autowired
    StationExitInitializer stationExitInfoInitializer;

    @Test
    void initializeStationExit() throws Exception {
        stationExitInfoInitializer.initializeStationExit();
    }
}