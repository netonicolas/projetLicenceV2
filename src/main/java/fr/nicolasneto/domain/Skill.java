package fr.nicolasneto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Skill.
 */
@Entity
@Table(name = "skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "skill")
public class Skill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name_skill", nullable = false)
    private String nameSkill;

    @OneToMany(mappedBy = "skill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobSkill> skilljobSkills = new HashSet<>();

    @OneToMany(mappedBy = "idSkill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CandidateSkill> skillcandidateSkills = new HashSet<>();

    @OneToMany(mappedBy = "idSkill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CandidateTrueSkill> skillcandidateTrueSkills = new HashSet<>();

    @OneToMany(mappedBy = "skill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillQuestion> skillskillQuestions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameSkill() {
        return nameSkill;
    }

    public Skill nameSkill(String nameSkill) {
        this.nameSkill = nameSkill;
        return this;
    }

    public void setNameSkill(String nameSkill) {
        this.nameSkill = nameSkill;
    }

    public Set<JobSkill> getSkilljobSkills() {
        return skilljobSkills;
    }

    public Skill skilljobSkills(Set<JobSkill> jobSkills) {
        this.skilljobSkills = jobSkills;
        return this;
    }

    public Skill addSkilljobSkill(JobSkill jobSkill) {
        this.skilljobSkills.add(jobSkill);
        jobSkill.setSkill(this);
        return this;
    }

    public Skill removeSkilljobSkill(JobSkill jobSkill) {
        this.skilljobSkills.remove(jobSkill);
        jobSkill.setSkill(null);
        return this;
    }

    public void setSkilljobSkills(Set<JobSkill> jobSkills) {
        this.skilljobSkills = jobSkills;
    }

    public Set<CandidateSkill> getSkillcandidateSkills() {
        return skillcandidateSkills;
    }

    public Skill skillcandidateSkills(Set<CandidateSkill> candidateSkills) {
        this.skillcandidateSkills = candidateSkills;
        return this;
    }

    public Skill addSkillcandidateSkill(CandidateSkill candidateSkill) {
        this.skillcandidateSkills.add(candidateSkill);
        candidateSkill.setIdSkill(this);
        return this;
    }

    public Skill removeSkillcandidateSkill(CandidateSkill candidateSkill) {
        this.skillcandidateSkills.remove(candidateSkill);
        candidateSkill.setIdSkill(null);
        return this;
    }

    public void setSkillcandidateSkills(Set<CandidateSkill> candidateSkills) {
        this.skillcandidateSkills = candidateSkills;
    }

    public Set<CandidateTrueSkill> getSkillcandidateTrueSkills() {
        return skillcandidateTrueSkills;
    }

    public Skill skillcandidateTrueSkills(Set<CandidateTrueSkill> candidateTrueSkills) {
        this.skillcandidateTrueSkills = candidateTrueSkills;
        return this;
    }

    public Skill addSkillcandidateTrueSkill(CandidateTrueSkill candidateTrueSkill) {
        this.skillcandidateTrueSkills.add(candidateTrueSkill);
        candidateTrueSkill.setIdSkill(this);
        return this;
    }

    public Skill removeSkillcandidateTrueSkill(CandidateTrueSkill candidateTrueSkill) {
        this.skillcandidateTrueSkills.remove(candidateTrueSkill);
        candidateTrueSkill.setIdSkill(null);
        return this;
    }

    public void setSkillcandidateTrueSkills(Set<CandidateTrueSkill> candidateTrueSkills) {
        this.skillcandidateTrueSkills = candidateTrueSkills;
    }

    public Set<SkillQuestion> getSkillskillQuestions() {
        return skillskillQuestions;
    }

    public Skill skillskillQuestions(Set<SkillQuestion> skillQuestions) {
        this.skillskillQuestions = skillQuestions;
        return this;
    }

    public Skill addSkillskillQuestion(SkillQuestion skillQuestion) {
        this.skillskillQuestions.add(skillQuestion);
        skillQuestion.setSkill(this);
        return this;
    }

    public Skill removeSkillskillQuestion(SkillQuestion skillQuestion) {
        this.skillskillQuestions.remove(skillQuestion);
        skillQuestion.setSkill(null);
        return this;
    }

    public void setSkillskillQuestions(Set<SkillQuestion> skillQuestions) {
        this.skillskillQuestions = skillQuestions;
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
        Skill skill = (Skill) o;
        if (skill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Skill{" +
            "id=" + getId() +
            ", nameSkill='" + getNameSkill() + "'" +
            "}";
    }
}
