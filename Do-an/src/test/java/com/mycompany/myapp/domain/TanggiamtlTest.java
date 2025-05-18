package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.NhanvienTestSamples.*;
import static com.mycompany.myapp.domain.TanggiamtlTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TanggiamtlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tanggiamtl.class);
        Tanggiamtl tanggiamtl1 = getTanggiamtlSample1();
        Tanggiamtl tanggiamtl2 = new Tanggiamtl();
        assertThat(tanggiamtl1).isNotEqualTo(tanggiamtl2);

        tanggiamtl2.setId(tanggiamtl1.getId());
        assertThat(tanggiamtl1).isEqualTo(tanggiamtl2);

        tanggiamtl2 = getTanggiamtlSample2();
        assertThat(tanggiamtl1).isNotEqualTo(tanggiamtl2);
    }

    @Test
    void nhanvienTest() throws Exception {
        Tanggiamtl tanggiamtl = getTanggiamtlRandomSampleGenerator();
        Nhanvien nhanvienBack = getNhanvienRandomSampleGenerator();

        tanggiamtl.setNhanvien(nhanvienBack);
        assertThat(tanggiamtl.getNhanvien()).isEqualTo(nhanvienBack);

        tanggiamtl.nhanvien(null);
        assertThat(tanggiamtl.getNhanvien()).isNull();
    }
}
