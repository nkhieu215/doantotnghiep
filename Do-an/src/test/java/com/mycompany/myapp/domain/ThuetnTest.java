package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ThuetnTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThuetnTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thuetn.class);
        Thuetn thuetn1 = getThuetnSample1();
        Thuetn thuetn2 = new Thuetn();
        assertThat(thuetn1).isNotEqualTo(thuetn2);

        thuetn2.setId(thuetn1.getId());
        assertThat(thuetn1).isEqualTo(thuetn2);

        thuetn2 = getThuetnSample2();
        assertThat(thuetn1).isNotEqualTo(thuetn2);
    }
}
