package fr.nicolasneto.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.nicolasneto.domain.CategoryOffer;

import fr.nicolasneto.repository.CategoryOfferRepository;
import fr.nicolasneto.repository.search.CategoryOfferSearchRepository;
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
 * REST controller for managing CategoryOffer.
 */
@RestController
@RequestMapping("/api")
public class CategoryOfferResource {

    private final Logger log = LoggerFactory.getLogger(CategoryOfferResource.class);

    private static final String ENTITY_NAME = "categoryOffer";

    private final CategoryOfferRepository categoryOfferRepository;

    private final CategoryOfferSearchRepository categoryOfferSearchRepository;

    public CategoryOfferResource(CategoryOfferRepository categoryOfferRepository, CategoryOfferSearchRepository categoryOfferSearchRepository) {
        this.categoryOfferRepository = categoryOfferRepository;
        this.categoryOfferSearchRepository = categoryOfferSearchRepository;
    }

    /**
     * POST  /category-offers : Create a new categoryOffer.
     *
     * @param categoryOffer the categoryOffer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new categoryOffer, or with status 400 (Bad Request) if the categoryOffer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/category-offers")
    @Timed
    public ResponseEntity<CategoryOffer> createCategoryOffer(@Valid @RequestBody CategoryOffer categoryOffer) throws URISyntaxException {
        log.debug("REST request to save CategoryOffer : {}", categoryOffer);
        if (categoryOffer.getId() != null) {
            throw new BadRequestAlertException("A new categoryOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryOffer result = categoryOfferRepository.save(categoryOffer);
        categoryOfferSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/category-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /category-offers : Updates an existing categoryOffer.
     *
     * @param categoryOffer the categoryOffer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated categoryOffer,
     * or with status 400 (Bad Request) if the categoryOffer is not valid,
     * or with status 500 (Internal Server Error) if the categoryOffer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/category-offers")
    @Timed
    public ResponseEntity<CategoryOffer> updateCategoryOffer(@Valid @RequestBody CategoryOffer categoryOffer) throws URISyntaxException {
        log.debug("REST request to update CategoryOffer : {}", categoryOffer);
        if (categoryOffer.getId() == null) {
            return createCategoryOffer(categoryOffer);
        }
        CategoryOffer result = categoryOfferRepository.save(categoryOffer);
        categoryOfferSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, categoryOffer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /category-offers : get all the categoryOffers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of categoryOffers in body
     */
    @GetMapping("/category-offers")
    @Timed
    public List<CategoryOffer> getAllCategoryOffers() {
        log.debug("REST request to get all CategoryOffers");
        return categoryOfferRepository.findAll();
        }

    /**
     * GET  /category-offers/:id : get the "id" categoryOffer.
     *
     * @param id the id of the categoryOffer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the categoryOffer, or with status 404 (Not Found)
     */
    @GetMapping("/category-offers/{id}")
    @Timed
    public ResponseEntity<CategoryOffer> getCategoryOffer(@PathVariable Long id) {
        log.debug("REST request to get CategoryOffer : {}", id);
        CategoryOffer categoryOffer = categoryOfferRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(categoryOffer));
    }

    /**
     * DELETE  /category-offers/:id : delete the "id" categoryOffer.
     *
     * @param id the id of the categoryOffer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/category-offers/{id}")
    @Timed
    public ResponseEntity<Void> deleteCategoryOffer(@PathVariable Long id) {
        log.debug("REST request to delete CategoryOffer : {}", id);
        categoryOfferRepository.delete(id);
        categoryOfferSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/category-offers?query=:query : search for the categoryOffer corresponding
     * to the query.
     *
     * @param query the query of the categoryOffer search
     * @return the result of the search
     */
    @GetMapping("/_search/category-offers")
    @Timed
    public List<CategoryOffer> searchCategoryOffers(@RequestParam String query) {
        log.debug("REST request to search CategoryOffers for query {}", query);
        return StreamSupport
            .stream(categoryOfferSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
