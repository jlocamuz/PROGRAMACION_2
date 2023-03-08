import venta from 'app/entities/venta/venta.reducer';
import reporteHistorico from 'app/entities/reporte-historico/reporte-historico.reducer';
import reporteRecurrente from 'app/entities/reporte-recurrente/reporte-recurrente.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  venta,
  reporteHistorico,
  reporteRecurrente,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
