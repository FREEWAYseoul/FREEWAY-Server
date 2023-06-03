package team.free.freeway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.free.freeway.domain.value.Coordinate;
import team.free.freeway.domain.value.ElevatorStatus;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "elevator")
@Entity
public class Elevator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "elevator_id")
    private Long id;

    @Embedded
    private Coordinate coordinate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "elevator_status")
    private ElevatorStatus status;

    @Column(name = "description")
    private String description;

    @Column(name = "nearest_exit")
    private String nearestExit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @Builder
    public Elevator(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void updateStation(Station station) {
        this.station = station;
        station.getElevators().add(this);
    }
}
