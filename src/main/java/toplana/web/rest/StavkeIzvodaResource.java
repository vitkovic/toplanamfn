package toplana.web.rest;

import toplana.domain.OstaliRacuni;
import toplana.domain.SifraPromene;
import toplana.domain.Stan;
import toplana.domain.StavkeIzvoda;
import toplana.domain.Transakcija;
import toplana.repository.OstaliRacuniRepository;
import toplana.repository.StanRepository;
import toplana.repository.StavkeIzvodaRepository;
import toplana.repository.SifraPromeneRepository;
import toplana.repository.TransakcijaRepository;
import toplana.web.rest.dto.StavkeIzvodaTransakcijaDTO;
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
 * REST controller for managing {@link toplana.domain.StavkeIzvoda}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StavkeIzvodaResource {

    private final Logger log = LoggerFactory.getLogger(StavkeIzvodaResource.class);

    private static final String ENTITY_NAME = "stavkeIzvoda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StavkeIzvodaRepository stavkeIzvodaRepository;
    private final StanRepository stanRepository;
    private final TransakcijaRepository transakcijaRepository;
    private final OstaliRacuniRepository ostaliRacuniRepository;
    private final SifraPromeneRepository sifraPromeneRepository;
    
	public StavkeIzvodaResource(StavkeIzvodaRepository stavkeIzvodaRepository, StanRepository stanRepository,
			TransakcijaRepository transakcijaRepository, OstaliRacuniRepository ostaliRacuniRepository, SifraPromeneRepository sifraPromeneRepository) {
		this.stavkeIzvodaRepository = stavkeIzvodaRepository;
		this.stanRepository = stanRepository;
		this.transakcijaRepository = transakcijaRepository;
		this.ostaliRacuniRepository = ostaliRacuniRepository;
		this.sifraPromeneRepository = sifraPromeneRepository;
	}

	/**
     * {@code POST  /stavke-izvodas} : Create a new stavkeIzvoda.
     *
     * @param stavkeIzvoda the stavkeIzvoda to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stavkeIzvoda, or with status {@code 400 (Bad Request)} if the stavkeIzvoda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stavke-izvodas")
    public ResponseEntity<StavkeIzvoda> createStavkeIzvoda(@RequestBody StavkeIzvoda stavkeIzvoda) throws URISyntaxException {
        log.debug("REST request to save StavkeIzvoda : {}", stavkeIzvoda);
        if (stavkeIzvoda.getId() != null) {
            throw new BadRequestAlertException("A new stavkeIzvoda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StavkeIzvoda result = stavkeIzvodaRepository.save(stavkeIzvoda);
        return ResponseEntity.created(new URI("/api/stavke-izvodas/" + result.getId()))
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
    @PutMapping("/stavke-izvodas")
    public ResponseEntity<StavkeIzvoda> updateStavkeIzvoda(@RequestBody StavkeIzvoda stavkeIzvoda) throws URISyntaxException {
        log.debug("REST request to update StavkeIzvoda : {}", stavkeIzvoda);
        if (stavkeIzvoda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StavkeIzvoda result = stavkeIzvodaRepository.save(stavkeIzvoda);
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
    @PutMapping("/stavke-izvodas-knjizenje")
    public ResponseEntity<StavkeIzvodaTransakcijaDTO> knjizenjeStavkeIzvoda(@RequestBody StavkeIzvodaTransakcijaDTO stavkeIzvodaTransakcija) throws URISyntaxException {
        log.debug("REST request to knjizenje StavkeIzvoda : {}", stavkeIzvodaTransakcija);
        
        System.out.println("*******************************((((((((((((((((((((((((((((((((((((((((#############################################");
        if (stavkeIzvodaTransakcija.getStavkeIzvoda().getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }   
        
        String sifraStana = stavkeIzvodaTransakcija.getStavkeIzvoda().getPozivOdobrenja();
        String tempSifraStana = sifraStana; 
                
        if(sifraStana == null || sifraStana.length() == 0) {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.stavkeIzvoda.sifraStanaNePostoji", "")).body(stavkeIzvodaTransakcija);
        }
        
        
        SifraPromene spDto = stavkeIzvodaTransakcija.getSifraPromene();
        if (spDto == null || spDto.getId() == null) {
            throw new BadRequestAlertException("SifraPromene id is required", ENTITY_NAME, "sifrapromene.idnull");
        }
        // attach to persistence context (does not hit DB for JPA impls that support it)
        SifraPromene sp = sifraPromeneRepository.getReferenceById(spDto.getId());
        
        
       // SifraPromene sp = stavkeIzvodaTransakcija.getSifraPromene();
        StavkeIzvoda stavkeIzvoda = stavkeIzvodaTransakcija.getStavkeIzvoda();
        
        Stan stan = stanRepository.findBySifra(sifraStana);
        if(stan != null) {
        	stavkeIzvoda.setRasporedjena(true);        	
        	Transakcija td = stavkeIzvodaTransakcija.getStavkeIzvoda().getTransakcija();
        	
        //	System.out.println(td.getOpis() + " **********************************************");
        	
        	if (td != null && td.getId() != null && td.getId() > 0 && td.getOpis() != "Рачун") {
        		
        		transakcijaRepository.delete(td);
        		
        	}
        	
        	
        	Transakcija t = new Transakcija(stavkeIzvoda, stan, sp);
        	Transakcija tResult = transakcijaRepository.save(t);
        	stavkeIzvoda.setTransakcija(tResult);
        	StavkeIzvoda result = stavkeIzvodaRepository.save(stavkeIzvoda);
        	
        	return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stavkeIzvoda.getId().toString()))
                    .body(stavkeIzvodaTransakcija);
        }else {
        	//ako je uneta sifra stana sa 05 na pocetku
        	
        	sifraStana = sifraStana.substring(2);
        	stan = stanRepository.findBySifra(sifraStana);
        	if(stan != null) {
        		OstaliRacuni ostaliRacuni = ostaliRacuniRepository.findByStan(stan);
        		if(ostaliRacuni != null) {
        			stavkeIzvoda.setRasporedjena(true);        	
                	
        			Transakcija td = stavkeIzvodaTransakcija.getStavkeIzvoda().getTransakcija();
                	
                	if (td != null && td.getId() != null && td.getId() > 0 && td.getOpis() != "Рачун") {
                		
                		transakcijaRepository.delete(td);
                		
                	}
        			
        			
                	Transakcija t = new Transakcija(stavkeIzvoda, ostaliRacuni, sp);
                	Transakcija tResult = transakcijaRepository.save(t);
                	stavkeIzvoda.setTransakcija(tResult);
                	StavkeIzvoda result = stavkeIzvodaRepository.save(stavkeIzvoda);
                	
                	return ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stavkeIzvoda.getId().toString()))
                            .body(stavkeIzvodaTransakcija);
        		}
        	}else {
        		
        		sifraStana = tempSifraStana;
        		//ako je uneta sifra centra ili sl.
        		OstaliRacuni ostaliRacuni = ostaliRacuniRepository.findBySifra(sifraStana);
        		if(ostaliRacuni != null) {
	        		stavkeIzvoda.setRasporedjena(true);        	
	            	
	        		Transakcija td = stavkeIzvodaTransakcija.getStavkeIzvoda().getTransakcija();
	            	
	            	if (td != null && td.getId() != null && td.getId() > 0 && td.getOpis() != "Рачун") {
	            		
	            		transakcijaRepository.delete(td);
	            		
	            	}
	        		
	            	Transakcija t = new Transakcija(stavkeIzvoda, ostaliRacuni, sp);
	            	Transakcija tResult = transakcijaRepository.save(t);
	            	stavkeIzvoda.setTransakcija(tResult);
	            	StavkeIzvoda result = stavkeIzvodaRepository.save(stavkeIzvoda);
	            	
	            	return ResponseEntity.ok()
	                        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stavkeIzvoda.getId().toString()))
	                        .body(stavkeIzvodaTransakcija);
        		}else {
        			return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.stavkeIzvoda.sifraStanaNePostoji", "")).body(stavkeIzvodaTransakcija);		
        		}
        	}
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.stavkeIzvoda.sifraStanaNePostoji", "")).body(stavkeIzvodaTransakcija);

        }
        
        
    }
    
    /**
     * !!! Knjizenje jedne po jedne stavke u izvodu. Dolazi se sa strane stavke izvoda update - !!! Dodatak za naknadnu podelu svote novca
     * @param stavkeIzvoda
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/stavke-izvodas-knjizenje-podela")
    public ResponseEntity<StavkeIzvodaTransakcijaDTO> knjizenjeStavkeIzvodaPodela(@RequestBody StavkeIzvodaTransakcijaDTO stavkeIzvodaTransakcija) throws URISyntaxException {
        log.debug("REST request to knjizenje StavkeIzvoda : {}", stavkeIzvodaTransakcija);
        System.out.println("*******************************(((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((");
       
        if (stavkeIzvodaTransakcija.getStavkeIzvoda().getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        String sifraStana = stavkeIzvodaTransakcija.getStavkeIzvoda().getPozivOdobrenja();
        String tempSifraStana = sifraStana; 
                    
        if(sifraStana == null || sifraStana.length() == 0) {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.stavkeIzvoda.sifraStanaNePostoji", "")).body(stavkeIzvodaTransakcija);
        }
        
        SifraPromene sp = stavkeIzvodaTransakcija.getSifraPromene();
        StavkeIzvoda stavkeIzvoda = stavkeIzvodaTransakcija.getStavkeIzvoda();
        
        Stan stan = stanRepository.findBySifra(sifraStana);
       
        		
        		sifraStana = tempSifraStana;
        		//ako je uneta sifra centra ili sl.
        		OstaliRacuni ostaliRacuni = ostaliRacuniRepository.findBySifra(sifraStana);
        		if(ostaliRacuni != null) {
	        		stavkeIzvoda.setRasporedjena(true);        	
	            	
	        		Transakcija td = stavkeIzvodaTransakcija.getStavkeIzvoda().getTransakcija();
	            	
	        		/* ne treba da brise jer ovde dodaje drugi deo novca
	            	if (td != null && td.getId() != null && td.getId() > 0) {
	            		
	            		transakcijaRepository.delete(td);
	            		
	            	}
	            	*/
	        		
	            	
	        		Transakcija t = new Transakcija(stavkeIzvoda, ostaliRacuni, sp);
	            	Transakcija tResult = transakcijaRepository.save(t);
	            	stavkeIzvoda.setTransakcija(tResult);
	            	stavkeIzvoda.setId(null);
	            	StavkeIzvoda result = stavkeIzvodaRepository.save(stavkeIzvoda);
	            	
	            	return ResponseEntity.ok()
	                        .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, stavkeIzvoda.getId().toString()))
	                        .body(stavkeIzvodaTransakcija);
       
                } else {
                	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.stavkeIzvoda.sifraStanaNePostoji", "")).body(stavkeIzvodaTransakcija);
                }
        
        
    }

    /**
     * {@code GET  /stavke-izvodas} : get all the stavkeIzvodas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stavkeIzvodas in body.
     */
    @GetMapping("/stavke-izvodas")
    public ResponseEntity<List<StavkeIzvoda>> getAllStavkeIzvodas(Pageable pageable) {
        log.debug("REST request to get a page of StavkeIzvodas");
        Page<StavkeIzvoda> page = stavkeIzvodaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stavke-izvodas/:id} : get the "id" stavkeIzvoda.
     *
     * @param id the id of the stavkeIzvoda to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stavkeIzvoda, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stavke-izvodas/{id}")
    public ResponseEntity<StavkeIzvodaTransakcijaDTO> getStavkeIzvoda(@PathVariable Long id) {
        log.debug("REST request to get StavkeIzvoda : {}", id);
        StavkeIzvodaTransakcijaDTO stavkeIzvodaTransakcija = new StavkeIzvodaTransakcijaDTO();
        Optional<StavkeIzvoda> stavkeIzvoda = stavkeIzvodaRepository.findById(id);
        StavkeIzvoda si = stavkeIzvoda.get();
        stavkeIzvodaTransakcija.setStavkeIzvoda(si);
        if(si.getTransakcija() != null){
        	SifraPromene sp = null;
        	Optional<Transakcija> tOptional = transakcijaRepository.findById(si.getTransakcija().getId());
        	tOptional.ifPresent(t -> {
        		stavkeIzvodaTransakcija.setSifraPromene(t.getSifraPromene());
        	});        	        	
        }
        return ResponseEntity.ok().body(stavkeIzvodaTransakcija);
    }

    /**
     * {@code DELETE  /stavke-izvodas/:id} : delete the "id" stavkeIzvoda.
     *
     * @param id the id of the stavkeIzvoda to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stavke-izvodas/{id}")
    public ResponseEntity<Void> deleteStavkeIzvoda(@PathVariable Long id) {
        log.debug("REST request to delete StavkeIzvoda : {}", id);
        stavkeIzvodaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
