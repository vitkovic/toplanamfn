package toplana.web.rest;

import toplana.domain.StanStanje;
import toplana.service.StanStanjeService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link toplana.domain.StanStanje}.
 */
@RestController
@RequestMapping("/api")
public class StanStanjeResource {

    private final Logger log = LoggerFactory.getLogger(StanStanjeResource.class);

    private static final String ENTITY_NAME = "stanStanje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StanStanjeService stanStanjeService;

    public StanStanjeResource(StanStanjeService stanStanjeService) {
        this.stanStanjeService = stanStanjeService;
    }

    /**
     * {@code POST  /stan-stanjes} : Create a new stanStanje.
     *
     * @param stanStanje the stanStanje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stanStanje, or with status {@code 400 (Bad Request)} if the stanStanje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stan-stanjes")
    public ResponseEntity<StanStanje> createStanStanje(@Valid @RequestBody StanStanje stanStanje) throws URISyntaxException {
        log.debug("REST request to save StanStanje : {}", stanStanje);
        if (stanStanje.getId() != null) {
            throw new BadRequestAlertException("A new stanStanje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StanStanje result = stanStanjeService.save(stanStanje);
        return ResponseEntity.created(new URI("/api/stan-stanjes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stan-stanjes} : Updates an existing stanStanje.
     *
     * @param stanStanje the stanStanje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stanStanje,
     * or with status {@code 400 (Bad Request)} if the stanStanje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stanStanje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stan-stanjes")
    public ResponseEntity<StanStanje> updateStanStanje(@Valid @RequestBody StanStanje stanStanje) throws URISyntaxException {
        log.debug("REST request to update StanStanje : {}", stanStanje);
        if (stanStanje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StanStanje result = stanStanjeService.save(stanStanje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stanStanje.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stan-stanjes} : get all the stanStanjes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stanStanjes in body.
     */
    @GetMapping("/stan-stanjes")
    public ResponseEntity<List<StanStanje>> getAllStanStanjes(Pageable pageable) {
        log.debug("REST request to get a page of StanStanjes");
        Page<StanStanje> page = stanStanjeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stan-stanjes/:id} : get the "id" stanStanje.
     *
     * @param id the id of the stanStanje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stanStanje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stan-stanjes/{id}")
    public ResponseEntity<StanStanje> getStanStanje(@PathVariable Long id) {
        log.debug("REST request to get StanStanje : {}", id);
        Optional<StanStanje> stanStanje = stanStanjeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stanStanje);
    }

    /**
     * {@code DELETE  /stan-stanjes/:id} : delete the "id" stanStanje.
     *
     * @param id the id of the stanStanje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stan-stanjes/{id}")
    public ResponseEntity<Void> deleteStanStanje(@PathVariable Long id) {
        log.debug("REST request to delete StanStanje : {}", id);
        stanStanjeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
