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
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @NotNull
    @Column(name = "telephone_entreprise", nullable = false)
    private String telephoneEntreprise;

    @NotNull
    @Column(name = "place", nullable = false)
    private String place;

    @NotNull
    @Column(name = "siren", nullable = false)
    private Long siren;

    @NotNull
    @Column(name = "logo", nullable = false)
    private String logo;

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobOffer> companies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Company companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTelephoneEntreprise() {
        return telephoneEntreprise;
    }

    public Company telephoneEntreprise(String telephoneEntreprise) {
        this.telephoneEntreprise = telephoneEntreprise;
        return this;
    }

    public void setTelephoneEntreprise(String telephoneEntreprise) {
        this.telephoneEntreprise = telephoneEntreprise;
    }

    public String getPlace() {
        return place;
    }

    public Company place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getSiren() {
        return siren;
    }

    public Company siren(Long siren) {
        this.siren = siren;
        return this;
    }

    public void setSiren(Long siren) {
        this.siren = siren;
    }

    public String getLogo() {
        return logo;
    }

    public Company logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Long getUserId() {
        return userId;
    }

    public Company userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<JobOffer> getCompanies() {
        return companies;
    }

    public Company companies(Set<JobOffer> jobOffers) {
        this.companies = jobOffers;
        return this;
    }

    public Company addCompany(JobOffer jobOffer) {
        this.companies.add(jobOffer);
        jobOffer.setCompany(this);
        return this;
    }

    public Company removeCompany(JobOffer jobOffer) {
        this.companies.remove(jobOffer);
        jobOffer.setCompany(null);
        return this;
    }

    public void setCompanies(Set<JobOffer> jobOffers) {
        this.companies = jobOffers;
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
        Company company = (Company) o;
        if (company.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", telephoneEntreprise='" + getTelephoneEntreprise() + "'" +
            ", place='" + getPlace() + "'" +
            ", siren=" + getSiren() +
            ", logo='" + getLogo() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
