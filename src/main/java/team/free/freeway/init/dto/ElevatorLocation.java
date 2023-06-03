package team.free.freeway.init.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.domain.value.Coordinate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElevatorLocation {

    @JsonAlias(value = "NODE_WKT")
    private String wellKnowText;

    @JsonAlias(value = "SW_NM")
    private String stationName;

    public Coordinate extractCoordinate() {
        String coordinatesString = wellKnowText.substring(wellKnowText.indexOf("(") + 1, wellKnowText.indexOf(")"));
        String[] coordinates = coordinatesString.split(" ");

        return new Coordinate(coordinates[1], coordinates[0]);
    }
}