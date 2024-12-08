package toplana.repository;

import toplana.domain.Racun;
import toplana.domain.Stan;
import toplana.web.rest.dto.DugujePotrazujeDTO;
import toplana.web.rest.dto.RekapitulacijaPoPdvDelimicnaDTO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Racun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacunRepository extends JpaRepository<Racun, Long>, JpaSpecificationExecutor<Racun> {
	
	void deleteByNacrtRacunaId(Long nacrtRacunaId);
	
	List<Racun> findByAzuriranAndNacrtRacunaId(Boolean azuriran, Long nacrtRacunaId);
	
	List<Racun> findAllByNacrtRacunaId(Long nacrtRacunaId);
	
	@Query(value="SELECT r FROM Racun r join r.stan s join s.vlasnik v"
			+ " WHERE r.datumRacuna BETWEEN :startDate AND :endDate"
			+ " and v.brojRacuna = :brojRacuna "
			+ " order by s.sifra")
	List<Racun> findAllForMonthAndBrojRacuna(@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate,
			@Param("brojRacuna") String brojRacuna);
	
	
	@Query(nativeQuery = true)
	RekapitulacijaPoPdvDelimicnaDTO rekapitulacijaPoPdv(@Param("nacrtRacunaId") Long nacrtRacunaId);	
}
