package com.hiraparl.hekimmaster.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Randevu.
 */
@Entity
@Table(name = "randevu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Randevu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "randevu", length = 255, nullable = false)
    private String randevu;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "randevus", allowSetters = true)
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRandevu() {
        return randevu;
    }

    public Randevu randevu(String randevu) {
        this.randevu = randevu;
        return this;
    }

    public void setRandevu(String randevu) {
        this.randevu = randevu;
    }

    public Patient getPatient() {
        return patient;
    }

    public Randevu patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Randevu)) {
            return false;
        }
        return id != null && id.equals(((Randevu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Randevu{" +
            "id=" + getId() +
            ", randevu='" + getRandevu() + "'" +
            "}";
    }
}
