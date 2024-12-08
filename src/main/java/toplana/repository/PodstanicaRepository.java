package toplana.repository;

import toplana.domain.Podstanica;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Podstanica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PodstanicaRepository extends JpaRepository<Podstanica, Long> {
	
	List<Podstanica> findAllByOrderByBroj();
	
	@Query(value="Select sum(s.povrsina) from Stan s where s.podstanica.id = :podstanicaId and s.ukljucen = true")
	BigDecimal findUkupnaPovrsina(@Param("podstanicaId") Long podstanicaId);
	
}
