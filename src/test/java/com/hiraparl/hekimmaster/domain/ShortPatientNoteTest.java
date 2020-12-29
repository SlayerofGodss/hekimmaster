package com.hiraparl.hekimmaster.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hiraparl.hekimmaster.web.rest.TestUtil;

public class ShortPatientNoteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShortPatientNote.class);
        ShortPatientNote shortPatientNote1 = new ShortPatientNote();
        shortPatientNote1.setId(1L);
        ShortPatientNote shortPatientNote2 = new ShortPatientNote();
        shortPatientNote2.setId(shortPatientNote1.getId());
        assertThat(shortPatientNote1).isEqualTo(shortPatientNote2);
        shortPatientNote2.setId(2L);
        assertThat(shortPatientNote1).isNotEqualTo(shortPatientNote2);
        shortPatientNote1.setId(null);
        assertThat(shortPatientNote1).isNotEqualTo(shortPatientNote2);
    }
}
