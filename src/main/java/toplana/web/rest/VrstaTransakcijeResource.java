package toplana.web.rest;

import toplana.domain.VrstaTransakcije;
import toplana.repository.VrstaTransakcijeRepository;
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
 * REST controller for managing {@link toplana.domain.VrstaTransakcije}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VrstaTransakcijeResource {

    private final Logger log = LoggerFactory.getLogger(VrstaTransakcijeResource.class);

    private static final String ENTITY_NAME = "vrstaTransakcije";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VrstaTransakcijeRepository vrstaTransakcijeRepository;

    public VrstaTransakcijeResource(VrstaTransakcijeRepository vrstaTransakcijeRepository) {
        this.vrstaTransakcijeRepository = vrstaTransakcijeRepository;
    }

    /**
     * {@code POST  /vrsta-transakcijes} : Create a new vrstaTransakcije.
     *
     * @param vrstaTransakcije the vrstaTransakcije to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vrstaTransakcije, or with status {@code 400 (Bad Request)} if the vrstaTransakcije has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vrsta-transakcijes")
    public ResponseEntity<VrstaTransakcije> createVrstaTransakcije(@RequestBody VrstaTransakcije vrstaTransakcije) throws URISyntaxException {
        log.debug("REST request to save VrstaTransakcije : {}", vrstaTransakcije);
        if (vrstaTransakcije.getId() != null) {
            throw new BadRequestAlertException("A new vrstaTransakcije cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VrstaTransakcije result = vrstaTransakcijeRepository.save(vrstaTransakcije);
        return ResponseEntity.created(new URI("/api/vrsta-transakcijes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vrsta-transakcijes} : Updates an existing vrstaTransakcije.
     *
     * @param vrstaTransakcije the vrstaTransakcije to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vrstaTransakcije,
     * or with status {@code 400 (Bad Request)} if the vrstaTransakcije is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vrstaTransakcije couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vrsta-transakcijes")
    public ResponseEntity<VrstaTransakcije> updateVrstaTransakcije(@RequestBody VrstaTransakcije vrstaTransakcije) throws URISyntaxException {
        log.debug("REST request to update VrstaTransakcije : {}", vrstaTransakcije);
        if (vrstaTransakcije.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VrstaTransakcije result = vrstaTransakcijeRepository.save(vrstaTransakcije);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vrstaTransakcije.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vrsta-transakcijes} : get all the vrstaTransakcijes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vrstaTransakcijes in body.
     */
    @GetMapping("/vrsta-transakcijes")
    public List<VrstaTransakcije> getAllVrstaTransakcijes() {
        log.debug("REST request to get all VrstaTransakcijes");
        return vrstaTransakcijeRepository.findAll();
    }

    /**
     * {@code GET  /vrsta-transakcijes/:id} : get the "id" vrstaTransakcije.
     *
     * @param id the id of the vrstaTransakcije to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vrstaTransakcije, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vrsta-transakcijes/{id}")
    public ResponseEntity<VrstaTransakcije> getVrstaTransakcije(@PathVariable Long id) {
        log.debug("REST request to get VrstaTransakcije : {}", id);
        Optional<VrstaTransakcije> vrstaTransakcije = vrstaTransakcijeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vrstaTransakcije);
    }

    /**
     * {@code DELETE  /vrsta-transakcijes/:id} : delete the "id" vrstaTransakcije.
     *
     * @param id the id of the vrstaTransakcije to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vrsta-transakcijes/{id}")
    public ResponseEntity<Void> deleteVrstaTransakcije(@PathVariable Long id) {
        log.debug("REST request to delete VrstaTransakcije : {}", id);
        vrstaTransakcijeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
