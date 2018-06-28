package fr.nicolasneto.repository;

import fr.nicolasneto.domain.JobSkill;
import fr.nicolasneto.domain.Profil;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Profil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {

    @Query("select p from Profil p where p.userId= :userId ")
    Profil getByUserId(@Param("userId")Long userId);

}
