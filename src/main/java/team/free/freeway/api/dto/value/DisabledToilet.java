package team.free.freeway.api.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisabledToilet {

    @JsonAlias(value = "railOprIsttCd")
    private String operatingInstitution;

    @JsonAlias(value = "stinCd")
    private String stationId;

    @JsonAlias(value = "lnCd")
    private String lineId;
}
