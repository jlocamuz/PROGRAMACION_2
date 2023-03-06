import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Venta from './venta';
import ReporteHistorico from './reporte-historico';
import ReporteRecurrente from './reporte-recurrente';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="venta/*" element={<Venta />} />
        <Route path="reporte-historico/*" element={<ReporteHistorico />} />
        <Route path="reporte-recurrente/*" element={<ReporteRecurrente />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
