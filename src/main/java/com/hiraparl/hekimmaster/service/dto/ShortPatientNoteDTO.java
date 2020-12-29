package com.hiraparl.hekimmaster.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hiraparl.hekimmaster.domain.ShortPatientNote} entity.
 */
public class ShortPatientNoteDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String shortPatientNote;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortPatientNote() {
        return shortPatientNote;
    }

    public void setShortPatientNote(String shortPatientNote) {
        this.shortPatientNote = shortPatientNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShortPatientNoteDTO)) {
            return false;
        }

        return id != null && id.equals(((ShortPatientNoteDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShortPatientNoteDTO{" +
            "id=" + getId() +
            ", shortPatientNote='" + getShortPatientNote() + "'" +
            "}";
    }
}
