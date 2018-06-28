package fr.nicolasneto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.nicolasneto.domain.enumeration.NiveauEtude;

/**
 * A Etude.
 */
@Entity
@Table(name = "etude")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "etude")
public class Etude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude")
    private NiveauEtude niveauEtude;

    @OneToMany(mappedBy = "etude")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobOffer> etudeJobOffers = new HashSet<>();

    @OneToMany(mappedBy = "idEtude")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EtudeProfil> etudeetudeProfils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Etude libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public Etude niveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Set<JobOffer> getEtudeJobOffers() {
        return etudeJobOffers;
    }

    public Etude etudeJobOffers(Set<JobOffer> jobOffers) {
        this.etudeJobOffers = jobOffers;
        return this;
    }

    public Etude addEtudeJobOffer(JobOffer jobOffer) {
        this.etudeJobOffers.add(jobOffer);
        jobOffer.setEtude(this);
        return this;
    }

    public Etude removeEtudeJobOffer(JobOffer jobOffer) {
        this.etudeJobOffers.remove(jobOffer);
        jobOffer.setEtude(null);
        return this;
    }

    public void setEtudeJobOffers(Set<JobOffer> jobOffers) {
        this.etudeJobOffers = jobOffers;
    }

    public Set<EtudeProfil> getEtudeetudeProfils() {
        return etudeetudeProfils;
    }

    public Etude etudeetudeProfils(Set<EtudeProfil> etudeProfils) {
        this.etudeetudeProfils = etudeProfils;
        return this;
    }

    public Etude addEtudeetudeProfil(EtudeProfil etudeProfil) {
        this.etudeetudeProfils.add(etudeProfil);
        etudeProfil.setIdEtude(this);
        return this;
    }

    public Etude removeEtudeetudeProfil(EtudeProfil etudeProfil) {
        this.etudeetudeProfils.remove(etudeProfil);
        etudeProfil.setIdEtude(null);
        return this;
    }

    public void setEtudeetudeProfils(Set<EtudeProfil> etudeProfils) {
        this.etudeetudeProfils = etudeProfils;
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
        Etude etude = (Etude) o;
        if (etude.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etude.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Etude{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            "}";
    }
}
