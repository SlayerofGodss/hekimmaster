package com.hiraparl.hekimmaster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ShortPatientNote.
 */
@Entity
@Table(name = "short_patient_note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShortPatientNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "short_patient_note", nullable = false)
    private String shortPatientNote;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortPatientNote() {
        return shortPatientNote;
    }

    public ShortPatientNote shortPatientNote(String shortPatientNote) {
        this.shortPatientNote = shortPatientNote;
        return this;
    }

    public void setShortPatientNote(String shortPatientNote) {
        this.shortPatientNote = shortPatientNote;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShortPatientNote)) {
            return false;
        }
        return id != null && id.equals(((ShortPatientNote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShortPatientNote{" +
            "id=" + getId() +
            ", shortPatientNote='" + getShortPatientNote() + "'" +
            "}";
    }
}
