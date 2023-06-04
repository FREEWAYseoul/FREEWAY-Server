package team.free.freeway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.free.freeway.domain.Facilities;

public interface FacilitiesRepository extends JpaRepository<Facilities, String> {
}
