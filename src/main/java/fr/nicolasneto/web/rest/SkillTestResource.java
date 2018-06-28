package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.SkillTest;

import fr.nicolasneto.repository.SkillTestRepository;
import fr.nicolasneto.repository.search.SkillTestSearchRepository;
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
 * REST controller for managing SkillTest.
 */
@RestController
@RequestMapping("/api")
public class SkillTestResource {

    private final Logger log = LoggerFactory.getLogger(SkillTestResource.class);

    private static final String ENTITY_NAME = "skillTest";

    private final SkillTestRepository skillTestRepository;

    private final SkillTestSearchRepository skillTestSearchRepository;

    public SkillTestResource(SkillTestRepository skillTestRepository, SkillTestSearchRepository skillTestSearchRepository) {
        this.skillTestRepository = skillTestRepository;
        this.skillTestSearchRepository = skillTestSearchRepository;
    }

    /**
     * POST  /skill-tests : Create a new skillTest.
     *
     * @param skillTest the skillTest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new skillTest, or with status 400 (Bad Request) if the skillTest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/skill-tests")
    @Timed
    public ResponseEntity<SkillTest> createSkillTest(@Valid @RequestBody SkillTest skillTest) throws URISyntaxException {
        log.debug("REST request to save SkillTest : {}", skillTest);
        if (skillTest.getId() != null) {
            throw new BadRequestAlertException("A new skillTest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillTest result = skillTestRepository.save(skillTest);
        skillTestSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/skill-tests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skill-tests : Updates an existing skillTest.
     *
     * @param skillTest the skillTest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated skillTest,
     * or with status 400 (Bad Request) if the skillTest is not valid,
     * or with status 500 (Internal Server Error) if the skillTest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/skill-tests")
    @Timed
    public ResponseEntity<SkillTest> updateSkillTest(@Valid @RequestBody SkillTest skillTest) throws URISyntaxException {
        log.debug("REST request to update SkillTest : {}", skillTest);
        if (skillTest.getId() == null) {
            return createSkillTest(skillTest);
        }
        SkillTest result = skillTestRepository.save(skillTest);
        skillTestSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, skillTest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skill-tests : get all the skillTests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of skillTests in body
     */
    @GetMapping("/skill-tests")
    @Timed
    public List<SkillTest> getAllSkillTests() {
        log.debug("REST request to get all SkillTests");
        return skillTestRepository.findAll();
        }

    /**
     * GET  /skill-tests/:id : get the "id" skillTest.
     *
     * @param id the id of the skillTest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the skillTest, or with status 404 (Not Found)
     */
    @GetMapping("/skill-tests/{id}")
    @Timed
    public ResponseEntity<SkillTest> getSkillTest(@PathVariable Long id) {
        log.debug("REST request to get SkillTest : {}", id);
        SkillTest skillTest = skillTestRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(skillTest));
    }

    /**
     * DELETE  /skill-tests/:id : delete the "id" skillTest.
     *
     * @param id the id of the skillTest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/skill-tests/{id}")
    @Timed
    public ResponseEntity<Void> deleteSkillTest(@PathVariable Long id) {
        log.debug("REST request to delete SkillTest : {}", id);
        skillTestRepository.delete(id);
        skillTestSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/skill-tests?query=:query : search for the skillTest corresponding
     * to the query.
     *
     * @param query the query of the skillTest search
     * @return the result of the search
     */
    @GetMapping("/_search/skill-tests")
    @Timed
    public List<SkillTest> searchSkillTests(@RequestParam String query) {
        log.debug("REST request to search SkillTests for query {}", query);
        return StreamSupport
            .stream(skillTestSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
