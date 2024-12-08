package toplana.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import toplana.domain.StavkeIzvodaPostanska;

@SuppressWarnings("unused")
@Repository
public interface StavkeIzvodaPostanskaRepository  extends JpaRepository<StavkeIzvodaPostanska, Long>{
	
	List<StavkeIzvodaPostanska> findByIzvodIdAndTransakcijaNotNullOrTransakcijaStaraNotNull(Long izvodId);
	
	void deleteByIzvodId(Long izvodId); 
}
