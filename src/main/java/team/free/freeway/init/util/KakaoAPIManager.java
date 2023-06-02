package team.free.freeway.init.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import team.free.freeway.init.dto.Location;
import team.free.freeway.init.dto.LocationDto;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
            if (location.getName().startsWith(stationName + "역")) {
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
}
