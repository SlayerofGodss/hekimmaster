package com.hiraparl.hekimmaster.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.hiraparl.hekimmaster.domain.Day} entity.
 */
public class DayDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ZonedDateTime randevuDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRandevuDate() {
        return randevuDate;
    }

    public void setRandevuDate(ZonedDateTime randevuDate) {
        this.randevuDate = randevuDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DayDTO)) {
            return false;
        }

        return id != null && id.equals(((DayDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayDTO{" +
            "id=" + getId() +
            ", randevuDate='" + getRandevuDate() + "'" +
            "}";
    }
}
