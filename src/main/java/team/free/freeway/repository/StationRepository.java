package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.free.freeway.domain.Station;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, String> {

    Optional<Station> findByName(String stationName);

    @Query("SELECT s FROM Station s WHERE s.name LIKE %:keyword%")
    List<Station> searchByName(@Param("keyword") String keyword);
}
