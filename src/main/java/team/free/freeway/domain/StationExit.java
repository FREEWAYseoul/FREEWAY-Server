package team.free.freeway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.init.dto.Location;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "station_exit")
@Entity
public class StationExit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exit_id")
    private Long id;

    @Column(name = "exit_number")
    private String exitNumber;

    @Embedded
    private Coordinate coordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @Builder
    public StationExit(String exitNumber, Coordinate coordinate) {
        this.exitNumber = exitNumber;
        this.coordinate = coordinate;
    }

    public static StationExit from(Location location) {
        String exitNumber = location.getName().split(" ")[2].split("번출구")[0];
        Coordinate coordinate = new Coordinate(location.getLatitude(), location.getLongitude());

        return StationExit.builder()
                .exitNumber(exitNumber)
                .coordinate(coordinate)
                .build();
    }

    public void setStation(Station station) {
        this.station = station;
        station.getExits().add(this);
    }
}
