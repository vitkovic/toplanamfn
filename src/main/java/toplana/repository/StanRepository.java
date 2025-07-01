package toplana.repository;

import toplana.domain.Podstanica;
import toplana.domain.Stan;
import toplana.domain.StanStanje;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Stan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StanRepository extends JpaRepository<Stan, Long> {
	
	List<Stan> findAllByPodstanicaId(Long podstanicaId);
	Stan findBySifra(String sifra);
	
	List <Stan> findAllByVlasnikBrojRacuna(String brojRacuna);
	
	
	@Query(value = "select SUM(s.povrsina) from stan s "
			+ "	 where s.podstanica_id = :podstanicaid", nativeQuery=true)
	Double findKvSumPodstanicaId(@Param("podstanicaid") Long podstanicaid);
	
	
	@Query(value = "select sstanje from Stan s, StanStanje sstanje "
			+ "	 where s.podstanica.id = :podstanicaid and s.id = sstanje.stan.id"
			+ " and date_part('month', sstanje.datum) <= (:month + 1) and date_part('month', sstanje.datum) >= (:month - 1) " 
			+ "	ORDER BY sstanje.sifra, sstanje.datum")
	List<StanStanje> findPotrosnjaPodstanicaId(@Param("podstanicaid") Long podstanicaid, @Param("month") int month);
	
	// SELECT sifra,vrednost,datum FROM public.stan_stanje
	//ORDER BY sifra, datum
	/*
	@Query(value = "select sifra,vrednost,datum from stan_stanje "
			+ "	ORDER BY sifra,datum DESC ", nativeQuery=true)
	List<Object> findPotrosnjaPodstanicaId(@Param("podstanicaid") Long podstanicaid, @Param("month") Integer month);*/
}
