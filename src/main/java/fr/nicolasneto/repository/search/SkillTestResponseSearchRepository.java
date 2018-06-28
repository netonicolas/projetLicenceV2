package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.SkillTestResponse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SkillTestResponse entity.
 */
public interface SkillTestResponseSearchRepository extends ElasticsearchRepository<SkillTestResponse, Long> {
}
