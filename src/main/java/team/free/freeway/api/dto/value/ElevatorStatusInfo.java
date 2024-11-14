package team.free.freeway.api.dto.value;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElevatorStatusInfo {

    @JsonAlias(value = "STN_NM")
    private String stationName;

    @JsonAlias(value = "ELVTR_NM")
    private String elevatorName;

    @JsonAlias(value = "OPR_SEC")
    private String elevatorFloor;

    @JsonAlias(value = "INSTL_PSTN")
    private String location;

    @JsonAlias(value = "USE_YN")
    private String status;

    @JsonAlias(value = "ELVTR_SE")
    private String category;

}
