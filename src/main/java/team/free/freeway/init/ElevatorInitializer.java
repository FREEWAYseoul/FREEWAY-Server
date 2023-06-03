package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.domain.Elevator;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.StationExit;
import team.free.freeway.domain.value.ElevatorStatus;
import team.free.freeway.init.dto.value.ElevatorLocation;
import team.free.freeway.init.dto.value.ElevatorStatusInfo;
import team.free.freeway.init.util.GeographicalDistanceUtils;
import team.free.freeway.init.util.SeoulOpenAPIManager;
import team.free.freeway.init.util.StationNameUtils;
import team.free.freeway.repository.ElevatorRepository;
import team.free.freeway.repository.StationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Component
public class ElevatorInitializer {

    private final SeoulOpenAPIManager seoulOpenAPIManager;
    private final StationRepository stationRepository;
    private final ElevatorRepository elevatorRepository;

    public void initializeElevator() {
        List<ElevatorLocation> elevatorLocationList = seoulOpenAPIManager.getElevatorLocationInfo();
        Map<String, List<ElevatorLocation>> elevatorLocationMap = createElevatorLocationMap(elevatorLocationList);

        List<Station> stations = stationRepository.findAll();
        for (Station station : stations) {
            mappingStationAndElevator(elevatorLocationMap, station);
        }
    }

    private Map<String, List<ElevatorLocation>> createElevatorLocationMap(List<ElevatorLocation> elevatorLocationList) {
        Map<String, List<ElevatorLocation>> elevatorLocationMap = new HashMap<>();
        for (ElevatorLocation elevatorLocation : elevatorLocationList) {
            String stationName = StationNameUtils.getPureStationName(elevatorLocation.getStationName());
            if (stationName.isEmpty()) {
                continue;
            }

            List<ElevatorLocation> specificElevatorLocationList =
                    elevatorLocationMap.getOrDefault(stationName, new ArrayList<>());
            specificElevatorLocationList.add(elevatorLocation);
            elevatorLocationMap.put(stationName, specificElevatorLocationList);
        }

        return elevatorLocationMap;
    }

    private void mappingStationAndElevator(Map<String, List<ElevatorLocation>> elevatorLocationMap, Station station) {
        List<ElevatorLocation> elevatorLocationList = elevatorLocationMap.get(station.getName());
        if (elevatorLocationList == null || elevatorLocationList.isEmpty()) {
            return;
        }

        for (ElevatorLocation elevatorLocation : elevatorLocationList) {
            if (validDistance(station, elevatorLocation)) {
                Elevator elevator = Elevator.builder()
                        .coordinate(elevatorLocation.extractCoordinate())
                        .build();
                elevator.updateStation(station);
                elevatorRepository.save(elevator);
            }
        }
    }

    private boolean validDistance(Station station, ElevatorLocation elevatorLocation) {
        double distance = GeographicalDistanceUtils
                .calculateDistance(station.getCoordinate(), elevatorLocation.extractCoordinate());
        return distance < 500;
    }

    public void initializeElevatorNearestExit() {
        List<Station> stations = stationRepository.findAll();
        for (Station station : stations) {
            setNearestExit(station);
        }
    }

    private void setNearestExit(Station station) {
        List<Elevator> elevators = station.getElevators();
        List<StationExit> exits = station.getExits();

        for (Elevator elevator : elevators) {
            double minDistance = 1_000_000;
            String nearestExit = getNearestExit(exits, elevator, minDistance);

            elevator.updateNearestExit(nearestExit);
        }
    }

    private String getNearestExit(List<StationExit> exits, Elevator elevator, double minDistance) {
        String nearestExit = null;
        for (StationExit exit : exits) {
            double distance =
                    GeographicalDistanceUtils.calculateDistance(elevator.getCoordinate(), exit.getCoordinate());
            if (distance < minDistance) {
                minDistance = distance;
                nearestExit = exit.getExitNumber();
            }
        }
        return nearestExit;
    }

    public void initializeElevatorStatusMapping() {
        List<Station> stations = stationRepository.findAll();
        Map<String, List<ElevatorStatusInfo>> statusInfoMap = seoulOpenAPIManager.getElevatorStatus();
        for (Station station : stations) {
            mappingElevator(station, statusInfoMap);
        }
    }

    private void mappingElevator(Station station, Map<String, List<ElevatorStatusInfo>> elevatorStatusMap) {
        List<Elevator> elevators = station.getElevators();
        List<ElevatorStatusInfo> elevatorStatusInfoList = elevatorStatusMap.get(station.getName());
        if (elevatorStatusInfoList == null) {
            return;
        }

        for (Elevator elevator : elevators) {
            updateDescriptionAndStatus(elevatorStatusInfoList, elevator);
            if (elevator.getStatus() == null) {
                elevator.updateElevatorStatus(ElevatorStatus.UNKNOWN);
            }
        }
    }

    private void updateDescriptionAndStatus(List<ElevatorStatusInfo> elevatorStatusInfoList, Elevator elevator) {
        for (ElevatorStatusInfo elevatorStatusInfo : elevatorStatusInfoList) {
            String nearestExit = elevator.getNearestExit();
            String location = elevatorStatusInfo.getLocation();
            if (location.contains(nearestExit)) {
                elevator.updateDescription(elevatorStatusInfo.getStationName() + "-" + elevatorStatusInfo.getElevatorName());
                String status = elevatorStatusInfo.getStatus();
                elevator.updateElevatorStatus(ElevatorStatus.of(status));
                break;
            }
        }
    }
}
