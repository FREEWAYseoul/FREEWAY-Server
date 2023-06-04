package team.free.freeway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import team.free.freeway.domain.value.Coordinate;
import team.free.freeway.domain.value.StationStatus;
import team.free.freeway.init.dto.value.Location;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

import static team.free.freeway.init.constant.StationExcelIndex.OPERATING_INSTITUTION_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.STATION_ID_INDEX;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "station", uniqueConstraints = @UniqueConstraint(
        columnNames = {"latitude", "longitude"}
))
@Entity
public class Station {

    @Id
    @Column(name = "station_id")
    private String id;

    @Column(name = "station_name")
    private String name;

    @Embedded
    private Coordinate coordinate;

    @Enumerated(EnumType.STRING)
    @Column(name = "station_status")
    private StationStatus status;

    @Column(name = "operating_institution")
    private String operatingInstitution;

    @Column(name = "station_address")
    private String address;

    @Column(name = "station_contact")
    private String contact;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_id")
    private SubwayLine subwayLine;

    @ManyToMany
    @JoinTable(name = "station_exits",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "exit_id"))
    private List<Exit> exits;

    @OneToMany(mappedBy = "station")
    private List<Elevator> elevators;

    @Builder
    protected Station(String id, String name, Coordinate coordinate, String operatingInstitution, String address) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.operatingInstitution = operatingInstitution;
        this.address = address;
    }

    public static Station of(String stationName, Row row, Location location) {
        String stationId = row.getCell(STATION_ID_INDEX).toString();
        String operatingInstitution = row.getCell(OPERATING_INSTITUTION_INDEX).toString();
        return Station.builder()
                .id(stationId)
                .name(stationName)
                .coordinate(new Coordinate(location.getLatitude(), location.getLongitude()))
                .operatingInstitution(operatingInstitution)
                .address(location.getAddress())
                .build();
    }

    public void updateSubwayLine(SubwayLine subwayLine) {
        this.subwayLine = subwayLine;
        subwayLine.getStations().add(this);
    }

    public void updateContact(String contact) {
        this.contact = contact;
    }

    public void addExit(Exit exit) {
        this.exits.add(exit);
    }
}
