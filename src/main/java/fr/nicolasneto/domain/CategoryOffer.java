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
 * A CategoryOffer.
 */
@Entity
@Table(name = "category_offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "categoryoffer")
public class CategoryOffer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name_category", nullable = false)
    private String nameCategory;

    @OneToMany(mappedBy = "categoryOffer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobOffer> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public CategoryOffer nameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
        return this;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public Set<JobOffer> getCategories() {
        return categories;
    }

    public CategoryOffer categories(Set<JobOffer> jobOffers) {
        this.categories = jobOffers;
        return this;
    }

    public CategoryOffer addCategory(JobOffer jobOffer) {
        this.categories.add(jobOffer);
        jobOffer.setCategoryOffer(this);
        return this;
    }

    public CategoryOffer removeCategory(JobOffer jobOffer) {
        this.categories.remove(jobOffer);
        jobOffer.setCategoryOffer(null);
        return this;
    }

    public void setCategories(Set<JobOffer> jobOffers) {
        this.categories = jobOffers;
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
        CategoryOffer categoryOffer = (CategoryOffer) o;
        if (categoryOffer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categoryOffer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategoryOffer{" +
            "id=" + getId() +
            ", nameCategory='" + getNameCategory() + "'" +
            "}";
    }
}
