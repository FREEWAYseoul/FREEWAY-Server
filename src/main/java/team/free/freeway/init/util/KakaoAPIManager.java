package team.free.freeway.init.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import team.free.freeway.init.dto.LocationDto;
import team.free.freeway.init.dto.value.Location;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class KakaoAPIManager {

    private static final String KAKAO_API_HOST = "https://dapi.kakao.com";
    private static final String KAKAO_API_KEY_PREFIX = "KakaoAK ";
    private static final String SEARCH_REQUEST_ENDPOINT = "/v2/local/search/keyword.json";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.key}")
    private String kakaoAPIKey;

    public Location getStationLocationInfo(String stationName, String lineName) {
        URI uri = getStationLocationRequestUri(stationName + "역 " + lineName);
        LocationDto locationDto = searchLocation(uri);
        assert locationDto != null;

        List<Location> locations = locationDto.getLocations();
        for (Location location : locations) {
            if (location.getName().startsWith(stationName + "역") && location.getName().endsWith(lineName)) {
                return location;
            }
        }

        return null;
    }

    private URI getStationLocationRequestUri(String keyword) {
        return UriComponentsBuilder.fromUriString(KAKAO_API_HOST + SEARCH_REQUEST_ENDPOINT)
                .queryParam("query", keyword)
                .queryParam("category_group_code", "SW8")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
    }

    private LocationDto searchLocation(URI uri) {
        HttpHeaders headers = generateHeader();
        RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, uri);
        return restTemplate.exchange(request, LocationDto.class).getBody();
    }

    private HttpHeaders generateHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, KAKAO_API_KEY_PREFIX + kakaoAPIKey);
        return headers;
    }

    public List<Location> getExitLocationList(String stationName, String lineName) {
        log.info("station = {}", stationName + "역 " + lineName);
        List<Location> exits = new ArrayList<>();
        for (int exitNumber = 1; exitNumber <= 20; exitNumber++) {
            String keyword = stationName + "역 " + exitNumber + "번출구";
            Location location = getLocation(stationName, String.valueOf(exitNumber), keyword);
            if (location == null) {
                continue;
            }

            exits.add(location);
            searchExistsSubNumberExit(stationName, lineName, exitNumber, exits);
        }

        return exits;
    }

    private void searchExistsSubNumberExit(String stationName, String lineName, int exitNumber, List<Location> exits) {
        int exitSubNumber = 1;
        while (true) {
            String fullNumber = exitNumber + "-" + exitSubNumber;
            String keyword = stationName + "역 " + lineName + " " + fullNumber + "번출구";
            Location location = getLocation(stationName, fullNumber, keyword);
            if (location == null) {
                break;
            }

            exits.add(location);
            exitSubNumber++;
        }
    }

    private Location getLocation(String stationName, String exitFullNumber, String keyword) {
        URI uri = getExitLocationRequestUri(keyword);
        LocationDto locationDto = searchLocation(uri);
        assert locationDto != null;

        List<Location> locations = locationDto.getLocations();
        if (locations.size() == 0) {
            return null;
        }

        for (Location location : locations) {
            if (!isSimilarWithKeywordOfExit(location.getName(), stationName, exitFullNumber)) {
                continue;
            }

            return location;
        }

        return null;
    }

    private URI getExitLocationRequestUri(String keyword) {
        return UriComponentsBuilder.fromUriString(KAKAO_API_HOST + SEARCH_REQUEST_ENDPOINT)
                .queryParam("query", keyword)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();
    }

    private boolean isSimilarWithKeywordOfExit(String name, String stationName, String exitFullNumber) {
        return name.startsWith(stationName + "역") && name.contains("선")
                && ((name.endsWith(exitFullNumber + "번출구") || name.endsWith(exitFullNumber + "번 출구")));
    }
}
