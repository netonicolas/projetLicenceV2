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
 * A Profil.
 */
@Entity
@Table(name = "profil")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "profil")
public class Profil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "idProfil",cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CandidateSkill> profilcandidateSkills = new HashSet<>();

    @OneToMany(mappedBy = "idProfil",cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CandidateTrueSkill> profilcandidateTrueSkills = new HashSet<>();

    @OneToMany(mappedBy = "candidat",cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillTestResponse> candidatskillTestResponses = new HashSet<>();

    @OneToMany(mappedBy = "candidat",cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobResponse> candidatjobResponses = new HashSet<>();

    @OneToMany(mappedBy = "idProfil",cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EtudeProfil> profiletudeProfils = new HashSet<>();

    @OneToMany(mappedBy = "idProfil",cascade=CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExperienceProfil> profilexperienceProfils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public Profil userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<CandidateSkill> getProfilcandidateSkills() {
        return profilcandidateSkills;
    }

    public Profil profilcandidateSkills(Set<CandidateSkill> candidateSkills) {
        this.profilcandidateSkills = candidateSkills;
        return this;
    }

    public Profil addProfilcandidateSkill(CandidateSkill candidateSkill) {
        this.profilcandidateSkills.add(candidateSkill);
        candidateSkill.setIdProfil(this);
        return this;
    }

    public Profil removeProfilcandidateSkill(CandidateSkill candidateSkill) {
        this.profilcandidateSkills.remove(candidateSkill);
        candidateSkill.setIdProfil(null);
        return this;
    }

    public void setProfilcandidateSkills(Set<CandidateSkill> candidateSkills) {
        this.profilcandidateSkills = candidateSkills;
    }

    public Set<CandidateTrueSkill> getProfilcandidateTrueSkills() {
        return profilcandidateTrueSkills;
    }

    public Profil profilcandidateTrueSkills(Set<CandidateTrueSkill> candidateTrueSkills) {
        this.profilcandidateTrueSkills = candidateTrueSkills;
        return this;
    }

    public Profil addProfilcandidateTrueSkill(CandidateTrueSkill candidateTrueSkill) {
        this.profilcandidateTrueSkills.add(candidateTrueSkill);
        candidateTrueSkill.setIdProfil(this);
        return this;
    }

    public Profil removeProfilcandidateTrueSkill(CandidateTrueSkill candidateTrueSkill) {
        this.profilcandidateTrueSkills.remove(candidateTrueSkill);
        candidateTrueSkill.setIdProfil(null);
        return this;
    }

    public void setProfilcandidateTrueSkills(Set<CandidateTrueSkill> candidateTrueSkills) {
        this.profilcandidateTrueSkills = candidateTrueSkills;
    }

    public Set<SkillTestResponse> getCandidatskillTestResponses() {
        return candidatskillTestResponses;
    }

    public Profil candidatskillTestResponses(Set<SkillTestResponse> skillTestResponses) {
        this.candidatskillTestResponses = skillTestResponses;
        return this;
    }

    public Profil addCandidatskillTestResponse(SkillTestResponse skillTestResponse) {
        this.candidatskillTestResponses.add(skillTestResponse);
        skillTestResponse.setCandidat(this);
        return this;
    }

    public Profil removeCandidatskillTestResponse(SkillTestResponse skillTestResponse) {
        this.candidatskillTestResponses.remove(skillTestResponse);
        skillTestResponse.setCandidat(null);
        return this;
    }

    public void setCandidatskillTestResponses(Set<SkillTestResponse> skillTestResponses) {
        this.candidatskillTestResponses = skillTestResponses;
    }

    public Set<JobResponse> getCandidatjobResponses() {
        return candidatjobResponses;
    }

    public Profil candidatjobResponses(Set<JobResponse> jobResponses) {
        this.candidatjobResponses = jobResponses;
        return this;
    }

    public Profil addCandidatjobResponse(JobResponse jobResponse) {
        this.candidatjobResponses.add(jobResponse);
        jobResponse.setCandidat(this);
        return this;
    }

    public Profil removeCandidatjobResponse(JobResponse jobResponse) {
        this.candidatjobResponses.remove(jobResponse);
        jobResponse.setCandidat(null);
        return this;
    }

    public void setCandidatjobResponses(Set<JobResponse> jobResponses) {
        this.candidatjobResponses = jobResponses;
    }

    public Set<EtudeProfil> getProfiletudeProfils() {
        return profiletudeProfils;
    }

    public Profil profiletudeProfils(Set<EtudeProfil> etudeProfils) {
        this.profiletudeProfils = etudeProfils;
        return this;
    }

    public Profil addProfiletudeProfil(EtudeProfil etudeProfil) {
        this.profiletudeProfils.add(etudeProfil);
        etudeProfil.setIdProfil(this);
        return this;
    }

    public Profil removeProfiletudeProfil(EtudeProfil etudeProfil) {
        this.profiletudeProfils.remove(etudeProfil);
        etudeProfil.setIdProfil(null);
        return this;
    }

    public void setProfiletudeProfils(Set<EtudeProfil> etudeProfils) {
        this.profiletudeProfils = etudeProfils;
    }

    public Set<ExperienceProfil> getProfilexperienceProfils() {
        return profilexperienceProfils;
    }

    public Profil profilexperienceProfils(Set<ExperienceProfil> experienceProfils) {
        this.profilexperienceProfils = experienceProfils;
        return this;
    }

    public Profil addProfilexperienceProfil(ExperienceProfil experienceProfil) {
        this.profilexperienceProfils.add(experienceProfil);
        experienceProfil.setIdProfil(this);
        return this;
    }

    public Profil removeProfilexperienceProfil(ExperienceProfil experienceProfil) {
        this.profilexperienceProfils.remove(experienceProfil);
        experienceProfil.setIdProfil(null);
        return this;
    }

    public void setProfilexperienceProfils(Set<ExperienceProfil> experienceProfils) {
        this.profilexperienceProfils = experienceProfils;
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
        Profil profil = (Profil) o;
        if (profil.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), profil.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Profil{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            "}";
    }
}
