package toplana.web.rest;

import toplana.domain.SifraPromene;
import toplana.repository.SifraPromeneRepository;
import toplana.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link toplana.domain.SifraPromene}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SifraPromeneResource {

    private final Logger log = LoggerFactory.getLogger(SifraPromeneResource.class);

    private static final String ENTITY_NAME = "sifraPromene";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SifraPromeneRepository sifraPromeneRepository;

    public SifraPromeneResource(SifraPromeneRepository sifraPromeneRepository) {
        this.sifraPromeneRepository = sifraPromeneRepository;
    }

    /**
     * {@code POST  /sifra-promenes} : Create a new sifraPromene.
     *
     * @param sifraPromene the sifraPromene to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sifraPromene, or with status {@code 400 (Bad Request)} if the sifraPromene has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sifra-promenes")
    public ResponseEntity<SifraPromene> createSifraPromene(@RequestBody SifraPromene sifraPromene) throws URISyntaxException {
        log.debug("REST request to save SifraPromene : {}", sifraPromene);
        if (sifraPromene.getId() != null) {
            throw new BadRequestAlertException("A new sifraPromene cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SifraPromene result = sifraPromeneRepository.save(sifraPromene);
        return ResponseEntity.created(new URI("/api/sifra-promenes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sifra-promenes} : Updates an existing sifraPromene.
     *
     * @param sifraPromene the sifraPromene to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sifraPromene,
     * or with status {@code 400 (Bad Request)} if the sifraPromene is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sifraPromene couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sifra-promenes")
    public ResponseEntity<SifraPromene> updateSifraPromene(@RequestBody SifraPromene sifraPromene) throws URISyntaxException {
        log.debug("REST request to update SifraPromene : {}", sifraPromene);
        if (sifraPromene.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SifraPromene result = sifraPromeneRepository.save(sifraPromene);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sifraPromene.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sifra-promenes} : get all the sifraPromenes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sifraPromenes in body.
     */
    @GetMapping("/sifra-promenes")
    public List<SifraPromene> getAllSifraPromenes() {
        log.debug("REST request to get all SifraPromenes");
        return sifraPromeneRepository.findAllOrderByTipPromeneAndBroj();
    }
    
    @GetMapping("/sifra-promenes/zaduzenje")
    public List<SifraPromene> getAllSifraPromenesZaduzenje() {
        log.debug("REST request to get all SifraPromenes");
        return sifraPromeneRepository.findAllByTipPromene("задужење");
    }
    
    @GetMapping("/sifra-promenes/razduzenje")
    public List<SifraPromene> getAllSifraPromenesRazduzenje() {
        log.debug("REST request to get all SifraPromenes");
        return sifraPromeneRepository.findAllByTipPromene("раздужење");
    }

    /**
     * {@code GET  /sifra-promenes/:id} : get the "id" sifraPromene.
     *
     * @param id the id of the sifraPromene to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sifraPromene, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sifra-promenes/{id}")
    public ResponseEntity<SifraPromene> getSifraPromene(@PathVariable Long id) {
        log.debug("REST request to get SifraPromene : {}", id);
        Optional<SifraPromene> sifraPromene = sifraPromeneRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sifraPromene);
    }

    /**
     * {@code DELETE  /sifra-promenes/:id} : delete the "id" sifraPromene.
     *
     * @param id the id of the sifraPromene to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sifra-promenes/{id}")
    public ResponseEntity<Void> deleteSifraPromene(@PathVariable Long id) {
        log.debug("REST request to delete SifraPromene : {}", id);
        sifraPromeneRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
