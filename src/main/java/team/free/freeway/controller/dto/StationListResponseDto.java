package team.free.freeway.controller.dto;

import lombok.Builder;
import lombok.Getter;
import team.free.freeway.domain.Station;

@Getter
public class StationListResponseDto {

    private final String stationId;
    private final String stationName;
    private final String lineId;
    private final String stationStatus;

    @Builder
    public StationListResponseDto(String stationId, String stationName, String lineId, String stationStatus) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.lineId = lineId;
        this.stationStatus = stationStatus;
    }

    public static StationListResponseDto from(Station station) {
        return StationListResponseDto.builder()
                .stationId(station.getId())
                .stationName(station.getName())
                .lineId(station.getSubwayLine().getId())
                .stationStatus(station.getStatus().getStatusName())
                .build();
    }
}
