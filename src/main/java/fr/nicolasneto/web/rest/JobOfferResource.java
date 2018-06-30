package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.JobOffer;

import fr.nicolasneto.repository.JobOfferRepository;
import fr.nicolasneto.repository.search.JobOfferSearchRepository;
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
 * REST controller for managing JobOffer.
 */
@RestController
@RequestMapping("/api")
public class JobOfferResource {

    private final Logger log = LoggerFactory.getLogger(JobOfferResource.class);

    private static final String ENTITY_NAME = "jobOffer";

    private final JobOfferRepository jobOfferRepository;

    private final JobOfferSearchRepository jobOfferSearchRepository;

    public JobOfferResource(JobOfferRepository jobOfferRepository, JobOfferSearchRepository jobOfferSearchRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferSearchRepository = jobOfferSearchRepository;
    }

    /**
     * POST  /job-offers : Create a new jobOffer.
     *
     * @param jobOffer the jobOffer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobOffer, or with status 400 (Bad Request) if the jobOffer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-offers")
    @Timed
    public ResponseEntity<JobOffer> createJobOffer(@Valid @RequestBody JobOffer jobOffer) throws URISyntaxException {
        log.debug("REST request to save JobOffer : {}", jobOffer);
        if (jobOffer.getId() != null) {
            throw new BadRequestAlertException("A new jobOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobOffer result = jobOfferRepository.save(jobOffer);
        jobOfferSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/job-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-offers : Updates an existing jobOffer.
     *
     * @param jobOffer the jobOffer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobOffer,
     * or with status 400 (Bad Request) if the jobOffer is not valid,
     * or with status 500 (Internal Server Error) if the jobOffer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-offers")
    @Timed
    public ResponseEntity<JobOffer> updateJobOffer(@Valid @RequestBody JobOffer jobOffer) throws URISyntaxException {
        log.debug("REST request to update JobOffer : {}", jobOffer);
        if (jobOffer.getId() == null) {
            return createJobOffer(jobOffer);
        }
        JobOffer result = jobOfferRepository.save(jobOffer);
        jobOfferSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobOffer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-offers : get all the jobOffers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobOffers in body
     */
    @GetMapping("/job-offers")
    @Timed
    public List<JobOffer> getAllJobOffers() {
        log.debug("REST request to get all JobOffers");
        return jobOfferRepository.findAll();
        }

    /**
     * GET  /job-offers/:id : get the "id" jobOffer.
     *
     * @param id the id of the jobOffer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobOffer, or with status 404 (Not Found)
     */
    @GetMapping("/job-offers/{id}")
    @Timed
    public ResponseEntity<JobOffer> getJobOffer(@PathVariable Long id) {
        log.debug("REST request to get JobOffer : {}", id);
        JobOffer jobOffer = jobOfferRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobOffer));
    }

    /**
     * DELETE  /job-offers/:id : delete the "id" jobOffer.
     *
     * @param id the id of the jobOffer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-offers/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
        log.debug("REST request to delete JobOffer : {}", id);
        jobOfferRepository.delete(id);
        jobOfferSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-offers?query=:query : search for the jobOffer corresponding
     * to the query.
     *
     * @param query the query of the jobOffer search
     * @return the result of the search
     */
    @GetMapping("/_search/job-offers")
    @Timed
    public List<JobOffer> searchJobOffers(@RequestParam String query) {
        log.debug("REST request to search JobOffers for query {}", query);
        return StreamSupport
            .stream(jobOfferSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @GetMapping("/job-offers/{limite}/{page}")
    @Timed
    public List<JobOffer> getAllJobOffersLimit(@PathVariable Long limite,@PathVariable Long page ) {
        log.debug("REST request to get all JobOffers Limite");
        return jobOfferRepository.findAllLimit( limite*page,limite);
    }

    @GetMapping("/job-offers/count")
    @Timed
    public long getCountJobOffers( ) {
        log.debug("REST request to get count of all JobOffers");
        return jobOfferRepository.countJobOfferOpen();
    }

    @GetMapping("/job-offers-company/{company}")
    @Timed
    public List<JobOffer> getJobOffersByCompanyLimit(@PathVariable Long company ) {
        log.debug("REST request to get company JobOffers Limite");
        return jobOfferRepository.findByCompanyLimit(company);
    }



}
