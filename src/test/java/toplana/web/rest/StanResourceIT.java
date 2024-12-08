package toplana.web.rest;

import toplana.ToplanaApp;
import toplana.domain.Stan;
import toplana.repository.StanRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link StanResource} REST controller.
 */
@SpringBootTest(classes = ToplanaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StanResourceIT {

    private static final BigDecimal DEFAULT_POVRSINA = new BigDecimal(1);
    private static final BigDecimal UPDATED_POVRSINA = new BigDecimal(2);

    private static final String DEFAULT_ULICA = "AAAAAAAAAA";
    private static final String UPDATED_ULICA = "BBBBBBBBBB";

    private static final Integer DEFAULT_ULAZ = 1;
    private static final Integer UPDATED_ULAZ = 2;

    private static final Integer DEFAULT_BROJ = 1;
    private static final Integer UPDATED_BROJ = 2;

    private static final Boolean DEFAULT_UKLJUCEN = false;
    private static final Boolean UPDATED_UKLJUCEN = true;

    private static final String DEFAULT_SIFRA = "AAAAAAAAAA";
    private static final String UPDATED_SIFRA = "BBBBBBBBBB";

    private static final String DEFAULT_GRAD = "AAAAAAAAAA";
    private static final String UPDATED_GRAD = "BBBBBBBBBB";

    private static final String DEFAULT_POSTANSKI_BROJ = "AAAAAAAAAA";
    private static final String UPDATED_POSTANSKI_BROJ = "BBBBBBBBBB";

    @Autowired
    private StanRepository stanRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStanMockMvc;

    private Stan stan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stan createEntity(EntityManager em) {
        Stan stan = new Stan()
            .povrsina(DEFAULT_POVRSINA)
            .ulica(DEFAULT_ULICA)
            .ulaz(DEFAULT_ULAZ)
            .broj(DEFAULT_BROJ)
            .ukljucen(DEFAULT_UKLJUCEN)
            .sifra(DEFAULT_SIFRA)
            .grad(DEFAULT_GRAD)
            .postanskiBroj(DEFAULT_POSTANSKI_BROJ);
        return stan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stan createUpdatedEntity(EntityManager em) {
        Stan stan = new Stan()
            .povrsina(UPDATED_POVRSINA)
            .ulica(UPDATED_ULICA)
            .ulaz(UPDATED_ULAZ)
            .broj(UPDATED_BROJ)
            .ukljucen(UPDATED_UKLJUCEN)
            .sifra(UPDATED_SIFRA)
            .grad(UPDATED_GRAD)
            .postanskiBroj(UPDATED_POSTANSKI_BROJ);
        return stan;
    }

    @BeforeEach
    public void initTest() {
        stan = createEntity(em);
    }

    @Test
    @Transactional
    public void createStan() throws Exception {
        int databaseSizeBeforeCreate = stanRepository.findAll().size();
        // Create the Stan
        restStanMockMvc.perform(post("/api/stans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stan)))
            .andExpect(status().isCreated());

        // Validate the Stan in the database
        List<Stan> stanList = stanRepository.findAll();
        assertThat(stanList).hasSize(databaseSizeBeforeCreate + 1);
        Stan testStan = stanList.get(stanList.size() - 1);
        assertThat(testStan.getPovrsina()).isEqualTo(DEFAULT_POVRSINA);
        assertThat(testStan.getUlica()).isEqualTo(DEFAULT_ULICA);
        assertThat(testStan.getUlaz()).isEqualTo(DEFAULT_ULAZ);
        assertThat(testStan.getBroj()).isEqualTo(DEFAULT_BROJ);
        assertThat(testStan.isUkljucen()).isEqualTo(DEFAULT_UKLJUCEN);
        assertThat(testStan.getSifra()).isEqualTo(DEFAULT_SIFRA);
        assertThat(testStan.getGrad()).isEqualTo(DEFAULT_GRAD);
        assertThat(testStan.getPostanskiBroj()).isEqualTo(DEFAULT_POSTANSKI_BROJ);
    }

    @Test
    @Transactional
    public void createStanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stanRepository.findAll().size();

        // Create the Stan with an existing ID
        stan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStanMockMvc.perform(post("/api/stans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stan)))
            .andExpect(status().isBadRequest());

        // Validate the Stan in the database
        List<Stan> stanList = stanRepository.findAll();
        assertThat(stanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPovrsinaIsRequired() throws Exception {
        int databaseSizeBeforeTest = stanRepository.findAll().size();
        // set the field null
        stan.setPovrsina(null);

        // Create the Stan, which fails.


        restStanMockMvc.perform(post("/api/stans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stan)))
            .andExpect(status().isBadRequest());

        List<Stan> stanList = stanRepository.findAll();
        assertThat(stanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStans() throws Exception {
        // Initialize the database
        stanRepository.saveAndFlush(stan);

        // Get all the stanList
        restStanMockMvc.perform(get("/api/stans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stan.getId().intValue())))
            .andExpect(jsonPath("$.[*].povrsina").value(hasItem(DEFAULT_POVRSINA.intValue())))
            .andExpect(jsonPath("$.[*].ulica").value(hasItem(DEFAULT_ULICA)))
            .andExpect(jsonPath("$.[*].ulaz").value(hasItem(DEFAULT_ULAZ)))
            .andExpect(jsonPath("$.[*].broj").value(hasItem(DEFAULT_BROJ)))
            .andExpect(jsonPath("$.[*].ukljucen").value(hasItem(DEFAULT_UKLJUCEN.booleanValue())))
            .andExpect(jsonPath("$.[*].sifra").value(hasItem(DEFAULT_SIFRA)))
            .andExpect(jsonPath("$.[*].grad").value(hasItem(DEFAULT_GRAD)))
            .andExpect(jsonPath("$.[*].postanskiBroj").value(hasItem(DEFAULT_POSTANSKI_BROJ)));
    }
    
    @Test
    @Transactional
    public void getStan() throws Exception {
        // Initialize the database
        stanRepository.saveAndFlush(stan);

        // Get the stan
        restStanMockMvc.perform(get("/api/stans/{id}", stan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(stan.getId().intValue()))
            .andExpect(jsonPath("$.povrsina").value(DEFAULT_POVRSINA.intValue()))
            .andExpect(jsonPath("$.ulica").value(DEFAULT_ULICA))
            .andExpect(jsonPath("$.ulaz").value(DEFAULT_ULAZ))
            .andExpect(jsonPath("$.broj").value(DEFAULT_BROJ))
            .andExpect(jsonPath("$.ukljucen").value(DEFAULT_UKLJUCEN.booleanValue()))
            .andExpect(jsonPath("$.sifra").value(DEFAULT_SIFRA))
            .andExpect(jsonPath("$.grad").value(DEFAULT_GRAD))
            .andExpect(jsonPath("$.postanskiBroj").value(DEFAULT_POSTANSKI_BROJ));
    }
    @Test
    @Transactional
    public void getNonExistingStan() throws Exception {
        // Get the stan
        restStanMockMvc.perform(get("/api/stans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStan() throws Exception {
        // Initialize the database
        stanRepository.saveAndFlush(stan);

        int databaseSizeBeforeUpdate = stanRepository.findAll().size();

        // Update the stan
        Stan updatedStan = stanRepository.findById(stan.getId()).get();
        // Disconnect from session so that the updates on updatedStan are not directly saved in db
        em.detach(updatedStan);
        updatedStan
            .povrsina(UPDATED_POVRSINA)
            .ulica(UPDATED_ULICA)
            .ulaz(UPDATED_ULAZ)
            .broj(UPDATED_BROJ)
            .ukljucen(UPDATED_UKLJUCEN)
            .sifra(UPDATED_SIFRA)
            .grad(UPDATED_GRAD)
            .postanskiBroj(UPDATED_POSTANSKI_BROJ);

        restStanMockMvc.perform(put("/api/stans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStan)))
            .andExpect(status().isOk());

        // Validate the Stan in the database
        List<Stan> stanList = stanRepository.findAll();
        assertThat(stanList).hasSize(databaseSizeBeforeUpdate);
        Stan testStan = stanList.get(stanList.size() - 1);
        assertThat(testStan.getPovrsina()).isEqualTo(UPDATED_POVRSINA);
        assertThat(testStan.getUlica()).isEqualTo(UPDATED_ULICA);
        assertThat(testStan.getUlaz()).isEqualTo(UPDATED_ULAZ);
        assertThat(testStan.getBroj()).isEqualTo(UPDATED_BROJ);
        assertThat(testStan.isUkljucen()).isEqualTo(UPDATED_UKLJUCEN);
        assertThat(testStan.getSifra()).isEqualTo(UPDATED_SIFRA);
        assertThat(testStan.getGrad()).isEqualTo(UPDATED_GRAD);
        assertThat(testStan.getPostanskiBroj()).isEqualTo(UPDATED_POSTANSKI_BROJ);
    }

    @Test
    @Transactional
    public void updateNonExistingStan() throws Exception {
        int databaseSizeBeforeUpdate = stanRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStanMockMvc.perform(put("/api/stans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(stan)))
            .andExpect(status().isBadRequest());

        // Validate the Stan in the database
        List<Stan> stanList = stanRepository.findAll();
        assertThat(stanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStan() throws Exception {
        // Initialize the database
        stanRepository.saveAndFlush(stan);

        int databaseSizeBeforeDelete = stanRepository.findAll().size();

        // Delete the stan
        restStanMockMvc.perform(delete("/api/stans/{id}", stan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Stan> stanList = stanRepository.findAll();
        assertThat(stanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
