package fr.nicolasneto.web.rest;

import fr.nicolasneto.VerifyMyCandidateApp;

import fr.nicolasneto.domain.EtudeProfil;
import fr.nicolasneto.repository.EtudeProfilRepository;
import fr.nicolasneto.repository.search.EtudeProfilSearchRepository;
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
 * Test class for the EtudeProfilResource REST controller.
 *
 * @see EtudeProfilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VerifyMyCandidateApp.class)
public class EtudeProfilResourceIntTest {

    private static final LocalDate DEFAULT_ANNEE_ETUDE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNEE_ETUDE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ANNE_ETUDE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ANNE_ETUDE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    @Autowired
    private EtudeProfilRepository etudeProfilRepository;

    @Autowired
    private EtudeProfilSearchRepository etudeProfilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtudeProfilMockMvc;

    private EtudeProfil etudeProfil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtudeProfilResource etudeProfilResource = new EtudeProfilResource(etudeProfilRepository, etudeProfilSearchRepository);
        this.restEtudeProfilMockMvc = MockMvcBuilders.standaloneSetup(etudeProfilResource)
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
    public static EtudeProfil createEntity(EntityManager em) {
        EtudeProfil etudeProfil = new EtudeProfil()
            .anneeEtudeDebut(DEFAULT_ANNEE_ETUDE_DEBUT)
            .anneEtudeFin(DEFAULT_ANNE_ETUDE_FIN)
            .comment(DEFAULT_COMMENT);
        return etudeProfil;
    }

    @Before
    public void initTest() {
        etudeProfilSearchRepository.deleteAll();
        etudeProfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtudeProfil() throws Exception {
        int databaseSizeBeforeCreate = etudeProfilRepository.findAll().size();

        // Create the EtudeProfil
        restEtudeProfilMockMvc.perform(post("/api/etude-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudeProfil)))
            .andExpect(status().isCreated());

        // Validate the EtudeProfil in the database
        List<EtudeProfil> etudeProfilList = etudeProfilRepository.findAll();
        assertThat(etudeProfilList).hasSize(databaseSizeBeforeCreate + 1);
        EtudeProfil testEtudeProfil = etudeProfilList.get(etudeProfilList.size() - 1);
        assertThat(testEtudeProfil.getAnneeEtudeDebut()).isEqualTo(DEFAULT_ANNEE_ETUDE_DEBUT);
        assertThat(testEtudeProfil.getAnneEtudeFin()).isEqualTo(DEFAULT_ANNE_ETUDE_FIN);
        assertThat(testEtudeProfil.getComment()).isEqualTo(DEFAULT_COMMENT);

        // Validate the EtudeProfil in Elasticsearch
        EtudeProfil etudeProfilEs = etudeProfilSearchRepository.findOne(testEtudeProfil.getId());
        assertThat(etudeProfilEs).isEqualToIgnoringGivenFields(testEtudeProfil);
    }

    @Test
    @Transactional
    public void createEtudeProfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etudeProfilRepository.findAll().size();

        // Create the EtudeProfil with an existing ID
        etudeProfil.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtudeProfilMockMvc.perform(post("/api/etude-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudeProfil)))
            .andExpect(status().isBadRequest());

        // Validate the EtudeProfil in the database
        List<EtudeProfil> etudeProfilList = etudeProfilRepository.findAll();
        assertThat(etudeProfilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEtudeProfils() throws Exception {
        // Initialize the database
        etudeProfilRepository.saveAndFlush(etudeProfil);

        // Get all the etudeProfilList
        restEtudeProfilMockMvc.perform(get("/api/etude-profils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudeProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].anneeEtudeDebut").value(hasItem(DEFAULT_ANNEE_ETUDE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].anneEtudeFin").value(hasItem(DEFAULT_ANNE_ETUDE_FIN.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getEtudeProfil() throws Exception {
        // Initialize the database
        etudeProfilRepository.saveAndFlush(etudeProfil);

        // Get the etudeProfil
        restEtudeProfilMockMvc.perform(get("/api/etude-profils/{id}", etudeProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etudeProfil.getId().intValue()))
            .andExpect(jsonPath("$.anneeEtudeDebut").value(DEFAULT_ANNEE_ETUDE_DEBUT.toString()))
            .andExpect(jsonPath("$.anneEtudeFin").value(DEFAULT_ANNE_ETUDE_FIN.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtudeProfil() throws Exception {
        // Get the etudeProfil
        restEtudeProfilMockMvc.perform(get("/api/etude-profils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtudeProfil() throws Exception {
        // Initialize the database
        etudeProfilRepository.saveAndFlush(etudeProfil);
        etudeProfilSearchRepository.save(etudeProfil);
        int databaseSizeBeforeUpdate = etudeProfilRepository.findAll().size();

        // Update the etudeProfil
        EtudeProfil updatedEtudeProfil = etudeProfilRepository.findOne(etudeProfil.getId());
        // Disconnect from session so that the updates on updatedEtudeProfil are not directly saved in db
        em.detach(updatedEtudeProfil);
        updatedEtudeProfil
            .anneeEtudeDebut(UPDATED_ANNEE_ETUDE_DEBUT)
            .anneEtudeFin(UPDATED_ANNE_ETUDE_FIN)
            .comment(UPDATED_COMMENT);

        restEtudeProfilMockMvc.perform(put("/api/etude-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtudeProfil)))
            .andExpect(status().isOk());

        // Validate the EtudeProfil in the database
        List<EtudeProfil> etudeProfilList = etudeProfilRepository.findAll();
        assertThat(etudeProfilList).hasSize(databaseSizeBeforeUpdate);
        EtudeProfil testEtudeProfil = etudeProfilList.get(etudeProfilList.size() - 1);
        assertThat(testEtudeProfil.getAnneeEtudeDebut()).isEqualTo(UPDATED_ANNEE_ETUDE_DEBUT);
        assertThat(testEtudeProfil.getAnneEtudeFin()).isEqualTo(UPDATED_ANNE_ETUDE_FIN);
        assertThat(testEtudeProfil.getComment()).isEqualTo(UPDATED_COMMENT);

        // Validate the EtudeProfil in Elasticsearch
        EtudeProfil etudeProfilEs = etudeProfilSearchRepository.findOne(testEtudeProfil.getId());
        assertThat(etudeProfilEs).isEqualToIgnoringGivenFields(testEtudeProfil);
    }

    @Test
    @Transactional
    public void updateNonExistingEtudeProfil() throws Exception {
        int databaseSizeBeforeUpdate = etudeProfilRepository.findAll().size();

        // Create the EtudeProfil

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtudeProfilMockMvc.perform(put("/api/etude-profils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudeProfil)))
            .andExpect(status().isCreated());

        // Validate the EtudeProfil in the database
        List<EtudeProfil> etudeProfilList = etudeProfilRepository.findAll();
        assertThat(etudeProfilList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtudeProfil() throws Exception {
        // Initialize the database
        etudeProfilRepository.saveAndFlush(etudeProfil);
        etudeProfilSearchRepository.save(etudeProfil);
        int databaseSizeBeforeDelete = etudeProfilRepository.findAll().size();

        // Get the etudeProfil
        restEtudeProfilMockMvc.perform(delete("/api/etude-profils/{id}", etudeProfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean etudeProfilExistsInEs = etudeProfilSearchRepository.exists(etudeProfil.getId());
        assertThat(etudeProfilExistsInEs).isFalse();

        // Validate the database is empty
        List<EtudeProfil> etudeProfilList = etudeProfilRepository.findAll();
        assertThat(etudeProfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEtudeProfil() throws Exception {
        // Initialize the database
        etudeProfilRepository.saveAndFlush(etudeProfil);
        etudeProfilSearchRepository.save(etudeProfil);

        // Search the etudeProfil
        restEtudeProfilMockMvc.perform(get("/api/_search/etude-profils?query=id:" + etudeProfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudeProfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].anneeEtudeDebut").value(hasItem(DEFAULT_ANNEE_ETUDE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].anneEtudeFin").value(hasItem(DEFAULT_ANNE_ETUDE_FIN.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtudeProfil.class);
        EtudeProfil etudeProfil1 = new EtudeProfil();
        etudeProfil1.setId(1L);
        EtudeProfil etudeProfil2 = new EtudeProfil();
        etudeProfil2.setId(etudeProfil1.getId());
        assertThat(etudeProfil1).isEqualTo(etudeProfil2);
        etudeProfil2.setId(2L);
        assertThat(etudeProfil1).isNotEqualTo(etudeProfil2);
        etudeProfil1.setId(null);
        assertThat(etudeProfil1).isNotEqualTo(etudeProfil2);
    }
}
