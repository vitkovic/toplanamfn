package toplana.web.rest;

import toplana.ToplanaApp;
import toplana.domain.Vlasnik;
import toplana.repository.VlasnikRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VlasnikResource} REST controller.
 */
@SpringBootTest(classes = ToplanaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VlasnikResourceIT {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_BROJ_RACUNA = "AAAAAAAAAA";
    private static final String UPDATED_BROJ_RACUNA = "BBBBBBBBBB";

    private static final String DEFAULT_PARTIJA_RACUNA = "AAAAAAAAAA";
    private static final String UPDATED_PARTIJA_RACUNA = "BBBBBBBBBB";

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "JLVInd@cUi.HH";
    private static final String UPDATED_EMAIL = "9DYX@uBuK.YLxylQ";

    @Autowired
    private VlasnikRepository vlasnikRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVlasnikMockMvc;

    private Vlasnik vlasnik;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vlasnik createEntity(EntityManager em) {
        Vlasnik vlasnik = new Vlasnik()
            .ime(DEFAULT_IME)
            .prezime(DEFAULT_PREZIME)
            .brojRacuna(DEFAULT_BROJ_RACUNA)
            .partijaRacuna(DEFAULT_PARTIJA_RACUNA)
            .naziv(DEFAULT_NAZIV)
            .email(DEFAULT_EMAIL);
        return vlasnik;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vlasnik createUpdatedEntity(EntityManager em) {
        Vlasnik vlasnik = new Vlasnik()
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .brojRacuna(UPDATED_BROJ_RACUNA)
            .partijaRacuna(UPDATED_PARTIJA_RACUNA)
            .naziv(UPDATED_NAZIV)
            .email(UPDATED_EMAIL);
        return vlasnik;
    }

    @BeforeEach
    public void initTest() {
        vlasnik = createEntity(em);
    }

    @Test
    @Transactional
    public void createVlasnik() throws Exception {
        int databaseSizeBeforeCreate = vlasnikRepository.findAll().size();
        // Create the Vlasnik
        restVlasnikMockMvc.perform(post("/api/vlasniks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vlasnik)))
            .andExpect(status().isCreated());

        // Validate the Vlasnik in the database
        List<Vlasnik> vlasnikList = vlasnikRepository.findAll();
        assertThat(vlasnikList).hasSize(databaseSizeBeforeCreate + 1);
        Vlasnik testVlasnik = vlasnikList.get(vlasnikList.size() - 1);
        assertThat(testVlasnik.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testVlasnik.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testVlasnik.getBrojRacuna()).isEqualTo(DEFAULT_BROJ_RACUNA);
        assertThat(testVlasnik.getPartijaRacuna()).isEqualTo(DEFAULT_PARTIJA_RACUNA);
        assertThat(testVlasnik.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testVlasnik.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createVlasnikWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vlasnikRepository.findAll().size();

        // Create the Vlasnik with an existing ID
        vlasnik.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVlasnikMockMvc.perform(post("/api/vlasniks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vlasnik)))
            .andExpect(status().isBadRequest());

        // Validate the Vlasnik in the database
        List<Vlasnik> vlasnikList = vlasnikRepository.findAll();
        assertThat(vlasnikList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVlasniks() throws Exception {
        // Initialize the database
        vlasnikRepository.saveAndFlush(vlasnik);

        // Get all the vlasnikList
        restVlasnikMockMvc.perform(get("/api/vlasniks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vlasnik.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME)))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME)))
            .andExpect(jsonPath("$.[*].brojRacuna").value(hasItem(DEFAULT_BROJ_RACUNA)))
            .andExpect(jsonPath("$.[*].partijaRacuna").value(hasItem(DEFAULT_PARTIJA_RACUNA)))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getVlasnik() throws Exception {
        // Initialize the database
        vlasnikRepository.saveAndFlush(vlasnik);

        // Get the vlasnik
        restVlasnikMockMvc.perform(get("/api/vlasniks/{id}", vlasnik.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vlasnik.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME))
            .andExpect(jsonPath("$.brojRacuna").value(DEFAULT_BROJ_RACUNA))
            .andExpect(jsonPath("$.partijaRacuna").value(DEFAULT_PARTIJA_RACUNA))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }
    @Test
    @Transactional
    public void getNonExistingVlasnik() throws Exception {
        // Get the vlasnik
        restVlasnikMockMvc.perform(get("/api/vlasniks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVlasnik() throws Exception {
        // Initialize the database
        vlasnikRepository.saveAndFlush(vlasnik);

        int databaseSizeBeforeUpdate = vlasnikRepository.findAll().size();

        // Update the vlasnik
        Vlasnik updatedVlasnik = vlasnikRepository.findById(vlasnik.getId()).get();
        // Disconnect from session so that the updates on updatedVlasnik are not directly saved in db
        em.detach(updatedVlasnik);
        updatedVlasnik
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .brojRacuna(UPDATED_BROJ_RACUNA)
            .partijaRacuna(UPDATED_PARTIJA_RACUNA)
            .naziv(UPDATED_NAZIV)
            .email(UPDATED_EMAIL);

        restVlasnikMockMvc.perform(put("/api/vlasniks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVlasnik)))
            .andExpect(status().isOk());

        // Validate the Vlasnik in the database
        List<Vlasnik> vlasnikList = vlasnikRepository.findAll();
        assertThat(vlasnikList).hasSize(databaseSizeBeforeUpdate);
        Vlasnik testVlasnik = vlasnikList.get(vlasnikList.size() - 1);
        assertThat(testVlasnik.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testVlasnik.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testVlasnik.getBrojRacuna()).isEqualTo(UPDATED_BROJ_RACUNA);
        assertThat(testVlasnik.getPartijaRacuna()).isEqualTo(UPDATED_PARTIJA_RACUNA);
        assertThat(testVlasnik.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testVlasnik.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingVlasnik() throws Exception {
        int databaseSizeBeforeUpdate = vlasnikRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVlasnikMockMvc.perform(put("/api/vlasniks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vlasnik)))
            .andExpect(status().isBadRequest());

        // Validate the Vlasnik in the database
        List<Vlasnik> vlasnikList = vlasnikRepository.findAll();
        assertThat(vlasnikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVlasnik() throws Exception {
        // Initialize the database
        vlasnikRepository.saveAndFlush(vlasnik);

        int databaseSizeBeforeDelete = vlasnikRepository.findAll().size();

        // Delete the vlasnik
        restVlasnikMockMvc.perform(delete("/api/vlasniks/{id}", vlasnik.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vlasnik> vlasnikList = vlasnikRepository.findAll();
        assertThat(vlasnikList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
