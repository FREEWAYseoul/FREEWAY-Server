package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.SubwayLine;

public interface SubwayLineRepository extends JpaRepository<SubwayLine, String> {
}
