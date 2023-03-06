import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ReporteRecurrente from './reporte-recurrente';
import ReporteRecurrenteDetail from './reporte-recurrente-detail';
import ReporteRecurrenteUpdate from './reporte-recurrente-update';
import ReporteRecurrenteDeleteDialog from './reporte-recurrente-delete-dialog';

const ReporteRecurrenteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ReporteRecurrente />} />
    <Route path="new" element={<ReporteRecurrenteUpdate />} />
    <Route path=":id">
      <Route index element={<ReporteRecurrenteDetail />} />
      <Route path="edit" element={<ReporteRecurrenteUpdate />} />
      <Route path="delete" element={<ReporteRecurrenteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReporteRecurrenteRoutes;
