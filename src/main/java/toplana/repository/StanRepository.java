package toplana.repository;

import toplana.domain.Stan;

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
	
	@Query(value = "select SUM(sstanje.vrednost) from stan s, stan_stanje sstanje "
			+ "	 where s.podstanica_id = :podstanicaid and s.id = sstanje.stan_id"
			+ "	and date_part('month', datum) = :month ", nativeQuery=true)
	Double findPotrosnjaPodstanicaId(@Param("podstanicaid") Long podstanicaid, @Param("month") Integer month);
}
