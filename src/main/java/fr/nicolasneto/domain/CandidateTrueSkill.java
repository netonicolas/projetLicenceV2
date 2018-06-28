package fr.nicolasneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CandidateTrueSkill.
 */
@Entity
@Table(name = "candidate_true_skill")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "candidatetrueskill")
public class CandidateTrueSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "candidateskillid", nullable = false)
    private Long candidateskillid;

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

    public Long getCandidateskillid() {
        return candidateskillid;
    }

    public CandidateTrueSkill candidateskillid(Long candidateskillid) {
        this.candidateskillid = candidateskillid;
        return this;
    }

    public void setCandidateskillid(Long candidateskillid) {
        this.candidateskillid = candidateskillid;
    }

    public Long getLevel() {
        return level;
    }

    public CandidateTrueSkill level(Long level) {
        this.level = level;
        return this;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getComment() {
        return comment;
    }

    public CandidateTrueSkill comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Profil getIdProfil() {
        return idProfil;
    }

    public CandidateTrueSkill idProfil(Profil profil) {
        this.idProfil = profil;
        return this;
    }

    public void setIdProfil(Profil profil) {
        this.idProfil = profil;
    }

    public Skill getIdSkill() {
        return idSkill;
    }

    public CandidateTrueSkill idSkill(Skill skill) {
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
        CandidateTrueSkill candidateTrueSkill = (CandidateTrueSkill) o;
        if (candidateTrueSkill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidateTrueSkill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidateTrueSkill{" +
            "id=" + getId() +
            ", candidateskillid=" + getCandidateskillid() +
            ", level=" + getLevel() +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
