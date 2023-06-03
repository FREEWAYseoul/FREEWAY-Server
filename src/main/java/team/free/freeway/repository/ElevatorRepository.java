package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.Elevator;

public interface ElevatorRepository extends JpaRepository<Elevator, Long> {
}
