package team.free.freeway.init;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.SubwayLine;
import team.free.freeway.init.constant.NextStationExcelIndex;
import team.free.freeway.init.dto.value.Location;
import team.free.freeway.init.dto.value.StationContact;
import team.free.freeway.init.dto.value.StationForeign;
import team.free.freeway.init.dto.value.StationImage;
import team.free.freeway.init.util.ExcelReader;
import team.free.freeway.init.util.KakaoAPIManager;
import team.free.freeway.init.util.SeoulOpenAPIManager;
import team.free.freeway.init.util.StationNameUtils;
import team.free.freeway.repository.StationRepository;
import team.free.freeway.repository.SubwayLineRepository;

import java.io.IOException;
import java.util.List;

import static team.free.freeway.init.constant.StationExcelIndex.*;

@Transactional
@RequiredArgsConstructor
@Component
public class StationInitializer {

    private static final String STATION_CODE_INFO_PATH = "/Users/jcw/Develop/Free-Way/src/main/resources/station_code.xlsx";
    private static final String NEXT_STATION_INFO_PATH = "/Users/jcw/Develop/Free-Way/src/main/resources/next_station.xlsx";

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
            if (!location.getAddress().startsWith("서울")) {
                continue;
            }

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

    public void initializeStationImage() {
        List<Station> stations = stationRepository.findAll();
        List<StationImage> stationImageList = seoulOpenAPIManager.getStationImage();
        for (Station station : stations) {
            setStationImage(stationImageList, station);
        }
    }

    private void setStationImage(List<StationImage> stationImageList, Station station) {
        for (StationImage stationImage : stationImageList) {
            String pureStationName = StationNameUtils.getPureStationName(stationImage.getStationName());
            if (pureStationName.endsWith("역")) {
                pureStationName = pureStationName.substring(0, pureStationName.length() - 1);
            }

            if (validImage(station, stationImage, pureStationName)) {
                station.updateImageUrl(stationImage.getStationImageUrl());
                return;
            }
        }
    }

    private boolean validImage(Station station, StationImage stationImage, String pureStationName) {
        return pureStationName.equals(station.getName())
                && stationImage.getLineId().equals(station.getSubwayLine().getId());
    }

    public void initializeStationForeignId() {
        List<Station> stations = stationRepository.findAll();
        List<StationForeign> stationForeignList = seoulOpenAPIManager.getStationForeignList();
        for (Station station : stations) {
            setStationForeignId(stationForeignList, station);
        }
    }

    private void setStationForeignId(List<StationForeign> stationForeignList, Station station) {
        for (StationForeign stationForeign : stationForeignList) {
            if (validStationForeign(station, stationForeign)) {
                station.updateForeignId(stationForeign.getStationForeignId());
            }
        }
    }

    private boolean validStationForeign(Station station, StationForeign stationForeign) {
        if (station.getSubwayLine().getLineName().equals("경의중앙선")) {
            if (station.getName().equals(stationForeign.getStationName())
                    && stationForeign.getLineName().equals("경의선")) {
                return true;
            }
        }
        return station.getName().equals(stationForeign.getStationName())
                && stationForeign.getLineName().contains(station.getSubwayLine().getLineName());
    }

    public void initializeAdjacentStations() throws IOException {
        Sheet sheet = ExcelReader.readSheet(NEXT_STATION_INFO_PATH);
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            String stationName =
                    StationNameUtils.getPureStationName(row.getCell(NextStationExcelIndex.STATION_NAME_INDEX).toString());
            String lineId = row.getCell(NextStationExcelIndex.LINE_ID_INDEX).toString();
            if (lineId.contains(".")) {
                lineId = lineId.split("\\.")[0];
            }

            SubwayLine subwayLine = lineRepository.findById(lineId).orElse(null);
            if (subwayLine == null) {
                continue;
            }

            stationRepository.findByNameAndSubwayLine(stationName, subwayLine)
                    .ifPresent(station -> setNextAndBranchStation(row, station, subwayLine));
        }
    }

    private void setNextAndBranchStation(Row row, Station station, SubwayLine subwayLine) {
        Cell nextStationNameCell = row.getCell(NextStationExcelIndex.NEXT_STATION_NAME_INDEX);
        if (nextStationNameCell == null) {
            return;
        }

        String nextStationName =
                StationNameUtils.getPureStationName(nextStationNameCell.toString());
        stationRepository.findByNameAndSubwayLine(nextStationName, subwayLine).ifPresent(station::linkNextStation);

        Cell branchStationNameCell = row.getCell(NextStationExcelIndex.BRANCH_STATION_NAME_INDEX);
        if (branchStationNameCell == null) {
            return;
        }

        String branchStationName = StationNameUtils.getPureStationName(branchStationNameCell.toString());
        stationRepository.findByNameAndSubwayLine(branchStationName, subwayLine)
                .ifPresent(station::linkBranchStation);
    }
}
