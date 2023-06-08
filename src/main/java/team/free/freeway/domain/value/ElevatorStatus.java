package team.free.freeway.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ElevatorStatus {

    AVAILABLE("사용 가능"),
    REPAIR("보수 중"),
    CONSTRUCTION("공사 중"),
    UNKNOWN("확인 불가");

    private final String statusName;

    public static ElevatorStatus of(String status) {
        switch (status) {
            case "사용가능":
            case "사용 가능": {
                return AVAILABLE;
            }
            case "보수중":
            case "보수 중": {
                return REPAIR;
            }
            case "공사중":
            case "공사 중": {
                return CONSTRUCTION;
            }
            default: {
                return UNKNOWN;
            }
        }
    }
}