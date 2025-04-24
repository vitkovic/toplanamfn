package toplana.web.rest;

import toplana.ToplanaApp;
import toplana.domain.StanStanje;
import toplana.repository.StanStanjeRepository;
import toplana.service.StanStanjeService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StanStanjeResource} REST controller.
 */
@SpringBootTest(classes = ToplanaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StanStanjeResourceIT {

    private static final String DEFAULT_SIFRA = "AAAAAAAAAA";
    private static final String UPDATED_SIFRA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_VREDNOST = 1L;
    private static final Long UPDATED_VREDNOST = 2L;

    private static final Long DEFAULT_STAN_ID = 1L;
    private static final Long UPDATED_STAN_ID = 2L;

    @Autowired
    private StanStanjeRepository stanStanjeRepository;

    @Autowired
    private StanStanjeService stanStanjeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStanStanjeMockMvc;

    private StanStanje stanStanje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StanStanje createEntity(EntityManager em) {
        StanStanje stanStanje = new StanStanje()
            .sifra(DEFAULT_SIFRA)
            .datum(DEFAULT_DATUM)
            .vrednost(DEFAULT_VREDNOST)
           ;
        return stanStanje;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StanStanje createUpdatedEntity(EntityManager em) {
        StanStanje stanStanje = new StanStanje()
            .sifra(UPDATED_SIFRA)
            .datum(UPDATED_DATUM)
            .vrednost(UPDATED_VREDNOST)
          ;
        return stanStanje;
    }

    @BeforeEach
    public void initTest() {
        stanStanje = createEntity(em);
    }

    @Test
    @Transactional
    public void createStanStanje() throws Exception {
        int databaseSizeBeforeCreate = stanStanjeRepository.findAll().size();
        // Create the StanStanje
        restStanStanjeMockMvc.perform(post("/api/stan-stanjes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanStanje)))
            .andExpect(status().isCreated());

        // Validate the StanStanje in the database
        List<StanStanje> stanStanjeList = stanStanjeRepository.findAll();
        assertThat(stanStanjeList).hasSize(databaseSizeBeforeCreate + 1);
        StanStanje testStanStanje = stanStanjeList.get(stanStanjeList.size() - 1);
        assertThat(testStanStanje.getSifra()).isEqualTo(DEFAULT_SIFRA);
        assertThat(testStanStanje.getDatum()).isEqualTo(DEFAULT_DATUM);
        assertThat(testStanStanje.getVrednost()).isEqualTo(DEFAULT_VREDNOST);
       
    }

    @Test
    @Transactional
    public void createStanStanjeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stanStanjeRepository.findAll().size();

        // Create the StanStanje with an existing ID
        stanStanje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStanStanjeMockMvc.perform(post("/api/stan-stanjes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanStanje)))
            .andExpect(status().isBadRequest());

        // Validate the StanStanje in the database
        List<StanStanje> stanStanjeList = stanStanjeRepository.findAll();
        assertThat(stanStanjeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSifraIsRequired() throws Exception {
        int databaseSizeBeforeTest = stanStanjeRepository.findAll().size();
        // set the field null
        stanStanje.setSifra(null);

        // Create the StanStanje, which fails.


        restStanStanjeMockMvc.perform(post("/api/stan-stanjes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanStanje)))
            .andExpect(status().isBadRequest());

        List<StanStanje> stanStanjeList = stanStanjeRepository.findAll();
        assertThat(stanStanjeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDatumIsRequired() throws Exception {
        int databaseSizeBeforeTest = stanStanjeRepository.findAll().size();
        // set the field null
        stanStanje.setDatum(null);

        // Create the StanStanje, which fails.


        restStanStanjeMockMvc.perform(post("/api/stan-stanjes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanStanje)))
            .andExpect(status().isBadRequest());

        List<StanStanje> stanStanjeList = stanStanjeRepository.findAll();
        assertThat(stanStanjeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVrednostIsRequired() throws Exception {
        int databaseSizeBeforeTest = stanStanjeRepository.findAll().size();
        // set the field null
        stanStanje.setVrednost(null);

        // Create the StanStanje, which fails.


        restStanStanjeMockMvc.perform(post("/api/stan-stanjes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanStanje)))
            .andExpect(status().isBadRequest());

        List<StanStanje> stanStanjeList = stanStanjeRepository.findAll();
        assertThat(stanStanjeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStanStanjes() throws Exception {
        // Initialize the database
        stanStanjeRepository.saveAndFlush(stanStanje);

        // Get all the stanStanjeList
        restStanStanjeMockMvc.perform(get("/api/stan-stanjes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stanStanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifra").value(hasItem(DEFAULT_SIFRA)))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(DEFAULT_DATUM.toString())))
            .andExpect(jsonPath("$.[*].vrednost").value(hasItem(DEFAULT_VREDNOST.intValue())))
            .andExpect(jsonPath("$.[*].stan_id").value(hasItem(DEFAULT_STAN_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getStanStanje() throws Exception {
        // Initialize the database
        stanStanjeRepository.saveAndFlush(stanStanje);

        // Get the stanStanje
        restStanStanjeMockMvc.perform(get("/api/stan-stanjes/{id}", stanStanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stanStanje.getId().intValue()))
            .andExpect(jsonPath("$.sifra").value(DEFAULT_SIFRA))
            .andExpect(jsonPath("$.datum").value(DEFAULT_DATUM.toString()))
            .andExpect(jsonPath("$.vrednost").value(DEFAULT_VREDNOST.intValue()))
            .andExpect(jsonPath("$.stan_id").value(DEFAULT_STAN_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStanStanje() throws Exception {
        // Get the stanStanje
        restStanStanjeMockMvc.perform(get("/api/stan-stanjes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStanStanje() throws Exception {
        // Initialize the database
        stanStanjeService.save(stanStanje);

        int databaseSizeBeforeUpdate = stanStanjeRepository.findAll().size();

        // Update the stanStanje
        StanStanje updatedStanStanje = stanStanjeRepository.findById(stanStanje.getId()).get();
        // Disconnect from session so that the updates on updatedStanStanje are not directly saved in db
        em.detach(updatedStanStanje);
        updatedStanStanje
            .sifra(UPDATED_SIFRA)
            .datum(UPDATED_DATUM)
            .vrednost(UPDATED_VREDNOST)
            ;

        restStanStanjeMockMvc.perform(put("/api/stan-stanjes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStanStanje)))
            .andExpect(status().isOk());

        // Validate the StanStanje in the database
        List<StanStanje> stanStanjeList = stanStanjeRepository.findAll();
        assertThat(stanStanjeList).hasSize(databaseSizeBeforeUpdate);
        StanStanje testStanStanje = stanStanjeList.get(stanStanjeList.size() - 1);
        assertThat(testStanStanje.getSifra()).isEqualTo(UPDATED_SIFRA);
        assertThat(testStanStanje.getDatum()).isEqualTo(UPDATED_DATUM);
        assertThat(testStanStanje.getVrednost()).isEqualTo(UPDATED_VREDNOST);
        ;
    }

    @Test
    @Transactional
    public void updateNonExistingStanStanje() throws Exception {
        int databaseSizeBeforeUpdate = stanStanjeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStanStanjeMockMvc.perform(put("/api/stan-stanjes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stanStanje)))
            .andExpect(status().isBadRequest());

        // Validate the StanStanje in the database
        List<StanStanje> stanStanjeList = stanStanjeRepository.findAll();
        assertThat(stanStanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStanStanje() throws Exception {
        // Initialize the database
        stanStanjeService.save(stanStanje);

        int databaseSizeBeforeDelete = stanStanjeRepository.findAll().size();

        // Delete the stanStanje
        restStanStanjeMockMvc.perform(delete("/api/stan-stanjes/{id}", stanStanje.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StanStanje> stanStanjeList = stanStanjeRepository.findAll();
        assertThat(stanStanjeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
