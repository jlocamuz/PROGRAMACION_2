import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ReporteHistorico from './reporte-historico';
import ReporteHistoricoDetail from './reporte-historico-detail';
import ReporteHistoricoUpdate from './reporte-historico-update';
import ReporteHistoricoDeleteDialog from './reporte-historico-delete-dialog';

const ReporteHistoricoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ReporteHistorico />} />
    <Route path="new" element={<ReporteHistoricoUpdate />} />
    <Route path=":id">
      <Route index element={<ReporteHistoricoDetail />} />
      <Route path="edit" element={<ReporteHistoricoUpdate />} />
      <Route path="delete" element={<ReporteHistoricoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReporteHistoricoRoutes;
