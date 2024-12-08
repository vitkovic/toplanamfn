package toplana.web.rest;

import toplana.domain.SifraDokumenta;
import toplana.repository.SifraDokumentaRepository;
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
 * REST controller for managing {@link toplana.domain.SifraDokumenta}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SifraDokumentaResource {

    private final Logger log = LoggerFactory.getLogger(SifraDokumentaResource.class);

    private static final String ENTITY_NAME = "sifraDokumenta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SifraDokumentaRepository sifraDokumentaRepository;

    public SifraDokumentaResource(SifraDokumentaRepository sifraDokumentaRepository) {
        this.sifraDokumentaRepository = sifraDokumentaRepository;
    }

    /**
     * {@code POST  /sifra-dokumentas} : Create a new sifraDokumenta.
     *
     * @param sifraDokumenta the sifraDokumenta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sifraDokumenta, or with status {@code 400 (Bad Request)} if the sifraDokumenta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sifra-dokumentas")
    public ResponseEntity<SifraDokumenta> createSifraDokumenta(@RequestBody SifraDokumenta sifraDokumenta) throws URISyntaxException {
        log.debug("REST request to save SifraDokumenta : {}", sifraDokumenta);
        if (sifraDokumenta.getId() != null) {
            throw new BadRequestAlertException("A new sifraDokumenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SifraDokumenta result = sifraDokumentaRepository.save(sifraDokumenta);
        return ResponseEntity.created(new URI("/api/sifra-dokumentas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sifra-dokumentas} : Updates an existing sifraDokumenta.
     *
     * @param sifraDokumenta the sifraDokumenta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sifraDokumenta,
     * or with status {@code 400 (Bad Request)} if the sifraDokumenta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sifraDokumenta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sifra-dokumentas")
    public ResponseEntity<SifraDokumenta> updateSifraDokumenta(@RequestBody SifraDokumenta sifraDokumenta) throws URISyntaxException {
        log.debug("REST request to update SifraDokumenta : {}", sifraDokumenta);
        if (sifraDokumenta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SifraDokumenta result = sifraDokumentaRepository.save(sifraDokumenta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sifraDokumenta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sifra-dokumentas} : get all the sifraDokumentas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sifraDokumentas in body.
     */
    @GetMapping("/sifra-dokumentas")
    public List<SifraDokumenta> getAllSifraDokumentas() {
        log.debug("REST request to get all SifraDokumentas");
        return sifraDokumentaRepository.findAll();
    }

    /**
     * {@code GET  /sifra-dokumentas/:id} : get the "id" sifraDokumenta.
     *
     * @param id the id of the sifraDokumenta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sifraDokumenta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sifra-dokumentas/{id}")
    public ResponseEntity<SifraDokumenta> getSifraDokumenta(@PathVariable Long id) {
        log.debug("REST request to get SifraDokumenta : {}", id);
        Optional<SifraDokumenta> sifraDokumenta = sifraDokumentaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sifraDokumenta);
    }

    /**
     * {@code DELETE  /sifra-dokumentas/:id} : delete the "id" sifraDokumenta.
     *
     * @param id the id of the sifraDokumenta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sifra-dokumentas/{id}")
    public ResponseEntity<Void> deleteSifraDokumenta(@PathVariable Long id) {
        log.debug("REST request to delete SifraDokumenta : {}", id);
        sifraDokumentaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
