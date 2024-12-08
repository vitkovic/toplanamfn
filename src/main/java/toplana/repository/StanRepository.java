package toplana.repository;

import toplana.domain.Stan;

import java.util.List;

import org.springframework.data.jpa.repository.*;
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
}
