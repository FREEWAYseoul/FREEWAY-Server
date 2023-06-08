package team.free.freeway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.free.freeway.controller.dto.StationDetailsResponseDto;
import team.free.freeway.controller.dto.StationListResponseDto;
import team.free.freeway.domain.Station;
import team.free.freeway.exception.StationNotFoundException;
import team.free.freeway.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BasicStationService implements StationService {

    private final StationRepository stationRepository;

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
}
