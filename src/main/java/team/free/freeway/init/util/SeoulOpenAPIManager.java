package team.free.freeway.init.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team.free.freeway.init.dto.ElevatorLocation;
import team.free.freeway.init.dto.ElevatorLocationDto;

import java.util.List;

@Component
public class SeoulOpenAPIManager {

    private static final String SEOUL_API_HOST = "http://openapi.seoul.go.kr:8088/";
    private static final String ELEVATOR_LOCATION_REQUEST_ENDPOINT = "/tbTraficElvtr";
    private static final String RESPONSE_TYPE = "/json";
    private static final String ELEVATOR_STATUS_REQUEST_SIZE1 = "/1/1000";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${data.seoul.authentication_key}")
    private String authenticationKey;

    public List<ElevatorLocation> getElevatorLocationInfo() {
        String url = SEOUL_API_HOST + authenticationKey + RESPONSE_TYPE + ELEVATOR_LOCATION_REQUEST_ENDPOINT +
                ELEVATOR_STATUS_REQUEST_SIZE1;

        return restTemplate.getForObject(url, ElevatorLocationDto.class).getElevatorLocationRow().getElevatorLocationList();
    }
}
