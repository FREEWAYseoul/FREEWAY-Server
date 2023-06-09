package team.free.freeway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import team.free.freeway.domain.value.Coordinate;
import team.free.freeway.domain.value.StationStatus;
import team.free.freeway.init.dto.value.Location;

import javax.persistence.*;
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

    @Column(name = "station_image_url")
    private String imageUrl;

    @Column(name = "foreign_id")
    private String foreignId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "line_id")
    private SubwayLine subwayLine;

    @ManyToMany
    @JoinTable(name = "station_exits",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "exit_id"))
    private List<Exit> exits;

    @ManyToMany
    @JoinTable(name = "station_elevator",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "elevator_id"))
    private List<Elevator> elevators;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "facilities_id")
    private Facilities facilities;

    @OneToOne
    @JoinColumn(name = "next_station")
    private Station nextStation;

    @OneToOne
    @JoinColumn(name = "previous_station")
    private Station previousStation;

    @OneToOne
    @JoinColumn(name = "branch_station")
    private Station branchStation;

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

    public void addElevator(Elevator elevator) {
        this.elevators.add(elevator);
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateForeignId(String foreignId) {
        this.foreignId = foreignId;
    }

    public void linkNextStation(Station nextStation) {
        this.nextStation = nextStation;
        nextStation.linkPreviousStation(this);
    }

    public void linkBranchStation(Station branchStation) {
        this.branchStation = branchStation;
        branchStation.linkPreviousStation(this);
    }

    public void linkPreviousStation(Station previousStation) {
        this.previousStation = previousStation;
    }

    public void updateStatus(StationStatus stationStatus) {
        this.status = stationStatus;
    }

    public void updateFacilities(Facilities facilities) {
        this.facilities = facilities;
    }
}
