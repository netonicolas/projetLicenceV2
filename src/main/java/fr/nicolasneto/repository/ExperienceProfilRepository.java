package fr.nicolasneto.repository;

import fr.nicolasneto.domain.ExperienceProfil;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExperienceProfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExperienceProfilRepository extends JpaRepository<ExperienceProfil, Long> {

}
