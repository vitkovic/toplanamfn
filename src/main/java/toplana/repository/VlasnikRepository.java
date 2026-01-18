package toplana.repository;

import toplana.domain.Racun;
import toplana.domain.Vlasnik;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vlasnik entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VlasnikRepository extends JpaRepository<Vlasnik, Long>, JpaSpecificationExecutor<Vlasnik> {
	
	 @Query("select distinct v from Vlasnik v left join fetch v.stans where v.id in :ids")
	    List<Vlasnik> findAllWithStansByIdIn(@Param("ids") List<Long> ids);
	
	
}
