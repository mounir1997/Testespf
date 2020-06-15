package com.rct.myapp.web.rest;

import com.rct.myapp.RctSampleApplicationApp;

import com.rct.myapp.domain.LigneESPF;
import com.rct.myapp.repository.LigneESPFRepository;
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
 * Test class for the LigneESPFResource REST controller.
 *
 * @see LigneESPFResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RctSampleApplicationApp.class)
public class LigneESPFResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private LigneESPFRepository ligneESPFRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLigneESPFMockMvc;

    private LigneESPF ligneESPF;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LigneESPFResource ligneESPFResource = new LigneESPFResource(ligneESPFRepository);
        this.restLigneESPFMockMvc = MockMvcBuilders.standaloneSetup(ligneESPFResource)
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
    public static LigneESPF createEntity(EntityManager em) {
        LigneESPF ligneESPF = new LigneESPF()
            .code(DEFAULT_CODE);
        return ligneESPF;
    }

    @Before
    public void initTest() {
        ligneESPF = createEntity(em);
    }

    @Test
    @Transactional
    public void createLigneESPF() throws Exception {
        int databaseSizeBeforeCreate = ligneESPFRepository.findAll().size();

        // Create the LigneESPF
        restLigneESPFMockMvc.perform(post("/api/ligne-espfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneESPF)))
            .andExpect(status().isCreated());

        // Validate the LigneESPF in the database
        List<LigneESPF> ligneESPFList = ligneESPFRepository.findAll();
        assertThat(ligneESPFList).hasSize(databaseSizeBeforeCreate + 1);
        LigneESPF testLigneESPF = ligneESPFList.get(ligneESPFList.size() - 1);
        assertThat(testLigneESPF.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createLigneESPFWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ligneESPFRepository.findAll().size();

        // Create the LigneESPF with an existing ID
        ligneESPF.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneESPFMockMvc.perform(post("/api/ligne-espfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneESPF)))
            .andExpect(status().isBadRequest());

        // Validate the LigneESPF in the database
        List<LigneESPF> ligneESPFList = ligneESPFRepository.findAll();
        assertThat(ligneESPFList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligneESPFRepository.findAll().size();
        // set the field null
        ligneESPF.setCode(null);

        // Create the LigneESPF, which fails.

        restLigneESPFMockMvc.perform(post("/api/ligne-espfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneESPF)))
            .andExpect(status().isBadRequest());

        List<LigneESPF> ligneESPFList = ligneESPFRepository.findAll();
        assertThat(ligneESPFList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLigneESPFS() throws Exception {
        // Initialize the database
        ligneESPFRepository.saveAndFlush(ligneESPF);

        // Get all the ligneESPFList
        restLigneESPFMockMvc.perform(get("/api/ligne-espfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneESPF.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }
    

    @Test
    @Transactional
    public void getLigneESPF() throws Exception {
        // Initialize the database
        ligneESPFRepository.saveAndFlush(ligneESPF);

        // Get the ligneESPF
        restLigneESPFMockMvc.perform(get("/api/ligne-espfs/{id}", ligneESPF.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ligneESPF.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingLigneESPF() throws Exception {
        // Get the ligneESPF
        restLigneESPFMockMvc.perform(get("/api/ligne-espfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLigneESPF() throws Exception {
        // Initialize the database
        ligneESPFRepository.saveAndFlush(ligneESPF);

        int databaseSizeBeforeUpdate = ligneESPFRepository.findAll().size();

        // Update the ligneESPF
        LigneESPF updatedLigneESPF = ligneESPFRepository.findById(ligneESPF.getId()).get();
        // Disconnect from session so that the updates on updatedLigneESPF are not directly saved in db
        em.detach(updatedLigneESPF);
        updatedLigneESPF
            .code(UPDATED_CODE);

        restLigneESPFMockMvc.perform(put("/api/ligne-espfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLigneESPF)))
            .andExpect(status().isOk());

        // Validate the LigneESPF in the database
        List<LigneESPF> ligneESPFList = ligneESPFRepository.findAll();
        assertThat(ligneESPFList).hasSize(databaseSizeBeforeUpdate);
        LigneESPF testLigneESPF = ligneESPFList.get(ligneESPFList.size() - 1);
        assertThat(testLigneESPF.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingLigneESPF() throws Exception {
        int databaseSizeBeforeUpdate = ligneESPFRepository.findAll().size();

        // Create the LigneESPF

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLigneESPFMockMvc.perform(put("/api/ligne-espfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligneESPF)))
            .andExpect(status().isBadRequest());

        // Validate the LigneESPF in the database
        List<LigneESPF> ligneESPFList = ligneESPFRepository.findAll();
        assertThat(ligneESPFList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLigneESPF() throws Exception {
        // Initialize the database
        ligneESPFRepository.saveAndFlush(ligneESPF);

        int databaseSizeBeforeDelete = ligneESPFRepository.findAll().size();

        // Get the ligneESPF
        restLigneESPFMockMvc.perform(delete("/api/ligne-espfs/{id}", ligneESPF.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LigneESPF> ligneESPFList = ligneESPFRepository.findAll();
        assertThat(ligneESPFList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneESPF.class);
        LigneESPF ligneESPF1 = new LigneESPF();
        ligneESPF1.setId(1L);
        LigneESPF ligneESPF2 = new LigneESPF();
        ligneESPF2.setId(ligneESPF1.getId());
        assertThat(ligneESPF1).isEqualTo(ligneESPF2);
        ligneESPF2.setId(2L);
        assertThat(ligneESPF1).isNotEqualTo(ligneESPF2);
        ligneESPF1.setId(null);
        assertThat(ligneESPF1).isNotEqualTo(ligneESPF2);
    }
}
