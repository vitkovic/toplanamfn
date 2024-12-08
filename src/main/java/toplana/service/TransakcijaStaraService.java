package toplana.service;

import toplana.domain.TransakcijaStara;
import toplana.repository.TransakcijaStaraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link TransakcijaStara}.
 */
@Service
@Transactional
public class TransakcijaStaraService {

    private final Logger log = LoggerFactory.getLogger(TransakcijaStaraService.class);

    private final TransakcijaStaraRepository transakcijaStaraRepository;

    public TransakcijaStaraService(TransakcijaStaraRepository transakcijaStaraRepository) {
        this.transakcijaStaraRepository = transakcijaStaraRepository;
    }

    /**
     * Save a transakcijaStara.
     *
     * @param transakcijaStara the entity to save.
     * @return the persisted entity.
     */
    public TransakcijaStara save(TransakcijaStara transakcijaStara) {
        log.debug("Request to save TransakcijaStara : {}", transakcijaStara);
        return transakcijaStaraRepository.save(transakcijaStara);
    }

    /**
     * Get all the transakcijaStaras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransakcijaStara> findAll(Pageable pageable) {
        log.debug("Request to get all TransakcijaStaras");
        return transakcijaStaraRepository.findAll(pageable);
    }



    /**
     *  Get all the transakcijaStaras where StavkaIzvoda is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<TransakcijaStara> findAllWhereStavkaIzvodaIsNull() {
        log.debug("Request to get all transakcijaStaras where StavkaIzvoda is null");
        return StreamSupport
            .stream(transakcijaStaraRepository.findAll().spliterator(), false)
            .filter(transakcijaStara -> transakcijaStara.getStavkaIzvoda() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one transakcijaStara by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransakcijaStara> findOne(Long id) {
        log.debug("Request to get TransakcijaStara : {}", id);
        return transakcijaStaraRepository.findById(id);
    }

    /**
     * Delete the transakcijaStara by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransakcijaStara : {}", id);
        transakcijaStaraRepository.deleteById(id);
    }
}
