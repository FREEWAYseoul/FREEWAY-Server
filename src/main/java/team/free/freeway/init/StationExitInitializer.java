package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.domain.Exit;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.value.Coordinate;
import team.free.freeway.api.dto.value.Location;
import team.free.freeway.init.util.GeographicalDistanceUtils;
import team.free.freeway.api.KakaoAPIManager;
import team.free.freeway.repository.StationExitRepository;
import team.free.freeway.repository.StationRepository;

import java.util.List;
import java.util.Optional;

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
            String lineName = station.getSubwayLine().getLineName();
            List<Location> exitLocationList = kakaoAPIManager.getExitLocationList(stationName, lineName);

            for (Location exitLocation : exitLocationList) {
                if (invalidDistance(station, exitLocation)) {
                    continue;
                }

                Coordinate exitCoordinate = exitLocation.extractCoordinate();
                Optional<Exit> exitOptional = exitRepository.findByCoordinate(exitCoordinate);
                if (exitOptional.isPresent()) {
                    Exit exit = exitOptional.get();
                    if (!station.getExits().contains(exit)) {
                        station.addExit(exit);
                    }
                    continue;
                }

                Exit exit = Exit.from(exitLocation);
                station.addExit(exit);
                exitRepository.save(exit);
            }
        }
    }

    private boolean invalidDistance(Station station, Location exit) {
        double distance = GeographicalDistanceUtils
                .calculateDistance(station.getCoordinate(), exit.extractCoordinate());
        return distance >= 500;
    }
}
