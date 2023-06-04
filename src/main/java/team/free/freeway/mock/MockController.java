package team.free.freeway.mock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static team.free.freeway.domain.value.StationStatus.AVAILABLE;
import static team.free.freeway.domain.value.StationStatus.SOME_AVAILABLE;
import static team.free.freeway.domain.value.StationStatus.UNAVAILABLE;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MockController {

    @GetMapping("/stations/search")
    public List<StationListResponseDto> searchStationByName(@RequestParam("keyword") String keyword) {
        List<StationListResponseDto> stationList = new ArrayList<>(3);
        stationList.add(new StationListResponseDto("201", "시청", 2, AVAILABLE));
        stationList.add(new StationListResponseDto("212", "건대입구", 2, SOME_AVAILABLE));
        stationList.add(new StationListResponseDto("222", "강남", 2, UNAVAILABLE));
        return stationList;
    }
}
