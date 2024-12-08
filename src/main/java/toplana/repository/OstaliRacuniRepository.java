package toplana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import toplana.domain.OstaliRacuni;
import toplana.domain.Stan;

@SuppressWarnings("unused")
@Repository
public interface OstaliRacuniRepository extends JpaRepository<OstaliRacuni, Long>{
	
	OstaliRacuni findByStan(Stan stan);
	OstaliRacuni findBySifra(String sifra);

}
