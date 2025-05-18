package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.BangchamcongTestSamples.*;
import static com.mycompany.myapp.domain.NhanvienTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BangchamcongTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bangchamcong.class);
        Bangchamcong bangchamcong1 = getBangchamcongSample1();
        Bangchamcong bangchamcong2 = new Bangchamcong();
        assertThat(bangchamcong1).isNotEqualTo(bangchamcong2);

        bangchamcong2.setId(bangchamcong1.getId());
        assertThat(bangchamcong1).isEqualTo(bangchamcong2);

        bangchamcong2 = getBangchamcongSample2();
        assertThat(bangchamcong1).isNotEqualTo(bangchamcong2);
    }

    @Test
    void nhanvienTest() throws Exception {
        Bangchamcong bangchamcong = getBangchamcongRandomSampleGenerator();
        Nhanvien nhanvienBack = getNhanvienRandomSampleGenerator();

        bangchamcong.setNhanvien(nhanvienBack);
        assertThat(bangchamcong.getNhanvien()).isEqualTo(nhanvienBack);

        bangchamcong.nhanvien(null);
        assertThat(bangchamcong.getNhanvien()).isNull();
    }
}
