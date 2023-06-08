package team.free.freeway.init;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StationExitInitializerTest {

    @Autowired
    StationInitializer stationInitializer;

    @Test
    void test() throws Exception {
        stationInitializer.initializeStationForeignId();
    }
}