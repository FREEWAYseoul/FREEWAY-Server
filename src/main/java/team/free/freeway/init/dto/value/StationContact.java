package team.free.freeway.init.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationContact {

    @JsonAlias(value = "LINE")
    private String lineName;

    @JsonAlias(value = "STATN_NM")
    private String stationName;

    @JsonAlias(value = "TELNO")
    private String contact;
}
