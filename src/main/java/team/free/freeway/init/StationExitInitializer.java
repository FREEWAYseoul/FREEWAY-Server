package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.StationExit;
import team.free.freeway.init.dto.Location;
import team.free.freeway.init.util.GeographicalDistanceUtils;
import team.free.freeway.init.util.KakaoAPIManager;
import team.free.freeway.repository.StationExitRepository;
import team.free.freeway.repository.StationRepository;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Component
public class StationExitInitializer {

    private final KakaoAPIManager kakaoAPIManager;
    private final StationRepository stationRepository;
    private final StationExitRepository exitRepository;

    public void initializeStationExit() {
        List<Station> stations = stationRepository.findAll();
        for (Station station : stations) {
            String stationName = station.getName();
            String lineName = station.getLineName();
            List<Location> exitLocationList = kakaoAPIManager.getExitLocationList(stationName, lineName);

            for (Location exitLocation : exitLocationList) {
                if (validDistance(station, exitLocation)) {
                    StationExit exit = StationExit.from(exitLocation);
                    exit.setStation(station);
                    exitRepository.save(exit);
                }
            }
        }
    }

    private boolean validDistance(Station station, Location exit) {
        double distance = GeographicalDistanceUtils
                .calculateDistance(station.getCoordinate(), exit.extractCoordinate());
        return !(distance >= 500);
    }
}
