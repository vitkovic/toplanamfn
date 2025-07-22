package toplana.web.rest;

import toplana.domain.OstaliRacuni;
import toplana.domain.Stan;
import toplana.repository.OstaliRacuniRepository;
import toplana.repository.StanRepository;
import toplana.repository.TransakcijaRepository;
import toplana.web.rest.dto.DugujePotrazujeDTO;
import toplana.web.rest.dto.StanDugujePotrazujeDTO;
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
 * REST controller for managing {@link toplana.domain.Stan}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StanResource {

    private final Logger log = LoggerFactory.getLogger(StanResource.class);

    private static final String ENTITY_NAME = "stan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StanRepository stanRepository;
    private final TransakcijaRepository transakcijaRepository;
    private final OstaliRacuniRepository ostaliRacuniRepository;
   


	public StanResource(StanRepository stanRepository, TransakcijaRepository transakcijaRepository,
			OstaliRacuniRepository ostaliRacuniRepository) {
		this.stanRepository = stanRepository;
		this.transakcijaRepository = transakcijaRepository;
		this.ostaliRacuniRepository = ostaliRacuniRepository;
	}

	/**
     * {@code POST  /stans} : Create a new stan.
     *
     * @param stan the stan to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stan, or with status {@code 400 (Bad Request)} if the stan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stans")
    public ResponseEntity<Stan> createStan(@Valid @RequestBody Stan stan) throws URISyntaxException {
        log.debug("REST request to save Stan : {}", stan);
        if (stan.getId() != null) {
            throw new BadRequestAlertException("A new stan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stan result = stanRepository.save(stan);
        return ResponseEntity.created(new URI("/api/stans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stans} : Updates an existing stan.
     *
     * @param stan the stan to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stan,
     * or with status {@code 400 (Bad Request)} if the stan is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stan couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stans")
    public ResponseEntity<Stan> updateStan(@Valid @RequestBody Stan stan) throws URISyntaxException {
        log.debug("REST request to update Stan : {}", stan);
        if (stan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stan result = stanRepository.save(stan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stan.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stans} : get all the stans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stans in body.
     */
    @GetMapping("/stans")
    public ResponseEntity<List<Stan>> getAllStans(Pageable pageable) {
        log.debug("REST request to get a page of Stans");
        Page<Stan> page = stanRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stans/:id} : get the "id" stan.
     *
     * @param id the id of the stan to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stan, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stans/{id}")
    public ResponseEntity<Stan> getStan(@PathVariable Long id) {
        log.debug("REST request to get Stan : {}", id);
        Optional<Stan> stan = stanRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stan);
    }
    
    @GetMapping("/stans-sifra/{sifra}")
    public ResponseEntity<StanDugujePotrazujeDTO> getStanZaSifru(@PathVariable String sifra) {
        log.debug("REST request to get Stan [p sifri : {}", sifra);
        Stan stan = stanRepository.findBySifra(sifra);
        
        
        
        if(stan != null) {
        	DugujePotrazujeDTO dugujePotrazujeDto = transakcijaRepository.getDugujePotrazujeZaStan(stan.getId());
        	return ResponseEntity.ok().body(new StanDugujePotrazujeDTO(stan,dugujePotrazujeDto));
        }else {
        	OstaliRacuni or = ostaliRacuniRepository.findBySifra(sifra);
        	if(or != null) {
        		DugujePotrazujeDTO dugujePotrazujeDto = transakcijaRepository.getDugujePotrazujeZaDodatniRacun(or.getId());
            	return ResponseEntity.ok().body(new StanDugujePotrazujeDTO(or,dugujePotrazujeDto));
        	}
        	
        
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.stan.nijePronadjenStanZaSifru", "")).body(null);        	
        }
    }

    /**
     * {@code DELETE  /stans/:id} : delete the "id" stan.
     *
     * @param id the id of the stan to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stans/{id}")
    public ResponseEntity<Void> deleteStan(@PathVariable Long id) {
        log.debug("REST request to delete Stan : {}", id);
        stanRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @GetMapping("/stans-all")
    public ResponseEntity<List<Stan>> getAllStansWithoutPageable() {
        log.debug("REST request to get a page of Stans");
        List<Stan> stanovi = stanRepository.findAll();
      
        
        return ResponseEntity.ok().body(stanovi);
    }
}
