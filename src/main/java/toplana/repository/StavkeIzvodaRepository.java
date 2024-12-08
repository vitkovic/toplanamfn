package toplana.repository;

import toplana.domain.StavkeIzvoda;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StavkeIzvoda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StavkeIzvodaRepository extends JpaRepository<StavkeIzvoda, Long> {
	
	List<StavkeIzvoda> findByIzvodIdAndTransakcijaNotNullOrTransakcijaStaraNotNull(Long izvodId);
	
	void deleteByIzvodId(Long izvodId); 
}
