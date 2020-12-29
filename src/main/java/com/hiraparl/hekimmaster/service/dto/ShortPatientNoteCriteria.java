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
 * Criteria class for the {@link com.hiraparl.hekimmaster.domain.ShortPatientNote} entity. This class is used
 * in {@link com.hiraparl.hekimmaster.web.rest.ShortPatientNoteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /short-patient-notes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ShortPatientNoteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter shortPatientNote;

    public ShortPatientNoteCriteria() {
    }

    public ShortPatientNoteCriteria(ShortPatientNoteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.shortPatientNote = other.shortPatientNote == null ? null : other.shortPatientNote.copy();
    }

    @Override
    public ShortPatientNoteCriteria copy() {
        return new ShortPatientNoteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getShortPatientNote() {
        return shortPatientNote;
    }

    public void setShortPatientNote(StringFilter shortPatientNote) {
        this.shortPatientNote = shortPatientNote;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ShortPatientNoteCriteria that = (ShortPatientNoteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(shortPatientNote, that.shortPatientNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        shortPatientNote
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShortPatientNoteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (shortPatientNote != null ? "shortPatientNote=" + shortPatientNote + ", " : "") +
            "}";
    }

}
