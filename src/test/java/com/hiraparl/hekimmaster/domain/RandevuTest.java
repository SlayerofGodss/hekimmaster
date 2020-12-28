package com.hiraparl.hekimmaster.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hiraparl.hekimmaster.web.rest.TestUtil;

public class RandevuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Randevu.class);
        Randevu randevu1 = new Randevu();
        randevu1.setId(1L);
        Randevu randevu2 = new Randevu();
        randevu2.setId(randevu1.getId());
        assertThat(randevu1).isEqualTo(randevu2);
        randevu2.setId(2L);
        assertThat(randevu1).isNotEqualTo(randevu2);
        randevu1.setId(null);
        assertThat(randevu1).isNotEqualTo(randevu2);
    }
}
