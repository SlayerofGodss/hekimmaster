package com.hiraparl.hekimmaster.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Day.
 */
@Entity
@Table(name = "day")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Day implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "randevu_date", nullable = false)
    private ZonedDateTime randevuDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRandevuDate() {
        return randevuDate;
    }

    public Day randevuDate(ZonedDateTime randevuDate) {
        this.randevuDate = randevuDate;
        return this;
    }

    public void setRandevuDate(ZonedDateTime randevuDate) {
        this.randevuDate = randevuDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Day)) {
            return false;
        }
        return id != null && id.equals(((Day) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Day{" +
            "id=" + getId() +
            ", randevuDate='" + getRandevuDate() + "'" +
            "}";
    }
}
