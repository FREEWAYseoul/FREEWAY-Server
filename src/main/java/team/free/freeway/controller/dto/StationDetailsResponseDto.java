package team.free.freeway.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.domain.value.Coordinate;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationDetailsResponseDto {

    private String stationId;
    private String stationName;
    private String lineId;
    private String lineName;
    private Coordinate stationCoordinate;
    private String stationStatus;
    private String stationContact;
    private String stationImageUrl;
    private List<ElevatorListResponseDto> elevators;
}
