package toplana.repository;

import toplana.domain.StavkeUtuzenja;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StavkeUtuzenja entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StavkeUtuzenjaRepository extends JpaRepository<StavkeUtuzenja, Long> {
}
