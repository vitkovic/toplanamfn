package toplana.web.rest;

import toplana.domain.Izvod;
import toplana.domain.Podstanica;
import toplana.domain.Stan;
import toplana.domain.StanStanje;
import toplana.domain.StanjaPodstanice;
import toplana.domain.StavkeIzvoda;
import toplana.repository.PodstanicaRepository;
import toplana.repository.StanjaPodstaniceRepository;
import toplana.repository.StanStanjeRepository;
import toplana.repository.StanRepository;
import toplana.web.rest.dto.IzvodDTO;
import toplana.web.rest.dto.StanStanjeDTO;
import toplana.web.rest.dto.StanjaPodstaniceBatchDTO;
import toplana.web.rest.errors.BadRequestAlertException;
import toplana.LatestPointReading;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
    private final StanStanjeRepository stanstanjaRepository;
    private final StanRepository stanRepository;



    public StanjaPodstaniceResource(StanjaPodstaniceRepository stanjaPodstaniceRepository,
			PodstanicaRepository podstanicaRepository, StanStanjeRepository stanjeRepository, StanRepository stanRepository ) {
		super();
		this.stanjaPodstaniceRepository = stanjaPodstaniceRepository;
		this.podstanicaRepository = podstanicaRepository;
		this.stanstanjaRepository = stanjeRepository;
		this.stanRepository = stanRepository;
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
    
    
    @PostMapping("/nstanice-upload") 
    public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file )  throws URISyntaxException {

    	StanStanjeDTO stanstanjeDto = null;
    	URI target = ServletUriComponentsBuilder.fromCurrentContextPath()
    		    .path("/stanja-podstanice/")
    		    .build()
    		    .toUri();

    	try {
    		String fileName = file.getOriginalFilename();
    		 BufferedReader reader = null;
    		// extract zip
    		
    		String tempFileName= "";
    		String extension= "";
    		
    		 if (!(fileName == null || fileName.lastIndexOf('.') == -1)) {
    			    tempFileName = fileName.substring(0, fileName.lastIndexOf('.')); 
    			    extension =  fileName.substring(fileName.length() - 3); 
    		    } 
    		 
    		 LocalDate today = LocalDate.now();
    		 String TodayasString = today.toString(); 
    		 
    		 fileName = tempFileName;
    		 
    		 String destDir = "c:/toplana/novestanicestanja/"+fileName+TodayasString+"/";
		     Path destPath = Paths.get(destDir);
		     if (Files.notExists(destPath)) {
		                Files.createDirectories(destPath);
		     }
		    		
		     InputStream inputStream =  new BufferedInputStream(file.getInputStream());  
    	     reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  // direktno citanje   
    	     
    	     
    	     //String csvFile = Paths.get(file.getOriginalFilename()).toString();
    	     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    	        List<StanStanjeDTO> readings = new ArrayList<>();

    	        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
    	            // skip header
    	            String line = br.readLine();
    	            while ((line = br.readLine()) != null) {
    	                String[] parts = line.split(",", -1);

    	                if (parts.length >= 3) {
    	                    // Example Point name: "07.291.0026|BNT27A ..."
    	                    String rawPoint = parts[0].trim();
    	                    String pointCode = rawPoint.split("\\|")[0]        // take "07.291.0026"
    	                                             .replace(".", "");       // remove dots → "072910026"

    	                    LocalDateTime time = LocalDateTime.parse(parts[1].trim(), formatter);
    	                    Double val = Double.parseDouble(parts[2].trim());
    	                    Long value = val.longValue();
    	                    readings.add(new StanStanjeDTO(pointCode, time.toLocalDate(), value));
    	                }
    	            }
    	        } catch (Exception e) {
    	           throw e;
    	        }

    	     // readings: List<StanStanjeDTO>
    	        Map<String, StanStanjeDTO> latestReadings = readings.stream()
    	            .collect(Collectors.toMap(
    	                StanStanjeDTO::getSifra,   // key mapper (point code)
    	                Function.identity(),       // value mapper (the DTO itself)
    	                BinaryOperator.maxBy(Comparator.comparing(StanStanjeDTO::getDatum)) // merge: keep later
    	            ));
    	        
    	        List<StanStanje> StanStanjaS = new ArrayList<>();
    	       // Stan stan = new Stan();
    	       
    	     // 1) Collect distinct sifre we need
    	        List<String> sifreToLoad = latestReadings.values().stream()
    	            .map(StanStanjeDTO::getSifra)
    	            .distinct()
    	            .collect(Collectors.toList());

    	        // 2) Bulk load Stan and index by sifra
    	        List<Stan> stans = stanRepository.findAllBySifraIn(sifreToLoad);
    	        Map<String, Stan> stanBySifra = stans.stream()
    	            .collect(Collectors.toMap(Stan::getSifra, Function.identity()));

    	        // 3) Sanity check: make sure all sifre exist
    	        List<String> missing = sifreToLoad.stream()
    	            .filter(s -> !stanBySifra.containsKey(s))
    	            .collect(Collectors.toList());

    	        if (!missing.isEmpty()) {
    	            throw new IllegalStateException("No Stan found for sifre: " + missing);
    	        }

    	        // 4) Build entities
    	        List<StanStanje> stanStanja = latestReadings.values().stream()
    	            .sorted(Comparator.comparing(StanStanjeDTO::getSifra))
    	            .map(r -> {
    	                Stan stan = stanBySifra.get(r.getSifra()); // guaranteed non-null
    	                // make sure this constructor sets both fields (dto fields + stan)
    	                return new StanStanje(
    	                    r.getSifra(),
    	                    r.getDatum(),
    	                    r.getVrednost(),
    	                    stan
    	                );
    	            })
    	            .collect(Collectors.toList());
    	        
    	        
    	        
    	        System.out.println("Sifra        | Datum               | Vrednost | Stan(Sifra/ID)");
    	        System.out.println("-------------+---------------------+----------+----------------");
    	        stanStanja.forEach(s -> {
    	            String stanInfo = "-";
    	            if (s.getStan() != null) {
    	                // Prefer stan.sifra if available, else stan.id
    	                if (s.getStan().getId() != null) {
    	                    stanInfo = String.valueOf(s.getStan().getId());
    	                }
    	            }

    	            System.out.printf("%-12s | %-19s | %8d | %s%n",
    	                s.getSifra(),
    	                String.valueOf(s.getDatum()),   // or format with DateTimeFormatter if you like
    	                s.getVrednost(),
    	                stanInfo
    	            );
    	        }); 
    	        
    	       
 
    	        this.stanstanjaRepository.saveAll(stanStanja);
    	     
    	   } catch (Exception e) {
	            e.printStackTrace();
	        

	        		return ResponseEntity.status(HttpStatus.BAD_REQUEST).location(target).build();
	        }
    	  
    	
    		return ResponseEntity.status(HttpStatus.OK).location(target).build();
    	}
    	
        
    
}
