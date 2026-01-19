package toplana.repository;

import toplana.domain.Racun;
import toplana.domain.Stan;
import toplana.web.rest.dto.DugujePotrazujeDTO;
import toplana.web.rest.dto.RacunDTO;
import toplana.web.rest.dto.RekapitulacijaPoPdvDelimicnaDTO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
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
	
	List<Racun> findAllByNacrtRacunaId(Long nacrtRacunaId, Sort sort);
	
	
	@Query(value =
		       "select r.* " +
		       "from racun r " +
		       "join stan s on s.id = r.stan_id " +
		       "where r.nacrt_racuna_id = :id " +
		       "order by cast(s.sifra as integer)",
		       nativeQuery = true)
		List<Racun> findAllByNacrtRacunaIdOrderByStanSifraNumNative(@Param("id") Long id);
	
	
	@Query(value = 
		    "(SELECT r.* FROM racun r " +
		    "JOIN stan s ON r.stan_id = s.id " +
		    "WHERE s.id < :Idr and r.datum_racuna = :Dt ORDER BY s.id DESC LIMIT 1 )  " +
		    "UNION ALL " +
		    "(SELECT r.* FROM racun r " +
		    "JOIN stan s ON r.stan_id = s.id " +
		    "WHERE s.id > :Idr and r.datum_racuna = :Dt ORDER BY s.id ASC LIMIT 1 ) ",
		    nativeQuery = true)
		List<Racun> getPreviousAndNextById(@Param("Idr") Long Idr, @Param("Dt") LocalDate dt);

	
	
	@Query(value="SELECT r FROM Racun r join r.stan s join s.vlasnik v"
			+ " WHERE r.datumRacuna BETWEEN :startDate AND :endDate"
			+ " and v.brojRacuna = :brojRacuna "
			+ " order by s.sifra")
	List<Racun> findAllForMonthAndBrojRacuna(@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate,
			@Param("brojRacuna") String brojRacuna);
	
	
	@Query(
		    value =
		        "SELECT " +
		        "  s.id AS stan_id, s.broj, " +
		        "  s.sifra, v.ime, v.prezime, v.partija_racuna AS partija, " +
		        "  COALESCE(SUM(t.duguje),0) - COALESCE(SUM(t.potrazuje),0) AS saldo " +
		        "FROM stan s " +
		        "JOIN vlasnik v ON v.id = s.vlasnik_id " +
		        "LEFT JOIN transakcija t " +
		        "  ON t.stan_id = s.id " +
		        "  AND t.datum <= :krajnjiDatum " +
		        "WHERE v.broj_racuna = :brojRacuna " +
		        "GROUP BY s.id, s.broj, s.sifra, v.ime, v.prezime, v.partija_racuna " +
		        "ORDER BY " +
		        "  NULLIF(regexp_replace(s.sifra, '\\\\D', '', 'g'), '')::bigint, " +
		        "  s.sifra",
		    nativeQuery = true
	)
		List<Object[]> findStanSaldoRaw(
		    @Param("krajnjiDatum") java.time.LocalDate krajnjiDatum,
		    @Param("brojRacuna") String brojRacuna
		);
	
	
	@Query(nativeQuery = true)
	RekapitulacijaPoPdvDelimicnaDTO rekapitulacijaPoPdv(@Param("nacrtRacunaId") Long nacrtRacunaId);	
}
