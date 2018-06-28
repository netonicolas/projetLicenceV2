package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.SkillTestResponse;

import fr.nicolasneto.repository.SkillTestResponseRepository;
import fr.nicolasneto.repository.search.SkillTestResponseSearchRepository;
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
 * REST controller for managing SkillTestResponse.
 */
@RestController
@RequestMapping("/api")
public class SkillTestResponseResource {

    private final Logger log = LoggerFactory.getLogger(SkillTestResponseResource.class);

    private static final String ENTITY_NAME = "skillTestResponse";

    private final SkillTestResponseRepository skillTestResponseRepository;

    private final SkillTestResponseSearchRepository skillTestResponseSearchRepository;

    public SkillTestResponseResource(SkillTestResponseRepository skillTestResponseRepository, SkillTestResponseSearchRepository skillTestResponseSearchRepository) {
        this.skillTestResponseRepository = skillTestResponseRepository;
        this.skillTestResponseSearchRepository = skillTestResponseSearchRepository;
    }

    /**
     * POST  /skill-test-responses : Create a new skillTestResponse.
     *
     * @param skillTestResponse the skillTestResponse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillTestResponse, or with status 400 (Bad Request) if the skillTestResponse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skill-test-responses")
    @Timed
    public ResponseEntity<SkillTestResponse> createSkillTestResponse(@Valid @RequestBody SkillTestResponse skillTestResponse) throws URISyntaxException {
        log.debug("REST request to save SkillTestResponse : {}", skillTestResponse);
        if (skillTestResponse.getId() != null) {
            throw new BadRequestAlertException("A new skillTestResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillTestResponse result = skillTestResponseRepository.save(skillTestResponse);
        skillTestResponseSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/skill-test-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skill-test-responses : Updates an existing skillTestResponse.
     *
     * @param skillTestResponse the skillTestResponse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillTestResponse,
     * or with status 400 (Bad Request) if the skillTestResponse is not valid,
     * or with status 500 (Internal Server Error) if the skillTestResponse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skill-test-responses")
    @Timed
    public ResponseEntity<SkillTestResponse> updateSkillTestResponse(@Valid @RequestBody SkillTestResponse skillTestResponse) throws URISyntaxException {
        log.debug("REST request to update SkillTestResponse : {}", skillTestResponse);
        if (skillTestResponse.getId() == null) {
            return createSkillTestResponse(skillTestResponse);
        }
        SkillTestResponse result = skillTestResponseRepository.save(skillTestResponse);
        skillTestResponseSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillTestResponse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skill-test-responses : get all the skillTestResponses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skillTestResponses in body
     */
    @GetMapping("/skill-test-responses")
    @Timed
    public List<SkillTestResponse> getAllSkillTestResponses() {
        log.debug("REST request to get all SkillTestResponses");
        return skillTestResponseRepository.findAll();
        }

    /**
     * GET  /skill-test-responses/:id : get the "id" skillTestResponse.
     *
     * @param id the id of the skillTestResponse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillTestResponse, or with status 404 (Not Found)
     */
    @GetMapping("/skill-test-responses/{id}")
    @Timed
    public ResponseEntity<SkillTestResponse> getSkillTestResponse(@PathVariable Long id) {
        log.debug("REST request to get SkillTestResponse : {}", id);
        SkillTestResponse skillTestResponse = skillTestResponseRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(skillTestResponse));
    }

    /**
     * DELETE  /skill-test-responses/:id : delete the "id" skillTestResponse.
     *
     * @param id the id of the skillTestResponse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skill-test-responses/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkillTestResponse(@PathVariable Long id) {
        log.debug("REST request to delete SkillTestResponse : {}", id);
        skillTestResponseRepository.delete(id);
        skillTestResponseSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/skill-test-responses?query=:query : search for the skillTestResponse corresponding
     * to the query.
     *
     * @param query the query of the skillTestResponse search
     * @return the result of the search
     */
    @GetMapping("/_search/skill-test-responses")
    @Timed
    public List<SkillTestResponse> searchSkillTestResponses(@RequestParam String query) {
        log.debug("REST request to search SkillTestResponses for query {}", query);
        return StreamSupport
            .stream(skillTestResponseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
