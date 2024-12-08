package toplana.repository;

import toplana.domain.ParametriIstorija;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ParametriIstorija entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametriIstorijaRepository extends JpaRepository<ParametriIstorija, Long> {
}
