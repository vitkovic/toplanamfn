package toplana.repository;

import toplana.domain.TipPotrosaca;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipPotrosaca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipPotrosacaRepository extends JpaRepository<TipPotrosaca, Long> {
}
