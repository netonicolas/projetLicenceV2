package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.SkillTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SkillTest entity.
 */
public interface SkillTestSearchRepository extends ElasticsearchRepository<SkillTest, Long> {
}
