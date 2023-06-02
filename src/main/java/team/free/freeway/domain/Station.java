package team.free.freeway.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import team.free.freeway.init.dto.Location;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;

import static team.free.freeway.init.constant.StationExcelIndex.LINE_ID_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.LINE_NAME_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.OPERATING_INSTITUTION_INDEX;
import static team.free.freeway.init.constant.StationExcelIndex.STATION_ID_INDEX;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "station")
@Entity
public class Station {

    @Id
    @Column(name = "station_id")
    private String id;

    @Column(name = "station_name", unique = true)
    private String name;

    @Embedded
    private Coordinate coordinate;

    @Column(name = "line_id")
    private String lineId;

    @Column(name = "line_name")
    private String lineName;

    @Enumerated(EnumType.STRING)
    @Column(name = "station_status")
    private StationStatus status;

    @Column(name = "operating_institution")
    private String operatingInstitution;

    @Column(name = "station_address")
    private String address;

    @Column(name = "station_contact")
    private String contact;

    @OneToMany(mappedBy = "station")
    private List<StationExit> exits;

    @Builder
    protected Station(String id, String name, Coordinate coordinate, String lineId, String lineName,
                      String operatingInstitution, String address) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.lineId = lineId;
        this.lineName = lineName;
        this.operatingInstitution = operatingInstitution;
        this.address = address;
    }

    public static Station of(String stationName, Row row, Location location) {
        String stationId = row.getCell(STATION_ID_INDEX).toString();
        String lineId = row.getCell(LINE_ID_INDEX).toString();
        String lineName = row.getCell(LINE_NAME_INDEX).toString();
        String operatingInstitution = row.getCell(OPERATING_INSTITUTION_INDEX).toString();
        return Station.builder()
                .id(stationId)
                .name(stationName)
                .lineId(lineId)
                .lineName(lineName)
                .coordinate(new Coordinate(location.getLatitude(), location.getLongitude()))
                .operatingInstitution(operatingInstitution)
                .address(location.getAddress())
                .build();
    }

    public void updateContact(String contact) {
        this.contact = contact;
    }
}
