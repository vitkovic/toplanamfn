package toplana.web.rest;

import toplana.domain.Parametri;
import toplana.repository.ParametriRepository;
import toplana.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link toplana.domain.Parametri}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParametriResource {

    private final Logger log = LoggerFactory.getLogger(ParametriResource.class);

    private static final String ENTITY_NAME = "parametri";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametriRepository parametriRepository;

    public ParametriResource(ParametriRepository parametriRepository) {
        this.parametriRepository = parametriRepository;
    }

    /**
     * {@code POST  /parametris} : Create a new parametri.
     *
     * @param parametri the parametri to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametri, or with status {@code 400 (Bad Request)} if the parametri has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parametris")
    public ResponseEntity<Parametri> createParametri(@RequestBody Parametri parametri) throws URISyntaxException {
        log.debug("REST request to save Parametri : {}", parametri);
        if (parametri.getId() != null) {
            throw new BadRequestAlertException("A new parametri cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Parametri result = parametriRepository.save(parametri);
        return ResponseEntity.created(new URI("/api/parametris/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parametris} : Updates an existing parametri.
     *
     * @param parametri the parametri to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametri,
     * or with status {@code 400 (Bad Request)} if the parametri is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametri couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parametris")
    public ResponseEntity<Parametri> updateParametri(@RequestBody Parametri parametri) throws URISyntaxException {
        log.debug("REST request to update Parametri : {}", parametri);
        if (parametri.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Parametri result = parametriRepository.save(parametri);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametri.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parametris} : get all the parametris.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametris in body.
     */
    @GetMapping("/parametris")
    public List<Parametri> getAllParametris() {
        log.debug("REST request to get all Parametris");
        return parametriRepository.findAll();
    }

    /**
     * {@code GET  /parametris/:id} : get the "id" parametri.
     *
     * @param id the id of the parametri to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametri, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parametris/{id}")
    public ResponseEntity<Parametri> getParametri(@PathVariable Long id) {
        log.debug("REST request to get Parametri : {}", id);
        Optional<Parametri> parametri = parametriRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parametri);
    }

    /**
     * {@code DELETE  /parametris/:id} : delete the "id" parametri.
     *
     * @param id the id of the parametri to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parametris/{id}")
    public ResponseEntity<Void> deleteParametri(@PathVariable Long id) {
        log.debug("REST request to delete Parametri : {}", id);
        parametriRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
