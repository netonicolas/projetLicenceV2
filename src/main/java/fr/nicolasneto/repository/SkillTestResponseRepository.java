package fr.nicolasneto.repository;

import fr.nicolasneto.domain.SkillTestResponse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SkillTestResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillTestResponseRepository extends JpaRepository<SkillTestResponse, Long> {

}
