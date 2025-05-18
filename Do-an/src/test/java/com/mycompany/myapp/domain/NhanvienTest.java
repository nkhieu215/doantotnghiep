package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.BangchamcongTestSamples.*;
import static com.mycompany.myapp.domain.ChucvuTestSamples.*;
import static com.mycompany.myapp.domain.NhanvienTestSamples.*;
import static com.mycompany.myapp.domain.PhongbanTestSamples.*;
import static com.mycompany.myapp.domain.TanggiamtlTestSamples.*;
import static com.mycompany.myapp.domain.ThamsotlTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class NhanvienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nhanvien.class);
        Nhanvien nhanvien1 = getNhanvienSample1();
        Nhanvien nhanvien2 = new Nhanvien();
        assertThat(nhanvien1).isNotEqualTo(nhanvien2);

        nhanvien2.setId(nhanvien1.getId());
        assertThat(nhanvien1).isEqualTo(nhanvien2);

        nhanvien2 = getNhanvienSample2();
        assertThat(nhanvien1).isNotEqualTo(nhanvien2);
    }

    @Test
    void chucvuTest() throws Exception {
        Nhanvien nhanvien = getNhanvienRandomSampleGenerator();
        Chucvu chucvuBack = getChucvuRandomSampleGenerator();

        nhanvien.setChucvu(chucvuBack);
        assertThat(nhanvien.getChucvu()).isEqualTo(chucvuBack);

        nhanvien.chucvu(null);
        assertThat(nhanvien.getChucvu()).isNull();
    }

    @Test
    void phongbanTest() throws Exception {
        Nhanvien nhanvien = getNhanvienRandomSampleGenerator();
        Phongban phongbanBack = getPhongbanRandomSampleGenerator();

        nhanvien.setPhongban(phongbanBack);
        assertThat(nhanvien.getPhongban()).isEqualTo(phongbanBack);

        nhanvien.phongban(null);
        assertThat(nhanvien.getPhongban()).isNull();
    }

    @Test
    void bangchamcongTest() throws Exception {
        Nhanvien nhanvien = getNhanvienRandomSampleGenerator();
        Bangchamcong bangchamcongBack = getBangchamcongRandomSampleGenerator();

        nhanvien.addBangchamcong(bangchamcongBack);
        assertThat(nhanvien.getBangchamcongs()).containsOnly(bangchamcongBack);
        assertThat(bangchamcongBack.getNhanvien()).isEqualTo(nhanvien);

        nhanvien.removeBangchamcong(bangchamcongBack);
        assertThat(nhanvien.getBangchamcongs()).doesNotContain(bangchamcongBack);
        assertThat(bangchamcongBack.getNhanvien()).isNull();

        nhanvien.bangchamcongs(new HashSet<>(Set.of(bangchamcongBack)));
        assertThat(nhanvien.getBangchamcongs()).containsOnly(bangchamcongBack);
        assertThat(bangchamcongBack.getNhanvien()).isEqualTo(nhanvien);

        nhanvien.setBangchamcongs(new HashSet<>());
        assertThat(nhanvien.getBangchamcongs()).doesNotContain(bangchamcongBack);
        assertThat(bangchamcongBack.getNhanvien()).isNull();
    }

    @Test
    void tanggiamtlTest() throws Exception {
        Nhanvien nhanvien = getNhanvienRandomSampleGenerator();
        Tanggiamtl tanggiamtlBack = getTanggiamtlRandomSampleGenerator();

        nhanvien.addTanggiamtl(tanggiamtlBack);
        assertThat(nhanvien.getTanggiamtls()).containsOnly(tanggiamtlBack);
        assertThat(tanggiamtlBack.getNhanvien()).isEqualTo(nhanvien);

        nhanvien.removeTanggiamtl(tanggiamtlBack);
        assertThat(nhanvien.getTanggiamtls()).doesNotContain(tanggiamtlBack);
        assertThat(tanggiamtlBack.getNhanvien()).isNull();

        nhanvien.tanggiamtls(new HashSet<>(Set.of(tanggiamtlBack)));
        assertThat(nhanvien.getTanggiamtls()).containsOnly(tanggiamtlBack);
        assertThat(tanggiamtlBack.getNhanvien()).isEqualTo(nhanvien);

        nhanvien.setTanggiamtls(new HashSet<>());
        assertThat(nhanvien.getTanggiamtls()).doesNotContain(tanggiamtlBack);
        assertThat(tanggiamtlBack.getNhanvien()).isNull();
    }

    @Test
    void thamsotlTest() throws Exception {
        Nhanvien nhanvien = getNhanvienRandomSampleGenerator();
        Thamsotl thamsotlBack = getThamsotlRandomSampleGenerator();

        nhanvien.setThamsotl(thamsotlBack);
        assertThat(nhanvien.getThamsotl()).isEqualTo(thamsotlBack);
        assertThat(thamsotlBack.getNhanvien()).isEqualTo(nhanvien);

        nhanvien.thamsotl(null);
        assertThat(nhanvien.getThamsotl()).isNull();
        assertThat(thamsotlBack.getNhanvien()).isNull();
    }
}
