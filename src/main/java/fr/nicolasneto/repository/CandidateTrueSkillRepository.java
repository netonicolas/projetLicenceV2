package fr.nicolasneto.repository;

import fr.nicolasneto.domain.CandidateTrueSkill;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateTrueSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateTrueSkillRepository extends JpaRepository<CandidateTrueSkill, Long> {

}
