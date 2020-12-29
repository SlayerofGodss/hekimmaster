package com.hiraparl.hekimmaster.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShortPatientNoteMapperTest {

    private ShortPatientNoteMapper shortPatientNoteMapper;

    @BeforeEach
    public void setUp() {
        shortPatientNoteMapper = new ShortPatientNoteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(shortPatientNoteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(shortPatientNoteMapper.fromId(null)).isNull();
    }
}
