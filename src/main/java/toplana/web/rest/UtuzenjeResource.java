package toplana.web.rest;

import toplana.domain.Utuzenje;
import toplana.repository.UtuzenjeRepository;
import toplana.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link toplana.domain.Utuzenje}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UtuzenjeResource {

    private final Logger log = LoggerFactory.getLogger(UtuzenjeResource.class);

    private static final String ENTITY_NAME = "utuzenje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UtuzenjeRepository utuzenjeRepository;

    public UtuzenjeResource(UtuzenjeRepository utuzenjeRepository) {
        this.utuzenjeRepository = utuzenjeRepository;
    }

    /**
     * {@code POST  /utuzenjes} : Create a new utuzenje.
     *
     * @param utuzenje the utuzenje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new utuzenje, or with status {@code 400 (Bad Request)} if the utuzenje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/utuzenjes")
    public ResponseEntity<Utuzenje> createUtuzenje(@Valid @RequestBody Utuzenje utuzenje) throws URISyntaxException {
        log.debug("REST request to save Utuzenje : {}", utuzenje);
        if (utuzenje.getId() != null) {
            throw new BadRequestAlertException("A new utuzenje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Utuzenje result = utuzenjeRepository.save(utuzenje);
        return ResponseEntity.created(new URI("/api/utuzenjes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /utuzenjes} : Updates an existing utuzenje.
     *
     * @param utuzenje the utuzenje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated utuzenje,
     * or with status {@code 400 (Bad Request)} if the utuzenje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the utuzenje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/utuzenjes")
    public ResponseEntity<Utuzenje> updateUtuzenje(@Valid @RequestBody Utuzenje utuzenje) throws URISyntaxException {
        log.debug("REST request to update Utuzenje : {}", utuzenje);
        if (utuzenje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Utuzenje result = utuzenjeRepository.save(utuzenje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, utuzenje.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /utuzenjes} : get all the utuzenjes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of utuzenjes in body.
     */
    @GetMapping("/utuzenjes")
    public ResponseEntity<List<Utuzenje>> getAllUtuzenjes(Pageable pageable) {
        log.debug("REST request to get a page of Utuzenjes");
        Page<Utuzenje> page = utuzenjeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /utuzenjes/:id} : get the "id" utuzenje.
     *
     * @param id the id of the utuzenje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the utuzenje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/utuzenjes/{id}")
    public ResponseEntity<Utuzenje> getUtuzenje(@PathVariable Long id) {
        log.debug("REST request to get Utuzenje : {}", id);
        Optional<Utuzenje> utuzenje = utuzenjeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(utuzenje);
    }

    /**
     * {@code DELETE  /utuzenjes/:id} : delete the "id" utuzenje.
     *
     * @param id the id of the utuzenje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/utuzenjes/{id}")
    public ResponseEntity<Void> deleteUtuzenje(@PathVariable Long id) {
        log.debug("REST request to delete Utuzenje : {}", id);
        utuzenjeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
