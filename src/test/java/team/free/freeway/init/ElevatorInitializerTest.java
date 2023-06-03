package team.free.freeway.init;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElevatorInitializerTest {

    @Autowired
    ElevatorInitializer elevatorInitializer;

    @Test
    void test() throws Exception {
        elevatorInitializer.initializeElevator();
    }
}