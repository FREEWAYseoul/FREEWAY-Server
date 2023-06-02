package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.Station;

import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, String> {

    Optional<Station> findByName(String stationName);
}
