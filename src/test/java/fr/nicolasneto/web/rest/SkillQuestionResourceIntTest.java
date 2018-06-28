package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.SkillQuestion;
import fr.nicolasneto.domain.SkillTest;
import fr.nicolasneto.domain.Skill;
import fr.nicolasneto.repository.SkillQuestionRepository;
import fr.nicolasneto.repository.search.SkillQuestionSearchRepository;
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
 * Test class for the SkillQuestionResource REST controller.
 *
 * @see SkillQuestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class SkillQuestionResourceIntTest {

    private static final String DEFAULT_QUESTION_SKILL_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_SKILL_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_SKILL_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_SKILL_QUESTION = "BBBBBBBBBB";

    @Autowired
    private SkillQuestionRepository skillQuestionRepository;

    @Autowired
    private SkillQuestionSearchRepository skillQuestionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkillQuestionMockMvc;

    private SkillQuestion skillQuestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillQuestionResource skillQuestionResource = new SkillQuestionResource(skillQuestionRepository, skillQuestionSearchRepository);
        this.restSkillQuestionMockMvc = MockMvcBuilders.standaloneSetup(skillQuestionResource)
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
    public static SkillQuestion createEntity(EntityManager em) {
        SkillQuestion skillQuestion = new SkillQuestion()
            .questionSkillQuestion(DEFAULT_QUESTION_SKILL_QUESTION)
            .responseSkillQuestion(DEFAULT_RESPONSE_SKILL_QUESTION);
        // Add required entity
        SkillTest question = SkillTestResourceIntTest.createEntity(em);
        em.persist(question);
        em.flush();
        skillQuestion.setQuestion(question);
        // Add required entity
        Skill skill = SkillResourceIntTest.createEntity(em);
        em.persist(skill);
        em.flush();
        skillQuestion.setSkill(skill);
        return skillQuestion;
    }

    @Before
    public void initTest() {
        skillQuestionSearchRepository.deleteAll();
        skillQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillQuestion() throws Exception {
        int databaseSizeBeforeCreate = skillQuestionRepository.findAll().size();

        // Create the SkillQuestion
        restSkillQuestionMockMvc.perform(post("/api/skill-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillQuestion)))
            .andExpect(status().isCreated());

        // Validate the SkillQuestion in the database
        List<SkillQuestion> skillQuestionList = skillQuestionRepository.findAll();
        assertThat(skillQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        SkillQuestion testSkillQuestion = skillQuestionList.get(skillQuestionList.size() - 1);
        assertThat(testSkillQuestion.getQuestionSkillQuestion()).isEqualTo(DEFAULT_QUESTION_SKILL_QUESTION);
        assertThat(testSkillQuestion.getResponseSkillQuestion()).isEqualTo(DEFAULT_RESPONSE_SKILL_QUESTION);

        // Validate the SkillQuestion in Elasticsearch
        SkillQuestion skillQuestionEs = skillQuestionSearchRepository.findOne(testSkillQuestion.getId());
        assertThat(skillQuestionEs).isEqualToIgnoringGivenFields(testSkillQuestion);
    }

    @Test
    @Transactional
    public void createSkillQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillQuestionRepository.findAll().size();

        // Create the SkillQuestion with an existing ID
        skillQuestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillQuestionMockMvc.perform(post("/api/skill-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the SkillQuestion in the database
        List<SkillQuestion> skillQuestionList = skillQuestionRepository.findAll();
        assertThat(skillQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuestionSkillQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillQuestionRepository.findAll().size();
        // set the field null
        skillQuestion.setQuestionSkillQuestion(null);

        // Create the SkillQuestion, which fails.

        restSkillQuestionMockMvc.perform(post("/api/skill-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillQuestion)))
            .andExpect(status().isBadRequest());

        List<SkillQuestion> skillQuestionList = skillQuestionRepository.findAll();
        assertThat(skillQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponseSkillQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillQuestionRepository.findAll().size();
        // set the field null
        skillQuestion.setResponseSkillQuestion(null);

        // Create the SkillQuestion, which fails.

        restSkillQuestionMockMvc.perform(post("/api/skill-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillQuestion)))
            .andExpect(status().isBadRequest());

        List<SkillQuestion> skillQuestionList = skillQuestionRepository.findAll();
        assertThat(skillQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkillQuestions() throws Exception {
        // Initialize the database
        skillQuestionRepository.saveAndFlush(skillQuestion);

        // Get all the skillQuestionList
        restSkillQuestionMockMvc.perform(get("/api/skill-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionSkillQuestion").value(hasItem(DEFAULT_QUESTION_SKILL_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].responseSkillQuestion").value(hasItem(DEFAULT_RESPONSE_SKILL_QUESTION.toString())));
    }

    @Test
    @Transactional
    public void getSkillQuestion() throws Exception {
        // Initialize the database
        skillQuestionRepository.saveAndFlush(skillQuestion);

        // Get the skillQuestion
        restSkillQuestionMockMvc.perform(get("/api/skill-questions/{id}", skillQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillQuestion.getId().intValue()))
            .andExpect(jsonPath("$.questionSkillQuestion").value(DEFAULT_QUESTION_SKILL_QUESTION.toString()))
            .andExpect(jsonPath("$.responseSkillQuestion").value(DEFAULT_RESPONSE_SKILL_QUESTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSkillQuestion() throws Exception {
        // Get the skillQuestion
        restSkillQuestionMockMvc.perform(get("/api/skill-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillQuestion() throws Exception {
        // Initialize the database
        skillQuestionRepository.saveAndFlush(skillQuestion);
        skillQuestionSearchRepository.save(skillQuestion);
        int databaseSizeBeforeUpdate = skillQuestionRepository.findAll().size();

        // Update the skillQuestion
        SkillQuestion updatedSkillQuestion = skillQuestionRepository.findOne(skillQuestion.getId());
        // Disconnect from session so that the updates on updatedSkillQuestion are not directly saved in db
        em.detach(updatedSkillQuestion);
        updatedSkillQuestion
            .questionSkillQuestion(UPDATED_QUESTION_SKILL_QUESTION)
            .responseSkillQuestion(UPDATED_RESPONSE_SKILL_QUESTION);

        restSkillQuestionMockMvc.perform(put("/api/skill-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillQuestion)))
            .andExpect(status().isOk());

        // Validate the SkillQuestion in the database
        List<SkillQuestion> skillQuestionList = skillQuestionRepository.findAll();
        assertThat(skillQuestionList).hasSize(databaseSizeBeforeUpdate);
        SkillQuestion testSkillQuestion = skillQuestionList.get(skillQuestionList.size() - 1);
        assertThat(testSkillQuestion.getQuestionSkillQuestion()).isEqualTo(UPDATED_QUESTION_SKILL_QUESTION);
        assertThat(testSkillQuestion.getResponseSkillQuestion()).isEqualTo(UPDATED_RESPONSE_SKILL_QUESTION);

        // Validate the SkillQuestion in Elasticsearch
        SkillQuestion skillQuestionEs = skillQuestionSearchRepository.findOne(testSkillQuestion.getId());
        assertThat(skillQuestionEs).isEqualToIgnoringGivenFields(testSkillQuestion);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillQuestion() throws Exception {
        int databaseSizeBeforeUpdate = skillQuestionRepository.findAll().size();

        // Create the SkillQuestion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSkillQuestionMockMvc.perform(put("/api/skill-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillQuestion)))
            .andExpect(status().isCreated());

        // Validate the SkillQuestion in the database
        List<SkillQuestion> skillQuestionList = skillQuestionRepository.findAll();
        assertThat(skillQuestionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSkillQuestion() throws Exception {
        // Initialize the database
        skillQuestionRepository.saveAndFlush(skillQuestion);
        skillQuestionSearchRepository.save(skillQuestion);
        int databaseSizeBeforeDelete = skillQuestionRepository.findAll().size();

        // Get the skillQuestion
        restSkillQuestionMockMvc.perform(delete("/api/skill-questions/{id}", skillQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean skillQuestionExistsInEs = skillQuestionSearchRepository.exists(skillQuestion.getId());
        assertThat(skillQuestionExistsInEs).isFalse();

        // Validate the database is empty
        List<SkillQuestion> skillQuestionList = skillQuestionRepository.findAll();
        assertThat(skillQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSkillQuestion() throws Exception {
        // Initialize the database
        skillQuestionRepository.saveAndFlush(skillQuestion);
        skillQuestionSearchRepository.save(skillQuestion);

        // Search the skillQuestion
        restSkillQuestionMockMvc.perform(get("/api/_search/skill-questions?query=id:" + skillQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].questionSkillQuestion").value(hasItem(DEFAULT_QUESTION_SKILL_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].responseSkillQuestion").value(hasItem(DEFAULT_RESPONSE_SKILL_QUESTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillQuestion.class);
        SkillQuestion skillQuestion1 = new SkillQuestion();
        skillQuestion1.setId(1L);
        SkillQuestion skillQuestion2 = new SkillQuestion();
        skillQuestion2.setId(skillQuestion1.getId());
        assertThat(skillQuestion1).isEqualTo(skillQuestion2);
        skillQuestion2.setId(2L);
        assertThat(skillQuestion1).isNotEqualTo(skillQuestion2);
        skillQuestion1.setId(null);
        assertThat(skillQuestion1).isNotEqualTo(skillQuestion2);
    }
}
