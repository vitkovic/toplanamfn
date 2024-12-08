package toplana.repository;

import toplana.domain.Parametri;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Parametri entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParametriRepository extends JpaRepository<Parametri, Long> {
}
