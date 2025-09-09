package toplana.repository;

import toplana.domain.SifraPromene;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SifraPromene entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SifraPromeneRepository extends JpaRepository<SifraPromene, Long> {
	
	List<SifraPromene> findAllByTipPromene(String tip);
	
	SifraPromene findByTipPromeneAndBroj(String tip, int broj);
	
	@Query(value="select sp from SifraPromene sp order by sp.tipPromene desc, broj asc")
	List<SifraPromene> findAllOrderByTipPromeneAndBroj();

	SifraPromene getReferenceById(Long id);
}
