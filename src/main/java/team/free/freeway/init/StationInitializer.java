package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.domain.Station;
import team.free.freeway.init.dto.value.Location;
import team.free.freeway.init.dto.value.StationContact;
import team.free.freeway.init.util.ExcelReader;
import team.free.freeway.init.util.KakaoAPIManager;
import team.free.freeway.init.util.SeoulOpenAPIManager;
import team.free.freeway.init.util.StationNameUtils;
import team.free.freeway.repository.StationRepository;

import java.io.IOException;
import java.util.List;

import static team.free.freeway.init.constant.StationExcelIndex.LINE_NAME_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.STATION_NAME_INDEX;

@Transactional
@RequiredArgsConstructor
@Component
public class StationInitializer {

    private static final String STATION_CODE_INFO_PATH = "/Users/jcw/Develop/Free-Way/src/main/resources/station_info_only2.xlsx";

    private final KakaoAPIManager kakaoAPIManager;
    private final StationRepository stationRepository;
    private final SeoulOpenAPIManager seoulOpenAPIManager;

    public void initializeStation() throws IOException {
        Sheet sheet = ExcelReader.readSheet(STATION_CODE_INFO_PATH);
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            String lineName = row.getCell(LINE_NAME_INDEX).toString();
            String stationName = row.getCell(STATION_NAME_INDEX).toString();
            stationName = StationNameUtils.getPureStationName(stationName);

            Location location = kakaoAPIManager.getStationLocationInfo(stationName, lineName);

            stationRepository.save(Station.of(stationName, row, location));
        }
    }

    public void initializeStationContact() {
        List<Station> stations = stationRepository.findAll();
        List<StationContact> stationContactList = seoulOpenAPIManager.getStationContactList();
        for (Station station : stations) {
            setStationContact(station, stationContactList);
        }
    }

    private void setStationContact(Station station, List<StationContact> stationContactList) {
        String lineId = station.getLineId();
        String stationName = station.getName();
        for (StationContact stationContact : stationContactList) {
            if (validContact(lineId, stationName, stationContact)) {
                station.updateContact(stationContact.getContact());
            }
        }
    }

    private boolean validContact(String lineId, String stationName, StationContact stationContact) {
        return stationContact.getStationName().equals(stationName) && stationContact.getLineName().contains(lineId);
    }
}
