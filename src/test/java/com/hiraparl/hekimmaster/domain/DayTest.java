package com.hiraparl.hekimmaster.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.hiraparl.hekimmaster.web.rest.TestUtil;

public class DayTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Day.class);
        Day day1 = new Day();
        day1.setId(1L);
        Day day2 = new Day();
        day2.setId(day1.getId());
        assertThat(day1).isEqualTo(day2);
        day2.setId(2L);
        assertThat(day1).isNotEqualTo(day2);
        day1.setId(null);
        assertThat(day1).isNotEqualTo(day2);
    }
}
