package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.CandidateTrueSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CandidateTrueSkill entity.
 */
public interface CandidateTrueSkillSearchRepository extends ElasticsearchRepository<CandidateTrueSkill, Long> {
}
