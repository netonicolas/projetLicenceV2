package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.JobSkill;
import fr.nicolasneto.domain.Skill;
import fr.nicolasneto.domain.JobOffer;
import fr.nicolasneto.repository.JobSkillRepository;
import fr.nicolasneto.repository.search.JobSkillSearchRepository;
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
 * Test class for the JobSkillResource REST controller.
 *
 * @see JobSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class JobSkillResourceIntTest {

    private static final Long DEFAULT_LEVEL = 1L;
    private static final Long UPDATED_LEVEL = 2L;

    private static final Boolean DEFAULT_OPTIMAL = false;
    private static final Boolean UPDATED_OPTIMAL = true;

    private static final Long DEFAULT_WEIGHT = 1L;
    private static final Long UPDATED_WEIGHT = 2L;

    private static final String DEFAULT_COMMENT_JOB_SKILL = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_JOB_SKILL = "BBBBBBBBBB";

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Autowired
    private JobSkillSearchRepository jobSkillSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobSkillMockMvc;

    private JobSkill jobSkill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobSkillResource jobSkillResource = new JobSkillResource(jobSkillRepository, jobSkillSearchRepository);
        this.restJobSkillMockMvc = MockMvcBuilders.standaloneSetup(jobSkillResource)
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
    public static JobSkill createEntity(EntityManager em) {
        JobSkill jobSkill = new JobSkill()
            .level(DEFAULT_LEVEL)
            .optimal(DEFAULT_OPTIMAL)
            .weight(DEFAULT_WEIGHT)
            .commentJobSkill(DEFAULT_COMMENT_JOB_SKILL);
        // Add required entity
        Skill skill = SkillResourceIntTest.createEntity(em);
        em.persist(skill);
        em.flush();
        jobSkill.setSkill(skill);
        // Add required entity
        JobOffer job = JobOfferResourceIntTest.createEntity(em);
        em.persist(job);
        em.flush();
        jobSkill.setJob(job);
        return jobSkill;
    }

    @Before
    public void initTest() {
        jobSkillSearchRepository.deleteAll();
        jobSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobSkill() throws Exception {
        int databaseSizeBeforeCreate = jobSkillRepository.findAll().size();

        // Create the JobSkill
        restJobSkillMockMvc.perform(post("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkill)))
            .andExpect(status().isCreated());

        // Validate the JobSkill in the database
        List<JobSkill> jobSkillList = jobSkillRepository.findAll();
        assertThat(jobSkillList).hasSize(databaseSizeBeforeCreate + 1);
        JobSkill testJobSkill = jobSkillList.get(jobSkillList.size() - 1);
        assertThat(testJobSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testJobSkill.isOptimal()).isEqualTo(DEFAULT_OPTIMAL);
        assertThat(testJobSkill.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testJobSkill.getCommentJobSkill()).isEqualTo(DEFAULT_COMMENT_JOB_SKILL);

        // Validate the JobSkill in Elasticsearch
        JobSkill jobSkillEs = jobSkillSearchRepository.findOne(testJobSkill.getId());
        assertThat(jobSkillEs).isEqualToIgnoringGivenFields(testJobSkill);
    }

    @Test
    @Transactional
    public void createJobSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobSkillRepository.findAll().size();

        // Create the JobSkill with an existing ID
        jobSkill.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobSkillMockMvc.perform(post("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkill)))
            .andExpect(status().isBadRequest());

        // Validate the JobSkill in the database
        List<JobSkill> jobSkillList = jobSkillRepository.findAll();
        assertThat(jobSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobSkillRepository.findAll().size();
        // set the field null
        jobSkill.setLevel(null);

        // Create the JobSkill, which fails.

        restJobSkillMockMvc.perform(post("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkill)))
            .andExpect(status().isBadRequest());

        List<JobSkill> jobSkillList = jobSkillRepository.findAll();
        assertThat(jobSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOptimalIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobSkillRepository.findAll().size();
        // set the field null
        jobSkill.setOptimal(null);

        // Create the JobSkill, which fails.

        restJobSkillMockMvc.perform(post("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkill)))
            .andExpect(status().isBadRequest());

        List<JobSkill> jobSkillList = jobSkillRepository.findAll();
        assertThat(jobSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobSkillRepository.findAll().size();
        // set the field null
        jobSkill.setWeight(null);

        // Create the JobSkill, which fails.

        restJobSkillMockMvc.perform(post("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkill)))
            .andExpect(status().isBadRequest());

        List<JobSkill> jobSkillList = jobSkillRepository.findAll();
        assertThat(jobSkillList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobSkills() throws Exception {
        // Initialize the database
        jobSkillRepository.saveAndFlush(jobSkill);

        // Get all the jobSkillList
        restJobSkillMockMvc.perform(get("/api/job-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].optimal").value(hasItem(DEFAULT_OPTIMAL.booleanValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].commentJobSkill").value(hasItem(DEFAULT_COMMENT_JOB_SKILL.toString())));
    }

    @Test
    @Transactional
    public void getJobSkill() throws Exception {
        // Initialize the database
        jobSkillRepository.saveAndFlush(jobSkill);

        // Get the jobSkill
        restJobSkillMockMvc.perform(get("/api/job-skills/{id}", jobSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobSkill.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.intValue()))
            .andExpect(jsonPath("$.optimal").value(DEFAULT_OPTIMAL.booleanValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.commentJobSkill").value(DEFAULT_COMMENT_JOB_SKILL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobSkill() throws Exception {
        // Get the jobSkill
        restJobSkillMockMvc.perform(get("/api/job-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobSkill() throws Exception {
        // Initialize the database
        jobSkillRepository.saveAndFlush(jobSkill);
        jobSkillSearchRepository.save(jobSkill);
        int databaseSizeBeforeUpdate = jobSkillRepository.findAll().size();

        // Update the jobSkill
        JobSkill updatedJobSkill = jobSkillRepository.findOne(jobSkill.getId());
        // Disconnect from session so that the updates on updatedJobSkill are not directly saved in db
        em.detach(updatedJobSkill);
        updatedJobSkill
            .level(UPDATED_LEVEL)
            .optimal(UPDATED_OPTIMAL)
            .weight(UPDATED_WEIGHT)
            .commentJobSkill(UPDATED_COMMENT_JOB_SKILL);

        restJobSkillMockMvc.perform(put("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobSkill)))
            .andExpect(status().isOk());

        // Validate the JobSkill in the database
        List<JobSkill> jobSkillList = jobSkillRepository.findAll();
        assertThat(jobSkillList).hasSize(databaseSizeBeforeUpdate);
        JobSkill testJobSkill = jobSkillList.get(jobSkillList.size() - 1);
        assertThat(testJobSkill.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testJobSkill.isOptimal()).isEqualTo(UPDATED_OPTIMAL);
        assertThat(testJobSkill.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testJobSkill.getCommentJobSkill()).isEqualTo(UPDATED_COMMENT_JOB_SKILL);

        // Validate the JobSkill in Elasticsearch
        JobSkill jobSkillEs = jobSkillSearchRepository.findOne(testJobSkill.getId());
        assertThat(jobSkillEs).isEqualToIgnoringGivenFields(testJobSkill);
    }

    @Test
    @Transactional
    public void updateNonExistingJobSkill() throws Exception {
        int databaseSizeBeforeUpdate = jobSkillRepository.findAll().size();

        // Create the JobSkill

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobSkillMockMvc.perform(put("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkill)))
            .andExpect(status().isCreated());

        // Validate the JobSkill in the database
        List<JobSkill> jobSkillList = jobSkillRepository.findAll();
        assertThat(jobSkillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobSkill() throws Exception {
        // Initialize the database
        jobSkillRepository.saveAndFlush(jobSkill);
        jobSkillSearchRepository.save(jobSkill);
        int databaseSizeBeforeDelete = jobSkillRepository.findAll().size();

        // Get the jobSkill
        restJobSkillMockMvc.perform(delete("/api/job-skills/{id}", jobSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobSkillExistsInEs = jobSkillSearchRepository.exists(jobSkill.getId());
        assertThat(jobSkillExistsInEs).isFalse();

        // Validate the database is empty
        List<JobSkill> jobSkillList = jobSkillRepository.findAll();
        assertThat(jobSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobSkill() throws Exception {
        // Initialize the database
        jobSkillRepository.saveAndFlush(jobSkill);
        jobSkillSearchRepository.save(jobSkill);

        // Search the jobSkill
        restJobSkillMockMvc.perform(get("/api/_search/job-skills?query=id:" + jobSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].optimal").value(hasItem(DEFAULT_OPTIMAL.booleanValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].commentJobSkill").value(hasItem(DEFAULT_COMMENT_JOB_SKILL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobSkill.class);
        JobSkill jobSkill1 = new JobSkill();
        jobSkill1.setId(1L);
        JobSkill jobSkill2 = new JobSkill();
        jobSkill2.setId(jobSkill1.getId());
        assertThat(jobSkill1).isEqualTo(jobSkill2);
        jobSkill2.setId(2L);
        assertThat(jobSkill1).isNotEqualTo(jobSkill2);
        jobSkill1.setId(null);
        assertThat(jobSkill1).isNotEqualTo(jobSkill2);
    }
}
