package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.Station;

public interface StationRepository extends JpaRepository<Station, String> {
}
