package com.hiraparl.hekimmaster.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PatientNoteMapperTest {

    private PatientNoteMapper patientNoteMapper;

    @BeforeEach
    public void setUp() {
        patientNoteMapper = new PatientNoteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(patientNoteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(patientNoteMapper.fromId(null)).isNull();
    }
}
