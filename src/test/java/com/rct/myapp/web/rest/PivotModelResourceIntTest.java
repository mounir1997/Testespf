package com.rct.myapp.web.rest;

import com.rct.myapp.RctSampleApplicationApp;

import com.rct.myapp.domain.PivotModel;
import com.rct.myapp.repository.PivotModelRepository;
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
 * Test class for the PivotModelResource REST controller.
 *
 * @see PivotModelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RctSampleApplicationApp.class)
public class PivotModelResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private PivotModelRepository pivotModelRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPivotModelMockMvc;

    private PivotModel pivotModel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PivotModelResource pivotModelResource = new PivotModelResource(pivotModelRepository);
        this.restPivotModelMockMvc = MockMvcBuilders.standaloneSetup(pivotModelResource)
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
    public static PivotModel createEntity(EntityManager em) {
        PivotModel pivotModel = new PivotModel()
            .nom(DEFAULT_NOM);
        return pivotModel;
    }

    @Before
    public void initTest() {
        pivotModel = createEntity(em);
    }

    @Test
    @Transactional
    public void createPivotModel() throws Exception {
        int databaseSizeBeforeCreate = pivotModelRepository.findAll().size();

        // Create the PivotModel
        restPivotModelMockMvc.perform(post("/api/pivot-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pivotModel)))
            .andExpect(status().isCreated());

        // Validate the PivotModel in the database
        List<PivotModel> pivotModelList = pivotModelRepository.findAll();
        assertThat(pivotModelList).hasSize(databaseSizeBeforeCreate + 1);
        PivotModel testPivotModel = pivotModelList.get(pivotModelList.size() - 1);
        assertThat(testPivotModel.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createPivotModelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pivotModelRepository.findAll().size();

        // Create the PivotModel with an existing ID
        pivotModel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPivotModelMockMvc.perform(post("/api/pivot-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pivotModel)))
            .andExpect(status().isBadRequest());

        // Validate the PivotModel in the database
        List<PivotModel> pivotModelList = pivotModelRepository.findAll();
        assertThat(pivotModelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = pivotModelRepository.findAll().size();
        // set the field null
        pivotModel.setNom(null);

        // Create the PivotModel, which fails.

        restPivotModelMockMvc.perform(post("/api/pivot-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pivotModel)))
            .andExpect(status().isBadRequest());

        List<PivotModel> pivotModelList = pivotModelRepository.findAll();
        assertThat(pivotModelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPivotModels() throws Exception {
        // Initialize the database
        pivotModelRepository.saveAndFlush(pivotModel);

        // Get all the pivotModelList
        restPivotModelMockMvc.perform(get("/api/pivot-models?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pivotModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }
    

    @Test
    @Transactional
    public void getPivotModel() throws Exception {
        // Initialize the database
        pivotModelRepository.saveAndFlush(pivotModel);

        // Get the pivotModel
        restPivotModelMockMvc.perform(get("/api/pivot-models/{id}", pivotModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pivotModel.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPivotModel() throws Exception {
        // Get the pivotModel
        restPivotModelMockMvc.perform(get("/api/pivot-models/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePivotModel() throws Exception {
        // Initialize the database
        pivotModelRepository.saveAndFlush(pivotModel);

        int databaseSizeBeforeUpdate = pivotModelRepository.findAll().size();

        // Update the pivotModel
        PivotModel updatedPivotModel = pivotModelRepository.findById(pivotModel.getId()).get();
        // Disconnect from session so that the updates on updatedPivotModel are not directly saved in db
        em.detach(updatedPivotModel);
        updatedPivotModel
            .nom(UPDATED_NOM);

        restPivotModelMockMvc.perform(put("/api/pivot-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPivotModel)))
            .andExpect(status().isOk());

        // Validate the PivotModel in the database
        List<PivotModel> pivotModelList = pivotModelRepository.findAll();
        assertThat(pivotModelList).hasSize(databaseSizeBeforeUpdate);
        PivotModel testPivotModel = pivotModelList.get(pivotModelList.size() - 1);
        assertThat(testPivotModel.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingPivotModel() throws Exception {
        int databaseSizeBeforeUpdate = pivotModelRepository.findAll().size();

        // Create the PivotModel

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPivotModelMockMvc.perform(put("/api/pivot-models")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pivotModel)))
            .andExpect(status().isBadRequest());

        // Validate the PivotModel in the database
        List<PivotModel> pivotModelList = pivotModelRepository.findAll();
        assertThat(pivotModelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePivotModel() throws Exception {
        // Initialize the database
        pivotModelRepository.saveAndFlush(pivotModel);

        int databaseSizeBeforeDelete = pivotModelRepository.findAll().size();

        // Get the pivotModel
        restPivotModelMockMvc.perform(delete("/api/pivot-models/{id}", pivotModel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PivotModel> pivotModelList = pivotModelRepository.findAll();
        assertThat(pivotModelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PivotModel.class);
        PivotModel pivotModel1 = new PivotModel();
        pivotModel1.setId(1L);
        PivotModel pivotModel2 = new PivotModel();
        pivotModel2.setId(pivotModel1.getId());
        assertThat(pivotModel1).isEqualTo(pivotModel2);
        pivotModel2.setId(2L);
        assertThat(pivotModel1).isNotEqualTo(pivotModel2);
        pivotModel1.setId(null);
        assertThat(pivotModel1).isNotEqualTo(pivotModel2);
    }
}
