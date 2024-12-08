package toplana.web.rest;

import toplana.domain.Racun;
import toplana.domain.Stan;
import toplana.domain.Transakcija;
import toplana.repository.StanRepository;
import toplana.repository.TransakcijaCustomRepository;
import toplana.repository.TransakcijaRepository;
import toplana.service.TransakcijaService;
import toplana.specifications.RacunSpecification;
import toplana.specifications.TransakcijaSpecification;
import toplana.web.rest.dto.DugujePotrazujeReoni;
import toplana.web.rest.dto.RekapitulacijaSifraPromeneDatumDTO;
import toplana.web.rest.dto.SearchRacunDTO;
import toplana.web.rest.dto.SearchTransakcijaDTO;
import toplana.web.rest.dto.TransakcijaStanUkupnoDTO;
import toplana.web.rest.dto.TransakcijaZaStanDTO;
import toplana.web.rest.dto.TransakcijeReoniDTO;
import toplana.web.rest.dto.TransakcijeZaStanZbirnoDTO;
import toplana.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link toplana.domain.Transakcija}.
 */
@RestController
@RequestMapping("/api")
public class TransakcijaResource {

    private final Logger log = LoggerFactory.getLogger(TransakcijaResource.class);

    private static final String ENTITY_NAME = "transakcija";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransakcijaService transakcijaService;
    private final TransakcijaRepository transakcijaRepository;
    private final TransakcijaCustomRepository transakcijaCustomRepository; 
    private final StanRepository stanRepository;

	
	public TransakcijaResource(TransakcijaService transakcijaService, TransakcijaRepository transakcijaRepository,
			TransakcijaCustomRepository transakcijaCustomRepository, StanRepository stanRepository) {
		super();
		this.transakcijaService = transakcijaService;
		this.transakcijaRepository = transakcijaRepository;
		this.transakcijaCustomRepository = transakcijaCustomRepository;
		this.stanRepository = stanRepository;
	}


	/**
     * {@code POST  /transakcijas} : Create a new transakcija.
     *
     * @param transakcija the transakcija to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transakcija, or with status {@code 400 (Bad Request)} if the transakcija has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transakcijas")
    public ResponseEntity<Transakcija> createTransakcija(@RequestBody Transakcija transakcija) throws URISyntaxException {
        log.debug("REST request to save Transakcija : {}", transakcija);
        if (transakcija.getId() != null) {
            throw new BadRequestAlertException("A new transakcija cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transakcija result = transakcijaService.save(transakcija);
        return ResponseEntity.created(new URI("/api/transakcijas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }    
   

    /**
     * {@code PUT  /transakcijas} : Updates an existing transakcija.
     *
     * @param transakcija the transakcija to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transakcija,
     * or with status {@code 400 (Bad Request)} if the transakcija is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transakcija couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transakcijas")
    public ResponseEntity<Transakcija> updateTransakcija(@RequestBody Transakcija transakcija) throws URISyntaxException {
        log.debug("REST request to update Transakcija : {}", transakcija);
        if (transakcija.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transakcija result = transakcijaService.save(transakcija);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transakcija.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transakcijas} : get all the transakcijas.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transakcijas in body.
     */
    @GetMapping("/transakcijas")
    public ResponseEntity<List<Transakcija>> getAllTransakcijas(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("stavkaizvoda-is-null".equals(filter)) {
            log.debug("REST request to get all Transakcijas where stavkaIzvoda is null");
            return new ResponseEntity<>(transakcijaService.findAllWhereStavkaIzvodaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Transakcijas");
        Page<Transakcija> page = transakcijaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transakcijas/:id} : get the "id" transakcija.
     *
     * @param id the id of the transakcija to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transakcija, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transakcijas/{id}")
    public ResponseEntity<Transakcija> getTransakcija(@PathVariable Long id) {
        log.debug("REST request to get Transakcija : {}", id);
        Optional<Transakcija> transakcija = transakcijaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transakcija);
    }

    /**
     * {@code DELETE  /transakcijas/:id} : delete the "id" transakcija.
     *
     * @param id the id of the transakcija to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transakcijas/{id}")
    public ResponseEntity<Void> deleteTransakcija(@PathVariable Long id) {
        log.debug("REST request to delete Transakcija : {}", id);
        transakcijaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    /**
     * Vraca se zbirni prikaz transakcija za zadate kriterijume (stanje, duguje, potrazuje, prezime i sifra stana)
     * @param search
     * Salje se u datatable i tamo se radi pagination i search
     * @return
     */
    @PostMapping("/transakcijas-criteria")
    public List<TransakcijaStanUkupnoDTO> getAllRacunsCriteria(@RequestBody SearchTransakcijaDTO search, Pageable pageable) {   
	    //TransakcijaSpecification transakcijaSpec = transakcijaService.createSpecification(search);
	    //List<TransakcijaStanUkupnoDTO> transakcije = transakcijaCustomRepository.findAllSumed(transakcijaSpec);
    	List<TransakcijaStanUkupnoDTO> transakcije = transakcijaService.findAllSumed(search);
	    return transakcije;
    }
    
    /**
     * Dolazi se sa strane knjigovodstveni dnevnik, analiticki. Prikazuju se transakcije koje zadovoljvaju kriterijume
     * @param search
     * @return
     */
    
    @PostMapping("/transakcijas-criteria-analiticki-dnevnik")
    public TransakcijeReoniDTO getAllRacunsCriteriaAnalitickiDnevnik(@RequestBody SearchTransakcijaDTO search) {   
	   // TransakcijaSpecification transakcijaSpec = transakcijaService.createSpecificationDnevnik(search);
	   // List<TransakcijaZaStanDTO> transakcije = transakcijaCustomRepository.findAllForDnevnik(transakcijaSpec);
	   // List<DugujePotrazujeReoni> dpr = transakcijaCustomRepository.findSumForDnevnik(transakcijaSpec);
    	List<TransakcijaZaStanDTO> transakcije = transakcijaService.findAllForAnalitickiDnevnik(search);
    	List<DugujePotrazujeReoni> dpr = transakcijaService.findSumForDnevnik(search);
	    
	    TransakcijeReoniDTO out = new TransakcijeReoniDTO(transakcije, dpr);
	    return out;
    }
    
/*************************************************************************************************************
 * Stampanje analitickog dnevnika. Isto kao prethodno, ali sada se pravi PDF        
 * @param search
 * @return
 **************************************************************************************************************/
    @RequestMapping(value = "/transakcijas-criteria-analiticki-dnevnik-stampanje",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getAllRacunsCriteriaAnalitickiDnevnikStampanje(@RequestBody SearchTransakcijaDTO search) 
    		throws IOException{   
	   // TransakcijaSpecification transakcijaSpec = transakcijaService.createSpecificationDnevnik(search);
	   // List<TransakcijaZaStanDTO> transakcije = transakcijaCustomRepository.findAllForDnevnik(transakcijaSpec);
	   // List<DugujePotrazujeReoni> dpr = transakcijaCustomRepository.findSumForDnevnik(transakcijaSpec);
    	List<TransakcijaZaStanDTO> transakcije = transakcijaService.findAllForAnalitickiDnevnik(search);
    	List<DugujePotrazujeReoni> dpr = transakcijaService.findSumForDnevnik(search);	    
	    TransakcijeReoniDTO out = new TransakcijeReoniDTO(transakcije, dpr);
	    
	    String filename = transakcijaService.generateReportAnalitickiDnevnik(transakcije);
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
    
/*******************************************************************************************************
 * Sve transakcije za stan    
 * @param sifraStana
 * @return
 ********************************************************************************************************/
    @GetMapping("/transakcijas/sve-prikaz/{sifraStana}")
    public TransakcijeZaStanZbirnoDTO getAllTransakcijeZaStan(@PathVariable String sifraStana) {
        log.debug("REST request to get a page of Transakcije zbirno za stan");
        TransakcijeZaStanZbirnoDTO trans = null;
        Stan stan = stanRepository.findBySifra(sifraStana);
        if(stan != null) {        
        	trans = transakcijaService.findAllByStanOrderByDatum(stan);
        }else {
        	trans = transakcijaService.findAllByDodatniRacunOrderByDatum(sifraStana);
        }
        
        
        return trans;
    }
/*******************************************************************************************************
 * Sve transakcije za stan    stampanje
 * @param sifraStana
 * @return
 ********************************************************************************************************/    
    @RequestMapping(value = "/transakcijas/sve-prikaz-stampanje",
    	    method = RequestMethod.POST,
    	    produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> stampajAllTransakcijeZaStan(@RequestBody SearchTransakcijaDTO search) 
    		throws IOException{
        log.debug("REST request to get a page of Transakcije zbirno za stan");
        TransakcijeZaStanZbirnoDTO trans = null;
        String sifra = search.getSifraStana();
        Stan stan = stanRepository.findBySifra(sifra);
        if(stan != null) {        
        	trans = transakcijaService.findAllByStanOrderByDatum(stan);
        }else {
        	trans = transakcijaService.findAllByDodatniRacunOrderByDatum(sifra);
        }
        
        String filename = transakcijaService.generateReportAnalitickaKartica(trans.getTransakcije());
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
/********************************************************************************************************
 * rekapitulacija po datumima i sifri promene    
 * @param search
 * @return
 *********************************************************************************************************/
    @PostMapping("/transakcijas-rekapitulacija-sifra-promene-datum")
    public List<RekapitulacijaSifraPromeneDatumDTO> rekapitulacijaSifraPromeneDatum(@RequestBody SearchTransakcijaDTO search) {   
    	List<RekapitulacijaSifraPromeneDatumDTO> out = transakcijaService.rekapitulacijaSifraPromeneDatum(search);
	    return out;
    }

/********************************************************************************************************
* rekapitulacija po datumima i sifri promene    
* @param search
* @return
*********************************************************************************************************/
    @RequestMapping(value = "/transakcijas-rekapitulacija-sifra-promene-datum-stampanje",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> rekapitulacijaSifraPromeneDatumStampanje(@RequestBody SearchTransakcijaDTO search) 
    		throws IOException{
    	List<RekapitulacijaSifraPromeneDatumDTO> out = transakcijaService.rekapitulacijaSifraPromeneDatum(search);
    	String filename = transakcijaService.generateReportRekapitulacijaSifraPromeneDatum(out);
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
    
/********************************************************************************************************
 * Sinteticki dnevnik    
 * @param search
 * @return
 *********************************************************************************************************/
    @PostMapping("/transakcijas-sinteticki-dnevnik")
    public List<RekapitulacijaSifraPromeneDatumDTO> sintetickiDnevnik(@RequestBody SearchTransakcijaDTO search) {   
    	List<RekapitulacijaSifraPromeneDatumDTO> out = transakcijaService.sintetickiDnevnik(search);
	    return out;
    }
    
/********************************************************************************************************
 * Sinteticki dnevnik stampanje
 * @param search
 * @return
 *********************************************************************************************************/
    @RequestMapping(value = "/transakcijas-sinteticki-dnevnik-stampanje",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> sintetickiDnevnikStampanje(@RequestBody SearchTransakcijaDTO search) 
    		throws IOException{   
    	List<RekapitulacijaSifraPromeneDatumDTO> out = transakcijaService.sintetickiDnevnik(search);
    	
    	String filename = transakcijaService.generateReportSintetickiDnevnik(out);
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
    
   
}
