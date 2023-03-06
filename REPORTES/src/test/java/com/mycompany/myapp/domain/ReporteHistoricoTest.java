package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ReporteHistoricoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReporteHistorico.class);
        ReporteHistorico reporteHistorico1 = new ReporteHistorico();
        reporteHistorico1.setId(1L);
        ReporteHistorico reporteHistorico2 = new ReporteHistorico();
        reporteHistorico2.setId(reporteHistorico1.getId());
        assertThat(reporteHistorico1).isEqualTo(reporteHistorico2);
        reporteHistorico2.setId(2L);
        assertThat(reporteHistorico1).isNotEqualTo(reporteHistorico2);
        reporteHistorico1.setId(null);
        assertThat(reporteHistorico1).isNotEqualTo(reporteHistorico2);
    }
}
