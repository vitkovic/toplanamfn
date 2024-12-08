package toplana.repository;

import toplana.domain.Cene;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Cene entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CeneRepository extends JpaRepository<Cene, Long> {
	
	Cene findFirstByOrderByKwhAsc();
}
