package team.free.freeway.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StationStatus {

    AVAILABLE("사용 가능"),
    SOME_AVAILABLE("일부 가능"),
    UNAVAILABLE("사용 불가능");

    @JsonValue
    private final String statusName;
}
