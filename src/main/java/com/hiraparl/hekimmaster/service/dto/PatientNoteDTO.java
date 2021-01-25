package com.hiraparl.hekimmaster.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.hiraparl.hekimmaster.domain.PatientNote} entity.
 */
public class PatientNoteDTO implements Serializable {
    
    private Long id;

    private String patientNote;


    private Long patientId;

    private String patientLastName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatientNote() {
        return patientNote;
    }

    public void setPatientNote(String patientNote) {
        this.patientNote = patientNote;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientNoteDTO)) {
            return false;
        }

        return id != null && id.equals(((PatientNoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientNoteDTO{" +
            "id=" + getId() +
            ", patientNote='" + getPatientNote() + "'" +
            ", patientId=" + getPatientId() +
            ", patientLastName='" + getPatientLastName() + "'" +
            "}";
    }
}
