package toplana.web.rest;

import toplana.FormPS;
import toplana.domain.NacrtRacuna;
import toplana.domain.Racun;
import toplana.domain.Stan;
import toplana.domain.StanjaPodstaniceZaRacun;
import toplana.repository.NacrtRacunaRepository;
import toplana.repository.RacunRepository;
import toplana.repository.StanRepository;
import toplana.service.RacunService;
import toplana.specifications.RacunSpecification;
import toplana.specifications.SearchCriteria;
import toplana.specifications.SearchOperation;
import toplana.web.rest.dto.RacunDTO;
import toplana.web.rest.dto.RekapitulacijaPoPdvDTO;
import toplana.web.rest.dto.RekapitulacijaPoPdvDelimicnaDTO;
import toplana.web.rest.dto.SearchRacunDTO;
import toplana.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link toplana.domain.Racun}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RacunResource {

    private final Logger log = LoggerFactory.getLogger(RacunResource.class);

    private static final String ENTITY_NAME = "racun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RacunRepository racunRepository;
    private final RacunService racunService;
    private final StanRepository stanRepository;
    private final NacrtRacunaRepository nacrtRacunaRepository;



	public RacunResource(RacunRepository racunRepository, RacunService racunService, StanRepository stanRepository,
			NacrtRacunaRepository nacrtRacunaRepository) {
		this.racunRepository = racunRepository;
		this.racunService = racunService;
		this.stanRepository = stanRepository;
		this.nacrtRacunaRepository = nacrtRacunaRepository;
	}

	/**
     * {@code POST  /racuns} : Create a new racun.
     *
     * @param racun the racun to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new racun, or with status {@code 400 (Bad Request)} if the racun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/racuns")
    public ResponseEntity<Racun> createRacun(@RequestBody Racun racun) throws URISyntaxException {
        log.debug("REST request to save Racun : {}", racun);
        if (racun.getId() != null) {
            throw new BadRequestAlertException("A new racun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Racun result = racunRepository.save(racun);
        return ResponseEntity.created(new URI("/api/racuns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /racuns} : Updates an existing racun.
     *
     * @param racun the racun to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated racun,
     * or with status {@code 400 (Bad Request)} if the racun is not valid,
     * or with status {@code 500 (Internal Server Error)} if the racun couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/racuns")
    public ResponseEntity<Racun> updateRacun(@RequestBody Racun racun) throws URISyntaxException {
        log.debug("REST request to update Racun : {}", racun);
        if (racun.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Racun result = racunRepository.save(racun);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, racun.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /racuns} : get all the racuns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of racuns in body.
     */
    @GetMapping("/racuns")
    public ResponseEntity<List<Racun>> getAllRacuns(Pageable pageable) {
        log.debug("REST request to get a page of Racuns");
        Page<Racun> page = racunRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /racuns/:id} : get the "id" racun.
     *
     * @param id the id of the racun to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the racun, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/racuns/{id}")
    public ResponseEntity<RacunDTO> getRacun(@PathVariable Long id) {
        log.debug("REST request to get Racun : {}", id);
        Optional<Racun> racun = racunRepository.findById(id);
        Racun r = racun.get();
        LocalDate dt = r.getDatumRacuna();
        List<Racun> racunPN = racunRepository.getPreviousAndNextById(r.getStan().getId(),dt);
        Set<StanjaPodstaniceZaRacun> a = r.getNacrtRacuna().getStanjaPodstaniceZaRacune();
        RacunDTO out = new RacunDTO(r);
        out.setPrevNextRacuni(racunPN);
        return ResponseEntity.ok().body(out);
    }

    /**
     * {@code DELETE  /racuns/:id} : delete the "id" racun.
     *
     * @param id the id of the racun to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/racuns/{id}")
    public ResponseEntity<Void> deleteRacun(@PathVariable Long id) {
        log.debug("REST request to delete Racun : {}", id);
        racunRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /***************************************************************************************************************
     * Pretraga racuna na osnovu zadatog kriterijuma
     * @param search
     * @param pageable
     * @return
     ****************************************************************************************************************/
    
    @PostMapping("/racuns/criteria")
    public ResponseEntity<List<Racun>> getAllRacunsCriteria(@RequestBody SearchRacunDTO search, Pageable pageable) {   
    	
	    RacunSpecification racunSpec = this.createSpecification(search);
	    
	    //msTitleRating.add(new SearchCriteria("sifra", "010230001", SearchOperation.EQUAL));
	    
	    //msTitleRating.add(new SearchCriteria("rating", 7, SearchOperation.GREATER_THAN));
	    
	    Page<Racun> page = racunRepository.findAll(racunSpec,pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
	    return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * Kreira specification na osnovu kriterijuma od klijenta
     * @param search
     * @return
     */
    private RacunSpecification createSpecification(SearchRacunDTO search) {
    	RacunSpecification racunSpec = new RacunSpecification();
	    if(search.getDatumOd() != null) {
	    	racunSpec.add(new SearchCriteria("Racun", "datumRacuna", search.getDatumOd(), SearchOperation.GREATER_THAN_EQUAL));
	    }
	    //if(search.isAzuriran()) {
	    	racunSpec.add(new SearchCriteria("Racun", "azuriran", search.isAzuriran(), SearchOperation.EQUAL));
	    //}
	    //if(search.isProknjizen()) {
	    	racunSpec.add(new SearchCriteria("Racun", "proknjizen", search.isProknjizen(), SearchOperation.EQUAL));
	    //}
	    if(search.getDatumDo() != null) {
	    	racunSpec.add(new SearchCriteria("Racun", "datumRacuna", search.getDatumDo(), SearchOperation.LESS_THAN_EQUAL));
	    }
	    if(search.getSifraStana() != null && !search.getSifraStana().trim().equals("")) {
	    	racunSpec.add(new SearchCriteria("Stan", "sifra", search.getSifraStana(), SearchOperation.MATCH));
	    }
	    if(search.getPodstanica() != null) {
	    	racunSpec.add(new SearchCriteria("Stan", "podstanica", search.getPodstanica().getId(), SearchOperation.EQUAL));
	    }
	    if(search.getPrezime() != null && !search.getPrezime().trim().equals("")) {
	    	racunSpec.add(new SearchCriteria("Vlasnik", "prezime", search.getPrezime(), SearchOperation.MATCH));
	    }
	    
	    if(search.getIme() != null && !search.getIme().trim().equals("")) {
	    	racunSpec.add(new SearchCriteria("Vlasnik", "ime", search.getIme(), SearchOperation.MATCH));
	    }
	    
	    return racunSpec;
    }
    
    
    /**
     * Stampanje liste racuna koja je definisana pomocu kriterijuma na strani za prikaz racuna
     * @param search
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/racuni-stampanje",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadFile(@RequestBody SearchRacunDTO search)
            throws IOException {    	
    	
    	
    	RacunSpecification racunSpec = this.createSpecification(search);
    	List<Racun> racuni = racunRepository.findAll(racunSpec);
    	String filename = racunService.createRacuneZaStampanje(racuni);
    	File file = new File(filename);   	       

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition","attachment; filename=\"" + filename +"\"");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }  
    
    /**
     * Stampanje samo jednog racuna
     * @param search
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/racuni-stampanje/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> stampanjeJednogRacuna(@PathVariable Long id)
            throws IOException {    	
    	
    	
    	
    	Optional<Racun> r = racunRepository.findById(id);
    	List<Racun> racuni = new ArrayList<Racun>();
    	racuni.add(r.get());
    	String filename = racunService.createRacuneZaStampanje(racuni);
    	File file = new File(filename);   	       
    	
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition","attachment; filename=\"" + racuni.get(0).getStan().getSifra() + ".pdf" +"\"");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }  
/**********************************************************************************************************
 * Kreiranje datoteke za psotansku stedionicu     
 * Ulaz je tekuci mesec
 * @param mesec
 * @return
 * @throws IOException
 ***********************************************************************************************************/
    @RequestMapping(value = "/racuni-kreiranje-datoteke-banka",
            method = RequestMethod.POST,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Resource> downloadFile(@RequestBody LocalDate mesec)
            throws IOException {    	    	
    	
    	YearMonth month = YearMonth.from(mesec);
    	LocalDate start = month.atDay(1);
    	LocalDate end   = month.atEndOfMonth();
    	
    	List<Racun> racuni = racunRepository.findAllForMonthAndBrojRacuna(start, end, "908-20001-18");
    	
    	
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	
    	/* old
    	for(Racun r : racuni) {
    		BigDecimal ukupnoZaduzenje = r.getUtrosakVarijabilni().add(r.getUtrosakFiksni()).add(r.getUtrosakOdrzavanje());
    		String zaduzenje = ukupnoZaduzenje.toString().replace(".","" ); //uklanja se decimalna tacka  		
    		
    		String zaduzenjeUpis = ("0000000000" + zaduzenje).substring(zaduzenje.length());// dodaju se nule na pocetku
    		String sifra = r.getStan().getSifra();
    		String sifraZaUpis = sifra.substring(0, 2) + "." + sifra.substring(2,5) + "." + sifra.substring(5);
    		//outputStream.writeBytes((r.getStan().getVlasnik().getPartijaRacuna() + sifraZaUpis + zaduzenjeUpis  + "\r\n").getBytes());	
    	}    	
    	*/
    	ByteArrayResource aa = new ByteArrayResource(outputStream.toByteArray());	     	   	     	

    	FormPS ps = new FormPS();
    	
    	ByteArrayResource bb = ps.createFile(racuni, end);
    	
    	     
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Content-Type", "text/plain; charset=utf-8");
    	headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    	headers.add("Pragma", "no-cache");
    	headers.add("Expires", "0");

        
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(bb);
    }  
    

}
