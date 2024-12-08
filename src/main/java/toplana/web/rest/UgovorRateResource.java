package toplana.web.rest;

import toplana.domain.UgovorRate;
import toplana.repository.UgovorRateRepository;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link toplana.domain.UgovorRate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UgovorRateResource {

    private final Logger log = LoggerFactory.getLogger(UgovorRateResource.class);

    private static final String ENTITY_NAME = "ugovorRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UgovorRateRepository ugovorRateRepository;

    public UgovorRateResource(UgovorRateRepository ugovorRateRepository) {
        this.ugovorRateRepository = ugovorRateRepository;
    }

    /**
     * {@code POST  /ugovor-rates} : Create a new ugovorRate.
     *
     * @param ugovorRate the ugovorRate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ugovorRate, or with status {@code 400 (Bad Request)} if the ugovorRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ugovor-rates")
    public ResponseEntity<UgovorRate> createUgovorRate(@RequestBody UgovorRate ugovorRate) throws URISyntaxException {
        log.debug("REST request to save UgovorRate : {}", ugovorRate);
        if (ugovorRate.getId() != null) {
            throw new BadRequestAlertException("A new ugovorRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UgovorRate result = ugovorRateRepository.save(ugovorRate);
        return ResponseEntity.created(new URI("/api/ugovor-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ugovor-rates} : Updates an existing ugovorRate.
     *
     * @param ugovorRate the ugovorRate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ugovorRate,
     * or with status {@code 400 (Bad Request)} if the ugovorRate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ugovorRate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ugovor-rates")
    public ResponseEntity<UgovorRate> updateUgovorRate(@RequestBody UgovorRate ugovorRate) throws URISyntaxException {
        log.debug("REST request to update UgovorRate : {}", ugovorRate);
        if (ugovorRate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UgovorRate result = ugovorRateRepository.save(ugovorRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ugovorRate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ugovor-rates} : get all the ugovorRates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ugovorRates in body.
     */
    @GetMapping("/ugovor-rates")
    public ResponseEntity<List<UgovorRate>> getAllUgovorRates(Pageable pageable) {
        log.debug("REST request to get a page of UgovorRates");
        Page<UgovorRate> page = ugovorRateRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ugovor-rates/:id} : get the "id" ugovorRate.
     *
     * @param id the id of the ugovorRate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ugovorRate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ugovor-rates/{id}")
    public ResponseEntity<UgovorRate> getUgovorRate(@PathVariable Long id) {
        log.debug("REST request to get UgovorRate : {}", id);
        Optional<UgovorRate> ugovorRate = ugovorRateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ugovorRate);
    }

    /**
     * {@code DELETE  /ugovor-rates/:id} : delete the "id" ugovorRate.
     *
     * @param id the id of the ugovorRate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ugovor-rates/{id}")
    public ResponseEntity<Void> deleteUgovorRate(@PathVariable Long id) {
        log.debug("REST request to delete UgovorRate : {}", id);
        ugovorRateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
