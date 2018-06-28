package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.CandidateSkill;

import fr.nicolasneto.repository.CandidateSkillRepository;
import fr.nicolasneto.repository.search.CandidateSkillSearchRepository;
import fr.nicolasneto.web.rest.errors.BadRequestAlertException;
import fr.nicolasneto.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CandidateSkill.
 */
@RestController
@RequestMapping("/api")
public class CandidateSkillResource {

    private final Logger log = LoggerFactory.getLogger(CandidateSkillResource.class);

    private static final String ENTITY_NAME = "candidateSkill";

    private final CandidateSkillRepository candidateSkillRepository;

    private final CandidateSkillSearchRepository candidateSkillSearchRepository;

    public CandidateSkillResource(CandidateSkillRepository candidateSkillRepository, CandidateSkillSearchRepository candidateSkillSearchRepository) {
        this.candidateSkillRepository = candidateSkillRepository;
        this.candidateSkillSearchRepository = candidateSkillSearchRepository;
    }

    /**
     * POST  /candidate-skills : Create a new candidateSkill.
     *
     * @param candidateSkill the candidateSkill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateSkill, or with status 400 (Bad Request) if the candidateSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-skills")
    @Timed
    public ResponseEntity<CandidateSkill> createCandidateSkill(@RequestBody CandidateSkill candidateSkill) throws URISyntaxException {
        log.debug("REST request to save CandidateSkill : {}", candidateSkill);
        if (candidateSkill.getId() != null) {
            throw new BadRequestAlertException("A new candidateSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateSkill result = candidateSkillRepository.save(candidateSkill);
        candidateSkillSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/candidate-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-skills : Updates an existing candidateSkill.
     *
     * @param candidateSkill the candidateSkill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateSkill,
     * or with status 400 (Bad Request) if the candidateSkill is not valid,
     * or with status 500 (Internal Server Error) if the candidateSkill couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-skills")
    @Timed
    public ResponseEntity<CandidateSkill> updateCandidateSkill(@RequestBody CandidateSkill candidateSkill) throws URISyntaxException {
        log.debug("REST request to update CandidateSkill : {}", candidateSkill);
        if (candidateSkill.getId() == null) {
            return createCandidateSkill(candidateSkill);
        }
        CandidateSkill result = candidateSkillRepository.save(candidateSkill);
        candidateSkillSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateSkill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-skills : get all the candidateSkills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of candidateSkills in body
     */
    @GetMapping("/candidate-skills")
    @Timed
    public List<CandidateSkill> getAllCandidateSkills() {
        log.debug("REST request to get all CandidateSkills");
        return candidateSkillRepository.findAll();
        }

    /**
     * GET  /candidate-skills/:id : get the "id" candidateSkill.
     *
     * @param id the id of the candidateSkill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateSkill, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-skills/{id}")
    @Timed
    public ResponseEntity<CandidateSkill> getCandidateSkill(@PathVariable Long id) {
        log.debug("REST request to get CandidateSkill : {}", id);
        CandidateSkill candidateSkill = candidateSkillRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateSkill));
    }

    /**
     * DELETE  /candidate-skills/:id : delete the "id" candidateSkill.
     *
     * @param id the id of the candidateSkill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateSkill(@PathVariable Long id) {
        log.debug("REST request to delete CandidateSkill : {}", id);
        candidateSkillRepository.delete(id);
        candidateSkillSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/candidate-skills?query=:query : search for the candidateSkill corresponding
     * to the query.
     *
     * @param query the query of the candidateSkill search
     * @return the result of the search
     */
    @GetMapping("/_search/candidate-skills")
    @Timed
    public List<CandidateSkill> searchCandidateSkills(@RequestParam String query) {
        log.debug("REST request to search CandidateSkills for query {}", query);
        return StreamSupport
            .stream(candidateSkillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
