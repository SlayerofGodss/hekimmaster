package com.hiraparl.hekimmaster.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hiraparl.hekimmaster.domain.Randevu} entity.
 */
public class RandevuDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String randevu;


    private Long patientId;

    private String patientLastName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRandevu() {
        return randevu;
    }

    public void setRandevu(String randevu) {
        this.randevu = randevu;
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
        if (!(o instanceof RandevuDTO)) {
            return false;
        }

        return id != null && id.equals(((RandevuDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RandevuDTO{" +
            "id=" + getId() +
            ", randevu='" + getRandevu() + "'" +
            ", patientId=" + getPatientId() +
            ", patientLastName='" + getPatientLastName() + "'" +
            "}";
    }
}
