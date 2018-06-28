package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.SkillTest;
import fr.nicolasneto.repository.SkillTestRepository;
import fr.nicolasneto.repository.search.SkillTestSearchRepository;
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
 * Test class for the SkillTestResource REST controller.
 *
 * @see SkillTestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class SkillTestResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SkillTestRepository skillTestRepository;

    @Autowired
    private SkillTestSearchRepository skillTestSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkillTestMockMvc;

    private SkillTest skillTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkillTestResource skillTestResource = new SkillTestResource(skillTestRepository, skillTestSearchRepository);
        this.restSkillTestMockMvc = MockMvcBuilders.standaloneSetup(skillTestResource)
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
    public static SkillTest createEntity(EntityManager em) {
        SkillTest skillTest = new SkillTest()
            .date(DEFAULT_DATE);
        return skillTest;
    }

    @Before
    public void initTest() {
        skillTestSearchRepository.deleteAll();
        skillTest = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillTest() throws Exception {
        int databaseSizeBeforeCreate = skillTestRepository.findAll().size();

        // Create the SkillTest
        restSkillTestMockMvc.perform(post("/api/skill-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTest)))
            .andExpect(status().isCreated());

        // Validate the SkillTest in the database
        List<SkillTest> skillTestList = skillTestRepository.findAll();
        assertThat(skillTestList).hasSize(databaseSizeBeforeCreate + 1);
        SkillTest testSkillTest = skillTestList.get(skillTestList.size() - 1);
        assertThat(testSkillTest.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the SkillTest in Elasticsearch
        SkillTest skillTestEs = skillTestSearchRepository.findOne(testSkillTest.getId());
        assertThat(skillTestEs).isEqualToIgnoringGivenFields(testSkillTest);
    }

    @Test
    @Transactional
    public void createSkillTestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillTestRepository.findAll().size();

        // Create the SkillTest with an existing ID
        skillTest.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillTestMockMvc.perform(post("/api/skill-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTest)))
            .andExpect(status().isBadRequest());

        // Validate the SkillTest in the database
        List<SkillTest> skillTestList = skillTestRepository.findAll();
        assertThat(skillTestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = skillTestRepository.findAll().size();
        // set the field null
        skillTest.setDate(null);

        // Create the SkillTest, which fails.

        restSkillTestMockMvc.perform(post("/api/skill-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTest)))
            .andExpect(status().isBadRequest());

        List<SkillTest> skillTestList = skillTestRepository.findAll();
        assertThat(skillTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkillTests() throws Exception {
        // Initialize the database
        skillTestRepository.saveAndFlush(skillTest);

        // Get all the skillTestList
        restSkillTestMockMvc.perform(get("/api/skill-tests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSkillTest() throws Exception {
        // Initialize the database
        skillTestRepository.saveAndFlush(skillTest);

        // Get the skillTest
        restSkillTestMockMvc.perform(get("/api/skill-tests/{id}", skillTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(skillTest.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSkillTest() throws Exception {
        // Get the skillTest
        restSkillTestMockMvc.perform(get("/api/skill-tests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillTest() throws Exception {
        // Initialize the database
        skillTestRepository.saveAndFlush(skillTest);
        skillTestSearchRepository.save(skillTest);
        int databaseSizeBeforeUpdate = skillTestRepository.findAll().size();

        // Update the skillTest
        SkillTest updatedSkillTest = skillTestRepository.findOne(skillTest.getId());
        // Disconnect from session so that the updates on updatedSkillTest are not directly saved in db
        em.detach(updatedSkillTest);
        updatedSkillTest
            .date(UPDATED_DATE);

        restSkillTestMockMvc.perform(put("/api/skill-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillTest)))
            .andExpect(status().isOk());

        // Validate the SkillTest in the database
        List<SkillTest> skillTestList = skillTestRepository.findAll();
        assertThat(skillTestList).hasSize(databaseSizeBeforeUpdate);
        SkillTest testSkillTest = skillTestList.get(skillTestList.size() - 1);
        assertThat(testSkillTest.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the SkillTest in Elasticsearch
        SkillTest skillTestEs = skillTestSearchRepository.findOne(testSkillTest.getId());
        assertThat(skillTestEs).isEqualToIgnoringGivenFields(testSkillTest);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillTest() throws Exception {
        int databaseSizeBeforeUpdate = skillTestRepository.findAll().size();

        // Create the SkillTest

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSkillTestMockMvc.perform(put("/api/skill-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skillTest)))
            .andExpect(status().isCreated());

        // Validate the SkillTest in the database
        List<SkillTest> skillTestList = skillTestRepository.findAll();
        assertThat(skillTestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSkillTest() throws Exception {
        // Initialize the database
        skillTestRepository.saveAndFlush(skillTest);
        skillTestSearchRepository.save(skillTest);
        int databaseSizeBeforeDelete = skillTestRepository.findAll().size();

        // Get the skillTest
        restSkillTestMockMvc.perform(delete("/api/skill-tests/{id}", skillTest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean skillTestExistsInEs = skillTestSearchRepository.exists(skillTest.getId());
        assertThat(skillTestExistsInEs).isFalse();

        // Validate the database is empty
        List<SkillTest> skillTestList = skillTestRepository.findAll();
        assertThat(skillTestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSkillTest() throws Exception {
        // Initialize the database
        skillTestRepository.saveAndFlush(skillTest);
        skillTestSearchRepository.save(skillTest);

        // Search the skillTest
        restSkillTestMockMvc.perform(get("/api/_search/skill-tests?query=id:" + skillTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillTest.class);
        SkillTest skillTest1 = new SkillTest();
        skillTest1.setId(1L);
        SkillTest skillTest2 = new SkillTest();
        skillTest2.setId(skillTest1.getId());
        assertThat(skillTest1).isEqualTo(skillTest2);
        skillTest2.setId(2L);
        assertThat(skillTest1).isNotEqualTo(skillTest2);
        skillTest1.setId(null);
        assertThat(skillTest1).isNotEqualTo(skillTest2);
    }
}
