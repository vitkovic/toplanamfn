package toplana.web.rest;

import toplana.domain.Podstanica;
import toplana.repository.PodstanicaRepository;
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
 * REST controller for managing {@link toplana.domain.Podstanica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PodstanicaResource {

    private final Logger log = LoggerFactory.getLogger(PodstanicaResource.class);

    private static final String ENTITY_NAME = "podstanica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PodstanicaRepository podstanicaRepository;

    public PodstanicaResource(PodstanicaRepository podstanicaRepository) {
        this.podstanicaRepository = podstanicaRepository;
    }

    /**
     * {@code POST  /podstanicas} : Create a new podstanica.
     *
     * @param podstanica the podstanica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new podstanica, or with status {@code 400 (Bad Request)} if the podstanica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/podstanicas")
    public ResponseEntity<Podstanica> createPodstanica(@RequestBody Podstanica podstanica) throws URISyntaxException {
        log.debug("REST request to save Podstanica : {}", podstanica);
        if (podstanica.getId() != null) {
            throw new BadRequestAlertException("A new podstanica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Podstanica result = podstanicaRepository.save(podstanica);
        return ResponseEntity.created(new URI("/api/podstanicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /podstanicas} : Updates an existing podstanica.
     *
     * @param podstanica the podstanica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated podstanica,
     * or with status {@code 400 (Bad Request)} if the podstanica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the podstanica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/podstanicas")
    public ResponseEntity<Podstanica> updatePodstanica(@RequestBody Podstanica podstanica) throws URISyntaxException {
        log.debug("REST request to update Podstanica : {}", podstanica);
        if (podstanica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Podstanica result = podstanicaRepository.save(podstanica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, podstanica.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /podstanicas} : get all the podstanicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of podstanicas in body.
     */
    @GetMapping("/podstanicas")
    public List<Podstanica> getAllPodstanicas() {
        log.debug("REST request to get all Podstanicas");
        return podstanicaRepository.findAll();
    }

    /**
     * {@code GET  /podstanicas/:id} : get the "id" podstanica.
     *
     * @param id the id of the podstanica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the podstanica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/podstanicas/{id}")
    public ResponseEntity<Podstanica> getPodstanica(@PathVariable Long id) {
        log.debug("REST request to get Podstanica : {}", id);
        Optional<Podstanica> podstanica = podstanicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(podstanica);
    }

    /**
     * {@code DELETE  /podstanicas/:id} : delete the "id" podstanica.
     *
     * @param id the id of the podstanica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/podstanicas/{id}")
    public ResponseEntity<Void> deletePodstanica(@PathVariable Long id) {
        log.debug("REST request to delete Podstanica : {}", id);
        podstanicaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
