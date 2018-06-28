package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.SkillTestResponse;
import fr.nicolasneto.domain.SkillTest;
import fr.nicolasneto.domain.Profil;
import fr.nicolasneto.repository.SkillTestResponseRepository;
import fr.nicolasneto.repository.search.SkillTestResponseSearchRepository;
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
 * Test class for the SkillTestResponseResource REST controller.
 *
 * @see SkillTestResponseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class SkillTestResponseResourceIntTest {

    private static final String DEFAULT_RESPONSE_SKILL_TEST_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_SKILL_TEST_RESPONSE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SkillTestResponseRepository skillTestResponseRepository;

    @Autowired
    private SkillTestResponseSearchRepository skillTestResponseSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkillTestResponseMockMvc;

    private SkillTestResponse skillTestResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillTestResponseResource skillTestResponseResource = new SkillTestResponseResource(skillTestResponseRepository, skillTestResponseSearchRepository);
        this.restSkillTestResponseMockMvc = MockMvcBuilders.standaloneSetup(skillTestResponseResource)
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
    public static SkillTestResponse createEntity(EntityManager em) {
        SkillTestResponse skillTestResponse = new SkillTestResponse()
            .responseSkillTestResponse(DEFAULT_RESPONSE_SKILL_TEST_RESPONSE)
            .date(DEFAULT_DATE);
        // Add required entity
        SkillTest skillTest = SkillTestResourceIntTest.createEntity(em);
        em.persist(skillTest);
        em.flush();
        skillTestResponse.setSkillTest(skillTest);
        // Add required entity
        Profil candidat = ProfilResourceIntTest.createEntity(em);
        em.persist(candidat);
        em.flush();
        skillTestResponse.setCandidat(candidat);
        return skillTestResponse;
    }

    @Before
    public void initTest() {
        skillTestResponseSearchRepository.deleteAll();
        skillTestResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillTestResponse() throws Exception {
        int databaseSizeBeforeCreate = skillTestResponseRepository.findAll().size();

        // Create the SkillTestResponse
        restSkillTestResponseMockMvc.perform(post("/api/skill-test-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTestResponse)))
            .andExpect(status().isCreated());

        // Validate the SkillTestResponse in the database
        List<SkillTestResponse> skillTestResponseList = skillTestResponseRepository.findAll();
        assertThat(skillTestResponseList).hasSize(databaseSizeBeforeCreate + 1);
        SkillTestResponse testSkillTestResponse = skillTestResponseList.get(skillTestResponseList.size() - 1);
        assertThat(testSkillTestResponse.getResponseSkillTestResponse()).isEqualTo(DEFAULT_RESPONSE_SKILL_TEST_RESPONSE);
        assertThat(testSkillTestResponse.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the SkillTestResponse in Elasticsearch
        SkillTestResponse skillTestResponseEs = skillTestResponseSearchRepository.findOne(testSkillTestResponse.getId());
        assertThat(skillTestResponseEs).isEqualToIgnoringGivenFields(testSkillTestResponse);
    }

    @Test
    @Transactional
    public void createSkillTestResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillTestResponseRepository.findAll().size();

        // Create the SkillTestResponse with an existing ID
        skillTestResponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillTestResponseMockMvc.perform(post("/api/skill-test-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTestResponse)))
            .andExpect(status().isBadRequest());

        // Validate the SkillTestResponse in the database
        List<SkillTestResponse> skillTestResponseList = skillTestResponseRepository.findAll();
        assertThat(skillTestResponseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkResponseSkillTestResponseIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillTestResponseRepository.findAll().size();
        // set the field null
        skillTestResponse.setResponseSkillTestResponse(null);

        // Create the SkillTestResponse, which fails.

        restSkillTestResponseMockMvc.perform(post("/api/skill-test-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTestResponse)))
            .andExpect(status().isBadRequest());

        List<SkillTestResponse> skillTestResponseList = skillTestResponseRepository.findAll();
        assertThat(skillTestResponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillTestResponseRepository.findAll().size();
        // set the field null
        skillTestResponse.setDate(null);

        // Create the SkillTestResponse, which fails.

        restSkillTestResponseMockMvc.perform(post("/api/skill-test-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTestResponse)))
            .andExpect(status().isBadRequest());

        List<SkillTestResponse> skillTestResponseList = skillTestResponseRepository.findAll();
        assertThat(skillTestResponseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkillTestResponses() throws Exception {
        // Initialize the database
        skillTestResponseRepository.saveAndFlush(skillTestResponse);

        // Get all the skillTestResponseList
        restSkillTestResponseMockMvc.perform(get("/api/skill-test-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillTestResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].responseSkillTestResponse").value(hasItem(DEFAULT_RESPONSE_SKILL_TEST_RESPONSE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSkillTestResponse() throws Exception {
        // Initialize the database
        skillTestResponseRepository.saveAndFlush(skillTestResponse);

        // Get the skillTestResponse
        restSkillTestResponseMockMvc.perform(get("/api/skill-test-responses/{id}", skillTestResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillTestResponse.getId().intValue()))
            .andExpect(jsonPath("$.responseSkillTestResponse").value(DEFAULT_RESPONSE_SKILL_TEST_RESPONSE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSkillTestResponse() throws Exception {
        // Get the skillTestResponse
        restSkillTestResponseMockMvc.perform(get("/api/skill-test-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillTestResponse() throws Exception {
        // Initialize the database
        skillTestResponseRepository.saveAndFlush(skillTestResponse);
        skillTestResponseSearchRepository.save(skillTestResponse);
        int databaseSizeBeforeUpdate = skillTestResponseRepository.findAll().size();

        // Update the skillTestResponse
        SkillTestResponse updatedSkillTestResponse = skillTestResponseRepository.findOne(skillTestResponse.getId());
        // Disconnect from session so that the updates on updatedSkillTestResponse are not directly saved in db
        em.detach(updatedSkillTestResponse);
        updatedSkillTestResponse
            .responseSkillTestResponse(UPDATED_RESPONSE_SKILL_TEST_RESPONSE)
            .date(UPDATED_DATE);

        restSkillTestResponseMockMvc.perform(put("/api/skill-test-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillTestResponse)))
            .andExpect(status().isOk());

        // Validate the SkillTestResponse in the database
        List<SkillTestResponse> skillTestResponseList = skillTestResponseRepository.findAll();
        assertThat(skillTestResponseList).hasSize(databaseSizeBeforeUpdate);
        SkillTestResponse testSkillTestResponse = skillTestResponseList.get(skillTestResponseList.size() - 1);
        assertThat(testSkillTestResponse.getResponseSkillTestResponse()).isEqualTo(UPDATED_RESPONSE_SKILL_TEST_RESPONSE);
        assertThat(testSkillTestResponse.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the SkillTestResponse in Elasticsearch
        SkillTestResponse skillTestResponseEs = skillTestResponseSearchRepository.findOne(testSkillTestResponse.getId());
        assertThat(skillTestResponseEs).isEqualToIgnoringGivenFields(testSkillTestResponse);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillTestResponse() throws Exception {
        int databaseSizeBeforeUpdate = skillTestResponseRepository.findAll().size();

        // Create the SkillTestResponse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSkillTestResponseMockMvc.perform(put("/api/skill-test-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTestResponse)))
            .andExpect(status().isCreated());

        // Validate the SkillTestResponse in the database
        List<SkillTestResponse> skillTestResponseList = skillTestResponseRepository.findAll();
        assertThat(skillTestResponseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSkillTestResponse() throws Exception {
        // Initialize the database
        skillTestResponseRepository.saveAndFlush(skillTestResponse);
        skillTestResponseSearchRepository.save(skillTestResponse);
        int databaseSizeBeforeDelete = skillTestResponseRepository.findAll().size();

        // Get the skillTestResponse
        restSkillTestResponseMockMvc.perform(delete("/api/skill-test-responses/{id}", skillTestResponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean skillTestResponseExistsInEs = skillTestResponseSearchRepository.exists(skillTestResponse.getId());
        assertThat(skillTestResponseExistsInEs).isFalse();

        // Validate the database is empty
        List<SkillTestResponse> skillTestResponseList = skillTestResponseRepository.findAll();
        assertThat(skillTestResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSkillTestResponse() throws Exception {
        // Initialize the database
        skillTestResponseRepository.saveAndFlush(skillTestResponse);
        skillTestResponseSearchRepository.save(skillTestResponse);

        // Search the skillTestResponse
        restSkillTestResponseMockMvc.perform(get("/api/_search/skill-test-responses?query=id:" + skillTestResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillTestResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].responseSkillTestResponse").value(hasItem(DEFAULT_RESPONSE_SKILL_TEST_RESPONSE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillTestResponse.class);
        SkillTestResponse skillTestResponse1 = new SkillTestResponse();
        skillTestResponse1.setId(1L);
        SkillTestResponse skillTestResponse2 = new SkillTestResponse();
        skillTestResponse2.setId(skillTestResponse1.getId());
        assertThat(skillTestResponse1).isEqualTo(skillTestResponse2);
        skillTestResponse2.setId(2L);
        assertThat(skillTestResponse1).isNotEqualTo(skillTestResponse2);
        skillTestResponse1.setId(null);
        assertThat(skillTestResponse1).isNotEqualTo(skillTestResponse2);
    }
}
