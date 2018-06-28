package fr.nicolasneto.repository;

import fr.nicolasneto.domain.CandidateSkill;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {

}
