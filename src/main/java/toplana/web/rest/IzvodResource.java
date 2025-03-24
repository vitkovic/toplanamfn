package toplana.web.rest;

import toplana.domain.Izvod;
import toplana.domain.NacrtRacuna;
import toplana.domain.StavkeIzvoda;
import toplana.domain.StavkeIzvodaPostanska;
import toplana.repository.IzvodRepository;
import toplana.repository.StavkeIzvodaPostanskaRepository;
import toplana.repository.StavkeIzvodaRepository;
import toplana.service.IzvodService;
import toplana.web.rest.dto.IzvodDTO;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.*;
import java.nio.file.*;
import java.io.*;

/**
 * REST controller for managing {@link toplana.domain.Izvod}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class IzvodResource {

    private final Logger log = LoggerFactory.getLogger(IzvodResource.class);

    private static final String ENTITY_NAME = "izvod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IzvodRepository izvodRepository;
    private final StavkeIzvodaRepository stavkeIzvodaRepository;
    private final IzvodService izvodService;
    private final StavkeIzvodaPostanskaRepository stavkeIzvodaPostanskaRepository;
   

	public IzvodResource(IzvodRepository izvodRepository, StavkeIzvodaRepository stavkeIzvodaRepository,
			IzvodService izvodService, StavkeIzvodaPostanskaRepository stavkeIzvodaPostanskaRepository) {
		this.izvodRepository = izvodRepository;
		this.stavkeIzvodaRepository = stavkeIzvodaRepository;
		this.izvodService = izvodService;
		this.stavkeIzvodaPostanskaRepository = stavkeIzvodaPostanskaRepository;
	}

	/**
     * {@code POST  /izvods} : Create a new izvod.
     *
     * @param izvod the izvod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new izvod, or with status {@code 400 (Bad Request)} if the izvod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/izvods")
    public ResponseEntity<Izvod> createIzvod(@RequestBody Izvod izvod) throws URISyntaxException {
        log.debug("REST request to save Izvod : {}", izvod);
        if (izvod.getId() != null) {
            throw new BadRequestAlertException("A new izvod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Izvod result = izvodRepository.save(izvod);
        return ResponseEntity.created(new URI("/api/izvods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    // find file in a specific folder
    public static Optional<Path> findFileByName(String directoryPath, String fileNameToFind) throws IOException {
        Path dir = Paths.get(directoryPath);

        if (!Files.isDirectory(dir)) {
            throw new IllegalArgumentException("Provided path is not a directory: " + directoryPath);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, entry -> entry.getFileName().toString().contains(fileNameToFind))) {
            for (Path entry : stream) {
            	if (getFileExtension(entry).equalsIgnoreCase("xml"))
            		return Optional.of(entry); // Found the file
            }
        }
        return Optional.empty(); // File not found
    }
    
    public static String getFileExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return ""; // No extension found
        }
    }
    
    @PostMapping("/izvods-upload") 
    public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file )  throws URISyntaxException {

    	IzvodDTO izvodDto = null;
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
    		 
    		 fileName = tempFileName;
    		 
    		if (extension.equalsIgnoreCase("zip")) {
    		
		    		String destDir = "c:/toplana/izvodi/"+fileName+"/";
		    		Path destPath = Paths.get(destDir);
		            if (Files.notExists(destPath)) {
		                Files.createDirectories(destPath);
		            }
		    		
		            
		            try (ZipInputStream zipIn = new ZipInputStream(file.getInputStream())) {
		                ZipEntry entry = zipIn.getNextEntry();
		                while (entry != null) {
		                    Path filePath = destPath.resolve(entry.getName()).normalize();
		
		                    // Prevent Zip Slip vulnerability
		                    if (!filePath.startsWith(destPath)) {
		                        throw new IOException("Bad ZIP entry: " + entry.getName());
		                    }
		
		                    if (entry.isDirectory()) {
		                        Files.createDirectories(filePath);
		                    } else {
		                        Files.createDirectories(filePath.getParent()); // Ensure parent directories exist
		                        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(filePath))) {
		                            byte[] buffer = new byte[4096];
		                            int bytesRead;
		                            while ((bytesRead = zipIn.read(buffer)) != -1) {
		                                bos.write(buffer, 0, bytesRead);
		                            }
		                        }
		                    }
		                    zipIn.closeEntry();
		                    entry = zipIn.getNextEntry();
		            
		                }
		            }
		            
		            File fileFromFolder = null;
		            
		            try {
		                Optional<Path> fileFound = findFileByName(destDir, "2696760");
		                fileFromFolder = fileFound.orElseThrow().toFile();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		            
		           reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileFromFolder), "UTF-8"));
    		} else {
    			InputStream inputStream =  new BufferedInputStream(file.getInputStream());  
    			reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  // direktno citanje   
    		}
            
    		XmlMapper xmlMap = new XmlMapper();    
    		// create instance of File  
    	//	InputStream inputStream =  new BufferedInputStream(file.getInputStream());  
    		// read data from College.xml and store it into xmlString  
    		String xmlString;        
    		StringBuilder builder = new StringBuilder();  
    		String line;  
    	//	BufferedReader reader = new BufferedReader(new FileReader(fileFromFolder)); // kad je iz zipa
    	//	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));   direktno citanje     
    		while ((line = reader.readLine()) != null) {  
    			builder.append(line);  
    		}  
    		reader.close();  
    		xmlString = builder.toString();  
    		izvodDto = xmlMap.readValue(xmlString, IzvodDTO.class);     		
    	} catch (JsonMappingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (JsonProcessingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	
     
    	if(izvodDto != null) {
    		Izvod izvod = new Izvod(izvodDto);
    		Izvod result = izvodRepository.save(izvod);
    		List<StavkeIzvoda> stavke = izvod.getStavkaIzvodas();
    		for(StavkeIzvoda s : stavke) {
    			s.setIzvod(result);
    		}
    		stavkeIzvodaRepository.saveAll(stavke);
    		return ResponseEntity.created(new URI("/api/izvods/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
    	}else {
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.izvod.neMozeDaSeProcita", "")).body(new Izvod());
    	}
    }
    
   

    /**
     * {@code PUT  /izvods} : Updates an existing izvod.
     *
     * @param izvod the izvod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated izvod,
     * or with status {@code 400 (Bad Request)} if the izvod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the izvod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/izvods")
    public ResponseEntity<Izvod> updateIzvod(@RequestBody Izvod izvod) throws URISyntaxException {
        log.debug("REST request to update Izvod : {}", izvod);
        if (izvod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Izvod result = izvodRepository.save(izvod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, izvod.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /izvods} : get all the izvods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of izvods in body.
     */
    @GetMapping("/izvods")
    public ResponseEntity<List<Izvod>> getAllIzvods(Pageable pageable) {
        log.debug("REST request to get a page of Izvods");
        Page<Izvod> page = izvodRepository.findAllByTipIsNull(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /izvods/:id} : get the "id" izvod.
     *
     * @param id the id of the izvod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the izvod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/izvods/{id}")
    public ResponseEntity<Izvod> getIzvod(@PathVariable Long id) {
        log.debug("REST request to get Izvod : {}", id);
        Optional<Izvod> izvod = izvodRepository.pronadjiPrekoId(id);
        return ResponseUtil.wrapOrNotFound(izvod);
    }

    /**
     * {@code DELETE  /izvods/:id} : delete the "id" izvod.
     *
     * @param id the id of the izvod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/izvods/{id}")
    public ResponseEntity<Void> deleteIzvod(@PathVariable Long id) {
        log.debug("REST request to delete Izvod : {}", id);
        Optional<Izvod> iOptional = izvodRepository.findById(id);
        Izvod i = iOptional.get();
        List<StavkeIzvoda> si = stavkeIzvodaRepository.findByIzvodIdAndTransakcijaNotNullOrTransakcijaStaraNotNull(id); 
        if(si != null && !si.isEmpty()) {
        	return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.izvod.stavkeIzvodaProknjizene", "")).build();
        }else {
        	stavkeIzvodaRepository.deleteByIzvodId(id);
            izvodRepository.deleteById(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        }        
    }
    /*************************************************************************************************************
     * Datoteke iz postanske stedionice - prikaz svih 
     * @param pageable
     * @return
     **************************************************************************************************************/
    @GetMapping("/izvod-postanskas")
    public ResponseEntity<List<Izvod>> getAllIzvodPostanskas(Pageable pageable) {
        log.debug("REST request to get a page of Izvods");
        Page<Izvod> page = izvodRepository.findAllByTip("POSTANSKA",pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /***************************************************************************************************************
     * Upload datoteke iz postanske stedionice
     ****************************************************************************************************************/
    @PostMapping(value="/izvod-postanskas-upload", consumes = {"multipart/form-data"}) 
    public ResponseEntity<?> handleFilePostanskaUpload( @RequestParam("file") MultipartFile file )  throws URISyntaxException {

    	try {
    		Izvod result = izvodService.readFilePostanska(file);
    		return ResponseEntity.created(new URI("/api/izvods/" + result.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                    .body(result);
    	}catch(Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.izvod.neMozeDaSeProcita", "")).body(new Izvod());
    	}    	  	
    	
    }
    
    /*****************************************************************************************************************
     * Brisanje izvoda iz postanske stedionice
     * @param id
     * @return
     ******************************************************************************************************************/
    @DeleteMapping("/izvod-postanskas/{id}")
    public ResponseEntity<Void> deleteIzvodPostanska(@PathVariable Long id) {
        log.debug("REST request to delete Izvod : {}", id);
        Optional<Izvod> iOptional = izvodRepository.findById(id);
        Izvod i = iOptional.get();
        List<StavkeIzvodaPostanska> si = stavkeIzvodaPostanskaRepository.findByIzvodIdAndTransakcijaNotNullOrTransakcijaStaraNotNull(id); 
        if(si != null && !si.isEmpty()) {
        	return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.izvod.stavkeIzvodaProknjizene", "")).build();
        }else {
        	stavkeIzvodaPostanskaRepository.deleteByIzvodId(id);
            izvodRepository.deleteById(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        }        
    }
    /******************************************************************************************************************
     * 
     * @param id
     * @return
     *******************************************************************************************************************/
    @GetMapping("/izvod-postanskas/{id}")
    public ResponseEntity<Izvod> getIzvodPostanska(@PathVariable Long id) {
        log.debug("REST request to get Izvod : {}", id);
        Optional<Izvod> izvod = izvodRepository.pronadjiPostanskaPrekoId(id);
        return ResponseUtil.wrapOrNotFound(izvod);
    }
}
