package toplana.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
import toplana.domain.OstaliRacuni;
import toplana.domain.Stan;
import toplana.domain.StavkeIzvoda;
import toplana.domain.StavkeIzvodaPostanska;
import toplana.domain.Transakcija;
import toplana.repository.OstaliRacuniRepository;
import toplana.repository.StanRepository;
import toplana.repository.StavkeIzvodaPostanskaRepository;
import toplana.repository.StavkeIzvodaRepository;
import toplana.repository.TransakcijaRepository;
import toplana.web.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
@Transactional
public class StavkeIzvodaPostanskaResource {

	private final Logger log = LoggerFactory.getLogger(StavkeIzvodaPostanskaResource.class);

    private static final String ENTITY_NAME = "stavkeIzvodaPostanska";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StavkeIzvodaPostanskaRepository stavkeIzvodaPostanskaRepository;
    private final StanRepository stanRepository;
    private final TransakcijaRepository transakcijaRepository;
    private final OstaliRacuniRepository ostaliRacuniRepository;
	public StavkeIzvodaPostanskaResource(StavkeIzvodaPostanskaRepository stavkeIzvodaPostanskaRepository,
			StanRepository stanRepository, TransakcijaRepository transakcijaRepository,
			OstaliRacuniRepository ostaliRacuniRepository) {
		this.stavkeIzvodaPostanskaRepository = stavkeIzvodaPostanskaRepository;
		this.stanRepository = stanRepository;
		this.transakcijaRepository = transakcijaRepository;
		this.ostaliRacuniRepository = ostaliRacuniRepository;
	}
	
	/**
     * {@code POST  /stavke-izvodas} : Create a new stavkeIzvoda.
     *
     * @param stavkeIzvoda the stavkeIzvoda to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stavkeIzvoda, or with status {@code 400 (Bad Request)} if the stavkeIzvoda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stavke-izvoda-postanskas")
    public ResponseEntity<StavkeIzvodaPostanska> createStavkeIzvoda(@RequestBody StavkeIzvodaPostanska stavkeIzvoda) throws URISyntaxException {
        log.debug("REST request to save StavkeIzvoda : {}", stavkeIzvoda);
        if (stavkeIzvoda.getId() != null) {
            throw new BadRequestAlertException("A new stavkeIzvodaPostanska cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StavkeIzvodaPostanska result = stavkeIzvodaPostanskaRepository.save(stavkeIzvoda);
        return ResponseEntity.created(new URI("/api/stavke-izvodapostanskas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stavke-izvodas} : Updates an existing stavkeIzvoda.
     *
     * @param stavkeIzvoda the stavkeIzvoda to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stavkeIzvoda,
     * or with status {@code 400 (Bad Request)} if the stavkeIzvoda is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stavkeIzvoda couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stavke-izvoda-postanskas")
    public ResponseEntity<StavkeIzvodaPostanska> updateStavkeIzvoda(@RequestBody StavkeIzvodaPostanska stavkeIzvoda) throws URISyntaxException {
        log.debug("REST request to update StavkeIzvodaPostanska : {}", stavkeIzvoda);
        if (stavkeIzvoda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StavkeIzvodaPostanska result = stavkeIzvodaPostanskaRepository.save(stavkeIzvoda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stavkeIzvoda.getId().toString()))
            .body(result);
    }
    
    /**
     * Knjizenje jedne po jedne stavke u izvodu. Dolazi se sa strane stavke izvoda update
     * @param stavkeIzvoda
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/stavke-izvoda-postanskas-knjizenje")
    public ResponseEntity<StavkeIzvodaPostanska> knjizenjeStavkeIzvoda(@RequestBody StavkeIzvodaPostanska stavkeIzvoda) throws URISyntaxException {
        log.debug("REST request to knjizenje StavkeIzvodaPostanska : {}", stavkeIzvoda);
        if (stavkeIzvoda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
       
        
        String sifraStana = stavkeIzvoda.getBrojPartijePoverioc();
        
       // sifraStana = sifraStana.substring(1);
        
        Stan stan = stanRepository.findBySifra(sifraStana);
        if(stan != null) {
        	stavkeIzvoda.setRasporedjena(true);        	
        	
        	Transakcija t = new Transakcija(stavkeIzvoda, stan);
        	Transakcija tResult = transakcijaRepository.save(t);
        	stavkeIzvoda.setTransakcija(tResult);
        	StavkeIzvodaPostanska result = stavkeIzvodaPostanskaRepository.save(stavkeIzvoda);
        	
        	return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stavkeIzvoda.getId().toString()))
                    .body(result);
        }else {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.stavkeIzvoda.sifraStanaNePostoji", "")).body(stavkeIzvoda);
        }               
    }

    /**
     * {@code GET  /stavke-izvodas} : get all the stavkeIzvodas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stavkeIzvodas in body.
     */
    @GetMapping("/stavke-izvoda-postanskas")
    public ResponseEntity<List<StavkeIzvodaPostanska>> getAllStavkeIzvodas(Pageable pageable) {
        log.debug("REST request to get a page of StavkeIzvodaPostanskas");
        Page<StavkeIzvodaPostanska> page = stavkeIzvodaPostanskaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stavke-izvodas/:id} : get the "id" stavkeIzvoda.
     *
     * @param id the id of the stavkeIzvoda to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stavkeIzvoda, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stavke-izvoda-postanskas/{id}")
    public ResponseEntity<StavkeIzvodaPostanska> getStavkeIzvoda(@PathVariable Long id) {
        log.debug("REST request to get StavkeIzvoda : {}", id);
        Optional<StavkeIzvodaPostanska> stavkeIzvoda = stavkeIzvodaPostanskaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(stavkeIzvoda);
    }

    /**
     * {@code DELETE  /stavke-izvodas/:id} : delete the "id" stavkeIzvoda.
     *
     * @param id the id of the stavkeIzvoda to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stavke-izvoda-postanskas/{id}")
    public ResponseEntity<Void> deleteStavkeIzvoda(@PathVariable Long id) {
        log.debug("REST request to delete StavkeIzvodaPostanska : {}", id);
        stavkeIzvodaPostanskaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    
    
    
}
