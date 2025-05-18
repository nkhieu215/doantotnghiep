package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.NhanvienTestSamples.*;
import static com.mycompany.myapp.domain.PhongbanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PhongbanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phongban.class);
        Phongban phongban1 = getPhongbanSample1();
        Phongban phongban2 = new Phongban();
        assertThat(phongban1).isNotEqualTo(phongban2);

        phongban2.setId(phongban1.getId());
        assertThat(phongban1).isEqualTo(phongban2);

        phongban2 = getPhongbanSample2();
        assertThat(phongban1).isNotEqualTo(phongban2);
    }

    @Test
    void nhanvienTest() throws Exception {
        Phongban phongban = getPhongbanRandomSampleGenerator();
        Nhanvien nhanvienBack = getNhanvienRandomSampleGenerator();

        phongban.addNhanvien(nhanvienBack);
        assertThat(phongban.getNhanviens()).containsOnly(nhanvienBack);
        assertThat(nhanvienBack.getPhongban()).isEqualTo(phongban);

        phongban.removeNhanvien(nhanvienBack);
        assertThat(phongban.getNhanviens()).doesNotContain(nhanvienBack);
        assertThat(nhanvienBack.getPhongban()).isNull();

        phongban.nhanviens(new HashSet<>(Set.of(nhanvienBack)));
        assertThat(phongban.getNhanviens()).containsOnly(nhanvienBack);
        assertThat(nhanvienBack.getPhongban()).isEqualTo(phongban);

        phongban.setNhanviens(new HashSet<>());
        assertThat(phongban.getNhanviens()).doesNotContain(nhanvienBack);
        assertThat(nhanvienBack.getPhongban()).isNull();
    }
}
