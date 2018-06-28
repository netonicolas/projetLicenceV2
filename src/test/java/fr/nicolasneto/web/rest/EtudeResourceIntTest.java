package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.Etude;
import fr.nicolasneto.repository.EtudeRepository;
import fr.nicolasneto.repository.search.EtudeSearchRepository;
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

import fr.nicolasneto.domain.enumeration.NiveauEtude;
/**
 * Test class for the EtudeResource REST controller.
 *
 * @see EtudeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class EtudeResourceIntTest {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final NiveauEtude DEFAULT_NIVEAU_ETUDE = NiveauEtude.BAC;
    private static final NiveauEtude UPDATED_NIVEAU_ETUDE = NiveauEtude.LICENCE;

    @Autowired
    private EtudeRepository etudeRepository;

    @Autowired
    private EtudeSearchRepository etudeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtudeMockMvc;

    private Etude etude;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtudeResource etudeResource = new EtudeResource(etudeRepository, etudeSearchRepository);
        this.restEtudeMockMvc = MockMvcBuilders.standaloneSetup(etudeResource)
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
    public static Etude createEntity(EntityManager em) {
        Etude etude = new Etude()
            .libelle(DEFAULT_LIBELLE)
            .niveauEtude(DEFAULT_NIVEAU_ETUDE);
        return etude;
    }

    @Before
    public void initTest() {
        etudeSearchRepository.deleteAll();
        etude = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtude() throws Exception {
        int databaseSizeBeforeCreate = etudeRepository.findAll().size();

        // Create the Etude
        restEtudeMockMvc.perform(post("/api/etudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etude)))
            .andExpect(status().isCreated());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeCreate + 1);
        Etude testEtude = etudeList.get(etudeList.size() - 1);
        assertThat(testEtude.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEtude.getNiveauEtude()).isEqualTo(DEFAULT_NIVEAU_ETUDE);

        // Validate the Etude in Elasticsearch
        Etude etudeEs = etudeSearchRepository.findOne(testEtude.getId());
        assertThat(etudeEs).isEqualToIgnoringGivenFields(testEtude);
    }

    @Test
    @Transactional
    public void createEtudeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etudeRepository.findAll().size();

        // Create the Etude with an existing ID
        etude.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtudeMockMvc.perform(post("/api/etudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etude)))
            .andExpect(status().isBadRequest());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEtudes() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);

        // Get all the etudeList
        restEtudeMockMvc.perform(get("/api/etudes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etude.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].niveauEtude").value(hasItem(DEFAULT_NIVEAU_ETUDE.toString())));
    }

    @Test
    @Transactional
    public void getEtude() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);

        // Get the etude
        restEtudeMockMvc.perform(get("/api/etudes/{id}", etude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etude.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.niveauEtude").value(DEFAULT_NIVEAU_ETUDE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtude() throws Exception {
        // Get the etude
        restEtudeMockMvc.perform(get("/api/etudes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtude() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);
        etudeSearchRepository.save(etude);
        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();

        // Update the etude
        Etude updatedEtude = etudeRepository.findOne(etude.getId());
        // Disconnect from session so that the updates on updatedEtude are not directly saved in db
        em.detach(updatedEtude);
        updatedEtude
            .libelle(UPDATED_LIBELLE)
            .niveauEtude(UPDATED_NIVEAU_ETUDE);

        restEtudeMockMvc.perform(put("/api/etudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtude)))
            .andExpect(status().isOk());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate);
        Etude testEtude = etudeList.get(etudeList.size() - 1);
        assertThat(testEtude.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtude.getNiveauEtude()).isEqualTo(UPDATED_NIVEAU_ETUDE);

        // Validate the Etude in Elasticsearch
        Etude etudeEs = etudeSearchRepository.findOne(testEtude.getId());
        assertThat(etudeEs).isEqualToIgnoringGivenFields(testEtude);
    }

    @Test
    @Transactional
    public void updateNonExistingEtude() throws Exception {
        int databaseSizeBeforeUpdate = etudeRepository.findAll().size();

        // Create the Etude

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtudeMockMvc.perform(put("/api/etudes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etude)))
            .andExpect(status().isCreated());

        // Validate the Etude in the database
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtude() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);
        etudeSearchRepository.save(etude);
        int databaseSizeBeforeDelete = etudeRepository.findAll().size();

        // Get the etude
        restEtudeMockMvc.perform(delete("/api/etudes/{id}", etude.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean etudeExistsInEs = etudeSearchRepository.exists(etude.getId());
        assertThat(etudeExistsInEs).isFalse();

        // Validate the database is empty
        List<Etude> etudeList = etudeRepository.findAll();
        assertThat(etudeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEtude() throws Exception {
        // Initialize the database
        etudeRepository.saveAndFlush(etude);
        etudeSearchRepository.save(etude);

        // Search the etude
        restEtudeMockMvc.perform(get("/api/_search/etudes?query=id:" + etude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etude.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].niveauEtude").value(hasItem(DEFAULT_NIVEAU_ETUDE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etude.class);
        Etude etude1 = new Etude();
        etude1.setId(1L);
        Etude etude2 = new Etude();
        etude2.setId(etude1.getId());
        assertThat(etude1).isEqualTo(etude2);
        etude2.setId(2L);
        assertThat(etude1).isNotEqualTo(etude2);
        etude1.setId(null);
        assertThat(etude1).isNotEqualTo(etude2);
    }
}
