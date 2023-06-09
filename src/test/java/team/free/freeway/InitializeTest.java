package team.free.freeway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.free.freeway.init.ElevatorInitializer;
import team.free.freeway.init.FacilitiesInitializer;
import team.free.freeway.init.StationExitInitializer;
import team.free.freeway.init.StationInitializer;

@SpringBootTest
public class InitializeTest {

    @Autowired
    StationInitializer stationInitializer;
    @Autowired
    FacilitiesInitializer facilitiesInitializer;
    @Autowired
    StationExitInitializer stationExitInitializer;
    @Autowired
    ElevatorInitializer elevatorInitializer;

    @Test
    void test() throws Exception {
        elevatorInitializer.initializeElevatorStatusMapping();
    }
}
