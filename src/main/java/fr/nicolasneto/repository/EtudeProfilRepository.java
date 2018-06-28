package fr.nicolasneto.repository;

import fr.nicolasneto.domain.EtudeProfil;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EtudeProfil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtudeProfilRepository extends JpaRepository<EtudeProfil, Long> {

}
