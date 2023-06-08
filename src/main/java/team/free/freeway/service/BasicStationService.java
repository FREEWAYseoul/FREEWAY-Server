package team.free.freeway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.controller.dto.StationDetailsResponseDto;
import team.free.freeway.controller.dto.StationListResponseDto;
import team.free.freeway.domain.Elevator;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.value.ElevatorStatus;
import team.free.freeway.domain.value.StationStatus;
import team.free.freeway.exception.StationNotFoundException;
import team.free.freeway.init.dto.value.ElevatorStatusInfo;
import team.free.freeway.init.util.SeoulOpenAPIManager;
import team.free.freeway.repository.StationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class BasicStationService implements StationService {

    private final StationRepository stationRepository;
    private final SeoulOpenAPIManager seoulOpenAPIManager;

    @Override
    public List<StationListResponseDto> searchStationsByName(String keyword) {
        List<Station> stations = stationRepository.searchByName(keyword);
        return createStationList(stations);
    }

    @Override
    public List<StationListResponseDto> getAllStations() {
        List<Station> stations = stationRepository.findAll();
        return createStationList(stations);
    }

    private List<StationListResponseDto> createStationList(List<Station> stations) {
        List<StationListResponseDto> stationList = new ArrayList<>();
        for (Station station : stations) {
            StationListResponseDto stationListResponseDto = StationListResponseDto.from(station);
            stationList.add(stationListResponseDto);
        }

        return stationList;
    }

    @Override
    public StationDetailsResponseDto getStationDetails(String stationId) {
        Station station = stationRepository.findById(stationId).orElseThrow(StationNotFoundException::new);
        StationDetailsResponseDto stationDetailsResponseDto = StationDetailsResponseDto.from(station);

        List<Station> transferStations = stationRepository.findByName(station.getName());
        for (Station transferStation : transferStations) {
            if (station == transferStation) {
                continue;
            }

            stationDetailsResponseDto.addTransferStation(transferStation);
        }

        return stationDetailsResponseDto;
    }

    @Override
    public void updateElevatorStatusAndStationStatus() {
        Map<String, List<ElevatorStatusInfo>> elevatorStatusMap = seoulOpenAPIManager.getElevatorStatus();
        List<Station> stations = stationRepository.findAllWithElevators();
        for (Station station : stations) {
            List<ElevatorStatusInfo> elevatorStatusList = elevatorStatusMap.get(station.getName());
            if (elevatorStatusList == null) {
                station.updateStatus(StationStatus.UNKNOWN);
                continue;
            }

            mappingAndUpdateStatus(station, elevatorStatusList);
        }
    }

    private void mappingAndUpdateStatus(Station station, List<ElevatorStatusInfo> elevatorStatusList) {
        Map<ElevatorStatus, Integer> elevatorNumberMap = new HashMap<>();
        initializeMap(elevatorNumberMap);

        List<Elevator> elevators = station.getElevators();
        for (Elevator elevator : elevators) {
            if (elevator.getDescription() == null) {
                continue;
            }

            for (ElevatorStatusInfo elevatorStatus : elevatorStatusList) {
                updateElevatorStatus(elevator, elevatorStatus);
            }

            int newNumber = elevatorNumberMap.get(elevator.getStatus()) + 1;
            elevatorNumberMap.put(elevator.getStatus(), newNumber);

            updateStationStatus(station, elevatorNumberMap);
        }
    }

    private void initializeMap(Map<ElevatorStatus, Integer> elevatorNumberMap) {
        for (ElevatorStatus elevatorStatus : ElevatorStatus.values()) {
            elevatorNumberMap.put(elevatorStatus, 0);
        }
    }

    private void updateElevatorStatus(Elevator elevator, ElevatorStatusInfo elevatorStatus) {
        if (elevator.getDescription().contains(elevatorStatus.getElevatorName())) {
            elevator.updateElevatorStatus(ElevatorStatus.of(elevatorStatus.getStatus()));
        }
    }

    private void updateStationStatus(Station station, Map<ElevatorStatus, Integer> elevatorNumberMap) {
        int allElevatorNumber = station.getElevators().size();
        int availableElevatorNumber = elevatorNumberMap.get(ElevatorStatus.AVAILABLE);

        if (availableElevatorNumber == allElevatorNumber) {
            station.updateStatus(StationStatus.AVAILABLE);
            return;
        }

        if (availableElevatorNumber >= 1) {
            station.updateStatus(StationStatus.SOME_AVAILABLE);
            return;
        }

        int unknownElevatorNumber = elevatorNumberMap.get(ElevatorStatus.UNKNOWN);
        if (unknownElevatorNumber == allElevatorNumber) {
            station.updateStatus(StationStatus.UNKNOWN);
            return;
        }

        station.updateStatus(StationStatus.UNAVAILABLE);
    }
}
