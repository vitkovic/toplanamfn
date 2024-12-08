package toplana.repository;

import toplana.domain.VrstaTransakcije;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the VrstaTransakcije entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VrstaTransakcijeRepository extends JpaRepository<VrstaTransakcije, Long> {
}
