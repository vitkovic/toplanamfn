package toplana.web.rest;

import toplana.ToplanaApp;
import toplana.domain.Transakcija;
import toplana.repository.TransakcijaRepository;
import toplana.service.TransakcijaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransakcijaResource} REST controller.
 */
@SpringBootTest(classes = ToplanaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TransakcijaResourceIT {

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VALUTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALUTA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_DUGUJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DUGUJE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_POTRAZUJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_POTRAZUJE = new BigDecimal(2);

    @Autowired
    private TransakcijaRepository transakcijaRepository;

    @Autowired
    private TransakcijaService transakcijaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransakcijaMockMvc;

    private Transakcija transakcija;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transakcija createEntity(EntityManager em) {
        Transakcija transakcija = new Transakcija()
            .datum(DEFAULT_DATUM)
            .valuta(DEFAULT_VALUTA)
            .status(DEFAULT_STATUS)
            .opis(DEFAULT_OPIS)
            .duguje(DEFAULT_DUGUJE)
            .potrazuje(DEFAULT_POTRAZUJE);
        return transakcija;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transakcija createUpdatedEntity(EntityManager em) {
        Transakcija transakcija = new Transakcija()
            .datum(UPDATED_DATUM)
            .valuta(UPDATED_VALUTA)
            .status(UPDATED_STATUS)
            .opis(UPDATED_OPIS)
            .duguje(UPDATED_DUGUJE)
            .potrazuje(UPDATED_POTRAZUJE);
        return transakcija;
    }

    @BeforeEach
    public void initTest() {
        transakcija = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransakcija() throws Exception {
        int databaseSizeBeforeCreate = transakcijaRepository.findAll().size();
        // Create the Transakcija
        restTransakcijaMockMvc.perform(post("/api/transakcijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transakcija)))
            .andExpect(status().isCreated());

        // Validate the Transakcija in the database
        List<Transakcija> transakcijaList = transakcijaRepository.findAll();
        assertThat(transakcijaList).hasSize(databaseSizeBeforeCreate + 1);
        Transakcija testTransakcija = transakcijaList.get(transakcijaList.size() - 1);
        assertThat(testTransakcija.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testTransakcija.getValuta()).isEqualTo(DEFAULT_VALUTA);
        assertThat(testTransakcija.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransakcija.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testTransakcija.getDuguje()).isEqualTo(DEFAULT_DUGUJE);
        assertThat(testTransakcija.getPotrazuje()).isEqualTo(DEFAULT_POTRAZUJE);
    }

    @Test
    @Transactional
    public void createTransakcijaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transakcijaRepository.findAll().size();

        // Create the Transakcija with an existing ID
        transakcija.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransakcijaMockMvc.perform(post("/api/transakcijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transakcija)))
            .andExpect(status().isBadRequest());

        // Validate the Transakcija in the database
        List<Transakcija> transakcijaList = transakcijaRepository.findAll();
        assertThat(transakcijaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransakcijas() throws Exception {
        // Initialize the database
        transakcijaRepository.saveAndFlush(transakcija);

        // Get all the transakcijaList
        restTransakcijaMockMvc.perform(get("/api/transakcijas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transakcija.getId().intValue())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].valuta").value(hasItem(DEFAULT_VALUTA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS)))
            .andExpect(jsonPath("$.[*].duguje").value(hasItem(DEFAULT_DUGUJE.intValue())))
            .andExpect(jsonPath("$.[*].potrazuje").value(hasItem(DEFAULT_POTRAZUJE.intValue())));
    }
    
    @Test
    @Transactional
    public void getTransakcija() throws Exception {
        // Initialize the database
        transakcijaRepository.saveAndFlush(transakcija);

        // Get the transakcija
        restTransakcijaMockMvc.perform(get("/api/transakcijas/{id}", transakcija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transakcija.getId().intValue()))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.valuta").value(DEFAULT_VALUTA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS))
            .andExpect(jsonPath("$.duguje").value(DEFAULT_DUGUJE.intValue()))
            .andExpect(jsonPath("$.potrazuje").value(DEFAULT_POTRAZUJE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTransakcija() throws Exception {
        // Get the transakcija
        restTransakcijaMockMvc.perform(get("/api/transakcijas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransakcija() throws Exception {
        // Initialize the database
        transakcijaService.save(transakcija);

        int databaseSizeBeforeUpdate = transakcijaRepository.findAll().size();

        // Update the transakcija
        Transakcija updatedTransakcija = transakcijaRepository.findById(transakcija.getId()).get();
        // Disconnect from session so that the updates on updatedTransakcija are not directly saved in db
        em.detach(updatedTransakcija);
        updatedTransakcija
            .datum(UPDATED_DATUM)
            .valuta(UPDATED_VALUTA)
            .status(UPDATED_STATUS)
            .opis(UPDATED_OPIS)
            .duguje(UPDATED_DUGUJE)
            .potrazuje(UPDATED_POTRAZUJE);

        restTransakcijaMockMvc.perform(put("/api/transakcijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransakcija)))
            .andExpect(status().isOk());

        // Validate the Transakcija in the database
        List<Transakcija> transakcijaList = transakcijaRepository.findAll();
        assertThat(transakcijaList).hasSize(databaseSizeBeforeUpdate);
        Transakcija testTransakcija = transakcijaList.get(transakcijaList.size() - 1);
        assertThat(testTransakcija.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testTransakcija.getValuta()).isEqualTo(UPDATED_VALUTA);
        assertThat(testTransakcija.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransakcija.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testTransakcija.getDuguje()).isEqualTo(UPDATED_DUGUJE);
        assertThat(testTransakcija.getPotrazuje()).isEqualTo(UPDATED_POTRAZUJE);
    }

    @Test
    @Transactional
    public void updateNonExistingTransakcija() throws Exception {
        int databaseSizeBeforeUpdate = transakcijaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransakcijaMockMvc.perform(put("/api/transakcijas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transakcija)))
            .andExpect(status().isBadRequest());

        // Validate the Transakcija in the database
        List<Transakcija> transakcijaList = transakcijaRepository.findAll();
        assertThat(transakcijaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransakcija() throws Exception {
        // Initialize the database
        transakcijaService.save(transakcija);

        int databaseSizeBeforeDelete = transakcijaRepository.findAll().size();

        // Delete the transakcija
        restTransakcijaMockMvc.perform(delete("/api/transakcijas/{id}", transakcija.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transakcija> transakcijaList = transakcijaRepository.findAll();
        assertThat(transakcijaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
