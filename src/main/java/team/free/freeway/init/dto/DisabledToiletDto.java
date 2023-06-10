package team.free.freeway.init.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.init.dto.value.DisabledToilet;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisabledToiletDto {

    @JsonAlias(value = "body")
    private List<DisabledToilet> disabledToiletList;
}
