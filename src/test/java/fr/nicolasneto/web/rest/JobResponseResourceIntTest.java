package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.JobResponse;
import fr.nicolasneto.domain.Profil;
import fr.nicolasneto.domain.JobOffer;
import fr.nicolasneto.repository.JobResponseRepository;
import fr.nicolasneto.repository.search.JobResponseSearchRepository;
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
 * Test class for the JobResponseResource REST controller.
 *
 * @see JobResponseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class JobResponseResourceIntTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_RESPONSE = "BBBBBBBBBB";

    @Autowired
    private JobResponseRepository jobResponseRepository;

    @Autowired
    private JobResponseSearchRepository jobResponseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobResponseMockMvc;

    private JobResponse jobResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobResponseResource jobResponseResource = new JobResponseResource(jobResponseRepository, jobResponseSearchRepository);
        this.restJobResponseMockMvc = MockMvcBuilders.standaloneSetup(jobResponseResource)
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
    public static JobResponse createEntity(EntityManager em) {
        JobResponse jobResponse = new JobResponse()
            .comment(DEFAULT_COMMENT)
            .dateResponse(DEFAULT_DATE_RESPONSE);
        // Add required entity
        Profil candidat = ProfilResourceIntTest.createEntity(em);
        em.persist(candidat);
        em.flush();
        jobResponse.setCandidat(candidat);
        // Add required entity
        JobOffer jobOffer = JobOfferResourceIntTest.createEntity(em);
        em.persist(jobOffer);
        em.flush();
        jobResponse.setJobOffer(jobOffer);
        return jobResponse;
    }

    @Before
    public void initTest() {
        jobResponseSearchRepository.deleteAll();
        jobResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobResponse() throws Exception {
        int databaseSizeBeforeCreate = jobResponseRepository.findAll().size();

        // Create the JobResponse
        restJobResponseMockMvc.perform(post("/api/job-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobResponse)))
            .andExpect(status().isCreated());

        // Validate the JobResponse in the database
        List<JobResponse> jobResponseList = jobResponseRepository.findAll();
        assertThat(jobResponseList).hasSize(databaseSizeBeforeCreate + 1);
        JobResponse testJobResponse = jobResponseList.get(jobResponseList.size() - 1);
        assertThat(testJobResponse.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testJobResponse.getDateResponse()).isEqualTo(DEFAULT_DATE_RESPONSE);

        // Validate the JobResponse in Elasticsearch
        JobResponse jobResponseEs = jobResponseSearchRepository.findOne(testJobResponse.getId());
        assertThat(jobResponseEs).isEqualToIgnoringGivenFields(testJobResponse);
    }

    @Test
    @Transactional
    public void createJobResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobResponseRepository.findAll().size();

        // Create the JobResponse with an existing ID
        jobResponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobResponseMockMvc.perform(post("/api/job-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobResponse)))
            .andExpect(status().isBadRequest());

        // Validate the JobResponse in the database
        List<JobResponse> jobResponseList = jobResponseRepository.findAll();
        assertThat(jobResponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobResponseRepository.findAll().size();
        // set the field null
        jobResponse.setComment(null);

        // Create the JobResponse, which fails.

        restJobResponseMockMvc.perform(post("/api/job-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobResponse)))
            .andExpect(status().isBadRequest());

        List<JobResponse> jobResponseList = jobResponseRepository.findAll();
        assertThat(jobResponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobResponses() throws Exception {
        // Initialize the database
        jobResponseRepository.saveAndFlush(jobResponse);

        // Get all the jobResponseList
        restJobResponseMockMvc.perform(get("/api/job-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].dateResponse").value(hasItem(DEFAULT_DATE_RESPONSE.toString())));
    }

    @Test
    @Transactional
    public void getJobResponse() throws Exception {
        // Initialize the database
        jobResponseRepository.saveAndFlush(jobResponse);

        // Get the jobResponse
        restJobResponseMockMvc.perform(get("/api/job-responses/{id}", jobResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobResponse.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.dateResponse").value(DEFAULT_DATE_RESPONSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobResponse() throws Exception {
        // Get the jobResponse
        restJobResponseMockMvc.perform(get("/api/job-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobResponse() throws Exception {
        // Initialize the database
        jobResponseRepository.saveAndFlush(jobResponse);
        jobResponseSearchRepository.save(jobResponse);
        int databaseSizeBeforeUpdate = jobResponseRepository.findAll().size();

        // Update the jobResponse
        JobResponse updatedJobResponse = jobResponseRepository.findOne(jobResponse.getId());
        // Disconnect from session so that the updates on updatedJobResponse are not directly saved in db
        em.detach(updatedJobResponse);
        updatedJobResponse
            .comment(UPDATED_COMMENT)
            .dateResponse(UPDATED_DATE_RESPONSE);

        restJobResponseMockMvc.perform(put("/api/job-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobResponse)))
            .andExpect(status().isOk());

        // Validate the JobResponse in the database
        List<JobResponse> jobResponseList = jobResponseRepository.findAll();
        assertThat(jobResponseList).hasSize(databaseSizeBeforeUpdate);
        JobResponse testJobResponse = jobResponseList.get(jobResponseList.size() - 1);
        assertThat(testJobResponse.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testJobResponse.getDateResponse()).isEqualTo(UPDATED_DATE_RESPONSE);

        // Validate the JobResponse in Elasticsearch
        JobResponse jobResponseEs = jobResponseSearchRepository.findOne(testJobResponse.getId());
        assertThat(jobResponseEs).isEqualToIgnoringGivenFields(testJobResponse);
    }

    @Test
    @Transactional
    public void updateNonExistingJobResponse() throws Exception {
        int databaseSizeBeforeUpdate = jobResponseRepository.findAll().size();

        // Create the JobResponse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobResponseMockMvc.perform(put("/api/job-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobResponse)))
            .andExpect(status().isCreated());

        // Validate the JobResponse in the database
        List<JobResponse> jobResponseList = jobResponseRepository.findAll();
        assertThat(jobResponseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobResponse() throws Exception {
        // Initialize the database
        jobResponseRepository.saveAndFlush(jobResponse);
        jobResponseSearchRepository.save(jobResponse);
        int databaseSizeBeforeDelete = jobResponseRepository.findAll().size();

        // Get the jobResponse
        restJobResponseMockMvc.perform(delete("/api/job-responses/{id}", jobResponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobResponseExistsInEs = jobResponseSearchRepository.exists(jobResponse.getId());
        assertThat(jobResponseExistsInEs).isFalse();

        // Validate the database is empty
        List<JobResponse> jobResponseList = jobResponseRepository.findAll();
        assertThat(jobResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobResponse() throws Exception {
        // Initialize the database
        jobResponseRepository.saveAndFlush(jobResponse);
        jobResponseSearchRepository.save(jobResponse);

        // Search the jobResponse
        restJobResponseMockMvc.perform(get("/api/_search/job-responses?query=id:" + jobResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].dateResponse").value(hasItem(DEFAULT_DATE_RESPONSE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobResponse.class);
        JobResponse jobResponse1 = new JobResponse();
        jobResponse1.setId(1L);
        JobResponse jobResponse2 = new JobResponse();
        jobResponse2.setId(jobResponse1.getId());
        assertThat(jobResponse1).isEqualTo(jobResponse2);
        jobResponse2.setId(2L);
        assertThat(jobResponse1).isNotEqualTo(jobResponse2);
        jobResponse1.setId(null);
        assertThat(jobResponse1).isNotEqualTo(jobResponse2);
    }
}
