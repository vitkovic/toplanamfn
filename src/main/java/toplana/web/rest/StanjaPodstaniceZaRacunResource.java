package toplana.web.rest;

import toplana.domain.StanjaPodstaniceZaRacun;
import toplana.repository.StanjaPodstaniceZaRacunRepository;
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
 * REST controller for managing {@link toplana.domain.StanjaPodstaniceZaRacun}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StanjaPodstaniceZaRacunResource {

    private final Logger log = LoggerFactory.getLogger(StanjaPodstaniceZaRacunResource.class);

    private static final String ENTITY_NAME = "stanjaPodstaniceZaRacun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StanjaPodstaniceZaRacunRepository stanjaPodstaniceZaRacunRepository;

    public StanjaPodstaniceZaRacunResource(StanjaPodstaniceZaRacunRepository stanjaPodstaniceZaRacunRepository) {
        this.stanjaPodstaniceZaRacunRepository = stanjaPodstaniceZaRacunRepository;
    }

    /**
     * {@code POST  /stanja-podstanice-za-racuns} : Create a new stanjaPodstaniceZaRacun.
     *
     * @param stanjaPodstaniceZaRacun the stanjaPodstaniceZaRacun to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stanjaPodstaniceZaRacun, or with status {@code 400 (Bad Request)} if the stanjaPodstaniceZaRacun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stanja-podstanice-za-racuns")
    public ResponseEntity<StanjaPodstaniceZaRacun> createStanjaPodstaniceZaRacun(@RequestBody StanjaPodstaniceZaRacun stanjaPodstaniceZaRacun) throws URISyntaxException {
        log.debug("REST request to save StanjaPodstaniceZaRacun : {}", stanjaPodstaniceZaRacun);
        if (stanjaPodstaniceZaRacun.getId() != null) {
            throw new BadRequestAlertException("A new stanjaPodstaniceZaRacun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StanjaPodstaniceZaRacun result = stanjaPodstaniceZaRacunRepository.save(stanjaPodstaniceZaRacun);
        return ResponseEntity.created(new URI("/api/stanja-podstanice-za-racuns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stanja-podstanice-za-racuns} : Updates an existing stanjaPodstaniceZaRacun.
     *
     * @param stanjaPodstaniceZaRacun the stanjaPodstaniceZaRacun to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stanjaPodstaniceZaRacun,
     * or with status {@code 400 (Bad Request)} if the stanjaPodstaniceZaRacun is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stanjaPodstaniceZaRacun couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stanja-podstanice-za-racuns")
    public ResponseEntity<StanjaPodstaniceZaRacun> updateStanjaPodstaniceZaRacun(@RequestBody StanjaPodstaniceZaRacun stanjaPodstaniceZaRacun) throws URISyntaxException {
        log.debug("REST request to update StanjaPodstaniceZaRacun : {}", stanjaPodstaniceZaRacun);
        if (stanjaPodstaniceZaRacun.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StanjaPodstaniceZaRacun result = stanjaPodstaniceZaRacunRepository.save(stanjaPodstaniceZaRacun);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stanjaPodstaniceZaRacun.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stanja-podstanice-za-racuns} : get all the stanjaPodstaniceZaRacuns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stanjaPodstaniceZaRacuns in body.
     */
    @GetMapping("/stanja-podstanice-za-racuns")
    public List<StanjaPodstaniceZaRacun> getAllStanjaPodstaniceZaRacuns() {
        log.debug("REST request to get all StanjaPodstaniceZaRacuns");
        return stanjaPodstaniceZaRacunRepository.findAll();
    }

    /**
     * {@code GET  /stanja-podstanice-za-racuns/:id} : get the "id" stanjaPodstaniceZaRacun.
     *
     * @param id the id of the stanjaPodstaniceZaRacun to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stanjaPodstaniceZaRacun, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stanja-podstanice-za-racuns/{id}")
    public ResponseEntity<StanjaPodstaniceZaRacun> getStanjaPodstaniceZaRacun(@PathVariable Long id) {
        log.debug("REST request to get StanjaPodstaniceZaRacun : {}", id);
        Optional<StanjaPodstaniceZaRacun> stanjaPodstaniceZaRacun = stanjaPodstaniceZaRacunRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stanjaPodstaniceZaRacun);
    }

    /**
     * {@code DELETE  /stanja-podstanice-za-racuns/:id} : delete the "id" stanjaPodstaniceZaRacun.
     *
     * @param id the id of the stanjaPodstaniceZaRacun to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stanja-podstanice-za-racuns/{id}")
    public ResponseEntity<Void> deleteStanjaPodstaniceZaRacun(@PathVariable Long id) {
        log.debug("REST request to delete StanjaPodstaniceZaRacun : {}", id);
        stanjaPodstaniceZaRacunRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
