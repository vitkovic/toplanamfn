package toplana.web.rest;

import toplana.domain.ParametriIstorija;
import toplana.repository.ParametriIstorijaRepository;
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
 * REST controller for managing {@link toplana.domain.ParametriIstorija}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParametriIstorijaResource {

    private final Logger log = LoggerFactory.getLogger(ParametriIstorijaResource.class);

    private static final String ENTITY_NAME = "parametriIstorija";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametriIstorijaRepository parametriIstorijaRepository;

    public ParametriIstorijaResource(ParametriIstorijaRepository parametriIstorijaRepository) {
        this.parametriIstorijaRepository = parametriIstorijaRepository;
    }

    /**
     * {@code POST  /parametri-istorijas} : Create a new parametriIstorija.
     *
     * @param parametriIstorija the parametriIstorija to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametriIstorija, or with status {@code 400 (Bad Request)} if the parametriIstorija has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parametri-istorijas")
    public ResponseEntity<ParametriIstorija> createParametriIstorija(@RequestBody ParametriIstorija parametriIstorija) throws URISyntaxException {
        log.debug("REST request to save ParametriIstorija : {}", parametriIstorija);
        if (parametriIstorija.getId() != null) {
            throw new BadRequestAlertException("A new parametriIstorija cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParametriIstorija result = parametriIstorijaRepository.save(parametriIstorija);
        return ResponseEntity.created(new URI("/api/parametri-istorijas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parametri-istorijas} : Updates an existing parametriIstorija.
     *
     * @param parametriIstorija the parametriIstorija to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametriIstorija,
     * or with status {@code 400 (Bad Request)} if the parametriIstorija is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametriIstorija couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parametri-istorijas")
    public ResponseEntity<ParametriIstorija> updateParametriIstorija(@RequestBody ParametriIstorija parametriIstorija) throws URISyntaxException {
        log.debug("REST request to update ParametriIstorija : {}", parametriIstorija);
        if (parametriIstorija.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParametriIstorija result = parametriIstorijaRepository.save(parametriIstorija);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametriIstorija.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /parametri-istorijas} : get all the parametriIstorijas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametriIstorijas in body.
     */
    @GetMapping("/parametri-istorijas")
    public List<ParametriIstorija> getAllParametriIstorijas() {
        log.debug("REST request to get all ParametriIstorijas");
        return parametriIstorijaRepository.findAll();
    }

    /**
     * {@code GET  /parametri-istorijas/:id} : get the "id" parametriIstorija.
     *
     * @param id the id of the parametriIstorija to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametriIstorija, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parametri-istorijas/{id}")
    public ResponseEntity<ParametriIstorija> getParametriIstorija(@PathVariable Long id) {
        log.debug("REST request to get ParametriIstorija : {}", id);
        Optional<ParametriIstorija> parametriIstorija = parametriIstorijaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(parametriIstorija);
    }

    /**
     * {@code DELETE  /parametri-istorijas/:id} : delete the "id" parametriIstorija.
     *
     * @param id the id of the parametriIstorija to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parametri-istorijas/{id}")
    public ResponseEntity<Void> deleteParametriIstorija(@PathVariable Long id) {
        log.debug("REST request to delete ParametriIstorija : {}", id);
        parametriIstorijaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
