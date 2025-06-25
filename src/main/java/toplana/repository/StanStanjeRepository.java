package toplana.repository;

import toplana.domain.StanStanje;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StanStanje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StanStanjeRepository extends JpaRepository<StanStanje, Long> {
	
	@Query(value = "select sstanje.vrednost, sstanje.datum from stan s, stan_stanje sstanje "
			+ "	 where s.id = sstanje.stan_id and sstanje.stan_id = stanid"
			+ "	ORDER BY Datum DESC LIMIT 2 ", nativeQuery=true)
	List<Double> getLastStatesForStan(@Param("stanid") Long stanid);
	
	
	
}
