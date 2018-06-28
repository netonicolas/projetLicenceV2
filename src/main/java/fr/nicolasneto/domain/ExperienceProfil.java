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
 * A ExperienceProfil.
 */
@Entity
@Table(name = "experience_profil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "experienceprofil")
public class ExperienceProfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "anne_experience_debut", nullable = false)
    private LocalDate anneExperienceDebut;

    @NotNull
    @Column(name = "anne_experience_fin", nullable = false)
    private LocalDate anneExperienceFin;

    @Column(name = "jhi_comment")
    private String comment;

    @ManyToOne
    private Profil idProfil;

    @ManyToOne
    private Experience idExperience;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAnneExperienceDebut() {
        return anneExperienceDebut;
    }

    public ExperienceProfil anneExperienceDebut(LocalDate anneExperienceDebut) {
        this.anneExperienceDebut = anneExperienceDebut;
        return this;
    }

    public void setAnneExperienceDebut(LocalDate anneExperienceDebut) {
        this.anneExperienceDebut = anneExperienceDebut;
    }

    public LocalDate getAnneExperienceFin() {
        return anneExperienceFin;
    }

    public ExperienceProfil anneExperienceFin(LocalDate anneExperienceFin) {
        this.anneExperienceFin = anneExperienceFin;
        return this;
    }

    public void setAnneExperienceFin(LocalDate anneExperienceFin) {
        this.anneExperienceFin = anneExperienceFin;
    }

    public String getComment() {
        return comment;
    }

    public ExperienceProfil comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Profil getIdProfil() {
        return idProfil;
    }

    public ExperienceProfil idProfil(Profil profil) {
        this.idProfil = profil;
        return this;
    }

    public void setIdProfil(Profil profil) {
        this.idProfil = profil;
    }

    public Experience getIdExperience() {
        return idExperience;
    }

    public ExperienceProfil idExperience(Experience experience) {
        this.idExperience = experience;
        return this;
    }

    public void setIdExperience(Experience experience) {
        this.idExperience = experience;
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
        ExperienceProfil experienceProfil = (ExperienceProfil) o;
        if (experienceProfil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), experienceProfil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExperienceProfil{" +
            "id=" + getId() +
            ", anneExperienceDebut='" + getAnneExperienceDebut() + "'" +
            ", anneExperienceFin='" + getAnneExperienceFin() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
