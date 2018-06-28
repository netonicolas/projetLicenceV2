package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.EtudeProfil;

import fr.nicolasneto.repository.EtudeProfilRepository;
import fr.nicolasneto.repository.search.EtudeProfilSearchRepository;
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
 * REST controller for managing EtudeProfil.
 */
@RestController
@RequestMapping("/api")
public class EtudeProfilResource {

    private final Logger log = LoggerFactory.getLogger(EtudeProfilResource.class);

    private static final String ENTITY_NAME = "etudeProfil";

    private final EtudeProfilRepository etudeProfilRepository;

    private final EtudeProfilSearchRepository etudeProfilSearchRepository;

    public EtudeProfilResource(EtudeProfilRepository etudeProfilRepository, EtudeProfilSearchRepository etudeProfilSearchRepository) {
        this.etudeProfilRepository = etudeProfilRepository;
        this.etudeProfilSearchRepository = etudeProfilSearchRepository;
    }

    /**
     * POST  /etude-profils : Create a new etudeProfil.
     *
     * @param etudeProfil the etudeProfil to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etudeProfil, or with status 400 (Bad Request) if the etudeProfil has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etude-profils")
    @Timed
    public ResponseEntity<EtudeProfil> createEtudeProfil(@RequestBody EtudeProfil etudeProfil) throws URISyntaxException {
        log.debug("REST request to save EtudeProfil : {}", etudeProfil);
        if (etudeProfil.getId() != null) {
            throw new BadRequestAlertException("A new etudeProfil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtudeProfil result = etudeProfilRepository.save(etudeProfil);
        etudeProfilSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/etude-profils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etude-profils : Updates an existing etudeProfil.
     *
     * @param etudeProfil the etudeProfil to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etudeProfil,
     * or with status 400 (Bad Request) if the etudeProfil is not valid,
     * or with status 500 (Internal Server Error) if the etudeProfil couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etude-profils")
    @Timed
    public ResponseEntity<EtudeProfil> updateEtudeProfil(@RequestBody EtudeProfil etudeProfil) throws URISyntaxException {
        log.debug("REST request to update EtudeProfil : {}", etudeProfil);
        if (etudeProfil.getId() == null) {
            return createEtudeProfil(etudeProfil);
        }
        EtudeProfil result = etudeProfilRepository.save(etudeProfil);
        etudeProfilSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etudeProfil.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etude-profils : get all the etudeProfils.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of etudeProfils in body
     */
    @GetMapping("/etude-profils")
    @Timed
    public List<EtudeProfil> getAllEtudeProfils() {
        log.debug("REST request to get all EtudeProfils");
        return etudeProfilRepository.findAll();
        }

    /**
     * GET  /etude-profils/:id : get the "id" etudeProfil.
     *
     * @param id the id of the etudeProfil to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etudeProfil, or with status 404 (Not Found)
     */
    @GetMapping("/etude-profils/{id}")
    @Timed
    public ResponseEntity<EtudeProfil> getEtudeProfil(@PathVariable Long id) {
        log.debug("REST request to get EtudeProfil : {}", id);
        EtudeProfil etudeProfil = etudeProfilRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(etudeProfil));
    }

    /**
     * DELETE  /etude-profils/:id : delete the "id" etudeProfil.
     *
     * @param id the id of the etudeProfil to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etude-profils/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtudeProfil(@PathVariable Long id) {
        log.debug("REST request to delete EtudeProfil : {}", id);
        etudeProfilRepository.delete(id);
        etudeProfilSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/etude-profils?query=:query : search for the etudeProfil corresponding
     * to the query.
     *
     * @param query the query of the etudeProfil search
     * @return the result of the search
     */
    @GetMapping("/_search/etude-profils")
    @Timed
    public List<EtudeProfil> searchEtudeProfils(@RequestParam String query) {
        log.debug("REST request to search EtudeProfils for query {}", query);
        return StreamSupport
            .stream(etudeProfilSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
