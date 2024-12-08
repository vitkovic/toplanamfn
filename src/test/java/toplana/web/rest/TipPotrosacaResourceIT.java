package toplana.web.rest;

import toplana.ToplanaApp;
import toplana.domain.TipPotrosaca;
import toplana.repository.TipPotrosacaRepository;

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
 * Integration tests for the {@link TipPotrosacaResource} REST controller.
 */
@SpringBootTest(classes = ToplanaApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipPotrosacaResourceIT {

    private static final Integer DEFAULT_TIP = 1;
    private static final Integer UPDATED_TIP = 2;

    @Autowired
    private TipPotrosacaRepository tipPotrosacaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipPotrosacaMockMvc;

    private TipPotrosaca tipPotrosaca;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipPotrosaca createEntity(EntityManager em) {
        TipPotrosaca tipPotrosaca = new TipPotrosaca()
            .tip(DEFAULT_TIP);
        return tipPotrosaca;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipPotrosaca createUpdatedEntity(EntityManager em) {
        TipPotrosaca tipPotrosaca = new TipPotrosaca()
            .tip(UPDATED_TIP);
        return tipPotrosaca;
    }

    @BeforeEach
    public void initTest() {
        tipPotrosaca = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipPotrosaca() throws Exception {
        int databaseSizeBeforeCreate = tipPotrosacaRepository.findAll().size();
        // Create the TipPotrosaca
        restTipPotrosacaMockMvc.perform(post("/api/tip-potrosacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipPotrosaca)))
            .andExpect(status().isCreated());

        // Validate the TipPotrosaca in the database
        List<TipPotrosaca> tipPotrosacaList = tipPotrosacaRepository.findAll();
        assertThat(tipPotrosacaList).hasSize(databaseSizeBeforeCreate + 1);
        TipPotrosaca testTipPotrosaca = tipPotrosacaList.get(tipPotrosacaList.size() - 1);
        assertThat(testTipPotrosaca.getTip()).isEqualTo(DEFAULT_TIP);
    }

    @Test
    @Transactional
    public void createTipPotrosacaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipPotrosacaRepository.findAll().size();

        // Create the TipPotrosaca with an existing ID
        tipPotrosaca.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipPotrosacaMockMvc.perform(post("/api/tip-potrosacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipPotrosaca)))
            .andExpect(status().isBadRequest());

        // Validate the TipPotrosaca in the database
        List<TipPotrosaca> tipPotrosacaList = tipPotrosacaRepository.findAll();
        assertThat(tipPotrosacaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipPotrosacas() throws Exception {
        // Initialize the database
        tipPotrosacaRepository.saveAndFlush(tipPotrosaca);

        // Get all the tipPotrosacaList
        restTipPotrosacaMockMvc.perform(get("/api/tip-potrosacas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipPotrosaca.getId().intValue())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP)));
    }
    
    @Test
    @Transactional
    public void getTipPotrosaca() throws Exception {
        // Initialize the database
        tipPotrosacaRepository.saveAndFlush(tipPotrosaca);

        // Get the tipPotrosaca
        restTipPotrosacaMockMvc.perform(get("/api/tip-potrosacas/{id}", tipPotrosaca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipPotrosaca.getId().intValue()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP));
    }
    @Test
    @Transactional
    public void getNonExistingTipPotrosaca() throws Exception {
        // Get the tipPotrosaca
        restTipPotrosacaMockMvc.perform(get("/api/tip-potrosacas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipPotrosaca() throws Exception {
        // Initialize the database
        tipPotrosacaRepository.saveAndFlush(tipPotrosaca);

        int databaseSizeBeforeUpdate = tipPotrosacaRepository.findAll().size();

        // Update the tipPotrosaca
        TipPotrosaca updatedTipPotrosaca = tipPotrosacaRepository.findById(tipPotrosaca.getId()).get();
        // Disconnect from session so that the updates on updatedTipPotrosaca are not directly saved in db
        em.detach(updatedTipPotrosaca);
        updatedTipPotrosaca
            .tip(UPDATED_TIP);

        restTipPotrosacaMockMvc.perform(put("/api/tip-potrosacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipPotrosaca)))
            .andExpect(status().isOk());

        // Validate the TipPotrosaca in the database
        List<TipPotrosaca> tipPotrosacaList = tipPotrosacaRepository.findAll();
        assertThat(tipPotrosacaList).hasSize(databaseSizeBeforeUpdate);
        TipPotrosaca testTipPotrosaca = tipPotrosacaList.get(tipPotrosacaList.size() - 1);
        assertThat(testTipPotrosaca.getTip()).isEqualTo(UPDATED_TIP);
    }

    @Test
    @Transactional
    public void updateNonExistingTipPotrosaca() throws Exception {
        int databaseSizeBeforeUpdate = tipPotrosacaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipPotrosacaMockMvc.perform(put("/api/tip-potrosacas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipPotrosaca)))
            .andExpect(status().isBadRequest());

        // Validate the TipPotrosaca in the database
        List<TipPotrosaca> tipPotrosacaList = tipPotrosacaRepository.findAll();
        assertThat(tipPotrosacaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipPotrosaca() throws Exception {
        // Initialize the database
        tipPotrosacaRepository.saveAndFlush(tipPotrosaca);

        int databaseSizeBeforeDelete = tipPotrosacaRepository.findAll().size();

        // Delete the tipPotrosaca
        restTipPotrosacaMockMvc.perform(delete("/api/tip-potrosacas/{id}", tipPotrosaca.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipPotrosaca> tipPotrosacaList = tipPotrosacaRepository.findAll();
        assertThat(tipPotrosacaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
