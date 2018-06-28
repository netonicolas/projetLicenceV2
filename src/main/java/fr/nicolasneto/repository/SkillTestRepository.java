package fr.nicolasneto.repository;

import fr.nicolasneto.domain.SkillTest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SkillTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillTestRepository extends JpaRepository<SkillTest, Long> {

}
