package team.free.freeway.init.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.init.dto.value.StationContactRow;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationContactDto {

    @JsonAlias(value = "StationAdresTelno")
    private StationContactRow stationContactRow;
}
