package com.hiraparl.hekimmaster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PatientNote.
 */
@Entity
@Table(name = "patient_note")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PatientNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_note")
    private String patientNote;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientNote() {
        return patientNote;
    }

    public PatientNote patientNote(String patientNote) {
        this.patientNote = patientNote;
        return this;
    }

    public void setPatientNote(String patientNote) {
        this.patientNote = patientNote;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientNote)) {
            return false;
        }
        return id != null && id.equals(((PatientNote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientNote{" +
            "id=" + getId() +
            ", patientNote='" + getPatientNote() + "'" +
            "}";
    }
}
