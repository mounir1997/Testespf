package com.rct.myapp.web.rest;

import com.rct.myapp.RctSampleApplicationApp;

import com.rct.myapp.domain.ValeurChamp;
import com.rct.myapp.repository.ValeurChampRepository;
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
 * Test class for the ValeurChampResource REST controller.
 *
 * @see ValeurChampResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RctSampleApplicationApp.class)
public class ValeurChampResourceIntTest {

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    @Autowired
    private ValeurChampRepository valeurChampRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValeurChampMockMvc;

    private ValeurChamp valeurChamp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValeurChampResource valeurChampResource = new ValeurChampResource(valeurChampRepository);
        this.restValeurChampMockMvc = MockMvcBuilders.standaloneSetup(valeurChampResource)
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
    public static ValeurChamp createEntity(EntityManager em) {
        ValeurChamp valeurChamp = new ValeurChamp()
            .valeur(DEFAULT_VALEUR);
        return valeurChamp;
    }

    @Before
    public void initTest() {
        valeurChamp = createEntity(em);
    }

    @Test
    @Transactional
    public void createValeurChamp() throws Exception {
        int databaseSizeBeforeCreate = valeurChampRepository.findAll().size();

        // Create the ValeurChamp
        restValeurChampMockMvc.perform(post("/api/valeur-champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeurChamp)))
            .andExpect(status().isCreated());

        // Validate the ValeurChamp in the database
        List<ValeurChamp> valeurChampList = valeurChampRepository.findAll();
        assertThat(valeurChampList).hasSize(databaseSizeBeforeCreate + 1);
        ValeurChamp testValeurChamp = valeurChampList.get(valeurChampList.size() - 1);
        assertThat(testValeurChamp.getValeur()).isEqualTo(DEFAULT_VALEUR);
    }

    @Test
    @Transactional
    public void createValeurChampWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valeurChampRepository.findAll().size();

        // Create the ValeurChamp with an existing ID
        valeurChamp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValeurChampMockMvc.perform(post("/api/valeur-champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeurChamp)))
            .andExpect(status().isBadRequest());

        // Validate the ValeurChamp in the database
        List<ValeurChamp> valeurChampList = valeurChampRepository.findAll();
        assertThat(valeurChampList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValeurIsRequired() throws Exception {
        int databaseSizeBeforeTest = valeurChampRepository.findAll().size();
        // set the field null
        valeurChamp.setValeur(null);

        // Create the ValeurChamp, which fails.

        restValeurChampMockMvc.perform(post("/api/valeur-champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeurChamp)))
            .andExpect(status().isBadRequest());

        List<ValeurChamp> valeurChampList = valeurChampRepository.findAll();
        assertThat(valeurChampList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllValeurChamps() throws Exception {
        // Initialize the database
        valeurChampRepository.saveAndFlush(valeurChamp);

        // Get all the valeurChampList
        restValeurChampMockMvc.perform(get("/api/valeur-champs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valeurChamp.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())));
    }
    

    @Test
    @Transactional
    public void getValeurChamp() throws Exception {
        // Initialize the database
        valeurChampRepository.saveAndFlush(valeurChamp);

        // Get the valeurChamp
        restValeurChampMockMvc.perform(get("/api/valeur-champs/{id}", valeurChamp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valeurChamp.getId().intValue()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingValeurChamp() throws Exception {
        // Get the valeurChamp
        restValeurChampMockMvc.perform(get("/api/valeur-champs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValeurChamp() throws Exception {
        // Initialize the database
        valeurChampRepository.saveAndFlush(valeurChamp);

        int databaseSizeBeforeUpdate = valeurChampRepository.findAll().size();

        // Update the valeurChamp
        ValeurChamp updatedValeurChamp = valeurChampRepository.findById(valeurChamp.getId()).get();
        // Disconnect from session so that the updates on updatedValeurChamp are not directly saved in db
        em.detach(updatedValeurChamp);
        updatedValeurChamp
            .valeur(UPDATED_VALEUR);

        restValeurChampMockMvc.perform(put("/api/valeur-champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValeurChamp)))
            .andExpect(status().isOk());

        // Validate the ValeurChamp in the database
        List<ValeurChamp> valeurChampList = valeurChampRepository.findAll();
        assertThat(valeurChampList).hasSize(databaseSizeBeforeUpdate);
        ValeurChamp testValeurChamp = valeurChampList.get(valeurChampList.size() - 1);
        assertThat(testValeurChamp.getValeur()).isEqualTo(UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingValeurChamp() throws Exception {
        int databaseSizeBeforeUpdate = valeurChampRepository.findAll().size();

        // Create the ValeurChamp

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValeurChampMockMvc.perform(put("/api/valeur-champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valeurChamp)))
            .andExpect(status().isBadRequest());

        // Validate the ValeurChamp in the database
        List<ValeurChamp> valeurChampList = valeurChampRepository.findAll();
        assertThat(valeurChampList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteValeurChamp() throws Exception {
        // Initialize the database
        valeurChampRepository.saveAndFlush(valeurChamp);

        int databaseSizeBeforeDelete = valeurChampRepository.findAll().size();

        // Get the valeurChamp
        restValeurChampMockMvc.perform(delete("/api/valeur-champs/{id}", valeurChamp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValeurChamp> valeurChampList = valeurChampRepository.findAll();
        assertThat(valeurChampList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValeurChamp.class);
        ValeurChamp valeurChamp1 = new ValeurChamp();
        valeurChamp1.setId(1L);
        ValeurChamp valeurChamp2 = new ValeurChamp();
        valeurChamp2.setId(valeurChamp1.getId());
        assertThat(valeurChamp1).isEqualTo(valeurChamp2);
        valeurChamp2.setId(2L);
        assertThat(valeurChamp1).isNotEqualTo(valeurChamp2);
        valeurChamp1.setId(null);
        assertThat(valeurChamp1).isNotEqualTo(valeurChamp2);
    }
}
