package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.domain.Station;
import team.free.freeway.init.dto.Location;
import team.free.freeway.init.util.ExcelReader;
import team.free.freeway.init.util.KakaoAPIManager;
import team.free.freeway.init.util.StationNameUtils;
import team.free.freeway.repository.StationRepository;

import java.io.IOException;

import static team.free.freeway.init.constant.StationExcelIndex.CONTACT_STATION_NAME_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.LINE_NAME_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.STATION_CONTACT_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.STATION_NAME_INDEX;

@Transactional
@RequiredArgsConstructor
@Component
public class StationInitializer {

    private static final String STATION_CODE_INFO_PATH = "/Users/jcw/Develop/Free-Way/src/main/resources/station_info_only2.xlsx";
    private static final String STATION_DETAILS_PATH = "/Users/jcw/Develop/Free-Way/src/main/resources/station_details.xlsx";

    private final ExcelReader excelReader;
    private final KakaoAPIManager kakaoAPIManager;
    private final StationRepository stationRepository;

    public void initializeStation() throws IOException {
        Sheet sheet = excelReader.readSheet(STATION_CODE_INFO_PATH);
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

    public void setStationContact() throws IOException {
        Sheet sheet = excelReader.readSheet(STATION_DETAILS_PATH);
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 0; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            String contact = row.getCell(STATION_CONTACT_INDEX).toString();
            String stationName = row.getCell(CONTACT_STATION_NAME_INDEX).toString();
            stationName = StationNameUtils.getPureStationName(stationName);

            Station station = stationRepository.findByName(stationName)
                    .orElseThrow(() -> null);
            station.updateContact(contact);
        }
    }
}
