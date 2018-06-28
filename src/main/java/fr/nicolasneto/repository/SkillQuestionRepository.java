package fr.nicolasneto.repository;

import fr.nicolasneto.domain.SkillQuestion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SkillQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillQuestionRepository extends JpaRepository<SkillQuestion, Long> {

}
