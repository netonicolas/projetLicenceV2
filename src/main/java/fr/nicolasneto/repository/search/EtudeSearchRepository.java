package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.Etude;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Etude entity.
 */
public interface EtudeSearchRepository extends ElasticsearchRepository<Etude, Long> {
}
