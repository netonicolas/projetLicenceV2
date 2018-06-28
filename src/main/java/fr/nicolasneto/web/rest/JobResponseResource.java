package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.JobResponse;

import fr.nicolasneto.repository.JobResponseRepository;
import fr.nicolasneto.repository.search.JobResponseSearchRepository;
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
 * REST controller for managing JobResponse.
 */
@RestController
@RequestMapping("/api")
public class JobResponseResource {

    private final Logger log = LoggerFactory.getLogger(JobResponseResource.class);

    private static final String ENTITY_NAME = "jobResponse";

    private final JobResponseRepository jobResponseRepository;

    private final JobResponseSearchRepository jobResponseSearchRepository;

    public JobResponseResource(JobResponseRepository jobResponseRepository, JobResponseSearchRepository jobResponseSearchRepository) {
        this.jobResponseRepository = jobResponseRepository;
        this.jobResponseSearchRepository = jobResponseSearchRepository;
    }

    /**
     * POST  /job-responses : Create a new jobResponse.
     *
     * @param jobResponse the jobResponse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobResponse, or with status 400 (Bad Request) if the jobResponse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-responses")
    @Timed
    public ResponseEntity<JobResponse> createJobResponse(@Valid @RequestBody JobResponse jobResponse) throws URISyntaxException {
        log.debug("REST request to save JobResponse : {}", jobResponse);
        if (jobResponse.getId() != null) {
            throw new BadRequestAlertException("A new jobResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobResponse result = jobResponseRepository.save(jobResponse);
        jobResponseSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/job-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-responses : Updates an existing jobResponse.
     *
     * @param jobResponse the jobResponse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobResponse,
     * or with status 400 (Bad Request) if the jobResponse is not valid,
     * or with status 500 (Internal Server Error) if the jobResponse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-responses")
    @Timed
    public ResponseEntity<JobResponse> updateJobResponse(@Valid @RequestBody JobResponse jobResponse) throws URISyntaxException {
        log.debug("REST request to update JobResponse : {}", jobResponse);
        if (jobResponse.getId() == null) {
            return createJobResponse(jobResponse);
        }
        JobResponse result = jobResponseRepository.save(jobResponse);
        jobResponseSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobResponse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-responses : get all the jobResponses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobResponses in body
     */
    @GetMapping("/job-responses")
    @Timed
    public List<JobResponse> getAllJobResponses() {
        log.debug("REST request to get all JobResponses");
        return jobResponseRepository.findAll();
        }

    /**
     * GET  /job-responses/:id : get the "id" jobResponse.
     *
     * @param id the id of the jobResponse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobResponse, or with status 404 (Not Found)
     */
    @GetMapping("/job-responses/{id}")
    @Timed
    public ResponseEntity<JobResponse> getJobResponse(@PathVariable Long id) {
        log.debug("REST request to get JobResponse : {}", id);
        JobResponse jobResponse = jobResponseRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobResponse));
    }

    /**
     * DELETE  /job-responses/:id : delete the "id" jobResponse.
     *
     * @param id the id of the jobResponse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-responses/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobResponse(@PathVariable Long id) {
        log.debug("REST request to delete JobResponse : {}", id);
        jobResponseRepository.delete(id);
        jobResponseSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-responses?query=:query : search for the jobResponse corresponding
     * to the query.
     *
     * @param query the query of the jobResponse search
     * @return the result of the search
     */
    @GetMapping("/_search/job-responses")
    @Timed
    public List<JobResponse> searchJobResponses(@RequestParam String query) {
        log.debug("REST request to search JobResponses for query {}", query);
        return StreamSupport
            .stream(jobResponseSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @GetMapping("/company-offer-job-responses/{jobId}")
    @Timed
    public List<JobResponse> getAllJobResponsesByJobId(@PathVariable Long jobId) {
        log.debug("REST request to get all JobResponses By Job");
        return jobResponseRepository.findByJobId(jobId);
    }

    @GetMapping("/user-offer-job-responses/{userId}")
    @Timed
    public List<JobResponse> getAllJobResponsesByUserId(@PathVariable Long userId) {
        log.debug("REST request to get all JobResponses By Job");
        return jobResponseRepository.findByUserId(userId);
    }
}
