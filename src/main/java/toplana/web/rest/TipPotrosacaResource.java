package toplana.web.rest;

import toplana.domain.TipPotrosaca;
import toplana.repository.TipPotrosacaRepository;
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
 * REST controller for managing {@link toplana.domain.TipPotrosaca}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipPotrosacaResource {

    private final Logger log = LoggerFactory.getLogger(TipPotrosacaResource.class);

    private static final String ENTITY_NAME = "tipPotrosaca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipPotrosacaRepository tipPotrosacaRepository;

    public TipPotrosacaResource(TipPotrosacaRepository tipPotrosacaRepository) {
        this.tipPotrosacaRepository = tipPotrosacaRepository;
    }

    /**
     * {@code POST  /tip-potrosacas} : Create a new tipPotrosaca.
     *
     * @param tipPotrosaca the tipPotrosaca to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipPotrosaca, or with status {@code 400 (Bad Request)} if the tipPotrosaca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tip-potrosacas")
    public ResponseEntity<TipPotrosaca> createTipPotrosaca(@RequestBody TipPotrosaca tipPotrosaca) throws URISyntaxException {
        log.debug("REST request to save TipPotrosaca : {}", tipPotrosaca);
        if (tipPotrosaca.getId() != null) {
            throw new BadRequestAlertException("A new tipPotrosaca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipPotrosaca result = tipPotrosacaRepository.save(tipPotrosaca);
        return ResponseEntity.created(new URI("/api/tip-potrosacas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tip-potrosacas} : Updates an existing tipPotrosaca.
     *
     * @param tipPotrosaca the tipPotrosaca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipPotrosaca,
     * or with status {@code 400 (Bad Request)} if the tipPotrosaca is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipPotrosaca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tip-potrosacas")
    public ResponseEntity<TipPotrosaca> updateTipPotrosaca(@RequestBody TipPotrosaca tipPotrosaca) throws URISyntaxException {
        log.debug("REST request to update TipPotrosaca : {}", tipPotrosaca);
        if (tipPotrosaca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipPotrosaca result = tipPotrosacaRepository.save(tipPotrosaca);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipPotrosaca.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tip-potrosacas} : get all the tipPotrosacas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipPotrosacas in body.
     */
    @GetMapping("/tip-potrosacas")
    public List<TipPotrosaca> getAllTipPotrosacas() {
        log.debug("REST request to get all TipPotrosacas");
        return tipPotrosacaRepository.findAll();
    }

    /**
     * {@code GET  /tip-potrosacas/:id} : get the "id" tipPotrosaca.
     *
     * @param id the id of the tipPotrosaca to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipPotrosaca, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tip-potrosacas/{id}")
    public ResponseEntity<TipPotrosaca> getTipPotrosaca(@PathVariable Long id) {
        log.debug("REST request to get TipPotrosaca : {}", id);
        Optional<TipPotrosaca> tipPotrosaca = tipPotrosacaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipPotrosaca);
    }

    /**
     * {@code DELETE  /tip-potrosacas/:id} : delete the "id" tipPotrosaca.
     *
     * @param id the id of the tipPotrosaca to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tip-potrosacas/{id}")
    public ResponseEntity<Void> deleteTipPotrosaca(@PathVariable Long id) {
        log.debug("REST request to delete TipPotrosaca : {}", id);
        tipPotrosacaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
