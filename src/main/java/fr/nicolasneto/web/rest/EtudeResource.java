package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.Etude;

import fr.nicolasneto.repository.EtudeRepository;
import fr.nicolasneto.repository.search.EtudeSearchRepository;
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
 * REST controller for managing Etude.
 */
@RestController
@RequestMapping("/api")
public class EtudeResource {

    private final Logger log = LoggerFactory.getLogger(EtudeResource.class);

    private static final String ENTITY_NAME = "etude";

    private final EtudeRepository etudeRepository;

    private final EtudeSearchRepository etudeSearchRepository;

    public EtudeResource(EtudeRepository etudeRepository, EtudeSearchRepository etudeSearchRepository) {
        this.etudeRepository = etudeRepository;
        this.etudeSearchRepository = etudeSearchRepository;
    }

    /**
     * POST  /etudes : Create a new etude.
     *
     * @param etude the etude to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etude, or with status 400 (Bad Request) if the etude has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etudes")
    @Timed
    public ResponseEntity<Etude> createEtude(@RequestBody Etude etude) throws URISyntaxException {
        log.debug("REST request to save Etude : {}", etude);
        if (etude.getId() != null) {
            throw new BadRequestAlertException("A new etude cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etude result = etudeRepository.save(etude);
        etudeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/etudes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etudes : Updates an existing etude.
     *
     * @param etude the etude to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etude,
     * or with status 400 (Bad Request) if the etude is not valid,
     * or with status 500 (Internal Server Error) if the etude couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etudes")
    @Timed
    public ResponseEntity<Etude> updateEtude(@RequestBody Etude etude) throws URISyntaxException {
        log.debug("REST request to update Etude : {}", etude);
        if (etude.getId() == null) {
            return createEtude(etude);
        }
        Etude result = etudeRepository.save(etude);
        etudeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etude.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etudes : get all the etudes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of etudes in body
     */
    @GetMapping("/etudes")
    @Timed
    public List<Etude> getAllEtudes() {
        log.debug("REST request to get all Etudes");
        return etudeRepository.findAll();
        }

    /**
     * GET  /etudes/:id : get the "id" etude.
     *
     * @param id the id of the etude to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etude, or with status 404 (Not Found)
     */
    @GetMapping("/etudes/{id}")
    @Timed
    public ResponseEntity<Etude> getEtude(@PathVariable Long id) {
        log.debug("REST request to get Etude : {}", id);
        Etude etude = etudeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(etude));
    }

    /**
     * DELETE  /etudes/:id : delete the "id" etude.
     *
     * @param id the id of the etude to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etudes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtude(@PathVariable Long id) {
        log.debug("REST request to delete Etude : {}", id);
        etudeRepository.delete(id);
        etudeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/etudes?query=:query : search for the etude corresponding
     * to the query.
     *
     * @param query the query of the etude search
     * @return the result of the search
     */
    @GetMapping("/_search/etudes")
    @Timed
    public List<Etude> searchEtudes(@RequestParam String query) {
        log.debug("REST request to search Etudes for query {}", query);
        return StreamSupport
            .stream(etudeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
