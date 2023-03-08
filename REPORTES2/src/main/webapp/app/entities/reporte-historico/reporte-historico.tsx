import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReporteHistorico } from 'app/shared/model/reporte-historico.model';
import { getEntities } from './reporte-historico.reducer';

export const ReporteHistorico = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const reporteHistoricoList = useAppSelector(state => state.reporteHistorico.entities);
  const loading = useAppSelector(state => state.reporteHistorico.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="reporte-historico-heading" data-cy="ReporteHistoricoHeading">
        <Translate contentKey="reportes2App.reporteHistorico.home.title">Reporte Historicos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="reportes2App.reporteHistorico.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/reporte-historico/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="reportes2App.reporteHistorico.home.createLabel">Create new Reporte Historico</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reporteHistoricoList && reporteHistoricoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="reportes2App.reporteHistorico.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteHistorico.accion">Accion</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteHistorico.franquiciaUUID">Franquicia UUID</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteHistorico.tipo">Tipo</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteHistorico.fechaInicio">Fecha Inicio</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteHistorico.fechaFin">Fecha Fin</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reporteHistoricoList.map((reporteHistorico, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/reporte-historico/${reporteHistorico.id}`} color="link" size="sm">
                      {reporteHistorico.id}
                    </Button>
                  </td>
                  <td>{reporteHistorico.accion}</td>
                  <td>{reporteHistorico.franquiciaUUID}</td>
                  <td>{reporteHistorico.tipo}</td>
                  <td>
                    {reporteHistorico.fechaInicio ? (
                      <TextFormat type="date" value={reporteHistorico.fechaInicio} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {reporteHistorico.fechaFin ? (
                      <TextFormat type="date" value={reporteHistorico.fechaFin} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/reporte-historico/${reporteHistorico.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/reporte-historico/${reporteHistorico.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/reporte-historico/${reporteHistorico.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="reportes2App.reporteHistorico.home.notFound">No Reporte Historicos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ReporteHistorico;
