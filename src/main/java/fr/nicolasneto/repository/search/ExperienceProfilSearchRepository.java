package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.ExperienceProfil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ExperienceProfil entity.
 */
public interface ExperienceProfilSearchRepository extends ElasticsearchRepository<ExperienceProfil, Long> {
}
