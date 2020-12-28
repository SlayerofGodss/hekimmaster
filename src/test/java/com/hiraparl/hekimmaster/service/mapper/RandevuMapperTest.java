package com.hiraparl.hekimmaster.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RandevuMapperTest {

    private RandevuMapper randevuMapper;

    @BeforeEach
    public void setUp() {
        randevuMapper = new RandevuMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(randevuMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(randevuMapper.fromId(null)).isNull();
    }
}
