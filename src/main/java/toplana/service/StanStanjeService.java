package toplana.service;

import toplana.domain.StanStanje;
import toplana.repository.StanStanjeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StanStanje}.
 */
@Service
@Transactional
public class StanStanjeService {

    private final Logger log = LoggerFactory.getLogger(StanStanjeService.class);

    private final StanStanjeRepository stanStanjeRepository;

    public StanStanjeService(StanStanjeRepository stanStanjeRepository) {
        this.stanStanjeRepository = stanStanjeRepository;
    }

    /**
     * Save a stanStanje.
     *
     * @param stanStanje the entity to save.
     * @return the persisted entity.
     */
    public StanStanje save(StanStanje stanStanje) {
        log.debug("Request to save StanStanje : {}", stanStanje);
        return stanStanjeRepository.save(stanStanje);
    }

    /**
     * Get all the stanStanjes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StanStanje> findAll(Pageable pageable) {
        log.debug("Request to get all StanStanjes");
        return stanStanjeRepository.findAll(pageable);
    }


    /**
     * Get one stanStanje by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StanStanje> findOne(Long id) {
        log.debug("Request to get StanStanje : {}", id);
        return stanStanjeRepository.findById(id);
    }

    /**
     * Delete the stanStanje by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StanStanje : {}", id);
        stanStanjeRepository.deleteById(id);
    }
}
