package toplana.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import toplana.domain.Opomena;
import toplana.domain.OstaliRacuni;
import toplana.repository.OpomenaRepository;
import toplana.repository.OstaliRacuniRepository;
import toplana.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
@Transactional
public class OstaliRacuniResource {

	 private final Logger log = LoggerFactory.getLogger(OstaliRacuniResource.class);

	 private static final String ENTITY_NAME = "ostaliRacuni";

	 @Value("${jhipster.clientApp.name}")
	 private String applicationName;

	 private final OstaliRacuniRepository ostaliRacuniRepository;

	public OstaliRacuniResource(OstaliRacuniRepository ostaliRacuniRepository) {
		super();
		this.ostaliRacuniRepository = ostaliRacuniRepository;
	}
	
	 /**
     * {@code POST  /opomenas} : Create a new OstalIRacuni.
     *
     * @param opomena the opomena to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opomena, or with status {@code 400 (Bad Request)} if the opomena has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ostali-racunis")
    public ResponseEntity<OstaliRacuni> createOstalIRacuni(@Valid @RequestBody OstaliRacuni ostaliRacuni) throws URISyntaxException {
        log.debug("REST request to save OStaliRacuni : {}", ostaliRacuni);
        if (ostaliRacuni.getId() != null) {
            throw new BadRequestAlertException("A new ostaliRacuni cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OstaliRacuni result = ostaliRacuniRepository.save(ostaliRacuni);
        return ResponseEntity.created(new URI("/api/ostalIRacunis/" + result.getId()))
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
    @PutMapping("/ostali-racunis")
    public ResponseEntity<OstaliRacuni> updateOstaliRacuni(@Valid @RequestBody OstaliRacuni ostaliRacuni) throws URISyntaxException {
        log.debug("REST request to update OStaliRacuni : {}", ostaliRacuni);
        if (ostaliRacuni.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OstaliRacuni result = ostaliRacuniRepository.save(ostaliRacuni);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ostaliRacuni.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /opomenas} : get all the opomenas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opomenas in body.
     */
    @GetMapping("/ostali-racunis")
    public ResponseEntity<List<OstaliRacuni>> getAllOstalIRacunis(Pageable pageable) {
        log.debug("REST request to get a page of OstaliRacuni");
        Page<OstaliRacuni> page = ostaliRacuniRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opomenas/:id} : get the "id" opomena.
     *
     * @param id the id of the opomena to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opomena, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ostali-racunis/{id}")
    public ResponseEntity<OstaliRacuni> getOstaliRacuni(@PathVariable Long id) {
        log.debug("REST request to get OstaliRacuni : {}", id);
        Optional<OstaliRacuni> ostaliRacuni = ostaliRacuniRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ostaliRacuni);
    }

    /**
     * {@code DELETE  /opomenas/:id} : delete the "id" opomena.
     *
     * @param id the id of the opomena to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ostali-racunis/{id}")
    public ResponseEntity<Void> deleteOstaliRacuni(@PathVariable Long id) {
        log.debug("REST request to delete OstaliRacuni : {}", id);
        ostaliRacuniRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
	 
	 
}
