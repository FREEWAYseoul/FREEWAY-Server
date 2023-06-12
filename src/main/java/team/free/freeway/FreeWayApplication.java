package team.free.freeway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FreeWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeWayApplication.class, args);
    }

}
