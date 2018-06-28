package fr.nicolasneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CandidateSkill.
 */
@Entity
@Table(name = "candidate_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "candidateskill")
public class CandidateSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_level")
    private Long level;

    @Column(name = "jhi_comment")
    private String comment;

    @ManyToOne
    private Profil idProfil;

    @ManyToOne
    private Skill idSkill;

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

    public CandidateSkill level(Long level) {
        this.level = level;
        return this;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getComment() {
        return comment;
    }

    public CandidateSkill comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Profil getIdProfil() {
        return idProfil;
    }

    public CandidateSkill idProfil(Profil profil) {
        this.idProfil = profil;
        return this;
    }

    public void setIdProfil(Profil profil) {
        this.idProfil = profil;
    }

    public Skill getIdSkill() {
        return idSkill;
    }

    public CandidateSkill idSkill(Skill skill) {
        this.idSkill = skill;
        return this;
    }

    public void setIdSkill(Skill skill) {
        this.idSkill = skill;
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
        CandidateSkill candidateSkill = (CandidateSkill) o;
        if (candidateSkill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidateSkill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidateSkill{" +
            "id=" + getId() +
            ", level=" + getLevel() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
