package toplana.web.rest;

import toplana.domain.StavkeUtuzenja;
import toplana.repository.StavkeUtuzenjaRepository;
import toplana.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link toplana.domain.StavkeUtuzenja}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StavkeUtuzenjaResource {

    private final Logger log = LoggerFactory.getLogger(StavkeUtuzenjaResource.class);

    private static final String ENTITY_NAME = "stavkeUtuzenja";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StavkeUtuzenjaRepository stavkeUtuzenjaRepository;

    public StavkeUtuzenjaResource(StavkeUtuzenjaRepository stavkeUtuzenjaRepository) {
        this.stavkeUtuzenjaRepository = stavkeUtuzenjaRepository;
    }

    /**
     * {@code POST  /stavke-utuzenjas} : Create a new stavkeUtuzenja.
     *
     * @param stavkeUtuzenja the stavkeUtuzenja to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stavkeUtuzenja, or with status {@code 400 (Bad Request)} if the stavkeUtuzenja has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stavke-utuzenjas")
    public ResponseEntity<StavkeUtuzenja> createStavkeUtuzenja(@Valid @RequestBody StavkeUtuzenja stavkeUtuzenja) throws URISyntaxException {
        log.debug("REST request to save StavkeUtuzenja : {}", stavkeUtuzenja);
        if (stavkeUtuzenja.getId() != null) {
            throw new BadRequestAlertException("A new stavkeUtuzenja cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StavkeUtuzenja result = stavkeUtuzenjaRepository.save(stavkeUtuzenja);
        return ResponseEntity.created(new URI("/api/stavke-utuzenjas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stavke-utuzenjas} : Updates an existing stavkeUtuzenja.
     *
     * @param stavkeUtuzenja the stavkeUtuzenja to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stavkeUtuzenja,
     * or with status {@code 400 (Bad Request)} if the stavkeUtuzenja is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stavkeUtuzenja couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stavke-utuzenjas")
    public ResponseEntity<StavkeUtuzenja> updateStavkeUtuzenja(@Valid @RequestBody StavkeUtuzenja stavkeUtuzenja) throws URISyntaxException {
        log.debug("REST request to update StavkeUtuzenja : {}", stavkeUtuzenja);
        if (stavkeUtuzenja.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StavkeUtuzenja result = stavkeUtuzenjaRepository.save(stavkeUtuzenja);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stavkeUtuzenja.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stavke-utuzenjas} : get all the stavkeUtuzenjas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stavkeUtuzenjas in body.
     */
    @GetMapping("/stavke-utuzenjas")
    public List<StavkeUtuzenja> getAllStavkeUtuzenjas() {
        log.debug("REST request to get all StavkeUtuzenjas");
        return stavkeUtuzenjaRepository.findAll();
    }

    /**
     * {@code GET  /stavke-utuzenjas/:id} : get the "id" stavkeUtuzenja.
     *
     * @param id the id of the stavkeUtuzenja to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stavkeUtuzenja, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stavke-utuzenjas/{id}")
    public ResponseEntity<StavkeUtuzenja> getStavkeUtuzenja(@PathVariable Long id) {
        log.debug("REST request to get StavkeUtuzenja : {}", id);
        Optional<StavkeUtuzenja> stavkeUtuzenja = stavkeUtuzenjaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stavkeUtuzenja);
    }

    /**
     * {@code DELETE  /stavke-utuzenjas/:id} : delete the "id" stavkeUtuzenja.
     *
     * @param id the id of the stavkeUtuzenja to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stavke-utuzenjas/{id}")
    public ResponseEntity<Void> deleteStavkeUtuzenja(@PathVariable Long id) {
        log.debug("REST request to delete StavkeUtuzenja : {}", id);
        stavkeUtuzenjaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
