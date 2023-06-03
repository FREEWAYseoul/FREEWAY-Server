package team.free.freeway.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ElevatorStatus {

    AVAILABLE("사용가능"),
    REPAIR("보수중"),
    CONSTRUCTION("공사중"),
    UNKNOWN("알 수 없음");

    private final String statusName;
}
