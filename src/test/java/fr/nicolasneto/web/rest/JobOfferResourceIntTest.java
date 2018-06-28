package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.JobOffer;
import fr.nicolasneto.repository.JobOfferRepository;
import fr.nicolasneto.repository.search.JobOfferSearchRepository;
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

import fr.nicolasneto.domain.enumeration.TypeOffre;
/**
 * Test class for the JobOfferResource REST controller.
 *
 * @see JobOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class JobOfferResourceIntTest {

    private static final String DEFAULT_TITLE_OFFER = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_OFFER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_OFFER = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_OFFER = "BBBBBBBBBB";

    private static final Long DEFAULT_SALAIRY_MIN = 1L;
    private static final Long UPDATED_SALAIRY_MIN = 2L;

    private static final Long DEFAULT_SALAIRY_MAX = 1L;
    private static final Long UPDATED_SALAIRY_MAX = 2L;

    private static final TypeOffre DEFAULT_TYPE_OFFER = TypeOffre.CDI;
    private static final TypeOffre UPDATED_TYPE_OFFER = TypeOffre.CDD;

    private static final LocalDate DEFAULT_DATE_OFFER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OFFER = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Autowired
    private JobOfferSearchRepository jobOfferSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobOfferMockMvc;

    private JobOffer jobOffer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobOfferResource jobOfferResource = new JobOfferResource(jobOfferRepository, jobOfferSearchRepository);
        this.restJobOfferMockMvc = MockMvcBuilders.standaloneSetup(jobOfferResource)
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
    public static JobOffer createEntity(EntityManager em) {
        JobOffer jobOffer = new JobOffer()
            .titleOffer(DEFAULT_TITLE_OFFER)
            .descriptionOffer(DEFAULT_DESCRIPTION_OFFER)
            .salairyMin(DEFAULT_SALAIRY_MIN)
            .salairyMax(DEFAULT_SALAIRY_MAX)
            .typeOffer(DEFAULT_TYPE_OFFER)
            .dateOffer(DEFAULT_DATE_OFFER)
            .comment(DEFAULT_COMMENT)
            .place(DEFAULT_PLACE)
            .contact(DEFAULT_CONTACT)
            .status(DEFAULT_STATUS);
        return jobOffer;
    }

    @Before
    public void initTest() {
        jobOfferSearchRepository.deleteAll();
        jobOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobOffer() throws Exception {
        int databaseSizeBeforeCreate = jobOfferRepository.findAll().size();

        // Create the JobOffer
        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isCreated());

        // Validate the JobOffer in the database
        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeCreate + 1);
        JobOffer testJobOffer = jobOfferList.get(jobOfferList.size() - 1);
        assertThat(testJobOffer.getTitleOffer()).isEqualTo(DEFAULT_TITLE_OFFER);
        assertThat(testJobOffer.getDescriptionOffer()).isEqualTo(DEFAULT_DESCRIPTION_OFFER);
        assertThat(testJobOffer.getSalairyMin()).isEqualTo(DEFAULT_SALAIRY_MIN);
        assertThat(testJobOffer.getSalairyMax()).isEqualTo(DEFAULT_SALAIRY_MAX);
        assertThat(testJobOffer.getTypeOffer()).isEqualTo(DEFAULT_TYPE_OFFER);
        assertThat(testJobOffer.getDateOffer()).isEqualTo(DEFAULT_DATE_OFFER);
        assertThat(testJobOffer.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testJobOffer.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testJobOffer.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testJobOffer.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the JobOffer in Elasticsearch
        JobOffer jobOfferEs = jobOfferSearchRepository.findOne(testJobOffer.getId());
        assertThat(jobOfferEs).isEqualToIgnoringGivenFields(testJobOffer);
    }

    @Test
    @Transactional
    public void createJobOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobOfferRepository.findAll().size();

        // Create the JobOffer with an existing ID
        jobOffer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        // Validate the JobOffer in the database
        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleOfferIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setTitleOffer(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionOfferIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setDescriptionOffer(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalairyMinIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setSalairyMin(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSalairyMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setSalairyMax(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeOfferIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setTypeOffer(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfferIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setDateOffer(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPlaceIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setPlace(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setContact(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = jobOfferRepository.findAll().size();
        // set the field null
        jobOffer.setStatus(null);

        // Create the JobOffer, which fails.

        restJobOfferMockMvc.perform(post("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isBadRequest());

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJobOffers() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);

        // Get all the jobOfferList
        restJobOfferMockMvc.perform(get("/api/job-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleOffer").value(hasItem(DEFAULT_TITLE_OFFER.toString())))
            .andExpect(jsonPath("$.[*].descriptionOffer").value(hasItem(DEFAULT_DESCRIPTION_OFFER.toString())))
            .andExpect(jsonPath("$.[*].salairyMin").value(hasItem(DEFAULT_SALAIRY_MIN.intValue())))
            .andExpect(jsonPath("$.[*].salairyMax").value(hasItem(DEFAULT_SALAIRY_MAX.intValue())))
            .andExpect(jsonPath("$.[*].typeOffer").value(hasItem(DEFAULT_TYPE_OFFER.toString())))
            .andExpect(jsonPath("$.[*].dateOffer").value(hasItem(DEFAULT_DATE_OFFER.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getJobOffer() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);

        // Get the jobOffer
        restJobOfferMockMvc.perform(get("/api/job-offers/{id}", jobOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobOffer.getId().intValue()))
            .andExpect(jsonPath("$.titleOffer").value(DEFAULT_TITLE_OFFER.toString()))
            .andExpect(jsonPath("$.descriptionOffer").value(DEFAULT_DESCRIPTION_OFFER.toString()))
            .andExpect(jsonPath("$.salairyMin").value(DEFAULT_SALAIRY_MIN.intValue()))
            .andExpect(jsonPath("$.salairyMax").value(DEFAULT_SALAIRY_MAX.intValue()))
            .andExpect(jsonPath("$.typeOffer").value(DEFAULT_TYPE_OFFER.toString()))
            .andExpect(jsonPath("$.dateOffer").value(DEFAULT_DATE_OFFER.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobOffer() throws Exception {
        // Get the jobOffer
        restJobOfferMockMvc.perform(get("/api/job-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobOffer() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);
        jobOfferSearchRepository.save(jobOffer);
        int databaseSizeBeforeUpdate = jobOfferRepository.findAll().size();

        // Update the jobOffer
        JobOffer updatedJobOffer = jobOfferRepository.findOne(jobOffer.getId());
        // Disconnect from session so that the updates on updatedJobOffer are not directly saved in db
        em.detach(updatedJobOffer);
        updatedJobOffer
            .titleOffer(UPDATED_TITLE_OFFER)
            .descriptionOffer(UPDATED_DESCRIPTION_OFFER)
            .salairyMin(UPDATED_SALAIRY_MIN)
            .salairyMax(UPDATED_SALAIRY_MAX)
            .typeOffer(UPDATED_TYPE_OFFER)
            .dateOffer(UPDATED_DATE_OFFER)
            .comment(UPDATED_COMMENT)
            .place(UPDATED_PLACE)
            .contact(UPDATED_CONTACT)
            .status(UPDATED_STATUS);

        restJobOfferMockMvc.perform(put("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobOffer)))
            .andExpect(status().isOk());

        // Validate the JobOffer in the database
        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeUpdate);
        JobOffer testJobOffer = jobOfferList.get(jobOfferList.size() - 1);
        assertThat(testJobOffer.getTitleOffer()).isEqualTo(UPDATED_TITLE_OFFER);
        assertThat(testJobOffer.getDescriptionOffer()).isEqualTo(UPDATED_DESCRIPTION_OFFER);
        assertThat(testJobOffer.getSalairyMin()).isEqualTo(UPDATED_SALAIRY_MIN);
        assertThat(testJobOffer.getSalairyMax()).isEqualTo(UPDATED_SALAIRY_MAX);
        assertThat(testJobOffer.getTypeOffer()).isEqualTo(UPDATED_TYPE_OFFER);
        assertThat(testJobOffer.getDateOffer()).isEqualTo(UPDATED_DATE_OFFER);
        assertThat(testJobOffer.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testJobOffer.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testJobOffer.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testJobOffer.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the JobOffer in Elasticsearch
        JobOffer jobOfferEs = jobOfferSearchRepository.findOne(testJobOffer.getId());
        assertThat(jobOfferEs).isEqualToIgnoringGivenFields(testJobOffer);
    }

    @Test
    @Transactional
    public void updateNonExistingJobOffer() throws Exception {
        int databaseSizeBeforeUpdate = jobOfferRepository.findAll().size();

        // Create the JobOffer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobOfferMockMvc.perform(put("/api/job-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobOffer)))
            .andExpect(status().isCreated());

        // Validate the JobOffer in the database
        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobOffer() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);
        jobOfferSearchRepository.save(jobOffer);
        int databaseSizeBeforeDelete = jobOfferRepository.findAll().size();

        // Get the jobOffer
        restJobOfferMockMvc.perform(delete("/api/job-offers/{id}", jobOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobOfferExistsInEs = jobOfferSearchRepository.exists(jobOffer.getId());
        assertThat(jobOfferExistsInEs).isFalse();

        // Validate the database is empty
        List<JobOffer> jobOfferList = jobOfferRepository.findAll();
        assertThat(jobOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobOffer() throws Exception {
        // Initialize the database
        jobOfferRepository.saveAndFlush(jobOffer);
        jobOfferSearchRepository.save(jobOffer);

        // Search the jobOffer
        restJobOfferMockMvc.perform(get("/api/_search/job-offers?query=id:" + jobOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].titleOffer").value(hasItem(DEFAULT_TITLE_OFFER.toString())))
            .andExpect(jsonPath("$.[*].descriptionOffer").value(hasItem(DEFAULT_DESCRIPTION_OFFER.toString())))
            .andExpect(jsonPath("$.[*].salairyMin").value(hasItem(DEFAULT_SALAIRY_MIN.intValue())))
            .andExpect(jsonPath("$.[*].salairyMax").value(hasItem(DEFAULT_SALAIRY_MAX.intValue())))
            .andExpect(jsonPath("$.[*].typeOffer").value(hasItem(DEFAULT_TYPE_OFFER.toString())))
            .andExpect(jsonPath("$.[*].dateOffer").value(hasItem(DEFAULT_DATE_OFFER.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobOffer.class);
        JobOffer jobOffer1 = new JobOffer();
        jobOffer1.setId(1L);
        JobOffer jobOffer2 = new JobOffer();
        jobOffer2.setId(jobOffer1.getId());
        assertThat(jobOffer1).isEqualTo(jobOffer2);
        jobOffer2.setId(2L);
        assertThat(jobOffer1).isNotEqualTo(jobOffer2);
        jobOffer1.setId(null);
        assertThat(jobOffer1).isNotEqualTo(jobOffer2);
    }
}
