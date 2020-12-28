package com.hiraparl.hekimmaster.service.mapper;


import com.hiraparl.hekimmaster.domain.*;
import com.hiraparl.hekimmaster.service.dto.RandevuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Randevu} and its DTO {@link RandevuDTO}.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface RandevuMapper extends EntityMapper<RandevuDTO, Randevu> {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.lastName", target = "patientLastName")
    RandevuDTO toDto(Randevu randevu);

    @Mapping(source = "patientId", target = "patient")
    Randevu toEntity(RandevuDTO randevuDTO);

    default Randevu fromId(Long id) {
        if (id == null) {
            return null;
        }
        Randevu randevu = new Randevu();
        randevu.setId(id);
        return randevu;
    }
}
