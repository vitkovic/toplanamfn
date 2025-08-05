package toplana.repository;

import toplana.domain.OstaliRacuni;
import toplana.domain.Racun;
import toplana.domain.Stan;
import toplana.domain.Transakcija;
import toplana.specifications.TransakcijaSpecification;
import toplana.web.rest.dto.DugujePotrazujeDTO;
import toplana.web.rest.dto.DugujePotrazujeReoni;
import toplana.web.rest.dto.RekapitulacijaSifraPromeneDatumDTO;
import toplana.web.rest.dto.TransakcijaStanUkupnoDTO;
import toplana.web.rest.dto.TransakcijaZaStanDTO;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transakcija entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransakcijaRepository extends JpaRepository<Transakcija, Long>, JpaSpecificationExecutor<Transakcija> {
	
	@Query(value = "coalesce(sum(duguje),0)- coalesce(sum(potrazuje), 0)"
			+ "	from transakcija t "
			+ "	where t.datum <= (SELECT (date_trunc('month', now()) - interval '1 day')) "
			+ "	and stan_id = :stanId", nativeQuery=true)
	BigDecimal getSaldoDoKrajaPrethodnogMesecaZaStan(@Param("stanId") Long stanId);
	
	@Query(value = "select coalesce(sum(duguje),0)- coalesce(sum(potrazuje), 0)"
			+ "	from transakcija t "
			+ "	where t.datum <= (SELECT (date_trunc('month', Date( ?1 )) - interval '1 day')) "
			+ "	and stan_id = ?2 ", nativeQuery=true)
	BigDecimal getSaldoDoKrajaPrethodnogMesecaZaStanAndValuta(LocalDate valuta, Long stanId);
	
	@Query(value = "SELECT (date_trunc('month', now()) - interval '1 day') ", nativeQuery=true)
	LocalDate getPoslednjiDanPrethodnogMeseca();

	@Query(nativeQuery = true)
	DugujePotrazujeDTO getDugujePotrazujeZaStan(@Param("stanId") Long stanId);
	
	@Query(nativeQuery = true)
	DugujePotrazujeDTO getDugujePotrazujeZaDodatniRacun(@Param("ostaliRacuniId") Long ostaliRacuniId);

	
	@Query(nativeQuery = true)
	List<TransakcijaStanUkupnoDTO> pronadjiZaStan(TransakcijaSpecification spec);
	
	@Query(nativeQuery = true) 
	List<TransakcijaStanUkupnoDTO> search(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo,
			@Param("sifraNotExists") Integer sifraNotExists,  @Param("sifra") String sifra,
			@Param("prezimeNotExists") Integer prezimeNotExists,@Param("prezime") String prezime,
			@Param("imeNotExists") Integer imeNotExists,@Param("ime") String ime,
			@Param("podstanicaNotExists") Integer podstanicaNotExists, @Param("podstanicaId") Long podstanicaId,
			@Param("tipPotrosacaNotExists") Integer tipPotrosacaNotExists,@Param("tipPotrosacaIds") List<Long> tipPotrosacaIds,
			@Param("stanUkljucen") Boolean stanUkljucen
			);
	
	@Query(nativeQuery = true) 
	List<TransakcijaStanUkupnoDTO> searchOR(@Param("sifra") String sifra);
	
	@Query(nativeQuery = true)
	List<TransakcijaZaStanDTO> searchForAnalitickiDnevnik(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo,
			@Param("sifraNotExists") Integer sifraNotExists,  @Param("sifra") String sifra,
			@Param("prezimeNotExists") Integer prezimeNotExists,@Param("prezime") String prezime,
			@Param("imeNotExists") Integer imeNotExists,@Param("ime") String ime,
			@Param("podstanicaNotExists") Integer podstanicaNotExists, @Param("podstanicaId") Long podstanicaId,
			@Param("tipPotrosacaNotExists") Integer tipPotrosacaNotExists,@Param("tipPotrosacaIds") List<Long> tipPotrosacaIds);
	
	
	
	
	@Query(nativeQuery = true)
	List<DugujePotrazujeReoni> findSumForDnevnik(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo,
			@Param("sifraNotExists") Integer sifraNotExists,  @Param("sifra") String sifra,
			@Param("prezimeNotExists") Integer prezimeNotExists,@Param("prezime") String prezime,
			@Param("imeNotExists") Integer imeNotExists,@Param("ime") String ime,
			@Param("podstanicaNotExists") Integer podstanicaNotExists, @Param("podstanicaId") Long podstanicaId,
			@Param("tipPotrosacaNotExists") Integer tipPotrosacaNotExists,@Param("tipPotrosacaIds") List<Long> tipPotrosacaIds);
	
	List<Transakcija> findAllByStanOrderByDatum(Stan s);
	
	List<Transakcija> findAllByOstaliRacuniOrderByDatum(OstaliRacuni or);
	
	@Query(nativeQuery = true)
	List<RekapitulacijaSifraPromeneDatumDTO> rekapitulacijaSifraPromeneDatum(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo);
	
	@Query(nativeQuery = true)
	List<RekapitulacijaSifraPromeneDatumDTO> sintetickiDnevnik(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo);
	
	
}
