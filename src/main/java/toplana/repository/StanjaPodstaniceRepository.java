package toplana.repository;

import toplana.domain.StanjaPodstanice;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StanjaPodstanice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StanjaPodstaniceRepository extends JpaRepository<StanjaPodstanice, Long> {
	
	List<StanjaPodstanice> findFirst2ByPodstanicaIdOrderByDatumOcitavanjaDesc(Long podstanicaId);
	
	/**
	 * Proverava da li podstanice imaju uneseno stanje za tekuci mesec
	 * Ako rezultat nema sest zapisa, onda za neku podstanicu nije uneseno stanje
	 * @return
	 */
	@Query(value = "select p.broj from stanja_podstanice sp "
			+ "	left join podstanica p on sp.podstanica_id = p.id "
			+ " where date_part('month', datum_ocitavanja) = "
			+ "	(SELECT date_part('month', (SELECT current_timestamp)))"
			+ " and date_part('year', datum_ocitavanja) = "
			+ " (SELECT date_part('year', (SELECT current_timestamp)))", nativeQuery=true)
	List<Integer> checkCurrentMonthRecords();
}
