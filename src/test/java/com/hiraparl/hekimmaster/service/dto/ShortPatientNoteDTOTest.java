package com.hiraparl.hekimmaster.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hiraparl.hekimmaster.web.rest.TestUtil;

public class ShortPatientNoteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShortPatientNoteDTO.class);
        ShortPatientNoteDTO shortPatientNoteDTO1 = new ShortPatientNoteDTO();
        shortPatientNoteDTO1.setId(1L);
        ShortPatientNoteDTO shortPatientNoteDTO2 = new ShortPatientNoteDTO();
        assertThat(shortPatientNoteDTO1).isNotEqualTo(shortPatientNoteDTO2);
        shortPatientNoteDTO2.setId(shortPatientNoteDTO1.getId());
        assertThat(shortPatientNoteDTO1).isEqualTo(shortPatientNoteDTO2);
        shortPatientNoteDTO2.setId(2L);
        assertThat(shortPatientNoteDTO1).isNotEqualTo(shortPatientNoteDTO2);
        shortPatientNoteDTO1.setId(null);
        assertThat(shortPatientNoteDTO1).isNotEqualTo(shortPatientNoteDTO2);
    }
}
