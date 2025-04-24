package toplana.repository;

import toplana.domain.StanStanje;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StanStanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StanStanjeRepository extends JpaRepository<StanStanje, Long> {
}
