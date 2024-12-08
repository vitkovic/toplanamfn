package toplana.web.rest;

import toplana.domain.Opomena;
import toplana.repository.OpomenaRepository;
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
 * REST controller for managing {@link toplana.domain.Opomena}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OpomenaResource {

    private final Logger log = LoggerFactory.getLogger(OpomenaResource.class);

    private static final String ENTITY_NAME = "opomena";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpomenaRepository opomenaRepository;

    public OpomenaResource(OpomenaRepository opomenaRepository) {
        this.opomenaRepository = opomenaRepository;
    }

    /**
     * {@code POST  /opomenas} : Create a new opomena.
     *
     * @param opomena the opomena to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opomena, or with status {@code 400 (Bad Request)} if the opomena has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opomenas")
    public ResponseEntity<Opomena> createOpomena(@Valid @RequestBody Opomena opomena) throws URISyntaxException {
        log.debug("REST request to save Opomena : {}", opomena);
        if (opomena.getId() != null) {
            throw new BadRequestAlertException("A new opomena cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Opomena result = opomenaRepository.save(opomena);
        return ResponseEntity.created(new URI("/api/opomenas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opomenas} : Updates an existing opomena.
     *
     * @param opomena the opomena to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opomena,
     * or with status {@code 400 (Bad Request)} if the opomena is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opomena couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opomenas")
    public ResponseEntity<Opomena> updateOpomena(@Valid @RequestBody Opomena opomena) throws URISyntaxException {
        log.debug("REST request to update Opomena : {}", opomena);
        if (opomena.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Opomena result = opomenaRepository.save(opomena);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opomena.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opomenas} : get all the opomenas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opomenas in body.
     */
    @GetMapping("/opomenas")
    public ResponseEntity<List<Opomena>> getAllOpomenas(Pageable pageable) {
        log.debug("REST request to get a page of Opomenas");
        Page<Opomena> page = opomenaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opomenas/:id} : get the "id" opomena.
     *
     * @param id the id of the opomena to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opomena, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opomenas/{id}")
    public ResponseEntity<Opomena> getOpomena(@PathVariable Long id) {
        log.debug("REST request to get Opomena : {}", id);
        Optional<Opomena> opomena = opomenaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(opomena);
    }

    /**
     * {@code DELETE  /opomenas/:id} : delete the "id" opomena.
     *
     * @param id the id of the opomena to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opomenas/{id}")
    public ResponseEntity<Void> deleteOpomena(@PathVariable Long id) {
        log.debug("REST request to delete Opomena : {}", id);
        opomenaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
