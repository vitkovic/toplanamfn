package toplana.repository;

import toplana.domain.Utuzenje;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Utuzenje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UtuzenjeRepository extends JpaRepository<Utuzenje, Long> {
}
