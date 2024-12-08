package toplana.web.rest;

import toplana.ToplanaApp;
import toplana.domain.Podstanica;
import toplana.repository.PodstanicaRepository;

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
 * Integration tests for the {@link PodstanicaResource} REST controller.
 */
@SpringBootTest(classes = ToplanaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PodstanicaResourceIT {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    @Autowired
    private PodstanicaRepository podstanicaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPodstanicaMockMvc;

    private Podstanica podstanica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Podstanica createEntity(EntityManager em) {
        Podstanica podstanica = new Podstanica()
            .naziv(DEFAULT_NAZIV);
        return podstanica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Podstanica createUpdatedEntity(EntityManager em) {
        Podstanica podstanica = new Podstanica()
            .naziv(UPDATED_NAZIV);
        return podstanica;
    }

    @BeforeEach
    public void initTest() {
        podstanica = createEntity(em);
    }

    @Test
    @Transactional
    public void createPodstanica() throws Exception {
        int databaseSizeBeforeCreate = podstanicaRepository.findAll().size();
        // Create the Podstanica
        restPodstanicaMockMvc.perform(post("/api/podstanicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(podstanica)))
            .andExpect(status().isCreated());

        // Validate the Podstanica in the database
        List<Podstanica> podstanicaList = podstanicaRepository.findAll();
        assertThat(podstanicaList).hasSize(databaseSizeBeforeCreate + 1);
        Podstanica testPodstanica = podstanicaList.get(podstanicaList.size() - 1);
        assertThat(testPodstanica.getNaziv()).isEqualTo(DEFAULT_NAZIV);
    }

    @Test
    @Transactional
    public void createPodstanicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = podstanicaRepository.findAll().size();

        // Create the Podstanica with an existing ID
        podstanica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPodstanicaMockMvc.perform(post("/api/podstanicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(podstanica)))
            .andExpect(status().isBadRequest());

        // Validate the Podstanica in the database
        List<Podstanica> podstanicaList = podstanicaRepository.findAll();
        assertThat(podstanicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPodstanicas() throws Exception {
        // Initialize the database
        podstanicaRepository.saveAndFlush(podstanica);

        // Get all the podstanicaList
        restPodstanicaMockMvc.perform(get("/api/podstanicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(podstanica.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV)));
    }
    
    @Test
    @Transactional
    public void getPodstanica() throws Exception {
        // Initialize the database
        podstanicaRepository.saveAndFlush(podstanica);

        // Get the podstanica
        restPodstanicaMockMvc.perform(get("/api/podstanicas/{id}", podstanica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(podstanica.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV));
    }
    @Test
    @Transactional
    public void getNonExistingPodstanica() throws Exception {
        // Get the podstanica
        restPodstanicaMockMvc.perform(get("/api/podstanicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePodstanica() throws Exception {
        // Initialize the database
        podstanicaRepository.saveAndFlush(podstanica);

        int databaseSizeBeforeUpdate = podstanicaRepository.findAll().size();

        // Update the podstanica
        Podstanica updatedPodstanica = podstanicaRepository.findById(podstanica.getId()).get();
        // Disconnect from session so that the updates on updatedPodstanica are not directly saved in db
        em.detach(updatedPodstanica);
        updatedPodstanica
            .naziv(UPDATED_NAZIV);

        restPodstanicaMockMvc.perform(put("/api/podstanicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPodstanica)))
            .andExpect(status().isOk());

        // Validate the Podstanica in the database
        List<Podstanica> podstanicaList = podstanicaRepository.findAll();
        assertThat(podstanicaList).hasSize(databaseSizeBeforeUpdate);
        Podstanica testPodstanica = podstanicaList.get(podstanicaList.size() - 1);
        assertThat(testPodstanica.getNaziv()).isEqualTo(UPDATED_NAZIV);
    }

    @Test
    @Transactional
    public void updateNonExistingPodstanica() throws Exception {
        int databaseSizeBeforeUpdate = podstanicaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPodstanicaMockMvc.perform(put("/api/podstanicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(podstanica)))
            .andExpect(status().isBadRequest());

        // Validate the Podstanica in the database
        List<Podstanica> podstanicaList = podstanicaRepository.findAll();
        assertThat(podstanicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePodstanica() throws Exception {
        // Initialize the database
        podstanicaRepository.saveAndFlush(podstanica);

        int databaseSizeBeforeDelete = podstanicaRepository.findAll().size();

        // Delete the podstanica
        restPodstanicaMockMvc.perform(delete("/api/podstanicas/{id}", podstanica.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Podstanica> podstanicaList = podstanicaRepository.findAll();
        assertThat(podstanicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
