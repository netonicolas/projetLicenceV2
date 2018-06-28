package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.JobSkill;

import fr.nicolasneto.repository.JobSkillRepository;
import fr.nicolasneto.repository.search.JobSkillSearchRepository;
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
 * REST controller for managing JobSkill.
 */
@RestController
@RequestMapping("/api")
public class JobSkillResource {

    private final Logger log = LoggerFactory.getLogger(JobSkillResource.class);

    private static final String ENTITY_NAME = "jobSkill";

    private final JobSkillRepository jobSkillRepository;

    private final JobSkillSearchRepository jobSkillSearchRepository;

    public JobSkillResource(JobSkillRepository jobSkillRepository, JobSkillSearchRepository jobSkillSearchRepository) {
        this.jobSkillRepository = jobSkillRepository;
        this.jobSkillSearchRepository = jobSkillSearchRepository;
    }

    /**
     * POST  /job-skills : Create a new jobSkill.
     *
     * @param jobSkill the jobSkill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobSkill, or with status 400 (Bad Request) if the jobSkill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-skills")
    @Timed
    public ResponseEntity<JobSkill> createJobSkill(@Valid @RequestBody JobSkill jobSkill) throws URISyntaxException {
        log.debug("REST request to save JobSkill : {}", jobSkill);
        if (jobSkill.getId() != null) {
            throw new BadRequestAlertException("A new jobSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobSkill result = jobSkillRepository.save(jobSkill);
        jobSkillSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/job-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-skills : Updates an existing jobSkill.
     *
     * @param jobSkill the jobSkill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobSkill,
     * or with status 400 (Bad Request) if the jobSkill is not valid,
     * or with status 500 (Internal Server Error) if the jobSkill couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-skills")
    @Timed
    public ResponseEntity<JobSkill> updateJobSkill(@Valid @RequestBody JobSkill jobSkill) throws URISyntaxException {
        log.debug("REST request to update JobSkill : {}", jobSkill);
        if (jobSkill.getId() == null) {
            return createJobSkill(jobSkill);
        }
        JobSkill result = jobSkillRepository.save(jobSkill);
        jobSkillSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobSkill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-skills : get all the jobSkills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobSkills in body
     */
    @GetMapping("/job-skills")
    @Timed
    public List<JobSkill> getAllJobSkills() {
        log.debug("REST request to get all JobSkills");
        return jobSkillRepository.findAll();
        }

    /**
     * GET  /job-skills/:id : get the "id" jobSkill.
     *
     * @param id the id of the jobSkill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobSkill, or with status 404 (Not Found)
     */
    @GetMapping("/job-skills/{id}")
    @Timed
    public ResponseEntity<JobSkill> getJobSkill(@PathVariable Long id) {
        log.debug("REST request to get JobSkill : {}", id);
        JobSkill jobSkill = jobSkillRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobSkill));
    }

    /**
     * DELETE  /job-skills/:id : delete the "id" jobSkill.
     *
     * @param id the id of the jobSkill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobSkill(@PathVariable Long id) {
        log.debug("REST request to delete JobSkill : {}", id);
        jobSkillRepository.delete(id);
        jobSkillSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-skills?query=:query : search for the jobSkill corresponding
     * to the query.
     *
     * @param query the query of the jobSkill search
     * @return the result of the search
     */
    @GetMapping("/_search/job-skills")
    @Timed
    public List<JobSkill> searchJobSkills(@RequestParam String query) {
        log.debug("REST request to search JobSkills for query {}", query);
        return StreamSupport
            .stream(jobSkillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @GetMapping("/job-skills/jobId/{id}")
    @Timed
    public List<JobSkill> getJobSkillJobId(@PathVariable Long id) {
        log.debug("REST request to get JobSkill JobId : {}", id);
        return  jobSkillRepository.findByJobId(id);
    }


}
