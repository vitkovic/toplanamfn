package toplana.repository;

import toplana.domain.Izvod;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the Izvod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IzvodRepository extends JpaRepository<Izvod, Long> {
	
	@Query(value = "select i from Izvod i "
			+ "	left join fetch i.stavkaIzvodas "
			+ "where i.id = :id")
	Optional<Izvod> pronadjiPrekoId(@Param("id") Long id);
	
    @Modifying
    @Transactional
	@Query(value = "update StavkeIzvoda s "
			+ "	set s.rasporedjena = true  "
			+ "where s.izvod.id = :id")
	void knjiziIzvod(@Param("id") Long id);
    
    @Modifying
    @Transactional
	@Query(value = "update StavkeIzvoda s "
			+ "	set s.rasporedjena = false  "
			+ "where s.izvod.id = :id")
	void rasknjiziIzvod(@Param("id") Long id);
	
	
	@Query(value = "select i from Izvod i "
			+ "	left join fetch i.stavkeIzvodaPostanska "
			+ "where i.id = :id")
	Optional<Izvod> pronadjiPostanskaPrekoId(@Param("id") Long id);
	
	Page<Izvod> findAllByTip(String tip, Pageable pageable);
	
	Page<Izvod> findAllByTipIsNull(Pageable pageable);
}
