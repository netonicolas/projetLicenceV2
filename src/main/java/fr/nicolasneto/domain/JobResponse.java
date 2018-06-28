package fr.nicolasneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A JobResponse.
 */
@Entity
@Table(name = "job_response")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobresponse")
public class JobResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_comment", nullable = false)
    private String comment;

    @Column(name = "date_response")
    private String dateResponse;

    @ManyToOne(optional = false)
    private Profil candidat;

    @ManyToOne(optional = false)
    private JobOffer jobOffer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public JobResponse comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateResponse() {
        return dateResponse;
    }

    public JobResponse dateResponse(String dateResponse) {
        this.dateResponse = dateResponse;
        return this;
    }

    public void setDateResponse(String dateResponse) {
        this.dateResponse = dateResponse;
    }

    public Profil getCandidat() {
        return candidat;
    }

    public JobResponse candidat(Profil profil) {
        this.candidat = profil;
        return this;
    }

    public void setCandidat(Profil profil) {
        this.candidat = profil;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public JobResponse jobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
        return this;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobResponse jobResponse = (JobResponse) o;
        if (jobResponse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobResponse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobResponse{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", dateResponse='" + getDateResponse() + "'" +
            "}";
    }
}
