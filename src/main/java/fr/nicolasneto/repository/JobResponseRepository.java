package fr.nicolasneto.repository;

import fr.nicolasneto.domain.JobResponse;
import fr.nicolasneto.domain.JobSkill;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the JobResponse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobResponseRepository extends JpaRepository<JobResponse, Long> {
    @Query("select jobResponse from JobResponse jobResponse where jobResponse.jobOffer.id= :jobId ")
    List<JobResponse> findByJobId(@Param("jobId")Long i);

    @Query("select jobResponse from JobResponse jobResponse where jobResponse.candidat.id= :userId ")
    List<JobResponse> findByUserId(@Param("userId")Long i);

}
