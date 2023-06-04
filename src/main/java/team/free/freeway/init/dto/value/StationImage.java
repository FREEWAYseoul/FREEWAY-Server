package team.free.freeway.init.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationImage {

    @JsonAlias(value = "STN_NM")
    private String stationName;

    @JsonAlias(value = "STN_LINE")
    private String lineId;

    @JsonAlias(value = "STN_IMG_URL")
    private String stationImageUrl;
}
