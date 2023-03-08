import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReporteRecurrente } from 'app/shared/model/reporte-recurrente.model';
import { getEntities } from './reporte-recurrente.reducer';

export const ReporteRecurrente = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const reporteRecurrenteList = useAppSelector(state => state.reporteRecurrente.entities);
  const loading = useAppSelector(state => state.reporteRecurrente.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="reporte-recurrente-heading" data-cy="ReporteRecurrenteHeading">
        <Translate contentKey="reportes2App.reporteRecurrente.home.title">Reporte Recurrentes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="reportes2App.reporteRecurrente.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/reporte-recurrente/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="reportes2App.reporteRecurrente.home.createLabel">Create new Reporte Recurrente</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reporteRecurrenteList && reporteRecurrenteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="reportes2App.reporteRecurrente.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteRecurrente.accion">Accion</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteRecurrente.franquiciaUUID">Franquicia UUID</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteRecurrente.tipo">Tipo</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteRecurrente.fechaInicio">Fecha Inicio</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteRecurrente.fechaFin">Fecha Fin</Translate>
                </th>
                <th>
                  <Translate contentKey="reportes2App.reporteRecurrente.intervalo">Intervalo</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reporteRecurrenteList.map((reporteRecurrente, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/reporte-recurrente/${reporteRecurrente.id}`} color="link" size="sm">
                      {reporteRecurrente.id}
                    </Button>
                  </td>
                  <td>{reporteRecurrente.accion}</td>
                  <td>{reporteRecurrente.franquiciaUUID}</td>
                  <td>{reporteRecurrente.tipo}</td>
                  <td>
                    {reporteRecurrente.fechaInicio ? (
                      <TextFormat type="date" value={reporteRecurrente.fechaInicio} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {reporteRecurrente.fechaFin ? (
                      <TextFormat type="date" value={reporteRecurrente.fechaFin} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{reporteRecurrente.intervalo}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/reporte-recurrente/${reporteRecurrente.id}`}
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
                        to={`/reporte-recurrente/${reporteRecurrente.id}/edit`}
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
                        to={`/reporte-recurrente/${reporteRecurrente.id}/delete`}
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
              <Translate contentKey="reportes2App.reporteRecurrente.home.notFound">No Reporte Recurrentes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ReporteRecurrente;
