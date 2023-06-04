package team.free.freeway.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "subway_line")
@Entity
public class SubwayLine {

    @Id
    @Column(name = "line_id")
    private String id;

    @Column(name = "line_name")
    private String lineName;

    @OneToMany(mappedBy = "subwayLine")
    private List<Station> stations;
}
