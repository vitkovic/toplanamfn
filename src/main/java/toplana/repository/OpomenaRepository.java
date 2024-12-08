package toplana.repository;

import toplana.domain.Opomena;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Opomena entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpomenaRepository extends JpaRepository<Opomena, Long> {
}
