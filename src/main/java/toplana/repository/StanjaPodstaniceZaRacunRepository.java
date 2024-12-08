package toplana.repository;

import toplana.domain.StanjaPodstaniceZaRacun;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StanjaPodstaniceZaRacun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StanjaPodstaniceZaRacunRepository extends JpaRepository<StanjaPodstaniceZaRacun, Long> {
	
	List<StanjaPodstaniceZaRacun> findAllByNacrtRacunaId(Long nacrtRacunaId);
	
	@Modifying(flushAutomatically = true)
	void deleteAllByNacrtRacunaId(Long racunId);
}
