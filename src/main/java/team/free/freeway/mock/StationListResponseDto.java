package team.free.freeway.mock;

import lombok.Builder;
import lombok.Getter;
import team.free.freeway.domain.value.StationStatus;

@Getter
public class StationListResponseDto {

    private final String stationId;
    private final String stationName;
    private final long lineId;
    private final StationStatus stationStatus;

    @Builder
    public StationListResponseDto(String stationId, String stationName, long lineId, StationStatus stationStatus) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.lineId = lineId;
        this.stationStatus = stationStatus;
    }
}
