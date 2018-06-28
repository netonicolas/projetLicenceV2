package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.JobResponse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the JobResponse entity.
 */
public interface JobResponseSearchRepository extends ElasticsearchRepository<JobResponse, Long> {
}
