package com.rct.myapp.web.rest;

import com.rct.myapp.RctSampleApplicationApp;

import com.rct.myapp.domain.Champs;
import com.rct.myapp.repository.ChampsRepository;
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
 * Test class for the ChampsResource REST controller.
 *
 * @see ChampsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RctSampleApplicationApp.class)
public class ChampsResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_LONGUEUR = "AAAAAAAAAA";
    private static final String UPDATED_LONGUEUR = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private ChampsRepository champsRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChampsMockMvc;

    private Champs champs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChampsResource champsResource = new ChampsResource(champsRepository);
        this.restChampsMockMvc = MockMvcBuilders.standaloneSetup(champsResource)
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
    public static Champs createEntity(EntityManager em) {
        Champs champs = new Champs()
            .code(DEFAULT_CODE)
            .position(DEFAULT_POSITION)
            .longueur(DEFAULT_LONGUEUR)
            .type(DEFAULT_TYPE);
        return champs;
    }

    @Before
    public void initTest() {
        champs = createEntity(em);
    }

    @Test
    @Transactional
    public void createChamps() throws Exception {
        int databaseSizeBeforeCreate = champsRepository.findAll().size();

        // Create the Champs
        restChampsMockMvc.perform(post("/api/champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champs)))
            .andExpect(status().isCreated());

        // Validate the Champs in the database
        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeCreate + 1);
        Champs testChamps = champsList.get(champsList.size() - 1);
        assertThat(testChamps.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testChamps.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testChamps.getLongueur()).isEqualTo(DEFAULT_LONGUEUR);
        assertThat(testChamps.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createChampsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = champsRepository.findAll().size();

        // Create the Champs with an existing ID
        champs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChampsMockMvc.perform(post("/api/champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champs)))
            .andExpect(status().isBadRequest());

        // Validate the Champs in the database
        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = champsRepository.findAll().size();
        // set the field null
        champs.setCode(null);

        // Create the Champs, which fails.

        restChampsMockMvc.perform(post("/api/champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champs)))
            .andExpect(status().isBadRequest());

        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = champsRepository.findAll().size();
        // set the field null
        champs.setPosition(null);

        // Create the Champs, which fails.

        restChampsMockMvc.perform(post("/api/champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champs)))
            .andExpect(status().isBadRequest());

        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongueurIsRequired() throws Exception {
        int databaseSizeBeforeTest = champsRepository.findAll().size();
        // set the field null
        champs.setLongueur(null);

        // Create the Champs, which fails.

        restChampsMockMvc.perform(post("/api/champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champs)))
            .andExpect(status().isBadRequest());

        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = champsRepository.findAll().size();
        // set the field null
        champs.setType(null);

        // Create the Champs, which fails.

        restChampsMockMvc.perform(post("/api/champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champs)))
            .andExpect(status().isBadRequest());

        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChamps() throws Exception {
        // Initialize the database
        champsRepository.saveAndFlush(champs);

        // Get all the champsList
        restChampsMockMvc.perform(get("/api/champs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(champs.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].longueur").value(hasItem(DEFAULT_LONGUEUR.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    

    @Test
    @Transactional
    public void getChamps() throws Exception {
        // Initialize the database
        champsRepository.saveAndFlush(champs);

        // Get the champs
        restChampsMockMvc.perform(get("/api/champs/{id}", champs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(champs.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.longueur").value(DEFAULT_LONGUEUR.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingChamps() throws Exception {
        // Get the champs
        restChampsMockMvc.perform(get("/api/champs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChamps() throws Exception {
        // Initialize the database
        champsRepository.saveAndFlush(champs);

        int databaseSizeBeforeUpdate = champsRepository.findAll().size();

        // Update the champs
        Champs updatedChamps = champsRepository.findById(champs.getId()).get();
        // Disconnect from session so that the updates on updatedChamps are not directly saved in db
        em.detach(updatedChamps);
        updatedChamps
            .code(UPDATED_CODE)
            .position(UPDATED_POSITION)
            .longueur(UPDATED_LONGUEUR)
            .type(UPDATED_TYPE);

        restChampsMockMvc.perform(put("/api/champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChamps)))
            .andExpect(status().isOk());

        // Validate the Champs in the database
        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeUpdate);
        Champs testChamps = champsList.get(champsList.size() - 1);
        assertThat(testChamps.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testChamps.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testChamps.getLongueur()).isEqualTo(UPDATED_LONGUEUR);
        assertThat(testChamps.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingChamps() throws Exception {
        int databaseSizeBeforeUpdate = champsRepository.findAll().size();

        // Create the Champs

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChampsMockMvc.perform(put("/api/champs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(champs)))
            .andExpect(status().isBadRequest());

        // Validate the Champs in the database
        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChamps() throws Exception {
        // Initialize the database
        champsRepository.saveAndFlush(champs);

        int databaseSizeBeforeDelete = champsRepository.findAll().size();

        // Get the champs
        restChampsMockMvc.perform(delete("/api/champs/{id}", champs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Champs> champsList = champsRepository.findAll();
        assertThat(champsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Champs.class);
        Champs champs1 = new Champs();
        champs1.setId(1L);
        Champs champs2 = new Champs();
        champs2.setId(champs1.getId());
        assertThat(champs1).isEqualTo(champs2);
        champs2.setId(2L);
        assertThat(champs1).isNotEqualTo(champs2);
        champs1.setId(null);
        assertThat(champs1).isNotEqualTo(champs2);
    }
}
