package team.free.freeway.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.api.dto.value.StationForeignRow;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationForeignDto {

    @JsonAlias(value = "SearchInfoBySubwayNameService")
    private StationForeignRow stationForeignRow;
}
