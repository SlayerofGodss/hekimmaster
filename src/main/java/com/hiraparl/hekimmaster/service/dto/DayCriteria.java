package com.hiraparl.hekimmaster.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.hiraparl.hekimmaster.domain.Day} entity. This class is used
 * in {@link com.hiraparl.hekimmaster.web.rest.DayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /days?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DayCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter randevuDate;

    public DayCriteria() {
    }

    public DayCriteria(DayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.randevuDate = other.randevuDate == null ? null : other.randevuDate.copy();
    }

    @Override
    public DayCriteria copy() {
        return new DayCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getRandevuDate() {
        return randevuDate;
    }

    public void setRandevuDate(ZonedDateTimeFilter randevuDate) {
        this.randevuDate = randevuDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DayCriteria that = (DayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(randevuDate, that.randevuDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        randevuDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (randevuDate != null ? "randevuDate=" + randevuDate + ", " : "") +
            "}";
    }

}
