package fr.nicolasneto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SkillTest.
 */
@Entity
@Table(name = "skill_test")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "skilltest")
public class SkillTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "skillTest")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillTestResponse> skillTestskillTestResponses = new HashSet<>();

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillQuestion> skillTestSkillQuestions = new HashSet<>();

    @ManyToOne
    private JobOffer jobOffer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public SkillTest date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<SkillTestResponse> getSkillTestskillTestResponses() {
        return skillTestskillTestResponses;
    }

    public SkillTest skillTestskillTestResponses(Set<SkillTestResponse> skillTestResponses) {
        this.skillTestskillTestResponses = skillTestResponses;
        return this;
    }

    public SkillTest addSkillTestskillTestResponse(SkillTestResponse skillTestResponse) {
        this.skillTestskillTestResponses.add(skillTestResponse);
        skillTestResponse.setSkillTest(this);
        return this;
    }

    public SkillTest removeSkillTestskillTestResponse(SkillTestResponse skillTestResponse) {
        this.skillTestskillTestResponses.remove(skillTestResponse);
        skillTestResponse.setSkillTest(null);
        return this;
    }

    public void setSkillTestskillTestResponses(Set<SkillTestResponse> skillTestResponses) {
        this.skillTestskillTestResponses = skillTestResponses;
    }

    public Set<SkillQuestion> getSkillTestSkillQuestions() {
        return skillTestSkillQuestions;
    }

    public SkillTest skillTestSkillQuestions(Set<SkillQuestion> skillQuestions) {
        this.skillTestSkillQuestions = skillQuestions;
        return this;
    }

    public SkillTest addSkillTestSkillQuestion(SkillQuestion skillQuestion) {
        this.skillTestSkillQuestions.add(skillQuestion);
        skillQuestion.setQuestion(this);
        return this;
    }

    public SkillTest removeSkillTestSkillQuestion(SkillQuestion skillQuestion) {
        this.skillTestSkillQuestions.remove(skillQuestion);
        skillQuestion.setQuestion(null);
        return this;
    }

    public void setSkillTestSkillQuestions(Set<SkillQuestion> skillQuestions) {
        this.skillTestSkillQuestions = skillQuestions;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public SkillTest jobOffer(JobOffer jobOffer) {
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
        SkillTest skillTest = (SkillTest) o;
        if (skillTest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillTest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillTest{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
