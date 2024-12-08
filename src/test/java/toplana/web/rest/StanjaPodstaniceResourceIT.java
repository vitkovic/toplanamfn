package toplana.web.rest;

import toplana.ToplanaApp;
import toplana.domain.StanjaPodstanice;
import toplana.repository.StanjaPodstaniceRepository;

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
 * Integration tests for the {@link StanjaPodstaniceResource} REST controller.
 */
@SpringBootTest(classes = ToplanaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StanjaPodstaniceResourceIT {

    private static final BigDecimal DEFAULT_STANJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_STANJE = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATUM_OCITAVANJA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM_OCITAVANJA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private StanjaPodstaniceRepository stanjaPodstaniceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStanjaPodstaniceMockMvc;

    private StanjaPodstanice stanjaPodstanice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StanjaPodstanice createEntity(EntityManager em) {
        StanjaPodstanice stanjaPodstanice = new StanjaPodstanice()
            .stanje(DEFAULT_STANJE)
            .datumOcitavanja(DEFAULT_DATUM_OCITAVANJA);
        return stanjaPodstanice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StanjaPodstanice createUpdatedEntity(EntityManager em) {
        StanjaPodstanice stanjaPodstanice = new StanjaPodstanice()
            .stanje(UPDATED_STANJE)
            .datumOcitavanja(UPDATED_DATUM_OCITAVANJA);
        return stanjaPodstanice;
    }

    @BeforeEach
    public void initTest() {
        stanjaPodstanice = createEntity(em);
    }

    @Test
    @Transactional
    public void createStanjaPodstanice() throws Exception {
        int databaseSizeBeforeCreate = stanjaPodstaniceRepository.findAll().size();
        // Create the StanjaPodstanice
        restStanjaPodstaniceMockMvc.perform(post("/api/stanja-podstanices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanjaPodstanice)))
            .andExpect(status().isCreated());

        // Validate the StanjaPodstanice in the database
        List<StanjaPodstanice> stanjaPodstaniceList = stanjaPodstaniceRepository.findAll();
        assertThat(stanjaPodstaniceList).hasSize(databaseSizeBeforeCreate + 1);
        StanjaPodstanice testStanjaPodstanice = stanjaPodstaniceList.get(stanjaPodstaniceList.size() - 1);
        assertThat(testStanjaPodstanice.getStanje()).isEqualTo(DEFAULT_STANJE);
        assertThat(testStanjaPodstanice.getDatumOcitavanja()).isEqualTo(DEFAULT_DATUM_OCITAVANJA);
    }

    @Test
    @Transactional
    public void createStanjaPodstaniceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stanjaPodstaniceRepository.findAll().size();

        // Create the StanjaPodstanice with an existing ID
        stanjaPodstanice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStanjaPodstaniceMockMvc.perform(post("/api/stanja-podstanices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanjaPodstanice)))
            .andExpect(status().isBadRequest());

        // Validate the StanjaPodstanice in the database
        List<StanjaPodstanice> stanjaPodstaniceList = stanjaPodstaniceRepository.findAll();
        assertThat(stanjaPodstaniceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStanjeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stanjaPodstaniceRepository.findAll().size();
        // set the field null
        stanjaPodstanice.setStanje(null);

        // Create the StanjaPodstanice, which fails.


        restStanjaPodstaniceMockMvc.perform(post("/api/stanja-podstanices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanjaPodstanice)))
            .andExpect(status().isBadRequest());

        List<StanjaPodstanice> stanjaPodstaniceList = stanjaPodstaniceRepository.findAll();
        assertThat(stanjaPodstaniceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumOcitavanjaIsRequired() throws Exception {
        int databaseSizeBeforeTest = stanjaPodstaniceRepository.findAll().size();
        // set the field null
        stanjaPodstanice.setDatumOcitavanja(null);

        // Create the StanjaPodstanice, which fails.


        restStanjaPodstaniceMockMvc.perform(post("/api/stanja-podstanices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanjaPodstanice)))
            .andExpect(status().isBadRequest());

        List<StanjaPodstanice> stanjaPodstaniceList = stanjaPodstaniceRepository.findAll();
        assertThat(stanjaPodstaniceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStanjaPodstanices() throws Exception {
        // Initialize the database
        stanjaPodstaniceRepository.saveAndFlush(stanjaPodstanice);

        // Get all the stanjaPodstaniceList
        restStanjaPodstaniceMockMvc.perform(get("/api/stanja-podstanices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stanjaPodstanice.getId().intValue())))
            .andExpect(jsonPath("$.[*].stanje").value(hasItem(DEFAULT_STANJE.intValue())))
            .andExpect(jsonPath("$.[*].datumOcitavanja").value(hasItem(DEFAULT_DATUM_OCITAVANJA.toString())));
    }
    
    @Test
    @Transactional
    public void getStanjaPodstanice() throws Exception {
        // Initialize the database
        stanjaPodstaniceRepository.saveAndFlush(stanjaPodstanice);

        // Get the stanjaPodstanice
        restStanjaPodstaniceMockMvc.perform(get("/api/stanja-podstanices/{id}", stanjaPodstanice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stanjaPodstanice.getId().intValue()))
            .andExpect(jsonPath("$.stanje").value(DEFAULT_STANJE.intValue()))
            .andExpect(jsonPath("$.datumOcitavanja").value(DEFAULT_DATUM_OCITAVANJA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingStanjaPodstanice() throws Exception {
        // Get the stanjaPodstanice
        restStanjaPodstaniceMockMvc.perform(get("/api/stanja-podstanices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStanjaPodstanice() throws Exception {
        // Initialize the database
        stanjaPodstaniceRepository.saveAndFlush(stanjaPodstanice);

        int databaseSizeBeforeUpdate = stanjaPodstaniceRepository.findAll().size();

        // Update the stanjaPodstanice
        StanjaPodstanice updatedStanjaPodstanice = stanjaPodstaniceRepository.findById(stanjaPodstanice.getId()).get();
        // Disconnect from session so that the updates on updatedStanjaPodstanice are not directly saved in db
        em.detach(updatedStanjaPodstanice);
        updatedStanjaPodstanice
            .stanje(UPDATED_STANJE)
            .datumOcitavanja(UPDATED_DATUM_OCITAVANJA);

        restStanjaPodstaniceMockMvc.perform(put("/api/stanja-podstanices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStanjaPodstanice)))
            .andExpect(status().isOk());

        // Validate the StanjaPodstanice in the database
        List<StanjaPodstanice> stanjaPodstaniceList = stanjaPodstaniceRepository.findAll();
        assertThat(stanjaPodstaniceList).hasSize(databaseSizeBeforeUpdate);
        StanjaPodstanice testStanjaPodstanice = stanjaPodstaniceList.get(stanjaPodstaniceList.size() - 1);
        assertThat(testStanjaPodstanice.getStanje()).isEqualTo(UPDATED_STANJE);
        assertThat(testStanjaPodstanice.getDatumOcitavanja()).isEqualTo(UPDATED_DATUM_OCITAVANJA);
    }

    @Test
    @Transactional
    public void updateNonExistingStanjaPodstanice() throws Exception {
        int databaseSizeBeforeUpdate = stanjaPodstaniceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStanjaPodstaniceMockMvc.perform(put("/api/stanja-podstanices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanjaPodstanice)))
            .andExpect(status().isBadRequest());

        // Validate the StanjaPodstanice in the database
        List<StanjaPodstanice> stanjaPodstaniceList = stanjaPodstaniceRepository.findAll();
        assertThat(stanjaPodstaniceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStanjaPodstanice() throws Exception {
        // Initialize the database
        stanjaPodstaniceRepository.saveAndFlush(stanjaPodstanice);

        int databaseSizeBeforeDelete = stanjaPodstaniceRepository.findAll().size();

        // Delete the stanjaPodstanice
        restStanjaPodstaniceMockMvc.perform(delete("/api/stanja-podstanices/{id}", stanjaPodstanice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StanjaPodstanice> stanjaPodstaniceList = stanjaPodstaniceRepository.findAll();
        assertThat(stanjaPodstaniceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
