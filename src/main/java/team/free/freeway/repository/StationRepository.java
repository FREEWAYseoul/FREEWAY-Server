package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.free.freeway.domain.Station;
import team.free.freeway.domain.SubwayLine;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, String> {

    List<Station> findByName(String stationName);

    @Query("SELECT DISTINCT s FROM Station s LEFT JOIN FETCH s.elevators WHERE s.name LIKE %:keyword% ORDER BY s.name")
    List<Station> searchByName(@Param("keyword") String keyword);

    Optional<Station> findByNameAndSubwayLine(String stationName, SubwayLine subwayLine);

    @Query("SELECT DISTINCT s FROM Station s LEFT JOIN FETCH s.elevators")
    List<Station> findAllWithElevators();
}
