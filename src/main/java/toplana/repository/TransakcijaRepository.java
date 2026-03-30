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
import toplana.web.rest.dto.RacunDTO;

import java.math.BigDecimal;
import java.math.BigInteger;
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
	
	
	@Query("select coalesce(sum(t.duguje), 0) - coalesce(sum(t.potrazuje), 0) " +
		       "from Transakcija t " +
		       "where t.stan.id = :stanId and t.datum < :datum")
		BigDecimal getSaldoPreDatuma(@Param("stanId") Long stanId, @Param("datum") LocalDate datum);
	
	List<Transakcija> findAllByStanAndDatumBetweenOrderByDatum(Stan stan, LocalDate datumOd, LocalDate datumDo);
	
	@Query("select coalesce(sum(t.duguje), 0) - coalesce(sum(t.potrazuje), 0) " +
		       "from Transakcija t " +
		       "where t.ostaliRacuni.id = :ostaliRacuniId and t.datum < :datum")
		BigDecimal getSaldoPreDatumaZaOstaleRacune(@Param("ostaliRacuniId") Long ostaliRacuniId,
		                                           @Param("datum") LocalDate datum);
	List<Transakcija> findAllByOstaliRacuniAndDatumBetweenOrderByDatum(OstaliRacuni ostaliRacuni,
            LocalDate datumOd,
            LocalDate datumDo);
	// opis
	/*
	@Query(value =
		    "select coalesce(sum(t.duguje), 0) - coalesce(sum(t.potrazuje), 0) " +
		    "from transakcija t " +
		    "where t.stan_id = :stanId " +
		    "and t.datum < :datum " +
		    "and coalesce(t.opis, '') ILIKE '%' || :opis || '%'",
		    nativeQuery = true)
		BigDecimal getSaldoPreDatumaZaStanIOpis(@Param("stanId") Long stanId,
		                                        @Param("datum") LocalDate datum,
		                                        @Param("opis") String opis);
	
	*/
	
	@Query(value =
		    "select coalesce(sum(t.duguje), 0) - coalesce(sum(t.potrazuje), 0) " +
		    "from transakcija t " +
		    "where t.stan_id = :stanId " +
		    "and t.datum < :datum " +
		    "and sr_lat(coalesce(t.opis, '')) like '%' || sr_lat(coalesce(:opis, '')) || '%'",
		    nativeQuery = true)
		BigDecimal getSaldoPreDatumaZaStanIOpis(@Param("stanId") Long stanId,
		                                        @Param("datum") LocalDate datum,
		                                        @Param("opis") String opis);
	
	
		@Query(value =
		    "select * from transakcija t " +
		    "where t.stan_id = :#{#stan.id} " +
		    "and sr_lat(coalesce(t.opis, '')) like '%' || sr_lat(coalesce(:opis, '')) || '%' " +
		    "order by t.datum, t.id",
		    nativeQuery = true)
		List<Transakcija> findAllByStanAndOpisContainingIgnoreCaseOrderByDatum(@Param("stan") Stan stan,
		                                                                       @Param("opis") String opis);
		@Query(value =
		    "select * from transakcija t " +
		    "where t.stan_id = :#{#stan.id} " +
		    "and t.datum >= :datumOd " +
		    "and t.datum <= :datumDo " +
		    "and sr_lat(coalesce(t.opis, '')) like '%' || sr_lat(coalesce(:opis, '')) || '%' " +
		    "order by t.datum, t.id",
		    nativeQuery = true)
		List<Transakcija> findAllByStanAndDatumBetweenAndOpisContainingIgnoreCaseOrderByDatum(
		        @Param("stan") Stan stan,
		        @Param("datumOd") LocalDate datumOd,
		        @Param("datumDo") LocalDate datumDo,
		        @Param("opis") String opis);
	
		@Query(value =
			    "select coalesce(sum(t.duguje), 0) - coalesce(sum(t.potrazuje), 0) " +
			    "from transakcija t " +
			    "where t.ostali_racuni_id = :ostaliRacuniId " +
			    "and t.datum < :datum " +
			    "and coalesce(t.opis, '') ILIKE '%' || :opis || '%'",
			    nativeQuery = true)
			BigDecimal getSaldoPreDatumaZaOstaleRacuneIOpis(@Param("ostaliRacuniId") Long ostaliRacuniId,
			                                                @Param("datum") LocalDate datum,
			                                                @Param("opis") String opis);


			@Query(value =
			    "select * from transakcija t " +
			    "where t.ostali_racuni_id = :#{#ostaliRacuni.id} " +
			    "and sr_lat(coalesce(t.opis, '')) like '%' || sr_lat(coalesce(:opis, '')) || '%' " +
			    "order by t.datum, t.id",
			    nativeQuery = true)
			List<Transakcija> findAllByOstaliRacuniAndOpisContainingIgnoreCaseOrderByDatum(
			        @Param("ostaliRacuni") OstaliRacuni ostaliRacuni,
			        @Param("opis") String opis);


			@Query(value =
			    "select * from transakcija t " +
			    "where t.ostali_racuni_id = :#{#ostaliRacuni.id} " +
			    "and t.datum >= :datumOd " +
			    "and t.datum <= :datumDo " +
			    "and sr_lat(coalesce(t.opis, '')) like '%' || sr_lat(coalesce(:opis, '')) || '%' " +
			    "order by t.datum, t.id",
			    nativeQuery = true)
			List<Transakcija> findAllByOstaliRacuniAndDatumBetweenAndOpisContainingIgnoreCaseOrderByDatum(
			        @Param("ostaliRacuni") OstaliRacuni ostaliRacuni,
			        @Param("datumOd") LocalDate datumOd,
			        @Param("datumDo") LocalDate datumDo,
			        @Param("opis") String opis);
	
	
	// do opis
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
	List<TransakcijaStanUkupnoDTO> searchSpec(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo,
			@Param("prezimeNotExists") Integer prezimeNotExists,@Param("prezime") String prezime,
			@Param("imeNotExists") Integer imeNotExists,@Param("ime") String ime,
			@Param("podstanicaNotExists") Integer podstanicaNotExists, @Param("podstanicaId") Long podstanicaId,
			@Param("tipPotrosacaNotExists") Integer tipPotrosacaNotExists,@Param("tipPotrosacaIds") List<Long> tipPotrosacaIds,
			@Param("stanUkljucen") Boolean stanUkljucen,
			@Param("sifraOd") String sifraOd,
			@Param("sifraDo") String sifraDo
			);
	
	
	
	
	@Query(nativeQuery = true) 
	List<TransakcijaStanUkupnoDTO> searchSUM(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
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
			@Param("podstanicaNotExists") Integer podstanicaNotExists, @Param("podstanicaId") Long podstanicaId,
			@Param("tipPotrosacaNotExists") Integer tipPotrosacaNotExists,@Param("tipPotrosacaIds") List<Long> tipPotrosacaIds);
			
	
	@Query(nativeQuery = true)
	List<DugujePotrazujeReoni> findSumForDnevnik(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo,
			@Param("sifraNotExists") Integer sifraNotExists,  @Param("sifra") String sifra,
			@Param("prezimeNotExists") Integer prezimeNotExists,@Param("prezime") String prezime,
		
			@Param("podstanicaNotExists") Integer podstanicaNotExists, @Param("podstanicaId") Long podstanicaId,
			@Param("tipPotrosacaNotExists") Integer tipPotrosacaNotExists,@Param("tipPotrosacaIds") List<Long> tipPotrosacaIds);
	
	List<Transakcija> findAllByStanOrderByDatum(Stan s);
	
	List<Transakcija> findAllByOstaliRacuniOrderByDatum(OstaliRacuni or);
	
	@Query(nativeQuery = true)
	List<RekapitulacijaSifraPromeneDatumDTO> rekapitulacijaSifraPromeneDatum(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo);
	
	@Query(nativeQuery = true)
	List<RekapitulacijaSifraPromeneDatumDTO> rekapitulacijaSifraPromeneDatumSearch(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo, @Param("sifraOd") String sifraOd, @Param("sifraDo") String sifraDo);
	
	
	
	@Query(name = "Transakcija.rekapitulacijaSifraPromeneDatumRacun", nativeQuery = true)
	List<RacunDTO> rekapitulacijaSifraPromeneDatumRacun(
	    @Param("datumDo") LocalDate datumDo,
	    @Param("podstanicaOd") Integer podstanicaOd,
	    @Param("podstanicaDo") Integer podstanicaDo,
	    @Param("sifraOd") String sifraOd,
	    @Param("sifraDo") String sifraDo
	);
	
	
	
	@Query(nativeQuery = true)
	List<RekapitulacijaSifraPromeneDatumDTO> sintetickiDnevnik(@Param("datumOdNotExists") Integer datumOdNotExists,@Param("datumOd") LocalDate datumOd,
			@Param("datumDoNotExists") Integer datumDoNotExists,@Param("datumDo") LocalDate datumDo);
	
	@Query(nativeQuery = true, name = "Transakcija.sintetickiDnevnikSearch")
	List<RekapitulacijaSifraPromeneDatumDTO> sintetickiDnevnikSearch(
	  @Param("datumOdNotExists") Integer datumOdNotExists,
	  @Param("datumOd") LocalDate datumOd,
	  @Param("datumDoNotExists") Integer datumDoNotExists,
	  @Param("datumDo") LocalDate datumDo,

	  @Param("sifraOdNotExists") Integer sifraOdNotExists,
	  @Param("sifraOd") String sifraOd,
	  @Param("sifraDoNotExists") Integer sifraDoNotExists,
	  @Param("sifraDo") String sifraDo,

	  @Param("dugujeOdNotExists") Integer dugujeOdNotExists,
	  @Param("dugujeOd") BigDecimal dugujeOd,
	  @Param("dugujeDoNotExists") Integer dugujeDoNotExists,
	  @Param("dugujeDo") BigDecimal dugujeDo,

	  @Param("potrazujeOdNotExists") Integer potrazujeOdNotExists,
	  @Param("potrazujeOd") BigDecimal potrazujeOd,
	  @Param("potrazujeDoNotExists") Integer potrazujeDoNotExists,
	  @Param("potrazujeDo") BigDecimal potrazujeDo
	);
	
	
	@Query(value = 
		    "(SELECT transakcija.* FROM transakcija t " +
		    "JOIN stan s ON t.stan_id = s.id " +
		    "WHERE s.id < :Idr ORDER BY s.id DESC LIMIT 1) " +
		    "UNION ALL " +
		    "(SELECT t.* FROM racun t " +
		    "JOIN stan s ON t.stan_id = s.id " +
		    "WHERE s.id > :Idr ORDER BY s.id ASC LIMIT 1)",
		    nativeQuery = true)
		List<Transakcija> getPreviousAndNextById(@Param("Idr") Long Idr);
	
	 @Query(
			    value = "select t from Transakcija t left join t.stan s",
			    countQuery = "select count(t) from Transakcija t"
			  )
			  Page<Transakcija> findAllWithStan(Pageable pageable);
}
