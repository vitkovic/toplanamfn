package toplana.repository;

import toplana.domain.TransakcijaStara;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TransakcijaStara entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransakcijaStaraRepository extends JpaRepository<TransakcijaStara, Long> {
}
