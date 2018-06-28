package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.JobOffer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobOffer entity.
 */
public interface JobOfferSearchRepository extends ElasticsearchRepository<JobOffer, Long> {
}
