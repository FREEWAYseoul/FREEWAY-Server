package team.free.freeway.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import team.free.freeway.api.dto.DisabledToiletDto;
import team.free.freeway.api.dto.value.DisabledToilet;
import team.free.freeway.domain.Station;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class RailPortalAPIManager {

    private static final String RAIL_PORTAL_HOST = "https://openapi.kric.go.kr/openapi";
    private static final String DISABLED_TOILET_REQUEST_ENDPOINT = "/vulnerableUserInfo/stationDisabledToilet";
    private static final String SERVICE_KEY_PARAM_NAME = "serviceKey";
    private static final String RESPONSE_FORMAT_PARAM_NAME = "format";
    private static final String STATION_CODE_PARAM_NAME = "stinCd";
    private static final String LINE_ID_PARAM_NAME = "lnCd";
    private static final String OPERATING_INSTITUTION_PARAM_NAME = "railOprIsttCd";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${rail.key}")
    private String serviceKey;

    public List<DisabledToilet> getDisabledToiletList(Station station) {
        URI uri = getDisabledToiletRequestUri(station);
        return restTemplate.getForObject(uri, DisabledToiletDto.class).getDisabledToiletList();
    }

    private URI getDisabledToiletRequestUri(Station station) {
        return UriComponentsBuilder.fromUriString(RAIL_PORTAL_HOST + DISABLED_TOILET_REQUEST_ENDPOINT)
                .queryParam(SERVICE_KEY_PARAM_NAME, serviceKey)
                .queryParam(RESPONSE_FORMAT_PARAM_NAME, "json")
                .queryParam(STATION_CODE_PARAM_NAME, station.getStationCode())
                .queryParam(LINE_ID_PARAM_NAME, station.getSubwayLine().getId())
                .queryParam(OPERATING_INSTITUTION_PARAM_NAME, station.getOperatingInstitution())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
    }
}
