package toplana.web.rest;

import toplana.domain.Podstanica;
import toplana.domain.StanjaPodstanice;
import toplana.repository.PodstanicaRepository;
import toplana.repository.StanjaPodstaniceRepository;
import toplana.web.rest.dto.StanjaPodstaniceBatchDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link toplana.domain.StanjaPodstanice}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StanjaPodstaniceResource {

    private final Logger log = LoggerFactory.getLogger(StanjaPodstaniceResource.class);

    private static final String ENTITY_NAME = "stanjaPodstanice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StanjaPodstaniceRepository stanjaPodstaniceRepository;
    private final PodstanicaRepository podstanicaRepository;


    public StanjaPodstaniceResource(StanjaPodstaniceRepository stanjaPodstaniceRepository,
			PodstanicaRepository podstanicaRepository) {
		super();
		this.stanjaPodstaniceRepository = stanjaPodstaniceRepository;
		this.podstanicaRepository = podstanicaRepository;
	}

	/**
     * {@code POST  /stanja-podstanices} : Create a new stanjaPodstanice.
     *
     * @param stanjaPodstanice the stanjaPodstanice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stanjaPodstanice, or with status {@code 400 (Bad Request)} if the stanjaPodstanice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stanja-podstanices")
    public ResponseEntity<StanjaPodstanice> createStanjaPodstanice(@Valid @RequestBody StanjaPodstanice stanjaPodstanice) throws URISyntaxException {
        log.debug("REST request to save StanjaPodstanice : {}", stanjaPodstanice);
        if (stanjaPodstanice.getId() != null) {
            throw new BadRequestAlertException("A new stanjaPodstanice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StanjaPodstanice result = stanjaPodstaniceRepository.save(stanjaPodstanice);
        return ResponseEntity.created(new URI("/api/stanja-podstanices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    /**
     * Post metod za zbirni unos stanja na podstanicama. 
     * @param stanjaPodstanice
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/stanja-podstanices/batch")
    public ResponseEntity<List<StanjaPodstanice>> createStanjaPodstaniceBatch(@Valid @RequestBody StanjaPodstaniceBatchDTO stanjaPodstanice) throws URISyntaxException {
        log.debug("REST request to save StanjaPodstaniceBatch : {}", stanjaPodstanice);
        
        List<StanjaPodstanice> sps = stanjaPodstaniceRepository.saveAll(stanjaPodstanice.getStanjaPodstanice());
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.stanjaPodstanice.uspesnoKreiranaStanja", "")).body(sps);
    }

    /**
     * {@code PUT  /stanja-podstanices} : Updates an existing stanjaPodstanice.
     *
     * @param stanjaPodstanice the stanjaPodstanice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stanjaPodstanice,
     * or with status {@code 400 (Bad Request)} if the stanjaPodstanice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stanjaPodstanice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stanja-podstanices")
    public ResponseEntity<StanjaPodstanice> updateStanjaPodstanice(@Valid @RequestBody StanjaPodstanice stanjaPodstanice) throws URISyntaxException {
        log.debug("REST request to update StanjaPodstanice : {}", stanjaPodstanice);
        if (stanjaPodstanice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StanjaPodstanice result = stanjaPodstaniceRepository.save(stanjaPodstanice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stanjaPodstanice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stanja-podstanices} : get all the stanjaPodstanices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stanjaPodstanices in body.
     */
    @GetMapping("/stanja-podstanices")
    public ResponseEntity<List<StanjaPodstanice>> getAllStanjaPodstanices(Pageable pageable) {
        log.debug("REST request to get a page of StanjaPodstanices");
        Page<StanjaPodstanice> page = stanjaPodstaniceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * Vraca prazna stanja za svaku podstanicu, kod zbirnog unosa stanja
     * @return
     */
    @GetMapping("/stanja-podstanices/vrati-prazna")
    public ResponseEntity<List<StanjaPodstanice>> getPraznaStanja() {
        log.debug("REST request to get a page of Prazna stanja");
        List<Podstanica> podstanice = podstanicaRepository.findAll();
        List<StanjaPodstanice> stanja = new ArrayList<StanjaPodstanice>();
        for(Podstanica p : podstanice) {
        	StanjaPodstanice sp = new StanjaPodstanice();
        	sp.setPodstanica(p);
        	stanja.add(sp);
        }        
        return ResponseEntity.ok().body(stanja);
    }

    /**
     * {@code GET  /stanja-podstanices/:id} : get the "id" stanjaPodstanice.
     *
     * @param id the id of the stanjaPodstanice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stanjaPodstanice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stanja-podstanices/{id}")
    public ResponseEntity<StanjaPodstanice> getStanjaPodstanice(@PathVariable Long id) {
        log.debug("REST request to get StanjaPodstanice : {}", id);
        Optional<StanjaPodstanice> stanjaPodstanice = stanjaPodstaniceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stanjaPodstanice);
    }

    /**
     * {@code DELETE  /stanja-podstanices/:id} : delete the "id" stanjaPodstanice.
     *
     * @param id the id of the stanjaPodstanice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stanja-podstanices/{id}")
    public ResponseEntity<Void> deleteStanjaPodstanice(@PathVariable Long id) {
        log.debug("REST request to delete StanjaPodstanice : {}", id);
        stanjaPodstaniceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
