package com.hiraparl.hekimmaster.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hiraparl.hekimmaster.web.rest.TestUtil;

public class PatientNoteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientNote.class);
        PatientNote patientNote1 = new PatientNote();
        patientNote1.setId(1L);
        PatientNote patientNote2 = new PatientNote();
        patientNote2.setId(patientNote1.getId());
        assertThat(patientNote1).isEqualTo(patientNote2);
        patientNote2.setId(2L);
        assertThat(patientNote1).isNotEqualTo(patientNote2);
        patientNote1.setId(null);
        assertThat(patientNote1).isNotEqualTo(patientNote2);
    }
}
