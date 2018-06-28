package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.SkillQuestion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SkillQuestion entity.
 */
public interface SkillQuestionSearchRepository extends ElasticsearchRepository<SkillQuestion, Long> {
}
