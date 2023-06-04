package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.domain.Facilities;
import team.free.freeway.domain.Station;
import team.free.freeway.init.dto.value.DisabledToilet;
import team.free.freeway.init.dto.value.StationFacilities;
import team.free.freeway.init.util.RailPortalAPIManager;
import team.free.freeway.init.util.SeoulOpenAPIManager;
import team.free.freeway.repository.FacilitiesRepository;
import team.free.freeway.repository.StationRepository;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Component
public class FacilitiesInitializer {

    private final SeoulOpenAPIManager seoulOpenAPIManager;
    private final RailPortalAPIManager railPortalAPIManager;
    private final StationRepository stationRepository;
    private final FacilitiesRepository facilitiesRepository;

    public void initializeStationFacilities() {
        List<Station> stations = stationRepository.findAll();
        for (Station station : stations) {
            Facilities facilities = Facilities.defaultObject(station);
            updateFacilities(facilities);

            facilitiesRepository.save(facilities);
        }
    }

    private void updateFacilities(Facilities facilities) {
        List<StationFacilities> stationFacilitiesList =
                seoulOpenAPIManager.getStationFacilitiesList(facilities.getStation().getName());
        if (stationFacilitiesList == null) {
            return;
        }

        updateFacilitiesInfo(stationFacilitiesList, facilities);
        updateDisabledToiletInfo(facilities);
    }

    private static void updateFacilitiesInfo(List<StationFacilities> stationFacilitiesList, Facilities facilities) {
        for (StationFacilities stationFacilities : stationFacilitiesList) {
            if (stationFacilities.getStationId().contains(facilities.getStation().getId())) {
                facilities.updateInfo(stationFacilities);
                return;
            }
        }
    }

    private void updateDisabledToiletInfo(Facilities facilities) {
        List<DisabledToilet> disabledToiletList = railPortalAPIManager.getDisabledToiletList(facilities.getStation());
        if (disabledToiletList != null && !disabledToiletList.isEmpty()) {
            facilities.updateDisabledToilet(true);
            return;
        }

        facilities.updateDisabledToilet(false);
    }
}
