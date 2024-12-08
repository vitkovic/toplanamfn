package toplana.repository;

import toplana.domain.UgovorRate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UgovorRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UgovorRateRepository extends JpaRepository<UgovorRate, Long> {
}
