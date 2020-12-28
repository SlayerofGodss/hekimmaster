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

/**
 * Criteria class for the {@link com.hiraparl.hekimmaster.domain.Randevu} entity. This class is used
 * in {@link com.hiraparl.hekimmaster.web.rest.RandevuResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /randevus?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RandevuCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter randevu;

    private LongFilter patientId;

    public RandevuCriteria() {
    }

    public RandevuCriteria(RandevuCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.randevu = other.randevu == null ? null : other.randevu.copy();
        this.patientId = other.patientId == null ? null : other.patientId.copy();
    }

    @Override
    public RandevuCriteria copy() {
        return new RandevuCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRandevu() {
        return randevu;
    }

    public void setRandevu(StringFilter randevu) {
        this.randevu = randevu;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RandevuCriteria that = (RandevuCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(randevu, that.randevu) &&
            Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        randevu,
        patientId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RandevuCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (randevu != null ? "randevu=" + randevu + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
            "}";
    }

}
