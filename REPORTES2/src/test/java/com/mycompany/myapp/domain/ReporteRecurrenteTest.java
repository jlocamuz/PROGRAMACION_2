package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReporteRecurrenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReporteRecurrente.class);
        ReporteRecurrente reporteRecurrente1 = new ReporteRecurrente();
        reporteRecurrente1.setId(1L);
        ReporteRecurrente reporteRecurrente2 = new ReporteRecurrente();
        reporteRecurrente2.setId(reporteRecurrente1.getId());
        assertThat(reporteRecurrente1).isEqualTo(reporteRecurrente2);
        reporteRecurrente2.setId(2L);
        assertThat(reporteRecurrente1).isNotEqualTo(reporteRecurrente2);
        reporteRecurrente1.setId(null);
        assertThat(reporteRecurrente1).isNotEqualTo(reporteRecurrente2);
    }
}
