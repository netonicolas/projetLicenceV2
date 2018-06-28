package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.SkillQuestion;

import fr.nicolasneto.repository.SkillQuestionRepository;
import fr.nicolasneto.repository.search.SkillQuestionSearchRepository;
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
 * REST controller for managing SkillQuestion.
 */
@RestController
@RequestMapping("/api")
public class SkillQuestionResource {

    private final Logger log = LoggerFactory.getLogger(SkillQuestionResource.class);

    private static final String ENTITY_NAME = "skillQuestion";

    private final SkillQuestionRepository skillQuestionRepository;

    private final SkillQuestionSearchRepository skillQuestionSearchRepository;

    public SkillQuestionResource(SkillQuestionRepository skillQuestionRepository, SkillQuestionSearchRepository skillQuestionSearchRepository) {
        this.skillQuestionRepository = skillQuestionRepository;
        this.skillQuestionSearchRepository = skillQuestionSearchRepository;
    }

    /**
     * POST  /skill-questions : Create a new skillQuestion.
     *
     * @param skillQuestion the skillQuestion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillQuestion, or with status 400 (Bad Request) if the skillQuestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skill-questions")
    @Timed
    public ResponseEntity<SkillQuestion> createSkillQuestion(@Valid @RequestBody SkillQuestion skillQuestion) throws URISyntaxException {
        log.debug("REST request to save SkillQuestion : {}", skillQuestion);
        if (skillQuestion.getId() != null) {
            throw new BadRequestAlertException("A new skillQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillQuestion result = skillQuestionRepository.save(skillQuestion);
        skillQuestionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/skill-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skill-questions : Updates an existing skillQuestion.
     *
     * @param skillQuestion the skillQuestion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillQuestion,
     * or with status 400 (Bad Request) if the skillQuestion is not valid,
     * or with status 500 (Internal Server Error) if the skillQuestion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skill-questions")
    @Timed
    public ResponseEntity<SkillQuestion> updateSkillQuestion(@Valid @RequestBody SkillQuestion skillQuestion) throws URISyntaxException {
        log.debug("REST request to update SkillQuestion : {}", skillQuestion);
        if (skillQuestion.getId() == null) {
            return createSkillQuestion(skillQuestion);
        }
        SkillQuestion result = skillQuestionRepository.save(skillQuestion);
        skillQuestionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillQuestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skill-questions : get all the skillQuestions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skillQuestions in body
     */
    @GetMapping("/skill-questions")
    @Timed
    public List<SkillQuestion> getAllSkillQuestions() {
        log.debug("REST request to get all SkillQuestions");
        return skillQuestionRepository.findAll();
        }

    /**
     * GET  /skill-questions/:id : get the "id" skillQuestion.
     *
     * @param id the id of the skillQuestion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillQuestion, or with status 404 (Not Found)
     */
    @GetMapping("/skill-questions/{id}")
    @Timed
    public ResponseEntity<SkillQuestion> getSkillQuestion(@PathVariable Long id) {
        log.debug("REST request to get SkillQuestion : {}", id);
        SkillQuestion skillQuestion = skillQuestionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(skillQuestion));
    }

    /**
     * DELETE  /skill-questions/:id : delete the "id" skillQuestion.
     *
     * @param id the id of the skillQuestion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skill-questions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkillQuestion(@PathVariable Long id) {
        log.debug("REST request to delete SkillQuestion : {}", id);
        skillQuestionRepository.delete(id);
        skillQuestionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/skill-questions?query=:query : search for the skillQuestion corresponding
     * to the query.
     *
     * @param query the query of the skillQuestion search
     * @return the result of the search
     */
    @GetMapping("/_search/skill-questions")
    @Timed
    public List<SkillQuestion> searchSkillQuestions(@RequestParam String query) {
        log.debug("REST request to search SkillQuestions for query {}", query);
        return StreamSupport
            .stream(skillQuestionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
