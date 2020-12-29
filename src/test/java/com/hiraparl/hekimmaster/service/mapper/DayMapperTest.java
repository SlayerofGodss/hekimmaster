package com.hiraparl.hekimmaster.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DayMapperTest {

    private DayMapper dayMapper;

    @BeforeEach
    public void setUp() {
        dayMapper = new DayMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(dayMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(dayMapper.fromId(null)).isNull();
    }
}
