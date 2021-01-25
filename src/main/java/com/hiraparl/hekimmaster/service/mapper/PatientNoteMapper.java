package com.hiraparl.hekimmaster.service.mapper;


import com.hiraparl.hekimmaster.domain.*;
import com.hiraparl.hekimmaster.service.dto.PatientNoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PatientNote} and its DTO {@link PatientNoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface PatientNoteMapper extends EntityMapper<PatientNoteDTO, PatientNote> {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.lastName", target = "patientLastName")
    PatientNoteDTO toDto(PatientNote patientNote);

    @Mapping(source = "patientId", target = "patient")
    PatientNote toEntity(PatientNoteDTO patientNoteDTO);

    default PatientNote fromId(Long id) {
        if (id == null) {
            return null;
        }
        PatientNote patientNote = new PatientNote();
        patientNote.setId(id);
        return patientNote;
    }
}
