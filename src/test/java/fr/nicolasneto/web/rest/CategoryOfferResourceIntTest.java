package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.CategoryOffer;
import fr.nicolasneto.repository.CategoryOfferRepository;
import fr.nicolasneto.repository.search.CategoryOfferSearchRepository;
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
 * Test class for the CategoryOfferResource REST controller.
 *
 * @see CategoryOfferResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class CategoryOfferResourceIntTest {

    private static final String DEFAULT_NAME_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_NAME_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private CategoryOfferRepository categoryOfferRepository;

    @Autowired
    private CategoryOfferSearchRepository categoryOfferSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCategoryOfferMockMvc;

    private CategoryOffer categoryOffer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategoryOfferResource categoryOfferResource = new CategoryOfferResource(categoryOfferRepository, categoryOfferSearchRepository);
        this.restCategoryOfferMockMvc = MockMvcBuilders.standaloneSetup(categoryOfferResource)
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
    public static CategoryOffer createEntity(EntityManager em) {
        CategoryOffer categoryOffer = new CategoryOffer()
            .nameCategory(DEFAULT_NAME_CATEGORY);
        return categoryOffer;
    }

    @Before
    public void initTest() {
        categoryOfferSearchRepository.deleteAll();
        categoryOffer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategoryOffer() throws Exception {
        int databaseSizeBeforeCreate = categoryOfferRepository.findAll().size();

        // Create the CategoryOffer
        restCategoryOfferMockMvc.perform(post("/api/category-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryOffer)))
            .andExpect(status().isCreated());

        // Validate the CategoryOffer in the database
        List<CategoryOffer> categoryOfferList = categoryOfferRepository.findAll();
        assertThat(categoryOfferList).hasSize(databaseSizeBeforeCreate + 1);
        CategoryOffer testCategoryOffer = categoryOfferList.get(categoryOfferList.size() - 1);
        assertThat(testCategoryOffer.getNameCategory()).isEqualTo(DEFAULT_NAME_CATEGORY);

        // Validate the CategoryOffer in Elasticsearch
        CategoryOffer categoryOfferEs = categoryOfferSearchRepository.findOne(testCategoryOffer.getId());
        assertThat(categoryOfferEs).isEqualToIgnoringGivenFields(testCategoryOffer);
    }

    @Test
    @Transactional
    public void createCategoryOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryOfferRepository.findAll().size();

        // Create the CategoryOffer with an existing ID
        categoryOffer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryOfferMockMvc.perform(post("/api/category-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryOffer)))
            .andExpect(status().isBadRequest());

        // Validate the CategoryOffer in the database
        List<CategoryOffer> categoryOfferList = categoryOfferRepository.findAll();
        assertThat(categoryOfferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoryOfferRepository.findAll().size();
        // set the field null
        categoryOffer.setNameCategory(null);

        // Create the CategoryOffer, which fails.

        restCategoryOfferMockMvc.perform(post("/api/category-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryOffer)))
            .andExpect(status().isBadRequest());

        List<CategoryOffer> categoryOfferList = categoryOfferRepository.findAll();
        assertThat(categoryOfferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategoryOffers() throws Exception {
        // Initialize the database
        categoryOfferRepository.saveAndFlush(categoryOffer);

        // Get all the categoryOfferList
        restCategoryOfferMockMvc.perform(get("/api/category-offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameCategory").value(hasItem(DEFAULT_NAME_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getCategoryOffer() throws Exception {
        // Initialize the database
        categoryOfferRepository.saveAndFlush(categoryOffer);

        // Get the categoryOffer
        restCategoryOfferMockMvc.perform(get("/api/category-offers/{id}", categoryOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categoryOffer.getId().intValue()))
            .andExpect(jsonPath("$.nameCategory").value(DEFAULT_NAME_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategoryOffer() throws Exception {
        // Get the categoryOffer
        restCategoryOfferMockMvc.perform(get("/api/category-offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategoryOffer() throws Exception {
        // Initialize the database
        categoryOfferRepository.saveAndFlush(categoryOffer);
        categoryOfferSearchRepository.save(categoryOffer);
        int databaseSizeBeforeUpdate = categoryOfferRepository.findAll().size();

        // Update the categoryOffer
        CategoryOffer updatedCategoryOffer = categoryOfferRepository.findOne(categoryOffer.getId());
        // Disconnect from session so that the updates on updatedCategoryOffer are not directly saved in db
        em.detach(updatedCategoryOffer);
        updatedCategoryOffer
            .nameCategory(UPDATED_NAME_CATEGORY);

        restCategoryOfferMockMvc.perform(put("/api/category-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategoryOffer)))
            .andExpect(status().isOk());

        // Validate the CategoryOffer in the database
        List<CategoryOffer> categoryOfferList = categoryOfferRepository.findAll();
        assertThat(categoryOfferList).hasSize(databaseSizeBeforeUpdate);
        CategoryOffer testCategoryOffer = categoryOfferList.get(categoryOfferList.size() - 1);
        assertThat(testCategoryOffer.getNameCategory()).isEqualTo(UPDATED_NAME_CATEGORY);

        // Validate the CategoryOffer in Elasticsearch
        CategoryOffer categoryOfferEs = categoryOfferSearchRepository.findOne(testCategoryOffer.getId());
        assertThat(categoryOfferEs).isEqualToIgnoringGivenFields(testCategoryOffer);
    }

    @Test
    @Transactional
    public void updateNonExistingCategoryOffer() throws Exception {
        int databaseSizeBeforeUpdate = categoryOfferRepository.findAll().size();

        // Create the CategoryOffer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCategoryOfferMockMvc.perform(put("/api/category-offers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categoryOffer)))
            .andExpect(status().isCreated());

        // Validate the CategoryOffer in the database
        List<CategoryOffer> categoryOfferList = categoryOfferRepository.findAll();
        assertThat(categoryOfferList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCategoryOffer() throws Exception {
        // Initialize the database
        categoryOfferRepository.saveAndFlush(categoryOffer);
        categoryOfferSearchRepository.save(categoryOffer);
        int databaseSizeBeforeDelete = categoryOfferRepository.findAll().size();

        // Get the categoryOffer
        restCategoryOfferMockMvc.perform(delete("/api/category-offers/{id}", categoryOffer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean categoryOfferExistsInEs = categoryOfferSearchRepository.exists(categoryOffer.getId());
        assertThat(categoryOfferExistsInEs).isFalse();

        // Validate the database is empty
        List<CategoryOffer> categoryOfferList = categoryOfferRepository.findAll();
        assertThat(categoryOfferList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCategoryOffer() throws Exception {
        // Initialize the database
        categoryOfferRepository.saveAndFlush(categoryOffer);
        categoryOfferSearchRepository.save(categoryOffer);

        // Search the categoryOffer
        restCategoryOfferMockMvc.perform(get("/api/_search/category-offers?query=id:" + categoryOffer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categoryOffer.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameCategory").value(hasItem(DEFAULT_NAME_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoryOffer.class);
        CategoryOffer categoryOffer1 = new CategoryOffer();
        categoryOffer1.setId(1L);
        CategoryOffer categoryOffer2 = new CategoryOffer();
        categoryOffer2.setId(categoryOffer1.getId());
        assertThat(categoryOffer1).isEqualTo(categoryOffer2);
        categoryOffer2.setId(2L);
        assertThat(categoryOffer1).isNotEqualTo(categoryOffer2);
        categoryOffer1.setId(null);
        assertThat(categoryOffer1).isNotEqualTo(categoryOffer2);
    }
}
