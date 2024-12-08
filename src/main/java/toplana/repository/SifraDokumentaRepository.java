package toplana.repository;

import toplana.domain.SifraDokumenta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SifraDokumenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SifraDokumentaRepository extends JpaRepository<SifraDokumenta, Long> {
}
