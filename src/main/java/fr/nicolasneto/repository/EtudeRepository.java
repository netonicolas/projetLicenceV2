package fr.nicolasneto.repository;

import fr.nicolasneto.domain.Etude;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Etude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtudeRepository extends JpaRepository<Etude, Long> {

}
