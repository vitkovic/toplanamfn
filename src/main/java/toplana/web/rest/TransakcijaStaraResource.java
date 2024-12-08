package toplana.web.rest;

import toplana.domain.TransakcijaStara;
import toplana.service.TransakcijaStaraService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link toplana.domain.TransakcijaStara}.
 */
@RestController
@RequestMapping("/api")
public class TransakcijaStaraResource {

    private final Logger log = LoggerFactory.getLogger(TransakcijaStaraResource.class);

    private static final String ENTITY_NAME = "transakcijaStara";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransakcijaStaraService transakcijaStaraService;

    public TransakcijaStaraResource(TransakcijaStaraService transakcijaStaraService) {
        this.transakcijaStaraService = transakcijaStaraService;
    }

    /**
     * {@code POST  /transakcija-staras} : Create a new transakcijaStara.
     *
     * @param transakcijaStara the transakcijaStara to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transakcijaStara, or with status {@code 400 (Bad Request)} if the transakcijaStara has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transakcija-staras")
    public ResponseEntity<TransakcijaStara> createTransakcijaStara(@RequestBody TransakcijaStara transakcijaStara) throws URISyntaxException {
        log.debug("REST request to save TransakcijaStara : {}", transakcijaStara);
        if (transakcijaStara.getId() != null) {
            throw new BadRequestAlertException("A new transakcijaStara cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransakcijaStara result = transakcijaStaraService.save(transakcijaStara);
        return ResponseEntity.created(new URI("/api/transakcija-staras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transakcija-staras} : Updates an existing transakcijaStara.
     *
     * @param transakcijaStara the transakcijaStara to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transakcijaStara,
     * or with status {@code 400 (Bad Request)} if the transakcijaStara is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transakcijaStara couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transakcija-staras")
    public ResponseEntity<TransakcijaStara> updateTransakcijaStara(@RequestBody TransakcijaStara transakcijaStara) throws URISyntaxException {
        log.debug("REST request to update TransakcijaStara : {}", transakcijaStara);
        if (transakcijaStara.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransakcijaStara result = transakcijaStaraService.save(transakcijaStara);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transakcijaStara.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transakcija-staras} : get all the transakcijaStaras.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transakcijaStaras in body.
     */
    @GetMapping("/transakcija-staras")
    public ResponseEntity<List<TransakcijaStara>> getAllTransakcijaStaras(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("stavkaizvoda-is-null".equals(filter)) {
            log.debug("REST request to get all TransakcijaStaras where stavkaIzvoda is null");
            return new ResponseEntity<>(transakcijaStaraService.findAllWhereStavkaIzvodaIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of TransakcijaStaras");
        Page<TransakcijaStara> page = transakcijaStaraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transakcija-staras/:id} : get the "id" transakcijaStara.
     *
     * @param id the id of the transakcijaStara to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transakcijaStara, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transakcija-staras/{id}")
    public ResponseEntity<TransakcijaStara> getTransakcijaStara(@PathVariable Long id) {
        log.debug("REST request to get TransakcijaStara : {}", id);
        Optional<TransakcijaStara> transakcijaStara = transakcijaStaraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transakcijaStara);
    }

    /**
     * {@code DELETE  /transakcija-staras/:id} : delete the "id" transakcijaStara.
     *
     * @param id the id of the transakcijaStara to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transakcija-staras/{id}")
    public ResponseEntity<Void> deleteTransakcijaStara(@PathVariable Long id) {
        log.debug("REST request to delete TransakcijaStara : {}", id);
        transakcijaStaraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
