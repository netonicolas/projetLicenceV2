package fr.nicolasneto.repository.search;

import fr.nicolasneto.domain.EtudeProfil;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EtudeProfil entity.
 */
public interface EtudeProfilSearchRepository extends ElasticsearchRepository<EtudeProfil, Long> {
}
