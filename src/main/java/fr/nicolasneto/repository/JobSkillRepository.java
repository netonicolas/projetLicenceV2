package fr.nicolasneto.repository;

import fr.nicolasneto.domain.JobSkill;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the JobSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
    @Query("select jobSkill from JobSkill jobSkill where jobSkill.job.id= :jobId ")
    List<JobSkill> findByJobId(@Param("jobId")Long i);
}
