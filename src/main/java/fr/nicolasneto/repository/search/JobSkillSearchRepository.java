package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.JobSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobSkill entity.
 */
public interface JobSkillSearchRepository extends ElasticsearchRepository<JobSkill, Long> {
}
