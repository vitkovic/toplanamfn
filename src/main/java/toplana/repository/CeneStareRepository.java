package toplana.repository;

import toplana.domain.CeneStare;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CeneStare entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CeneStareRepository extends JpaRepository<CeneStare, Long> {
}
