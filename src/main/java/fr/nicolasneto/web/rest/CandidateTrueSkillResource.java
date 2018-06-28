package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.CandidateTrueSkill;

import fr.nicolasneto.repository.CandidateTrueSkillRepository;
import fr.nicolasneto.repository.search.CandidateTrueSkillSearchRepository;
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
 * REST controller for managing CandidateTrueSkill.
 */
@RestController
@RequestMapping("/api")
public class CandidateTrueSkillResource {

    private final Logger log = LoggerFactory.getLogger(CandidateTrueSkillResource.class);

    private static final String ENTITY_NAME = "candidateTrueSkill";

    private final CandidateTrueSkillRepository candidateTrueSkillRepository;

    private final CandidateTrueSkillSearchRepository candidateTrueSkillSearchRepository;

    public CandidateTrueSkillResource(CandidateTrueSkillRepository candidateTrueSkillRepository, CandidateTrueSkillSearchRepository candidateTrueSkillSearchRepository) {
        this.candidateTrueSkillRepository = candidateTrueSkillRepository;
        this.candidateTrueSkillSearchRepository = candidateTrueSkillSearchRepository;
    }

    /**
     * POST  /candidate-true-skills : Create a new candidateTrueSkill.
     *
     * @param candidateTrueSkill the candidateTrueSkill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateTrueSkill, or with status 400 (Bad Request) if the candidateTrueSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-true-skills")
    @Timed
    public ResponseEntity<CandidateTrueSkill> createCandidateTrueSkill(@Valid @RequestBody CandidateTrueSkill candidateTrueSkill) throws URISyntaxException {
        log.debug("REST request to save CandidateTrueSkill : {}", candidateTrueSkill);
        if (candidateTrueSkill.getId() != null) {
            throw new BadRequestAlertException("A new candidateTrueSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateTrueSkill result = candidateTrueSkillRepository.save(candidateTrueSkill);
        candidateTrueSkillSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/candidate-true-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-true-skills : Updates an existing candidateTrueSkill.
     *
     * @param candidateTrueSkill the candidateTrueSkill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateTrueSkill,
     * or with status 400 (Bad Request) if the candidateTrueSkill is not valid,
     * or with status 500 (Internal Server Error) if the candidateTrueSkill couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-true-skills")
    @Timed
    public ResponseEntity<CandidateTrueSkill> updateCandidateTrueSkill(@Valid @RequestBody CandidateTrueSkill candidateTrueSkill) throws URISyntaxException {
        log.debug("REST request to update CandidateTrueSkill : {}", candidateTrueSkill);
        if (candidateTrueSkill.getId() == null) {
            return createCandidateTrueSkill(candidateTrueSkill);
        }
        CandidateTrueSkill result = candidateTrueSkillRepository.save(candidateTrueSkill);
        candidateTrueSkillSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateTrueSkill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-true-skills : get all the candidateTrueSkills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateTrueSkills in body
     */
    @GetMapping("/candidate-true-skills")
    @Timed
    public List<CandidateTrueSkill> getAllCandidateTrueSkills() {
        log.debug("REST request to get all CandidateTrueSkills");
        return candidateTrueSkillRepository.findAll();
        }

    /**
     * GET  /candidate-true-skills/:id : get the "id" candidateTrueSkill.
     *
     * @param id the id of the candidateTrueSkill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateTrueSkill, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-true-skills/{id}")
    @Timed
    public ResponseEntity<CandidateTrueSkill> getCandidateTrueSkill(@PathVariable Long id) {
        log.debug("REST request to get CandidateTrueSkill : {}", id);
        CandidateTrueSkill candidateTrueSkill = candidateTrueSkillRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateTrueSkill));
    }

    /**
     * DELETE  /candidate-true-skills/:id : delete the "id" candidateTrueSkill.
     *
     * @param id the id of the candidateTrueSkill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-true-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateTrueSkill(@PathVariable Long id) {
        log.debug("REST request to delete CandidateTrueSkill : {}", id);
        candidateTrueSkillRepository.delete(id);
        candidateTrueSkillSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/candidate-true-skills?query=:query : search for the candidateTrueSkill corresponding
     * to the query.
     *
     * @param query the query of the candidateTrueSkill search
     * @return the result of the search
     */
    @GetMapping("/_search/candidate-true-skills")
    @Timed
    public List<CandidateTrueSkill> searchCandidateTrueSkills(@RequestParam String query) {
        log.debug("REST request to search CandidateTrueSkills for query {}", query);
        return StreamSupport
            .stream(candidateTrueSkillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
