package team.free.freeway.init.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.init.dto.value.ElevatorLocationRow;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElevatorLocationDto {

    @JsonAlias(value = "tbTraficElvtr")
    private ElevatorLocationRow elevatorLocationRow;
}
