package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.NhanvienTestSamples.*;
import static com.mycompany.myapp.domain.ThamsotlTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThamsotlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thamsotl.class);
        Thamsotl thamsotl1 = getThamsotlSample1();
        Thamsotl thamsotl2 = new Thamsotl();
        assertThat(thamsotl1).isNotEqualTo(thamsotl2);

        thamsotl2.setId(thamsotl1.getId());
        assertThat(thamsotl1).isEqualTo(thamsotl2);

        thamsotl2 = getThamsotlSample2();
        assertThat(thamsotl1).isNotEqualTo(thamsotl2);
    }

    @Test
    void nhanvienTest() throws Exception {
        Thamsotl thamsotl = getThamsotlRandomSampleGenerator();
        Nhanvien nhanvienBack = getNhanvienRandomSampleGenerator();

        thamsotl.setNhanvien(nhanvienBack);
        assertThat(thamsotl.getNhanvien()).isEqualTo(nhanvienBack);

        thamsotl.nhanvien(null);
        assertThat(thamsotl.getNhanvien()).isNull();
    }
}
