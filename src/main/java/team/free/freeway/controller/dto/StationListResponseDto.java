package team.free.freeway.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.domain.Elevator;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.value.Coordinate;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationListResponseDto {

    private String stationId;
    private String stationName;
    private String lineId;
    private Coordinate coordinate;
    private String stationStatus;
    private int elevatorsNumber;

    @Builder
    public StationListResponseDto(String stationId, String stationName, String lineId, Coordinate coordinate,
                                  String stationStatus, int elevatorsNumber) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.lineId = lineId;
        this.coordinate = coordinate;
        this.stationStatus = stationStatus;
        this.elevatorsNumber = elevatorsNumber;
    }

    public static StationListResponseDto from(Station station) {
        List<Elevator> elevators = station.getElevators();
        return StationListResponseDto.builder()
                .stationId(station.getId())
                .stationName(station.getName())
                .lineId(station.getSubwayLine().getId())
                .coordinate(station.getCoordinate())
                .stationStatus(station.getStatus().getStatusName())
                .elevatorsNumber(elevators.size())
                .build();
    }
}
