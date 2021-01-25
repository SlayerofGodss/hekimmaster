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
 * Criteria class for the {@link com.hiraparl.hekimmaster.domain.PatientNote} entity. This class is used
 * in {@link com.hiraparl.hekimmaster.web.rest.PatientNoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /patient-notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PatientNoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter patientNote;

    private LongFilter patientId;

    public PatientNoteCriteria() {
    }

    public PatientNoteCriteria(PatientNoteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.patientNote = other.patientNote == null ? null : other.patientNote.copy();
        this.patientId = other.patientId == null ? null : other.patientId.copy();
    }

    @Override
    public PatientNoteCriteria copy() {
        return new PatientNoteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPatientNote() {
        return patientNote;
    }

    public void setPatientNote(StringFilter patientNote) {
        this.patientNote = patientNote;
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
        final PatientNoteCriteria that = (PatientNoteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(patientNote, that.patientNote) &&
            Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        patientNote,
        patientId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientNoteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (patientNote != null ? "patientNote=" + patientNote + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
            "}";
    }

}
