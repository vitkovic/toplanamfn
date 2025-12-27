package toplana.web.rest;

import toplana.domain.Racun;
import toplana.domain.Vlasnik;
import toplana.repository.VlasnikRepository;
import toplana.specifications.VlasnikSpecification;
import toplana.specifications.SearchCriteria;
import toplana.specifications.SearchOperation;
import toplana.web.rest.dto.SearchRacunDTO;
import toplana.web.rest.dto.SearchVlasnikDTO;
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
 * REST controller for managing {@link toplana.domain.Vlasnik}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VlasnikResource {

    private final Logger log = LoggerFactory.getLogger(VlasnikResource.class);

    private static final String ENTITY_NAME = "vlasnik";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VlasnikRepository vlasnikRepository;

    public VlasnikResource(VlasnikRepository vlasnikRepository) {
        this.vlasnikRepository = vlasnikRepository;
    }

    /**
     * {@code POST  /vlasniks} : Create a new vlasnik.
     *
     * @param vlasnik the vlasnik to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vlasnik, or with status {@code 400 (Bad Request)} if the vlasnik has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vlasniks")
    public ResponseEntity<Vlasnik> createVlasnik(@Valid @RequestBody Vlasnik vlasnik) throws URISyntaxException {
        log.debug("REST request to save Vlasnik : {}", vlasnik);
        if (vlasnik.getId() != null) {
            throw new BadRequestAlertException("A new vlasnik cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vlasnik result = vlasnikRepository.save(vlasnik);
        return ResponseEntity.created(new URI("/api/vlasniks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vlasniks} : Updates an existing vlasnik.
     *
     * @param vlasnik the vlasnik to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vlasnik,
     * or with status {@code 400 (Bad Request)} if the vlasnik is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vlasnik couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vlasniks")
    public ResponseEntity<Vlasnik> updateVlasnik(@Valid @RequestBody Vlasnik vlasnik) throws URISyntaxException {
        log.debug("REST request to update Vlasnik : {}", vlasnik);
        if (vlasnik.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vlasnik result = vlasnikRepository.save(vlasnik);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vlasnik.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vlasniks} : get all the vlasniks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vlasniks in body.
     */
    @GetMapping("/vlasniks")
    public ResponseEntity<List<Vlasnik>> getAllVlasniks(Pageable pageable) {
        log.debug("REST request to get a page of Vlasniks");
        Page<Vlasnik> page = vlasnikRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /vlasniks/:id} : get the "id" vlasnik.
     *
     * @param id the id of the vlasnik to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vlasnik, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vlasniks/{id}")
    public ResponseEntity<Vlasnik> getVlasnik(@PathVariable Long id) {
        log.debug("REST request to get Vlasnik : {}", id);
        Optional<Vlasnik> vlasnik = vlasnikRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vlasnik);
    }

    /**
     * {@code DELETE  /vlasniks/:id} : delete the "id" vlasnik.
     *
     * @param id the id of the vlasnik to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vlasniks/{id}")
    public ResponseEntity<Void> deleteVlasnik(@PathVariable Long id) {
        log.debug("REST request to delete Vlasnik : {}", id);
        vlasnikRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /***************************************************************************************************************
     * Pretraga racuna na osnovu zadatog kriterijuma
     * @param search
     * @param pageable
     * @return
     ****************************************************************************************************************/
    
    @PostMapping("/vlasniks/criteria")
    public ResponseEntity<List<Vlasnik>> getAllVlasniksCriteria(@RequestBody SearchVlasnikDTO search, Pageable pageable) {   
    	
	    VlasnikSpecification vlasnikSpec = this.createSpecification(search);
	    
	    //msTitleRating.add(new SearchCriteria("sifra", "010230001", SearchOperation.EQUAL));
	    
	    //msTitleRating.add(new SearchCriteria("rating", 7, SearchOperation.GREATER_THAN));
	    
	    Page<Vlasnik> page = vlasnikRepository.findAll(vlasnikSpec,pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	    return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * Kreira specification na osnovu kriterijuma od klijenta
     * @param search
     * @return
     */
    private VlasnikSpecification createSpecification(SearchVlasnikDTO search) {
    	VlasnikSpecification vlasnikSpec = new VlasnikSpecification();
	   
	    if(search.getSifraStana() != null && !search.getSifraStana().trim().equals("")) {
	    	vlasnikSpec.add(new SearchCriteria("Stan", "sifra", search.getSifraStana(), SearchOperation.MATCH));
	    }
	   
	    if(search.getPrezime() != null && !search.getPrezime().trim().equals("")) {
	    	vlasnikSpec.add(new SearchCriteria("Vlasnik", "prezime", search.getPrezime(), SearchOperation.MATCH));
	    }
	    
	    if(search.getIme() != null && !search.getIme().trim().equals("")) {
	    	vlasnikSpec.add(new SearchCriteria("Vlasnik", "ime", search.getIme(), SearchOperation.MATCH));
	    }
	    
	    return vlasnikSpec;
    }
    
    
    
}
