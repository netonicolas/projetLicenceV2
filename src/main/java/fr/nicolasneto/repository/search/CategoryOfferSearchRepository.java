package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.CategoryOffer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CategoryOffer entity.
 */
public interface CategoryOfferSearchRepository extends ElasticsearchRepository<CategoryOffer, Long> {
}
