package toplana.web.rest;

import toplana.domain.Cene;
import toplana.repository.CeneRepository;
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
 * REST controller for managing {@link toplana.domain.Cene}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CeneResource {

    private final Logger log = LoggerFactory.getLogger(CeneResource.class);

    private static final String ENTITY_NAME = "cene";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CeneRepository ceneRepository;

    public CeneResource(CeneRepository ceneRepository) {
        this.ceneRepository = ceneRepository;
    }

    /**
     * {@code POST  /cenes} : Create a new cene.
     *
     * @param cene the cene to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cene, or with status {@code 400 (Bad Request)} if the cene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cenes")
    public ResponseEntity<Cene> createCene(@RequestBody Cene cene) throws URISyntaxException {
        log.debug("REST request to save Cene : {}", cene);
        if (cene.getId() != null) {
            throw new BadRequestAlertException("A new cene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cene result = ceneRepository.save(cene);
        return ResponseEntity.created(new URI("/api/cenes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cenes} : Updates an existing cene.
     *
     * @param cene the cene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cene,
     * or with status {@code 400 (Bad Request)} if the cene is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cenes")
    public ResponseEntity<Cene> updateCene(@RequestBody Cene cene) throws URISyntaxException {
        log.debug("REST request to update Cene : {}", cene);
        if (cene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cene result = ceneRepository.save(cene);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cene.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cenes} : get all the cenes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cenes in body.
     */
    @GetMapping("/cenes")
    public List<Cene> getAllCenes() {
        log.debug("REST request to get all Cenes");
        return ceneRepository.findAll();
    }

    /**
     * {@code GET  /cenes/:id} : get the "id" cene.
     *
     * @param id the id of the cene to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cene, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cenes/{id}")
    public ResponseEntity<Cene> getCene(@PathVariable Long id) {
        log.debug("REST request to get Cene : {}", id);
        Optional<Cene> cene = ceneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cene);
    }

    /**
     * {@code DELETE  /cenes/:id} : delete the "id" cene.
     *
     * @param id the id of the cene to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cenes/{id}")
    public ResponseEntity<Void> deleteCene(@PathVariable Long id) {
        log.debug("REST request to delete Cene : {}", id);
        ceneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
