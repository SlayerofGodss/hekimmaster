package com.hiraparl.hekimmaster.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hiraparl.hekimmaster.web.rest.TestUtil;

public class PatientNoteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientNoteDTO.class);
        PatientNoteDTO patientNoteDTO1 = new PatientNoteDTO();
        patientNoteDTO1.setId(1L);
        PatientNoteDTO patientNoteDTO2 = new PatientNoteDTO();
        assertThat(patientNoteDTO1).isNotEqualTo(patientNoteDTO2);
        patientNoteDTO2.setId(patientNoteDTO1.getId());
        assertThat(patientNoteDTO1).isEqualTo(patientNoteDTO2);
        patientNoteDTO2.setId(2L);
        assertThat(patientNoteDTO1).isNotEqualTo(patientNoteDTO2);
        patientNoteDTO1.setId(null);
        assertThat(patientNoteDTO1).isNotEqualTo(patientNoteDTO2);
    }
}
