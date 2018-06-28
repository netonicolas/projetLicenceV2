package fr.nicolasneto.repository;

import fr.nicolasneto.domain.CategoryOffer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CategoryOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryOfferRepository extends JpaRepository<CategoryOffer, Long> {

}
