package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.ExperienceProfil;

import fr.nicolasneto.repository.ExperienceProfilRepository;
import fr.nicolasneto.repository.search.ExperienceProfilSearchRepository;
import fr.nicolasneto.web.rest.errors.BadRequestAlertException;
import fr.nicolasneto.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ExperienceProfil.
 */
@RestController
@RequestMapping("/api")
public class ExperienceProfilResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceProfilResource.class);

    private static final String ENTITY_NAME = "experienceProfil";

    private final ExperienceProfilRepository experienceProfilRepository;

    private final ExperienceProfilSearchRepository experienceProfilSearchRepository;

    public ExperienceProfilResource(ExperienceProfilRepository experienceProfilRepository, ExperienceProfilSearchRepository experienceProfilSearchRepository) {
        this.experienceProfilRepository = experienceProfilRepository;
        this.experienceProfilSearchRepository = experienceProfilSearchRepository;
    }

    /**
     * POST  /experience-profils : Create a new experienceProfil.
     *
     * @param experienceProfil the experienceProfil to create
     * @return the ResponseEntity with status 201 (Created) and with body the new experienceProfil, or with status 400 (Bad Request) if the experienceProfil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/experience-profils")
    @Timed
    public ResponseEntity<ExperienceProfil> createExperienceProfil(@Valid @RequestBody ExperienceProfil experienceProfil) throws URISyntaxException {
        log.debug("REST request to save ExperienceProfil : {}", experienceProfil);
        if (experienceProfil.getId() != null) {
            throw new BadRequestAlertException("A new experienceProfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExperienceProfil result = experienceProfilRepository.save(experienceProfil);
        experienceProfilSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/experience-profils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /experience-profils : Updates an existing experienceProfil.
     *
     * @param experienceProfil the experienceProfil to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated experienceProfil,
     * or with status 400 (Bad Request) if the experienceProfil is not valid,
     * or with status 500 (Internal Server Error) if the experienceProfil couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/experience-profils")
    @Timed
    public ResponseEntity<ExperienceProfil> updateExperienceProfil(@Valid @RequestBody ExperienceProfil experienceProfil) throws URISyntaxException {
        log.debug("REST request to update ExperienceProfil : {}", experienceProfil);
        if (experienceProfil.getId() == null) {
            return createExperienceProfil(experienceProfil);
        }
        ExperienceProfil result = experienceProfilRepository.save(experienceProfil);
        experienceProfilSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, experienceProfil.getId().toString()))
            .body(result);
    }

    /**
     * GET  /experience-profils : get all the experienceProfils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of experienceProfils in body
     */
    @GetMapping("/experience-profils")
    @Timed
    public List<ExperienceProfil> getAllExperienceProfils() {
        log.debug("REST request to get all ExperienceProfils");
        return experienceProfilRepository.findAll();
        }

    /**
     * GET  /experience-profils/:id : get the "id" experienceProfil.
     *
     * @param id the id of the experienceProfil to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the experienceProfil, or with status 404 (Not Found)
     */
    @GetMapping("/experience-profils/{id}")
    @Timed
    public ResponseEntity<ExperienceProfil> getExperienceProfil(@PathVariable Long id) {
        log.debug("REST request to get ExperienceProfil : {}", id);
        ExperienceProfil experienceProfil = experienceProfilRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(experienceProfil));
    }

    /**
     * DELETE  /experience-profils/:id : delete the "id" experienceProfil.
     *
     * @param id the id of the experienceProfil to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/experience-profils/{id}")
    @Timed
    public ResponseEntity<Void> deleteExperienceProfil(@PathVariable Long id) {
        log.debug("REST request to delete ExperienceProfil : {}", id);
        experienceProfilRepository.delete(id);
        experienceProfilSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/experience-profils?query=:query : search for the experienceProfil corresponding
     * to the query.
     *
     * @param query the query of the experienceProfil search
     * @return the result of the search
     */
    @GetMapping("/_search/experience-profils")
    @Timed
    public List<ExperienceProfil> searchExperienceProfils(@RequestParam String query) {
        log.debug("REST request to search ExperienceProfils for query {}", query);
        return StreamSupport
            .stream(experienceProfilSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
