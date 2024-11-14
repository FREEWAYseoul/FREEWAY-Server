package team.free.freeway.api.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationImage {

    @JsonAlias(value = "STTN")
    private String stationName;

    @JsonAlias(value = "SBWY_ROUT_LN")
    private String lineId;

    @JsonAlias(value = "IMG_LINK")
    private String stationImageUrl;
}
