package team.free.freeway.api.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationForeign {

    @JsonAlias(value = "STATION_NM")
    private String stationName;

    @JsonAlias(value = "LINE_NUM")
    private String lineName;

    @JsonAlias(value = "FR_CODE")
    private String stationForeignId;
}
