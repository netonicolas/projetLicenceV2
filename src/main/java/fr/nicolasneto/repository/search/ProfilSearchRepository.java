package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.Profil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Profil entity.
 */
public interface ProfilSearchRepository extends ElasticsearchRepository<Profil, Long> {
}
