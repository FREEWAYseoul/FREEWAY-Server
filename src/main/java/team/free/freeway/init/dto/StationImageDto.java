package team.free.freeway.init.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.init.dto.value.StationImageRow;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationImageDto {

    @JsonAlias(value = "SmrtEmergerncyGuideImg")
    private StationImageRow stationImageRow;
}
