package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.CandidateSkill;
import fr.nicolasneto.repository.CandidateSkillRepository;
import fr.nicolasneto.repository.search.CandidateSkillSearchRepository;
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
 * Test class for the CandidateSkillResource REST controller.
 *
 * @see CandidateSkillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class CandidateSkillResourceIntTest {

    private static final Long DEFAULT_LEVEL = 1L;
    private static final Long UPDATED_LEVEL = 2L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Autowired
    private CandidateSkillSearchRepository candidateSkillSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidateSkillMockMvc;

    private CandidateSkill candidateSkill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidateSkillResource candidateSkillResource = new CandidateSkillResource(candidateSkillRepository, candidateSkillSearchRepository);
        this.restCandidateSkillMockMvc = MockMvcBuilders.standaloneSetup(candidateSkillResource)
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
    public static CandidateSkill createEntity(EntityManager em) {
        CandidateSkill candidateSkill = new CandidateSkill()
            .level(DEFAULT_LEVEL)
            .comment(DEFAULT_COMMENT);
        return candidateSkill;
    }

    @Before
    public void initTest() {
        candidateSkillSearchRepository.deleteAll();
        candidateSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateSkill() throws Exception {
        int databaseSizeBeforeCreate = candidateSkillRepository.findAll().size();

        // Create the CandidateSkill
        restCandidateSkillMockMvc.perform(post("/api/candidate-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateSkill)))
            .andExpect(status().isCreated());

        // Validate the CandidateSkill in the database
        List<CandidateSkill> candidateSkillList = candidateSkillRepository.findAll();
        assertThat(candidateSkillList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateSkill testCandidateSkill = candidateSkillList.get(candidateSkillList.size() - 1);
        assertThat(testCandidateSkill.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testCandidateSkill.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the CandidateSkill in Elasticsearch
        CandidateSkill candidateSkillEs = candidateSkillSearchRepository.findOne(testCandidateSkill.getId());
        assertThat(candidateSkillEs).isEqualToIgnoringGivenFields(testCandidateSkill);
    }

    @Test
    @Transactional
    public void createCandidateSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateSkillRepository.findAll().size();

        // Create the CandidateSkill with an existing ID
        candidateSkill.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateSkillMockMvc.perform(post("/api/candidate-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateSkill)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateSkill in the database
        List<CandidateSkill> candidateSkillList = candidateSkillRepository.findAll();
        assertThat(candidateSkillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandidateSkills() throws Exception {
        // Initialize the database
        candidateSkillRepository.saveAndFlush(candidateSkill);

        // Get all the candidateSkillList
        restCandidateSkillMockMvc.perform(get("/api/candidate-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getCandidateSkill() throws Exception {
        // Initialize the database
        candidateSkillRepository.saveAndFlush(candidateSkill);

        // Get the candidateSkill
        restCandidateSkillMockMvc.perform(get("/api/candidate-skills/{id}", candidateSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateSkill.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateSkill() throws Exception {
        // Get the candidateSkill
        restCandidateSkillMockMvc.perform(get("/api/candidate-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateSkill() throws Exception {
        // Initialize the database
        candidateSkillRepository.saveAndFlush(candidateSkill);
        candidateSkillSearchRepository.save(candidateSkill);
        int databaseSizeBeforeUpdate = candidateSkillRepository.findAll().size();

        // Update the candidateSkill
        CandidateSkill updatedCandidateSkill = candidateSkillRepository.findOne(candidateSkill.getId());
        // Disconnect from session so that the updates on updatedCandidateSkill are not directly saved in db
        em.detach(updatedCandidateSkill);
        updatedCandidateSkill
            .level(UPDATED_LEVEL)
            .comment(UPDATED_COMMENT);

        restCandidateSkillMockMvc.perform(put("/api/candidate-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCandidateSkill)))
            .andExpect(status().isOk());

        // Validate the CandidateSkill in the database
        List<CandidateSkill> candidateSkillList = candidateSkillRepository.findAll();
        assertThat(candidateSkillList).hasSize(databaseSizeBeforeUpdate);
        CandidateSkill testCandidateSkill = candidateSkillList.get(candidateSkillList.size() - 1);
        assertThat(testCandidateSkill.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testCandidateSkill.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the CandidateSkill in Elasticsearch
        CandidateSkill candidateSkillEs = candidateSkillSearchRepository.findOne(testCandidateSkill.getId());
        assertThat(candidateSkillEs).isEqualToIgnoringGivenFields(testCandidateSkill);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidateSkill() throws Exception {
        int databaseSizeBeforeUpdate = candidateSkillRepository.findAll().size();

        // Create the CandidateSkill

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandidateSkillMockMvc.perform(put("/api/candidate-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateSkill)))
            .andExpect(status().isCreated());

        // Validate the CandidateSkill in the database
        List<CandidateSkill> candidateSkillList = candidateSkillRepository.findAll();
        assertThat(candidateSkillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandidateSkill() throws Exception {
        // Initialize the database
        candidateSkillRepository.saveAndFlush(candidateSkill);
        candidateSkillSearchRepository.save(candidateSkill);
        int databaseSizeBeforeDelete = candidateSkillRepository.findAll().size();

        // Get the candidateSkill
        restCandidateSkillMockMvc.perform(delete("/api/candidate-skills/{id}", candidateSkill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean candidateSkillExistsInEs = candidateSkillSearchRepository.exists(candidateSkill.getId());
        assertThat(candidateSkillExistsInEs).isFalse();

        // Validate the database is empty
        List<CandidateSkill> candidateSkillList = candidateSkillRepository.findAll();
        assertThat(candidateSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCandidateSkill() throws Exception {
        // Initialize the database
        candidateSkillRepository.saveAndFlush(candidateSkill);
        candidateSkillSearchRepository.save(candidateSkill);

        // Search the candidateSkill
        restCandidateSkillMockMvc.perform(get("/api/_search/candidate-skills?query=id:" + candidateSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateSkill.class);
        CandidateSkill candidateSkill1 = new CandidateSkill();
        candidateSkill1.setId(1L);
        CandidateSkill candidateSkill2 = new CandidateSkill();
        candidateSkill2.setId(candidateSkill1.getId());
        assertThat(candidateSkill1).isEqualTo(candidateSkill2);
        candidateSkill2.setId(2L);
        assertThat(candidateSkill1).isNotEqualTo(candidateSkill2);
        candidateSkill1.setId(null);
        assertThat(candidateSkill1).isNotEqualTo(candidateSkill2);
    }
}
