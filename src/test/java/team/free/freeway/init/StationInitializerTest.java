package team.free.freeway.init;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StationInitializerTest {

    @Autowired
    StationInitializer stationInitializer;

    @Test
    void initializeStation() throws Exception {
        stationInitializer.setStationContact();
    }
}