package toplana.repository;

import toplana.domain.NacrtRacuna;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NacrtRacuna entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NacrtRacunaRepository extends JpaRepository<NacrtRacuna, Long> {
	
	@Query(value="Select n from NacrtRacuna n left join fetch n.stanjaPodstaniceZaRacune")
	Optional<NacrtRacuna> getOneWithStanja(@Param("nacrtId") Long nacrtId);
	
	@Query(value = "select n.id from nacrt_racuna n "
			+ " where date_part('month', datum_racuna) = "
			+ "	(SELECT date_part('month', (SELECT current_timestamp)))"
			+ " and date_part('year', datum_racuna) = "
			+ " (SELECT date_part('year', (SELECT current_timestamp)))", nativeQuery=true)
	List<Long> getAllOfCurrentMonth();
	
	@Query(value = "select n.id from nacrt_racuna n "
			+ "	 where date_part('month', datum_racuna) = :month"
			+ "	 and date_part('year', datum_racuna) = :year", nativeQuery=true)
	List<Long> getAllForGivenMonthAndYear(@Param("month") Integer month, @Param("year") Integer year);

	
	//@Query("select n from NacrtRacuna n where year(n.datumRacuna) = year(current_date()) and  month(n.datumRacuna) = month(current_date())")
	//List<NacrtRacuna> getAllOfCurrentMonth();
	
	
}
