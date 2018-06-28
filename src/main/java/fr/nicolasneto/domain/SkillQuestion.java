package fr.nicolasneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SkillQuestion.
 */
@Entity
@Table(name = "skill_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "skillquestion")
public class SkillQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "question_skill_question", nullable = false)
    private String questionSkillQuestion;

    @NotNull
    @Column(name = "response_skill_question", nullable = false)
    private String responseSkillQuestion;

    @ManyToOne(optional = false)
    @NotNull
    private SkillTest question;

    @ManyToOne(optional = false)
    @NotNull
    private Skill skill;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionSkillQuestion() {
        return questionSkillQuestion;
    }

    public SkillQuestion questionSkillQuestion(String questionSkillQuestion) {
        this.questionSkillQuestion = questionSkillQuestion;
        return this;
    }

    public void setQuestionSkillQuestion(String questionSkillQuestion) {
        this.questionSkillQuestion = questionSkillQuestion;
    }

    public String getResponseSkillQuestion() {
        return responseSkillQuestion;
    }

    public SkillQuestion responseSkillQuestion(String responseSkillQuestion) {
        this.responseSkillQuestion = responseSkillQuestion;
        return this;
    }

    public void setResponseSkillQuestion(String responseSkillQuestion) {
        this.responseSkillQuestion = responseSkillQuestion;
    }

    public SkillTest getQuestion() {
        return question;
    }

    public SkillQuestion question(SkillTest skillTest) {
        this.question = skillTest;
        return this;
    }

    public void setQuestion(SkillTest skillTest) {
        this.question = skillTest;
    }

    public Skill getSkill() {
        return skill;
    }

    public SkillQuestion skill(Skill skill) {
        this.skill = skill;
        return this;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
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
        SkillQuestion skillQuestion = (SkillQuestion) o;
        if (skillQuestion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillQuestion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillQuestion{" +
            "id=" + getId() +
            ", questionSkillQuestion='" + getQuestionSkillQuestion() + "'" +
            ", responseSkillQuestion='" + getResponseSkillQuestion() + "'" +
            "}";
    }
}
