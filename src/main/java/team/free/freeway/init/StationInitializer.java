package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.SubwayLine;
import team.free.freeway.init.dto.value.Location;
import team.free.freeway.init.dto.value.StationContact;
import team.free.freeway.init.util.ExcelReader;
import team.free.freeway.init.util.KakaoAPIManager;
import team.free.freeway.init.util.SeoulOpenAPIManager;
import team.free.freeway.init.util.StationNameUtils;
import team.free.freeway.repository.StationRepository;
import team.free.freeway.repository.SubwayLineRepository;

import java.io.IOException;
import java.util.List;

import static team.free.freeway.init.constant.StationExcelIndex.LINE_ID_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.LINE_NAME_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.STATION_NAME_INDEX;

@Transactional
@RequiredArgsConstructor
@Component
public class StationInitializer {

    private static final String STATION_CODE_INFO_PATH = "/Users/jcw/Develop/Free-Way/src/main/resources/station_info.xlsx";

    private final KakaoAPIManager kakaoAPIManager;
    private final StationRepository stationRepository;
    private final SeoulOpenAPIManager seoulOpenAPIManager;
    private final SubwayLineRepository lineRepository;

    public void initializeStation() throws IOException {
        Sheet sheet = ExcelReader.readSheet(STATION_CODE_INFO_PATH);
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            String lineName = row.getCell(LINE_NAME_INDEX).toString();
            String stationName = row.getCell(STATION_NAME_INDEX).toString();
            stationName = StationNameUtils.getPureStationName(stationName);

            Location location = kakaoAPIManager.getStationLocationInfo(stationName, lineName);

            Station station = Station.of(stationName, row, location);
            String lineId = row.getCell(LINE_ID_INDEX).toString();
            SubwayLine subwayLine = lineRepository.findById(lineId)
                    .orElseThrow();
            station.updateSubwayLine(subwayLine);

            stationRepository.save(station);
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
        String lineId = station.getSubwayLine().getId();
        String stationName = station.getName();
        for (StationContact stationContact : stationContactList) {
            if (validContact(lineId, stationName, stationContact)) {
                station.updateContact(stationContact.getContact());
            }
        }
    }

    private boolean validContact(String lineId, String stationName, StationContact stationContact) {
        String pureStationName = StationNameUtils.getPureStationName(stationContact.getStationName());
        return pureStationName.equals(stationName) && stationContact.getLineName().contains(lineId);
    }
}
