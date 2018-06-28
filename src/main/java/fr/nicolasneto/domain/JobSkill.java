package fr.nicolasneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A JobSkill.
 */
@Entity
@Table(name = "job_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "jobskill")
public class JobSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_level", nullable = false)
    private Long level;

    @NotNull
    @Column(name = "jhi_optimal", nullable = false)
    private Boolean optimal;

    @NotNull
    @Column(name = "weight", nullable = false)
    private Long weight;

    @Column(name = "comment_job_skill")
    private String commentJobSkill;

    @ManyToOne(optional = false)
    @NotNull
    private Skill skill;

    @ManyToOne(optional = false)
    @NotNull
    private JobOffer job;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLevel() {
        return level;
    }

    public JobSkill level(Long level) {
        this.level = level;
        return this;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Boolean isOptimal() {
        return optimal;
    }

    public JobSkill optimal(Boolean optimal) {
        this.optimal = optimal;
        return this;
    }

    public void setOptimal(Boolean optimal) {
        this.optimal = optimal;
    }

    public Long getWeight() {
        return weight;
    }

    public JobSkill weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getCommentJobSkill() {
        return commentJobSkill;
    }

    public JobSkill commentJobSkill(String commentJobSkill) {
        this.commentJobSkill = commentJobSkill;
        return this;
    }

    public void setCommentJobSkill(String commentJobSkill) {
        this.commentJobSkill = commentJobSkill;
    }

    public Skill getSkill() {
        return skill;
    }

    public JobSkill skill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public JobOffer getJob() {
        return job;
    }

    public JobSkill job(JobOffer jobOffer) {
        this.job = jobOffer;
        return this;
    }

    public void setJob(JobOffer jobOffer) {
        this.job = jobOffer;
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
        JobSkill jobSkill = (JobSkill) o;
        if (jobSkill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobSkill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobSkill{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", optimal='" + isOptimal() + "'" +
            ", weight=" + getWeight() +
            ", commentJobSkill='" + getCommentJobSkill() + "'" +
            "}";
    }
}
