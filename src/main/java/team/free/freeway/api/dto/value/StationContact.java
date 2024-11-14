package team.free.freeway.api.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationContact {

    @JsonAlias(value = "SBWY_ROUT_LN")
    private String lineName;

    @JsonAlias(value = "SBWY_STNS_NM")
    private String stationName;

    @JsonAlias(value = "TELNO")
    private String contact;
}
