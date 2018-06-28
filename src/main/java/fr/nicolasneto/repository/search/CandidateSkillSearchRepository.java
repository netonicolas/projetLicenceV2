package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.CandidateSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CandidateSkill entity.
 */
public interface CandidateSkillSearchRepository extends ElasticsearchRepository<CandidateSkill, Long> {
}
