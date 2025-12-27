package toplana.repository;

import toplana.domain.Racun;
import toplana.domain.Vlasnik;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Vlasnik entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VlasnikRepository extends JpaRepository<Vlasnik, Long>, JpaSpecificationExecutor<Vlasnik> {
}
