package toplana.web.rest;

import toplana.domain.CeneStare;
import toplana.repository.CeneStareRepository;
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
 * REST controller for managing {@link toplana.domain.CeneStare}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CeneStareResource {

    private final Logger log = LoggerFactory.getLogger(CeneStareResource.class);

    private static final String ENTITY_NAME = "ceneStare";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CeneStareRepository ceneStareRepository;

    public CeneStareResource(CeneStareRepository ceneStareRepository) {
        this.ceneStareRepository = ceneStareRepository;
    }

    /**
     * {@code POST  /cene-stares} : Create a new ceneStare.
     *
     * @param ceneStare the ceneStare to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ceneStare, or with status {@code 400 (Bad Request)} if the ceneStare has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cene-stares")
    public ResponseEntity<CeneStare> createCeneStare(@RequestBody CeneStare ceneStare) throws URISyntaxException {
        log.debug("REST request to save CeneStare : {}", ceneStare);
        if (ceneStare.getId() != null) {
            throw new BadRequestAlertException("A new ceneStare cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CeneStare result = ceneStareRepository.save(ceneStare);
        return ResponseEntity.created(new URI("/api/cene-stares/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cene-stares} : Updates an existing ceneStare.
     *
     * @param ceneStare the ceneStare to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ceneStare,
     * or with status {@code 400 (Bad Request)} if the ceneStare is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ceneStare couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cene-stares")
    public ResponseEntity<CeneStare> updateCeneStare(@RequestBody CeneStare ceneStare) throws URISyntaxException {
        log.debug("REST request to update CeneStare : {}", ceneStare);
        if (ceneStare.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CeneStare result = ceneStareRepository.save(ceneStare);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ceneStare.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cene-stares} : get all the ceneStares.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ceneStares in body.
     */
    @GetMapping("/cene-stares")
    public List<CeneStare> getAllCeneStares() {
        log.debug("REST request to get all CeneStares");
        return ceneStareRepository.findAll();
    }

    /**
     * {@code GET  /cene-stares/:id} : get the "id" ceneStare.
     *
     * @param id the id of the ceneStare to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ceneStare, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cene-stares/{id}")
    public ResponseEntity<CeneStare> getCeneStare(@PathVariable Long id) {
        log.debug("REST request to get CeneStare : {}", id);
        Optional<CeneStare> ceneStare = ceneStareRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ceneStare);
    }

    /**
     * {@code DELETE  /cene-stares/:id} : delete the "id" ceneStare.
     *
     * @param id the id of the ceneStare to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cene-stares/{id}")
    public ResponseEntity<Void> deleteCeneStare(@PathVariable Long id) {
        log.debug("REST request to delete CeneStare : {}", id);
        ceneStareRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
