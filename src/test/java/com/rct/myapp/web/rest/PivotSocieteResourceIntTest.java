package com.rct.myapp.web.rest;

import com.rct.myapp.RctSampleApplicationApp;

import com.rct.myapp.domain.PivotSociete;
import com.rct.myapp.repository.PivotSocieteRepository;
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
 * Test class for the PivotSocieteResource REST controller.
 *
 * @see PivotSocieteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RctSampleApplicationApp.class)
public class PivotSocieteResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private PivotSocieteRepository pivotSocieteRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPivotSocieteMockMvc;

    private PivotSociete pivotSociete;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PivotSocieteResource pivotSocieteResource = new PivotSocieteResource(pivotSocieteRepository);
        this.restPivotSocieteMockMvc = MockMvcBuilders.standaloneSetup(pivotSocieteResource)
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
    public static PivotSociete createEntity(EntityManager em) {
        PivotSociete pivotSociete = new PivotSociete()
            .nom(DEFAULT_NOM);
        return pivotSociete;
    }

    @Before
    public void initTest() {
        pivotSociete = createEntity(em);
    }

    @Test
    @Transactional
    public void createPivotSociete() throws Exception {
        int databaseSizeBeforeCreate = pivotSocieteRepository.findAll().size();

        // Create the PivotSociete
        restPivotSocieteMockMvc.perform(post("/api/pivot-societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pivotSociete)))
            .andExpect(status().isCreated());

        // Validate the PivotSociete in the database
        List<PivotSociete> pivotSocieteList = pivotSocieteRepository.findAll();
        assertThat(pivotSocieteList).hasSize(databaseSizeBeforeCreate + 1);
        PivotSociete testPivotSociete = pivotSocieteList.get(pivotSocieteList.size() - 1);
        assertThat(testPivotSociete.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createPivotSocieteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pivotSocieteRepository.findAll().size();

        // Create the PivotSociete with an existing ID
        pivotSociete.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPivotSocieteMockMvc.perform(post("/api/pivot-societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pivotSociete)))
            .andExpect(status().isBadRequest());

        // Validate the PivotSociete in the database
        List<PivotSociete> pivotSocieteList = pivotSocieteRepository.findAll();
        assertThat(pivotSocieteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = pivotSocieteRepository.findAll().size();
        // set the field null
        pivotSociete.setNom(null);

        // Create the PivotSociete, which fails.

        restPivotSocieteMockMvc.perform(post("/api/pivot-societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pivotSociete)))
            .andExpect(status().isBadRequest());

        List<PivotSociete> pivotSocieteList = pivotSocieteRepository.findAll();
        assertThat(pivotSocieteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPivotSocietes() throws Exception {
        // Initialize the database
        pivotSocieteRepository.saveAndFlush(pivotSociete);

        // Get all the pivotSocieteList
        restPivotSocieteMockMvc.perform(get("/api/pivot-societes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pivotSociete.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }
    

    @Test
    @Transactional
    public void getPivotSociete() throws Exception {
        // Initialize the database
        pivotSocieteRepository.saveAndFlush(pivotSociete);

        // Get the pivotSociete
        restPivotSocieteMockMvc.perform(get("/api/pivot-societes/{id}", pivotSociete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pivotSociete.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPivotSociete() throws Exception {
        // Get the pivotSociete
        restPivotSocieteMockMvc.perform(get("/api/pivot-societes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePivotSociete() throws Exception {
        // Initialize the database
        pivotSocieteRepository.saveAndFlush(pivotSociete);

        int databaseSizeBeforeUpdate = pivotSocieteRepository.findAll().size();

        // Update the pivotSociete
        PivotSociete updatedPivotSociete = pivotSocieteRepository.findById(pivotSociete.getId()).get();
        // Disconnect from session so that the updates on updatedPivotSociete are not directly saved in db
        em.detach(updatedPivotSociete);
        updatedPivotSociete
            .nom(UPDATED_NOM);

        restPivotSocieteMockMvc.perform(put("/api/pivot-societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPivotSociete)))
            .andExpect(status().isOk());

        // Validate the PivotSociete in the database
        List<PivotSociete> pivotSocieteList = pivotSocieteRepository.findAll();
        assertThat(pivotSocieteList).hasSize(databaseSizeBeforeUpdate);
        PivotSociete testPivotSociete = pivotSocieteList.get(pivotSocieteList.size() - 1);
        assertThat(testPivotSociete.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingPivotSociete() throws Exception {
        int databaseSizeBeforeUpdate = pivotSocieteRepository.findAll().size();

        // Create the PivotSociete

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPivotSocieteMockMvc.perform(put("/api/pivot-societes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pivotSociete)))
            .andExpect(status().isBadRequest());

        // Validate the PivotSociete in the database
        List<PivotSociete> pivotSocieteList = pivotSocieteRepository.findAll();
        assertThat(pivotSocieteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePivotSociete() throws Exception {
        // Initialize the database
        pivotSocieteRepository.saveAndFlush(pivotSociete);

        int databaseSizeBeforeDelete = pivotSocieteRepository.findAll().size();

        // Get the pivotSociete
        restPivotSocieteMockMvc.perform(delete("/api/pivot-societes/{id}", pivotSociete.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PivotSociete> pivotSocieteList = pivotSocieteRepository.findAll();
        assertThat(pivotSocieteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PivotSociete.class);
        PivotSociete pivotSociete1 = new PivotSociete();
        pivotSociete1.setId(1L);
        PivotSociete pivotSociete2 = new PivotSociete();
        pivotSociete2.setId(pivotSociete1.getId());
        assertThat(pivotSociete1).isEqualTo(pivotSociete2);
        pivotSociete2.setId(2L);
        assertThat(pivotSociete1).isNotEqualTo(pivotSociete2);
        pivotSociete1.setId(null);
        assertThat(pivotSociete1).isNotEqualTo(pivotSociete2);
    }
}
