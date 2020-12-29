package com.hiraparl.hekimmaster.service.mapper;


import com.hiraparl.hekimmaster.domain.*;
import com.hiraparl.hekimmaster.service.dto.ShortPatientNoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShortPatientNote} and its DTO {@link ShortPatientNoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShortPatientNoteMapper extends EntityMapper<ShortPatientNoteDTO, ShortPatientNote> {



    default ShortPatientNote fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShortPatientNote shortPatientNote = new ShortPatientNote();
        shortPatientNote.setId(id);
        return shortPatientNote;
    }
}
