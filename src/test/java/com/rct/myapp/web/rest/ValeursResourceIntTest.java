package com.rct.myapp.web.rest;

import com.rct.myapp.RctSampleApplicationApp;

import com.rct.myapp.domain.Valeurs;
import com.rct.myapp.repository.ValeursRepository;
import com.rct.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.rct.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ValeursResource REST controller.
 *
 * @see ValeursResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RctSampleApplicationApp.class)
public class ValeursResourceIntTest {

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    @Autowired
    private ValeursRepository valeursRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValeursMockMvc;

    private Valeurs valeurs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValeursResource valeursResource = new ValeursResource(valeursRepository);
        this.restValeursMockMvc = MockMvcBuilders.standaloneSetup(valeursResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Valeurs createEntity(EntityManager em) {
        Valeurs valeurs = new Valeurs()
            .valeur(DEFAULT_VALEUR);
        return valeurs;
    }

    @Before
    public void initTest() {
        valeurs = createEntity(em);
    }

    @Test
    @Transactional
    public void createValeurs() throws Exception {
        int databaseSizeBeforeCreate = valeursRepository.findAll().size();

        // Create the Valeurs
        restValeursMockMvc.perform(post("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeurs)))
            .andExpect(status().isCreated());

        // Validate the Valeurs in the database
        List<Valeurs> valeursList = valeursRepository.findAll();
        assertThat(valeursList).hasSize(databaseSizeBeforeCreate + 1);
        Valeurs testValeurs = valeursList.get(valeursList.size() - 1);
        assertThat(testValeurs.getValeur()).isEqualTo(DEFAULT_VALEUR);
    }

    @Test
    @Transactional
    public void createValeursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valeursRepository.findAll().size();

        // Create the Valeurs with an existing ID
        valeurs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValeursMockMvc.perform(post("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeurs)))
            .andExpect(status().isBadRequest());

        // Validate the Valeurs in the database
        List<Valeurs> valeursList = valeursRepository.findAll();
        assertThat(valeursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = valeursRepository.findAll().size();
        // set the field null
        valeurs.setValeur(null);

        // Create the Valeurs, which fails.

        restValeursMockMvc.perform(post("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeurs)))
            .andExpect(status().isBadRequest());

        List<Valeurs> valeursList = valeursRepository.findAll();
        assertThat(valeursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValeurs() throws Exception {
        // Initialize the database
        valeursRepository.saveAndFlush(valeurs);

        // Get all the valeursList
        restValeursMockMvc.perform(get("/api/valeurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valeurs.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())));
    }
    

    @Test
    @Transactional
    public void getValeurs() throws Exception {
        // Initialize the database
        valeursRepository.saveAndFlush(valeurs);

        // Get the valeurs
        restValeursMockMvc.perform(get("/api/valeurs/{id}", valeurs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valeurs.getId().intValue()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingValeurs() throws Exception {
        // Get the valeurs
        restValeursMockMvc.perform(get("/api/valeurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValeurs() throws Exception {
        // Initialize the database
        valeursRepository.saveAndFlush(valeurs);

        int databaseSizeBeforeUpdate = valeursRepository.findAll().size();

        // Update the valeurs
        Valeurs updatedValeurs = valeursRepository.findById(valeurs.getId()).get();
        // Disconnect from session so that the updates on updatedValeurs are not directly saved in db
        em.detach(updatedValeurs);
        updatedValeurs
            .valeur(UPDATED_VALEUR);

        restValeursMockMvc.perform(put("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValeurs)))
            .andExpect(status().isOk());

        // Validate the Valeurs in the database
        List<Valeurs> valeursList = valeursRepository.findAll();
        assertThat(valeursList).hasSize(databaseSizeBeforeUpdate);
        Valeurs testValeurs = valeursList.get(valeursList.size() - 1);
        assertThat(testValeurs.getValeur()).isEqualTo(UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingValeurs() throws Exception {
        int databaseSizeBeforeUpdate = valeursRepository.findAll().size();

        // Create the Valeurs

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValeursMockMvc.perform(put("/api/valeurs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeurs)))
            .andExpect(status().isBadRequest());

        // Validate the Valeurs in the database
        List<Valeurs> valeursList = valeursRepository.findAll();
        assertThat(valeursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteValeurs() throws Exception {
        // Initialize the database
        valeursRepository.saveAndFlush(valeurs);

        int databaseSizeBeforeDelete = valeursRepository.findAll().size();

        // Get the valeurs
        restValeursMockMvc.perform(delete("/api/valeurs/{id}", valeurs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Valeurs> valeursList = valeursRepository.findAll();
        assertThat(valeursList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Valeurs.class);
        Valeurs valeurs1 = new Valeurs();
        valeurs1.setId(1L);
        Valeurs valeurs2 = new Valeurs();
        valeurs2.setId(valeurs1.getId());
        assertThat(valeurs1).isEqualTo(valeurs2);
        valeurs2.setId(2L);
        assertThat(valeurs1).isNotEqualTo(valeurs2);
        valeurs1.setId(null);
        assertThat(valeurs1).isNotEqualTo(valeurs2);
    }
}
