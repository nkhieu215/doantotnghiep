package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ChucvuTestSamples.*;
import static com.mycompany.myapp.domain.NhanvienTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ChucvuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chucvu.class);
        Chucvu chucvu1 = getChucvuSample1();
        Chucvu chucvu2 = new Chucvu();
        assertThat(chucvu1).isNotEqualTo(chucvu2);

        chucvu2.setId(chucvu1.getId());
        assertThat(chucvu1).isEqualTo(chucvu2);

        chucvu2 = getChucvuSample2();
        assertThat(chucvu1).isNotEqualTo(chucvu2);
    }

    @Test
    void nhanvienTest() throws Exception {
        Chucvu chucvu = getChucvuRandomSampleGenerator();
        Nhanvien nhanvienBack = getNhanvienRandomSampleGenerator();

        chucvu.addNhanvien(nhanvienBack);
        assertThat(chucvu.getNhanviens()).containsOnly(nhanvienBack);
        assertThat(nhanvienBack.getChucvu()).isEqualTo(chucvu);

        chucvu.removeNhanvien(nhanvienBack);
        assertThat(chucvu.getNhanviens()).doesNotContain(nhanvienBack);
        assertThat(nhanvienBack.getChucvu()).isNull();

        chucvu.nhanviens(new HashSet<>(Set.of(nhanvienBack)));
        assertThat(chucvu.getNhanviens()).containsOnly(nhanvienBack);
        assertThat(nhanvienBack.getChucvu()).isEqualTo(chucvu);

        chucvu.setNhanviens(new HashSet<>());
        assertThat(chucvu.getNhanviens()).doesNotContain(nhanvienBack);
        assertThat(nhanvienBack.getChucvu()).isNull();
    }
}
