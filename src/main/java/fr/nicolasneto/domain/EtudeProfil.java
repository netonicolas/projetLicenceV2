package fr.nicolasneto.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A EtudeProfil.
 */
@Entity
@Table(name = "etude_profil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "etudeprofil")
public class EtudeProfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "annee_etude_debut")
    private LocalDate anneeEtudeDebut;

    @Column(name = "anne_etude_fin")
    private LocalDate anneEtudeFin;

    @Column(name = "jhi_comment")
    private String comment;

    @ManyToOne
    private Profil idProfil;

    @ManyToOne
    private Etude idEtude;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAnneeEtudeDebut() {
        return anneeEtudeDebut;
    }

    public EtudeProfil anneeEtudeDebut(LocalDate anneeEtudeDebut) {
        this.anneeEtudeDebut = anneeEtudeDebut;
        return this;
    }

    public void setAnneeEtudeDebut(LocalDate anneeEtudeDebut) {
        this.anneeEtudeDebut = anneeEtudeDebut;
    }

    public LocalDate getAnneEtudeFin() {
        return anneEtudeFin;
    }

    public EtudeProfil anneEtudeFin(LocalDate anneEtudeFin) {
        this.anneEtudeFin = anneEtudeFin;
        return this;
    }

    public void setAnneEtudeFin(LocalDate anneEtudeFin) {
        this.anneEtudeFin = anneEtudeFin;
    }

    public String getComment() {
        return comment;
    }

    public EtudeProfil comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Profil getIdProfil() {
        return idProfil;
    }

    public EtudeProfil idProfil(Profil profil) {
        this.idProfil = profil;
        return this;
    }

    public void setIdProfil(Profil profil) {
        this.idProfil = profil;
    }

    public Etude getIdEtude() {
        return idEtude;
    }

    public EtudeProfil idEtude(Etude etude) {
        this.idEtude = etude;
        return this;
    }

    public void setIdEtude(Etude etude) {
        this.idEtude = etude;
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
        EtudeProfil etudeProfil = (EtudeProfil) o;
        if (etudeProfil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etudeProfil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EtudeProfil{" +
            "id=" + getId() +
            ", anneeEtudeDebut='" + getAnneeEtudeDebut() + "'" +
            ", anneEtudeFin='" + getAnneEtudeFin() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}
