package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.api.RailPortalAPIManager;
import team.free.freeway.api.SeoulOpenAPIManager;
import team.free.freeway.api.dto.value.DisabledToilet;
import team.free.freeway.api.dto.value.StationFacilities;
import team.free.freeway.domain.Facilities;
import team.free.freeway.domain.Station;
import team.free.freeway.repository.StationRepository;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Component
public class FacilitiesInitializer {

    private final SeoulOpenAPIManager seoulOpenAPIManager;
    private final RailPortalAPIManager railPortalAPIManager;
    private final StationRepository stationRepository;

    public void initializeStationFacilities() {
        List<Station> stations = stationRepository.findAll();
        for (Station station : stations) {
            Facilities facilities = Facilities.defaultObject();
            updateFacilities(facilities, station);
        }
    }

    private void updateFacilities(Facilities facilities, Station station) {
        List<StationFacilities> stationFacilitiesList =
                seoulOpenAPIManager.getStationFacilitiesList(station.getName());
        if (stationFacilitiesList == null) {
            return;
        }

        updateFacilitiesInfo(stationFacilitiesList, facilities, station);
        updateDisabledToiletInfo(facilities, station);

        station.updateFacilities(facilities);
    }

    private static void updateFacilitiesInfo(
            List<StationFacilities> stationFacilitiesList, Facilities facilities, Station station
    ) {
        for (StationFacilities stationFacilities : stationFacilitiesList) {
            if (isValidFacilities(station, stationFacilities)) {
                facilities.updateInfo(stationFacilities);
                return;
            }
        }
    }

    private static boolean isValidFacilities(Station station, StationFacilities stationFacilities) {
        return stationFacilities.getStationCode().contains(station.getStationCode())
                && stationFacilities.getLineName().contains(station.getSubwayLine().getLineName());
    }

    private void updateDisabledToiletInfo(Facilities facilities, Station station) {
        List<DisabledToilet> disabledToiletList = railPortalAPIManager.getDisabledToiletList(station);
        if (disabledToiletList != null && !disabledToiletList.isEmpty()) {
            facilities.updateDisabledToilet(true);
            return;
        }

        facilities.updateDisabledToilet(false);
    }
}
