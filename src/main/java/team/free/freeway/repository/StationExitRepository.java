package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.Exit;
import team.free.freeway.domain.value.Coordinate;

import java.util.Optional;

public interface StationExitRepository extends JpaRepository<Exit, Long> {

    Optional<Exit> findByCoordinate(Coordinate coordinate);
}
