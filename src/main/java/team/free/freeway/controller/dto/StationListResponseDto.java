package team.free.freeway.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.domain.Elevator;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.value.Coordinate;
import team.free.freeway.domain.value.ElevatorStatus;
import team.free.freeway.domain.value.StationStatus;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationListResponseDto {

    private Long stationId;
    private String stationName;
    private String lineId;
    private Coordinate coordinate;
    private String stationStatus;
    private int availableElevatorsNumber;

    @Builder
    public StationListResponseDto(Long stationId, String stationName, String lineId, Coordinate coordinate,
                                  String stationStatus, int availableElevatorsNumber) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.lineId = lineId;
        this.coordinate = coordinate;
        this.stationStatus = stationStatus;
        this.availableElevatorsNumber = availableElevatorsNumber;
    }

    public static StationListResponseDto from(Station station) {
        List<Elevator> elevators = station.getElevators();
        int availableElevatorsNumber = 0;
        for (Elevator elevator : elevators) {
            if (elevator.getStatus() == ElevatorStatus.AVAILABLE) {
                availableElevatorsNumber++;
            }
        }

        if (availableElevatorsNumber == 0 && station.getStatus() == StationStatus.UNKNOWN) {
            availableElevatorsNumber = -1;
        }

        return StationListResponseDto.builder()
                .stationId(station.getId())
                .stationName(station.getName())
                .lineId(station.getSubwayLine().getId())
                .coordinate(station.getCoordinate())
                .stationStatus(station.getStatus().getStatusName())
                .availableElevatorsNumber(availableElevatorsNumber)
                .build();
    }
}
