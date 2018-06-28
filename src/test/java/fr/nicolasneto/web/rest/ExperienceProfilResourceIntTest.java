package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.ExperienceProfil;
import fr.nicolasneto.repository.ExperienceProfilRepository;
import fr.nicolasneto.repository.search.ExperienceProfilSearchRepository;
import fr.nicolasneto.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static fr.nicolasneto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExperienceProfilResource REST controller.
 *
 * @see ExperienceProfilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class ExperienceProfilResourceIntTest {

    private static final LocalDate DEFAULT_ANNE_EXPERIENCE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNE_EXPERIENCE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ANNE_EXPERIENCE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNE_EXPERIENCE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private ExperienceProfilRepository experienceProfilRepository;

    @Autowired
    private ExperienceProfilSearchRepository experienceProfilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExperienceProfilMockMvc;

    private ExperienceProfil experienceProfil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExperienceProfilResource experienceProfilResource = new ExperienceProfilResource(experienceProfilRepository, experienceProfilSearchRepository);
        this.restExperienceProfilMockMvc = MockMvcBuilders.standaloneSetup(experienceProfilResource)
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
    public static ExperienceProfil createEntity(EntityManager em) {
        ExperienceProfil experienceProfil = new ExperienceProfil()
            .anneExperienceDebut(DEFAULT_ANNE_EXPERIENCE_DEBUT)
            .anneExperienceFin(DEFAULT_ANNE_EXPERIENCE_FIN)
            .comment(DEFAULT_COMMENT);
        return experienceProfil;
    }

    @Before
    public void initTest() {
        experienceProfilSearchRepository.deleteAll();
        experienceProfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createExperienceProfil() throws Exception {
        int databaseSizeBeforeCreate = experienceProfilRepository.findAll().size();

        // Create the ExperienceProfil
        restExperienceProfilMockMvc.perform(post("/api/experience-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceProfil)))
            .andExpect(status().isCreated());

        // Validate the ExperienceProfil in the database
        List<ExperienceProfil> experienceProfilList = experienceProfilRepository.findAll();
        assertThat(experienceProfilList).hasSize(databaseSizeBeforeCreate + 1);
        ExperienceProfil testExperienceProfil = experienceProfilList.get(experienceProfilList.size() - 1);
        assertThat(testExperienceProfil.getAnneExperienceDebut()).isEqualTo(DEFAULT_ANNE_EXPERIENCE_DEBUT);
        assertThat(testExperienceProfil.getAnneExperienceFin()).isEqualTo(DEFAULT_ANNE_EXPERIENCE_FIN);
        assertThat(testExperienceProfil.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the ExperienceProfil in Elasticsearch
        ExperienceProfil experienceProfilEs = experienceProfilSearchRepository.findOne(testExperienceProfil.getId());
        assertThat(experienceProfilEs).isEqualToIgnoringGivenFields(testExperienceProfil);
    }

    @Test
    @Transactional
    public void createExperienceProfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = experienceProfilRepository.findAll().size();

        // Create the ExperienceProfil with an existing ID
        experienceProfil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExperienceProfilMockMvc.perform(post("/api/experience-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceProfil)))
            .andExpect(status().isBadRequest());

        // Validate the ExperienceProfil in the database
        List<ExperienceProfil> experienceProfilList = experienceProfilRepository.findAll();
        assertThat(experienceProfilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAnneExperienceDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceProfilRepository.findAll().size();
        // set the field null
        experienceProfil.setAnneExperienceDebut(null);

        // Create the ExperienceProfil, which fails.

        restExperienceProfilMockMvc.perform(post("/api/experience-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceProfil)))
            .andExpect(status().isBadRequest());

        List<ExperienceProfil> experienceProfilList = experienceProfilRepository.findAll();
        assertThat(experienceProfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnneExperienceFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = experienceProfilRepository.findAll().size();
        // set the field null
        experienceProfil.setAnneExperienceFin(null);

        // Create the ExperienceProfil, which fails.

        restExperienceProfilMockMvc.perform(post("/api/experience-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceProfil)))
            .andExpect(status().isBadRequest());

        List<ExperienceProfil> experienceProfilList = experienceProfilRepository.findAll();
        assertThat(experienceProfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExperienceProfils() throws Exception {
        // Initialize the database
        experienceProfilRepository.saveAndFlush(experienceProfil);

        // Get all the experienceProfilList
        restExperienceProfilMockMvc.perform(get("/api/experience-profils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].anneExperienceDebut").value(hasItem(DEFAULT_ANNE_EXPERIENCE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].anneExperienceFin").value(hasItem(DEFAULT_ANNE_EXPERIENCE_FIN.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getExperienceProfil() throws Exception {
        // Initialize the database
        experienceProfilRepository.saveAndFlush(experienceProfil);

        // Get the experienceProfil
        restExperienceProfilMockMvc.perform(get("/api/experience-profils/{id}", experienceProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(experienceProfil.getId().intValue()))
            .andExpect(jsonPath("$.anneExperienceDebut").value(DEFAULT_ANNE_EXPERIENCE_DEBUT.toString()))
            .andExpect(jsonPath("$.anneExperienceFin").value(DEFAULT_ANNE_EXPERIENCE_FIN.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExperienceProfil() throws Exception {
        // Get the experienceProfil
        restExperienceProfilMockMvc.perform(get("/api/experience-profils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExperienceProfil() throws Exception {
        // Initialize the database
        experienceProfilRepository.saveAndFlush(experienceProfil);
        experienceProfilSearchRepository.save(experienceProfil);
        int databaseSizeBeforeUpdate = experienceProfilRepository.findAll().size();

        // Update the experienceProfil
        ExperienceProfil updatedExperienceProfil = experienceProfilRepository.findOne(experienceProfil.getId());
        // Disconnect from session so that the updates on updatedExperienceProfil are not directly saved in db
        em.detach(updatedExperienceProfil);
        updatedExperienceProfil
            .anneExperienceDebut(UPDATED_ANNE_EXPERIENCE_DEBUT)
            .anneExperienceFin(UPDATED_ANNE_EXPERIENCE_FIN)
            .comment(UPDATED_COMMENT);

        restExperienceProfilMockMvc.perform(put("/api/experience-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExperienceProfil)))
            .andExpect(status().isOk());

        // Validate the ExperienceProfil in the database
        List<ExperienceProfil> experienceProfilList = experienceProfilRepository.findAll();
        assertThat(experienceProfilList).hasSize(databaseSizeBeforeUpdate);
        ExperienceProfil testExperienceProfil = experienceProfilList.get(experienceProfilList.size() - 1);
        assertThat(testExperienceProfil.getAnneExperienceDebut()).isEqualTo(UPDATED_ANNE_EXPERIENCE_DEBUT);
        assertThat(testExperienceProfil.getAnneExperienceFin()).isEqualTo(UPDATED_ANNE_EXPERIENCE_FIN);
        assertThat(testExperienceProfil.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the ExperienceProfil in Elasticsearch
        ExperienceProfil experienceProfilEs = experienceProfilSearchRepository.findOne(testExperienceProfil.getId());
        assertThat(experienceProfilEs).isEqualToIgnoringGivenFields(testExperienceProfil);
    }

    @Test
    @Transactional
    public void updateNonExistingExperienceProfil() throws Exception {
        int databaseSizeBeforeUpdate = experienceProfilRepository.findAll().size();

        // Create the ExperienceProfil

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExperienceProfilMockMvc.perform(put("/api/experience-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(experienceProfil)))
            .andExpect(status().isCreated());

        // Validate the ExperienceProfil in the database
        List<ExperienceProfil> experienceProfilList = experienceProfilRepository.findAll();
        assertThat(experienceProfilList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExperienceProfil() throws Exception {
        // Initialize the database
        experienceProfilRepository.saveAndFlush(experienceProfil);
        experienceProfilSearchRepository.save(experienceProfil);
        int databaseSizeBeforeDelete = experienceProfilRepository.findAll().size();

        // Get the experienceProfil
        restExperienceProfilMockMvc.perform(delete("/api/experience-profils/{id}", experienceProfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean experienceProfilExistsInEs = experienceProfilSearchRepository.exists(experienceProfil.getId());
        assertThat(experienceProfilExistsInEs).isFalse();

        // Validate the database is empty
        List<ExperienceProfil> experienceProfilList = experienceProfilRepository.findAll();
        assertThat(experienceProfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchExperienceProfil() throws Exception {
        // Initialize the database
        experienceProfilRepository.saveAndFlush(experienceProfil);
        experienceProfilSearchRepository.save(experienceProfil);

        // Search the experienceProfil
        restExperienceProfilMockMvc.perform(get("/api/_search/experience-profils?query=id:" + experienceProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(experienceProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].anneExperienceDebut").value(hasItem(DEFAULT_ANNE_EXPERIENCE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].anneExperienceFin").value(hasItem(DEFAULT_ANNE_EXPERIENCE_FIN.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExperienceProfil.class);
        ExperienceProfil experienceProfil1 = new ExperienceProfil();
        experienceProfil1.setId(1L);
        ExperienceProfil experienceProfil2 = new ExperienceProfil();
        experienceProfil2.setId(experienceProfil1.getId());
        assertThat(experienceProfil1).isEqualTo(experienceProfil2);
        experienceProfil2.setId(2L);
        assertThat(experienceProfil1).isNotEqualTo(experienceProfil2);
        experienceProfil1.setId(null);
        assertThat(experienceProfil1).isNotEqualTo(experienceProfil2);
    }
}
