package team.free.freeway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.free.freeway.domain.Station;
import team.free.freeway.controller.dto.StationListResponseDto;
import team.free.freeway.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BasicStationService implements StationService {

    private final StationRepository stationRepository;

    @Override
    public List<StationListResponseDto> searchStationsByName(String keyword) {
        List<StationListResponseDto> stationList = new ArrayList<>();
        List<Station> stations = stationRepository.searchByName(keyword);
        for (Station station : stations) {
            StationListResponseDto stationListResponseDto = StationListResponseDto.from(station);
            stationList.add(stationListResponseDto);
        }

        return stationList;
    }
}
