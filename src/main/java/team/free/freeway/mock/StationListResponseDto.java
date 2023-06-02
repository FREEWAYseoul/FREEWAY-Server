package team.free.freeway.mock;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StationListResponseDto {

    private String stationId;
    private String stationName;
    private long lineId;
    private StationStatus stationStatus;
}
