package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.CandidateTrueSkill;
import fr.nicolasneto.repository.CandidateTrueSkillRepository;
import fr.nicolasneto.repository.search.CandidateTrueSkillSearchRepository;
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
import java.util.List;

import static fr.nicolasneto.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CandidateTrueSkillResource REST controller.
 *
 * @see CandidateTrueSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class CandidateTrueSkillResourceIntTest {

    private static final Long DEFAULT_CANDIDATESKILLID = 1L;
    private static final Long UPDATED_CANDIDATESKILLID = 2L;

    private static final Long DEFAULT_LEVEL = 1L;
    private static final Long UPDATED_LEVEL = 2L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private CandidateTrueSkillRepository candidateTrueSkillRepository;

    @Autowired
    private CandidateTrueSkillSearchRepository candidateTrueSkillSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidateTrueSkillMockMvc;

    private CandidateTrueSkill candidateTrueSkill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidateTrueSkillResource candidateTrueSkillResource = new CandidateTrueSkillResource(candidateTrueSkillRepository, candidateTrueSkillSearchRepository);
        this.restCandidateTrueSkillMockMvc = MockMvcBuilders.standaloneSetup(candidateTrueSkillResource)
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
    public static CandidateTrueSkill createEntity(EntityManager em) {
        CandidateTrueSkill candidateTrueSkill = new CandidateTrueSkill()
            .candidateskillid(DEFAULT_CANDIDATESKILLID)
            .level(DEFAULT_LEVEL)
            .comment(DEFAULT_COMMENT);
        return candidateTrueSkill;
    }

    @Before
    public void initTest() {
        candidateTrueSkillSearchRepository.deleteAll();
        candidateTrueSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateTrueSkill() throws Exception {
        int databaseSizeBeforeCreate = candidateTrueSkillRepository.findAll().size();

        // Create the CandidateTrueSkill
        restCandidateTrueSkillMockMvc.perform(post("/api/candidate-true-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateTrueSkill)))
            .andExpect(status().isCreated());

        // Validate the CandidateTrueSkill in the database
        List<CandidateTrueSkill> candidateTrueSkillList = candidateTrueSkillRepository.findAll();
        assertThat(candidateTrueSkillList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateTrueSkill testCandidateTrueSkill = candidateTrueSkillList.get(candidateTrueSkillList.size() - 1);
        assertThat(testCandidateTrueSkill.getCandidateskillid()).isEqualTo(DEFAULT_CANDIDATESKILLID);
        assertThat(testCandidateTrueSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testCandidateTrueSkill.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the CandidateTrueSkill in Elasticsearch
        CandidateTrueSkill candidateTrueSkillEs = candidateTrueSkillSearchRepository.findOne(testCandidateTrueSkill.getId());
        assertThat(candidateTrueSkillEs).isEqualToIgnoringGivenFields(testCandidateTrueSkill);
    }

    @Test
    @Transactional
    public void createCandidateTrueSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateTrueSkillRepository.findAll().size();

        // Create the CandidateTrueSkill with an existing ID
        candidateTrueSkill.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateTrueSkillMockMvc.perform(post("/api/candidate-true-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateTrueSkill)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateTrueSkill in the database
        List<CandidateTrueSkill> candidateTrueSkillList = candidateTrueSkillRepository.findAll();
        assertThat(candidateTrueSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCandidateskillidIsRequired() throws Exception {
        int databaseSizeBeforeTest = candidateTrueSkillRepository.findAll().size();
        // set the field null
        candidateTrueSkill.setCandidateskillid(null);

        // Create the CandidateTrueSkill, which fails.

        restCandidateTrueSkillMockMvc.perform(post("/api/candidate-true-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateTrueSkill)))
            .andExpect(status().isBadRequest());

        List<CandidateTrueSkill> candidateTrueSkillList = candidateTrueSkillRepository.findAll();
        assertThat(candidateTrueSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCandidateTrueSkills() throws Exception {
        // Initialize the database
        candidateTrueSkillRepository.saveAndFlush(candidateTrueSkill);

        // Get all the candidateTrueSkillList
        restCandidateTrueSkillMockMvc.perform(get("/api/candidate-true-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateTrueSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].candidateskillid").value(hasItem(DEFAULT_CANDIDATESKILLID.intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getCandidateTrueSkill() throws Exception {
        // Initialize the database
        candidateTrueSkillRepository.saveAndFlush(candidateTrueSkill);

        // Get the candidateTrueSkill
        restCandidateTrueSkillMockMvc.perform(get("/api/candidate-true-skills/{id}", candidateTrueSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateTrueSkill.getId().intValue()))
            .andExpect(jsonPath("$.candidateskillid").value(DEFAULT_CANDIDATESKILLID.intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateTrueSkill() throws Exception {
        // Get the candidateTrueSkill
        restCandidateTrueSkillMockMvc.perform(get("/api/candidate-true-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateTrueSkill() throws Exception {
        // Initialize the database
        candidateTrueSkillRepository.saveAndFlush(candidateTrueSkill);
        candidateTrueSkillSearchRepository.save(candidateTrueSkill);
        int databaseSizeBeforeUpdate = candidateTrueSkillRepository.findAll().size();

        // Update the candidateTrueSkill
        CandidateTrueSkill updatedCandidateTrueSkill = candidateTrueSkillRepository.findOne(candidateTrueSkill.getId());
        // Disconnect from session so that the updates on updatedCandidateTrueSkill are not directly saved in db
        em.detach(updatedCandidateTrueSkill);
        updatedCandidateTrueSkill
            .candidateskillid(UPDATED_CANDIDATESKILLID)
            .level(UPDATED_LEVEL)
            .comment(UPDATED_COMMENT);

        restCandidateTrueSkillMockMvc.perform(put("/api/candidate-true-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidateTrueSkill)))
            .andExpect(status().isOk());

        // Validate the CandidateTrueSkill in the database
        List<CandidateTrueSkill> candidateTrueSkillList = candidateTrueSkillRepository.findAll();
        assertThat(candidateTrueSkillList).hasSize(databaseSizeBeforeUpdate);
        CandidateTrueSkill testCandidateTrueSkill = candidateTrueSkillList.get(candidateTrueSkillList.size() - 1);
        assertThat(testCandidateTrueSkill.getCandidateskillid()).isEqualTo(UPDATED_CANDIDATESKILLID);
        assertThat(testCandidateTrueSkill.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testCandidateTrueSkill.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the CandidateTrueSkill in Elasticsearch
        CandidateTrueSkill candidateTrueSkillEs = candidateTrueSkillSearchRepository.findOne(testCandidateTrueSkill.getId());
        assertThat(candidateTrueSkillEs).isEqualToIgnoringGivenFields(testCandidateTrueSkill);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidateTrueSkill() throws Exception {
        int databaseSizeBeforeUpdate = candidateTrueSkillRepository.findAll().size();

        // Create the CandidateTrueSkill

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandidateTrueSkillMockMvc.perform(put("/api/candidate-true-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateTrueSkill)))
            .andExpect(status().isCreated());

        // Validate the CandidateTrueSkill in the database
        List<CandidateTrueSkill> candidateTrueSkillList = candidateTrueSkillRepository.findAll();
        assertThat(candidateTrueSkillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandidateTrueSkill() throws Exception {
        // Initialize the database
        candidateTrueSkillRepository.saveAndFlush(candidateTrueSkill);
        candidateTrueSkillSearchRepository.save(candidateTrueSkill);
        int databaseSizeBeforeDelete = candidateTrueSkillRepository.findAll().size();

        // Get the candidateTrueSkill
        restCandidateTrueSkillMockMvc.perform(delete("/api/candidate-true-skills/{id}", candidateTrueSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean candidateTrueSkillExistsInEs = candidateTrueSkillSearchRepository.exists(candidateTrueSkill.getId());
        assertThat(candidateTrueSkillExistsInEs).isFalse();

        // Validate the database is empty
        List<CandidateTrueSkill> candidateTrueSkillList = candidateTrueSkillRepository.findAll();
        assertThat(candidateTrueSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCandidateTrueSkill() throws Exception {
        // Initialize the database
        candidateTrueSkillRepository.saveAndFlush(candidateTrueSkill);
        candidateTrueSkillSearchRepository.save(candidateTrueSkill);

        // Search the candidateTrueSkill
        restCandidateTrueSkillMockMvc.perform(get("/api/_search/candidate-true-skills?query=id:" + candidateTrueSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateTrueSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].candidateskillid").value(hasItem(DEFAULT_CANDIDATESKILLID.intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateTrueSkill.class);
        CandidateTrueSkill candidateTrueSkill1 = new CandidateTrueSkill();
        candidateTrueSkill1.setId(1L);
        CandidateTrueSkill candidateTrueSkill2 = new CandidateTrueSkill();
        candidateTrueSkill2.setId(candidateTrueSkill1.getId());
        assertThat(candidateTrueSkill1).isEqualTo(candidateTrueSkill2);
        candidateTrueSkill2.setId(2L);
        assertThat(candidateTrueSkill1).isNotEqualTo(candidateTrueSkill2);
        candidateTrueSkill1.setId(null);
        assertThat(candidateTrueSkill1).isNotEqualTo(candidateTrueSkill2);
    }
}
