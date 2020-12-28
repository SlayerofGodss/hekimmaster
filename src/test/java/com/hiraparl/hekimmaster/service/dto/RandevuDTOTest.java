package com.hiraparl.hekimmaster.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hiraparl.hekimmaster.web.rest.TestUtil;

public class RandevuDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RandevuDTO.class);
        RandevuDTO randevuDTO1 = new RandevuDTO();
        randevuDTO1.setId(1L);
        RandevuDTO randevuDTO2 = new RandevuDTO();
        assertThat(randevuDTO1).isNotEqualTo(randevuDTO2);
        randevuDTO2.setId(randevuDTO1.getId());
        assertThat(randevuDTO1).isEqualTo(randevuDTO2);
        randevuDTO2.setId(2L);
        assertThat(randevuDTO1).isNotEqualTo(randevuDTO2);
        randevuDTO1.setId(null);
        assertThat(randevuDTO1).isNotEqualTo(randevuDTO2);
    }
}
