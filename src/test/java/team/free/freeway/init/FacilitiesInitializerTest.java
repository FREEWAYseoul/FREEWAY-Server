package team.free.freeway.init;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FacilitiesInitializerTest {

    @Autowired
    FacilitiesInitializer facilitiesInitializer;

    @Test
    void test() throws Exception {
        facilitiesInitializer.initializeStationFacilities();
    }
}