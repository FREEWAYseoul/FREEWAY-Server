package team.free.freeway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.free.freeway.controller.dto.StationDetailsResponseDto;
import team.free.freeway.controller.dto.StationListResponseDto;
import team.free.freeway.service.StationService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/stations")
@RestController
public class StationController {

    private final StationService stationService;

    @GetMapping("/search")
    public ResponseEntity<List<StationListResponseDto>> searchStations(@RequestParam("keyword") String keyword) {
        List<StationListResponseDto> stationList = stationService.searchStationsByName(keyword);
        return ResponseEntity.ok().body(stationList);
    }

    @GetMapping
    public ResponseEntity<List<StationListResponseDto>> allStations() {
        List<StationListResponseDto> stationList = stationService.getAllStations();
        return ResponseEntity.ok().body(stationList);
    }

    @GetMapping("/{stationId}")
    public ResponseEntity<StationDetailsResponseDto> stationDetailsInfo(@PathVariable Long stationId) {
        StationDetailsResponseDto stationDetails = stationService.getStationDetails(stationId);
        return ResponseEntity.ok().body(stationDetails);
    }
}
