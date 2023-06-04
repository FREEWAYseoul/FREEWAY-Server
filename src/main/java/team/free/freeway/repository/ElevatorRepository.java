package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.Elevator;
import team.free.freeway.domain.value.Coordinate;

import java.util.Optional;

public interface ElevatorRepository extends JpaRepository<Elevator, Long> {

    Optional<Elevator> findByCoordinate(Coordinate coordinate);
}
