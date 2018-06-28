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

import fr.nicolasneto.domain.enumeration.TypeOffre;

/**
 * A JobOffer.
 */
@Entity
@Table(name = "job_offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "joboffer")
public class JobOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "title_offer", nullable = false)
    private String titleOffer;

    @NotNull
    @Column(name = "description_offer", nullable = false)
    private String descriptionOffer;

    @NotNull
    @Column(name = "salairy_min", nullable = false)
    private Long salairyMin;

    @NotNull
    @Column(name = "salairy_max", nullable = false)
    private Long salairyMax;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_offer", nullable = false)
    private TypeOffre typeOffer;

    @NotNull
    @Column(name = "date_offer", nullable = false)
    private LocalDate dateOffer;

    @Column(name = "jhi_comment")
    private String comment;

    @NotNull
    @Column(name = "place", nullable = false)
    private String place;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "job")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobSkill> jobsjobSkills = new HashSet<>();

    @OneToMany(mappedBy = "jobOffer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillTest> idProfilSkillTests = new HashSet<>();

    @OneToMany(mappedBy = "jobOffer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobResponse> jobjobResponses = new HashSet<>();

    @ManyToOne
    private Company company;

    @ManyToOne
    private CategoryOffer categoryOffer;

    @ManyToOne
    private Etude etude;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleOffer() {
        return titleOffer;
    }

    public JobOffer titleOffer(String titleOffer) {
        this.titleOffer = titleOffer;
        return this;
    }

    public void setTitleOffer(String titleOffer) {
        this.titleOffer = titleOffer;
    }

    public String getDescriptionOffer() {
        return descriptionOffer;
    }

    public JobOffer descriptionOffer(String descriptionOffer) {
        this.descriptionOffer = descriptionOffer;
        return this;
    }

    public void setDescriptionOffer(String descriptionOffer) {
        this.descriptionOffer = descriptionOffer;
    }

    public Long getSalairyMin() {
        return salairyMin;
    }

    public JobOffer salairyMin(Long salairyMin) {
        this.salairyMin = salairyMin;
        return this;
    }

    public void setSalairyMin(Long salairyMin) {
        this.salairyMin = salairyMin;
    }

    public Long getSalairyMax() {
        return salairyMax;
    }

    public JobOffer salairyMax(Long salairyMax) {
        this.salairyMax = salairyMax;
        return this;
    }

    public void setSalairyMax(Long salairyMax) {
        this.salairyMax = salairyMax;
    }

    public TypeOffre getTypeOffer() {
        return typeOffer;
    }

    public JobOffer typeOffer(TypeOffre typeOffer) {
        this.typeOffer = typeOffer;
        return this;
    }

    public void setTypeOffer(TypeOffre typeOffer) {
        this.typeOffer = typeOffer;
    }

    public LocalDate getDateOffer() {
        return dateOffer;
    }

    public JobOffer dateOffer(LocalDate dateOffer) {
        this.dateOffer = dateOffer;
        return this;
    }

    public void setDateOffer(LocalDate dateOffer) {
        this.dateOffer = dateOffer;
    }

    public String getComment() {
        return comment;
    }

    public JobOffer comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPlace() {
        return place;
    }

    public JobOffer place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContact() {
        return contact;
    }

    public JobOffer contact(String contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public JobOffer status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<JobSkill> getJobsjobSkills() {
        return jobsjobSkills;
    }

    public JobOffer jobsjobSkills(Set<JobSkill> jobSkills) {
        this.jobsjobSkills = jobSkills;
        return this;
    }

    public JobOffer addJobsjobSkill(JobSkill jobSkill) {
        this.jobsjobSkills.add(jobSkill);
        jobSkill.setJob(this);
        return this;
    }

    public JobOffer removeJobsjobSkill(JobSkill jobSkill) {
        this.jobsjobSkills.remove(jobSkill);
        jobSkill.setJob(null);
        return this;
    }

    public void setJobsjobSkills(Set<JobSkill> jobSkills) {
        this.jobsjobSkills = jobSkills;
    }

    public Set<SkillTest> getIdProfilSkillTests() {
        return idProfilSkillTests;
    }

    public JobOffer idProfilSkillTests(Set<SkillTest> skillTests) {
        this.idProfilSkillTests = skillTests;
        return this;
    }

    public JobOffer addIdProfilSkillTest(SkillTest skillTest) {
        this.idProfilSkillTests.add(skillTest);
        skillTest.setJobOffer(this);
        return this;
    }

    public JobOffer removeIdProfilSkillTest(SkillTest skillTest) {
        this.idProfilSkillTests.remove(skillTest);
        skillTest.setJobOffer(null);
        return this;
    }

    public void setIdProfilSkillTests(Set<SkillTest> skillTests) {
        this.idProfilSkillTests = skillTests;
    }

    public Set<JobResponse> getJobjobResponses() {
        return jobjobResponses;
    }

    public JobOffer jobjobResponses(Set<JobResponse> jobResponses) {
        this.jobjobResponses = jobResponses;
        return this;
    }

    public JobOffer addJobjobResponse(JobResponse jobResponse) {
        this.jobjobResponses.add(jobResponse);
        jobResponse.setJobOffer(this);
        return this;
    }

    public JobOffer removeJobjobResponse(JobResponse jobResponse) {
        this.jobjobResponses.remove(jobResponse);
        jobResponse.setJobOffer(null);
        return this;
    }

    public void setJobjobResponses(Set<JobResponse> jobResponses) {
        this.jobjobResponses = jobResponses;
    }

    public Company getCompany() {
        return company;
    }

    public JobOffer company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public CategoryOffer getCategoryOffer() {
        return categoryOffer;
    }

    public JobOffer categoryOffer(CategoryOffer categoryOffer) {
        this.categoryOffer = categoryOffer;
        return this;
    }

    public void setCategoryOffer(CategoryOffer categoryOffer) {
        this.categoryOffer = categoryOffer;
    }

    public Etude getEtude() {
        return etude;
    }

    public JobOffer etude(Etude etude) {
        this.etude = etude;
        return this;
    }

    public void setEtude(Etude etude) {
        this.etude = etude;
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
        JobOffer jobOffer = (JobOffer) o;
        if (jobOffer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobOffer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobOffer{" +
            "id=" + getId() +
            ", titleOffer='" + getTitleOffer() + "'" +
            ", descriptionOffer='" + getDescriptionOffer() + "'" +
            ", salairyMin=" + getSalairyMin() +
            ", salairyMax=" + getSalairyMax() +
            ", typeOffer='" + getTypeOffer() + "'" +
            ", dateOffer='" + getDateOffer() + "'" +
            ", comment='" + getComment() + "'" +
            ", place='" + getPlace() + "'" +
            ", contact='" + getContact() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
