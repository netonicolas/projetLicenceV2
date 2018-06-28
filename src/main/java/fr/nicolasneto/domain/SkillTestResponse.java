package fr.nicolasneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SkillTestResponse.
 */
@Entity
@Table(name = "skill_test_response")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "skilltestresponse")
public class SkillTestResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "response_skill_test_response", nullable = false)
    private String responseSkillTestResponse;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    private SkillTest skillTest;

    @ManyToOne(optional = false)
    @NotNull
    private Profil candidat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseSkillTestResponse() {
        return responseSkillTestResponse;
    }

    public SkillTestResponse responseSkillTestResponse(String responseSkillTestResponse) {
        this.responseSkillTestResponse = responseSkillTestResponse;
        return this;
    }

    public void setResponseSkillTestResponse(String responseSkillTestResponse) {
        this.responseSkillTestResponse = responseSkillTestResponse;
    }

    public LocalDate getDate() {
        return date;
    }

    public SkillTestResponse date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public SkillTest getSkillTest() {
        return skillTest;
    }

    public SkillTestResponse skillTest(SkillTest skillTest) {
        this.skillTest = skillTest;
        return this;
    }

    public void setSkillTest(SkillTest skillTest) {
        this.skillTest = skillTest;
    }

    public Profil getCandidat() {
        return candidat;
    }

    public SkillTestResponse candidat(Profil profil) {
        this.candidat = profil;
        return this;
    }

    public void setCandidat(Profil profil) {
        this.candidat = profil;
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
        SkillTestResponse skillTestResponse = (SkillTestResponse) o;
        if (skillTestResponse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillTestResponse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillTestResponse{" +
            "id=" + getId() +
            ", responseSkillTestResponse='" + getResponseSkillTestResponse() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
