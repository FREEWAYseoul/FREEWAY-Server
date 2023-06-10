package team.free.freeway.init.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team.free.freeway.init.dto.ElevatorLocationDto;
import team.free.freeway.init.dto.ElevatorStatusDto;
import team.free.freeway.init.dto.StationContactDto;
import team.free.freeway.init.dto.StationFacilitiesDto;
import team.free.freeway.init.dto.StationForeignDto;
import team.free.freeway.init.dto.StationImageDto;
import team.free.freeway.init.dto.value.ElevatorLocation;
import team.free.freeway.init.dto.value.ElevatorStatusInfo;
import team.free.freeway.init.dto.value.StationContact;
import team.free.freeway.init.dto.value.StationFacilities;
import team.free.freeway.init.dto.value.StationFacilitiesRow;
import team.free.freeway.init.dto.value.StationForeign;
import team.free.freeway.init.dto.value.StationImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SeoulOpenAPIManager {

    private static final String SEOUL_API_HOST = "http://openapi.seoul.go.kr:8088/";
    private static final String ELEVATOR_LOCATION_REQUEST_ENDPOINT = "/tbTraficElvtr";
    private static final String ELEVATOR_STATUS_REQUEST_ENDPOINT = "/SeoulMetroFaciInfo";
    private static final String STATION_CONTACT_REQUEST_ENDPOINT = "/StationAdresTelno";
    private static final String STATION_FACILITIES_REQUEST_ENDPOINT = "/TbSeoulmetroStConve";
    private static final String STATION_IMAGE_REQUEST_ENDPOINT = "/SmrtEmergerncyGuideImg";
    private static final String STATION_FOREIGN_ID_REQUEST_ENDPOINT = "/SearchInfoBySubwayNameService";
    private static final String RESPONSE_TYPE = "/json";
    private static final String REQUEST_SIZE1 = "/1/1000";
    private static final String REQUEST_SIZE2 = "/1001/2000";
    private static final String REQUEST_SIZE3 = "/2001/3000";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${data.seoul.authentication_key}")
    private String authenticationKey;

    public List<ElevatorLocation> getElevatorLocationInfo() {
        String url = SEOUL_API_HOST + authenticationKey + RESPONSE_TYPE
                + ELEVATOR_LOCATION_REQUEST_ENDPOINT + REQUEST_SIZE1;

        return restTemplate.getForObject(url, ElevatorLocationDto.class)
                .getElevatorLocationRow().getElevatorLocationList();
    }

    public Map<String, List<ElevatorStatusInfo>> getElevatorStatus() {
        Map<String, List<ElevatorStatusInfo>> elevatorStatusInfoMap = new HashMap<>();
        List<ElevatorStatusInfo> elevatorStatusInfoList =
                getElevatorStatusObject(ELEVATOR_STATUS_REQUEST_ENDPOINT + REQUEST_SIZE1)
                        .getElevatorStatusRow().getElevatorStatusInfoList();
        setElevatorStatusList(elevatorStatusInfoList, elevatorStatusInfoMap);

        elevatorStatusInfoList = getElevatorStatusObject(ELEVATOR_STATUS_REQUEST_ENDPOINT + REQUEST_SIZE2)
                .getElevatorStatusRow().getElevatorStatusInfoList();
        setElevatorStatusList(elevatorStatusInfoList, elevatorStatusInfoMap);

        elevatorStatusInfoList = getElevatorStatusObject(ELEVATOR_STATUS_REQUEST_ENDPOINT + REQUEST_SIZE3)
                .getElevatorStatusRow().getElevatorStatusInfoList();
        setElevatorStatusList(elevatorStatusInfoList, elevatorStatusInfoMap);

        return elevatorStatusInfoMap;
    }

    private ElevatorStatusDto getElevatorStatusObject(String endpoint) {
        String url = SEOUL_API_HOST + authenticationKey + RESPONSE_TYPE + endpoint;
        return restTemplate.getForObject(url, ElevatorStatusDto.class);
    }

    private void setElevatorStatusList(
            List<ElevatorStatusInfo> elevatorStatusInfoList, Map<String, List<ElevatorStatusInfo>> elevatorStatusInfoMap
    ) {
        for (ElevatorStatusInfo elevatorStatusInfo : elevatorStatusInfoList) {
            if (elevatorStatusInfo.getCategory().equals("EV")) {
                putOutsideElevatorStatusInfoToMap(elevatorStatusInfo, elevatorStatusInfoMap);
            }
        }
    }

    private void putOutsideElevatorStatusInfoToMap(
            ElevatorStatusInfo elevatorStatusInfo, Map<String, List<ElevatorStatusInfo>> elevatorStatusInfoMap
    ) {
        if (elevatorStatusInfo.getElevatorName().contains("외부") || elevatorStatusInfo.getLocation().contains("출")
                || elevatorStatusInfo.getLocation().contains("외부")) {
            String stationName = StationNameUtils.getPureStationName(elevatorStatusInfo.getStationName());

            List<ElevatorStatusInfo> value = elevatorStatusInfoMap.getOrDefault(stationName, new ArrayList<>());
            value.add(elevatorStatusInfo);
            elevatorStatusInfoMap.put(stationName, value);
        }
    }

    public List<StationContact> getStationContactList() {
        String url = SEOUL_API_HOST + authenticationKey + RESPONSE_TYPE
                + STATION_CONTACT_REQUEST_ENDPOINT + REQUEST_SIZE1;

        return restTemplate.getForObject(url, StationContactDto.class)
                .getStationContactRow().getStationContactList();
    }

    public List<StationFacilities> getStationFacilitiesList(String stationName) {
        String url = SEOUL_API_HOST + authenticationKey + RESPONSE_TYPE
                + STATION_FACILITIES_REQUEST_ENDPOINT + REQUEST_SIZE1 + "/" + stationName;

        StationFacilitiesRow stationFacilitiesRow =
                restTemplate.getForObject(url, StationFacilitiesDto.class).getStationFacilitiesRow();
        if (stationFacilitiesRow == null) {
            return null;
        }

        return stationFacilitiesRow.getStationFacilities();
    }

    public List<StationImage> getStationImage() {
        String url = SEOUL_API_HOST + authenticationKey + RESPONSE_TYPE
                + STATION_IMAGE_REQUEST_ENDPOINT + REQUEST_SIZE1;

        return restTemplate.getForObject(url, StationImageDto.class)
                .getStationImageRow().getStationImageList();
    }

    public List<StationForeign> getStationForeignList() {
        String url = SEOUL_API_HOST + authenticationKey + RESPONSE_TYPE
                + STATION_FOREIGN_ID_REQUEST_ENDPOINT + REQUEST_SIZE1;

        return restTemplate.getForObject(url, StationForeignDto.class)
                .getStationForeignRow().getStationForeignList();
    }
}
