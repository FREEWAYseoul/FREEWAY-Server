package team.free.freeway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.free.freeway.service.StationService;

@SpringBootTest
class FreeWayApplicationTests {

	@Autowired
	StationService stationService;

	@Test
	void contextLoads() {
		stationService.updateElevatorStatusAndStationStatus();
	}

}
