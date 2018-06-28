package fr.nicolasneto.repository;

import fr.nicolasneto.domain.JobOffer;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the JobOffer entity.
 */


@SuppressWarnings("unused")
@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {

    @Query(value = "select * from job_offer where id NOT IN (select id from job_offer LIMIT :Limite)  LIMIT :Limite2",nativeQuery = true)
    List<JobOffer> findAllLimit(@Param("Limite")Long i,@Param("Limite2") Long i2);
    @Query(value = "select * from job_offer where company_id= :idCompany",nativeQuery = true)
    List<JobOffer> findByCompanyLimit(@Param("idCompany") Long idCompany);

}
