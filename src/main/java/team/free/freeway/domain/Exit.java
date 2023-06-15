package team.free.freeway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.domain.value.Coordinate;
import team.free.freeway.api.dto.value.Location;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "exits")
@Entity
public class Exit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exit_id")
    private Long id;

    @Column(name = "exit_number")
    private String exitNumber;

    @Embedded
    private Coordinate coordinate;

    @Builder
    public Exit(String exitNumber, Coordinate coordinate) {
        this.exitNumber = exitNumber;
        this.coordinate = coordinate;
    }

    public static Exit from(Location location) {
        String exitNumber = location.getName().split(" ")[2].split("번출구")[0];
        Coordinate coordinate = new Coordinate(location.getLatitude(), location.getLongitude());

        return Exit.builder()
                .exitNumber(exitNumber)
                .coordinate(coordinate)
                .build();
    }
}
