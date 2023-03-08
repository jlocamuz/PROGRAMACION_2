import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reporte-historico.reducer';

export const ReporteHistoricoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reporteHistoricoEntity = useAppSelector(state => state.reporteHistorico.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reporteHistoricoDetailsHeading">
          <Translate contentKey="reportes2App.reporteHistorico.detail.title">ReporteHistorico</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reporteHistoricoEntity.id}</dd>
          <dt>
            <span id="accion">
              <Translate contentKey="reportes2App.reporteHistorico.accion">Accion</Translate>
            </span>
          </dt>
          <dd>{reporteHistoricoEntity.accion}</dd>
          <dt>
            <span id="franquiciaUUID">
              <Translate contentKey="reportes2App.reporteHistorico.franquiciaUUID">Franquicia UUID</Translate>
            </span>
          </dt>
          <dd>{reporteHistoricoEntity.franquiciaUUID}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="reportes2App.reporteHistorico.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{reporteHistoricoEntity.tipo}</dd>
          <dt>
            <span id="fechaInicio">
              <Translate contentKey="reportes2App.reporteHistorico.fechaInicio">Fecha Inicio</Translate>
            </span>
          </dt>
          <dd>
            {reporteHistoricoEntity.fechaInicio ? (
              <TextFormat value={reporteHistoricoEntity.fechaInicio} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaFin">
              <Translate contentKey="reportes2App.reporteHistorico.fechaFin">Fecha Fin</Translate>
            </span>
          </dt>
          <dd>
            {reporteHistoricoEntity.fechaFin ? (
              <TextFormat value={reporteHistoricoEntity.fechaFin} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/reporte-historico" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reporte-historico/${reporteHistoricoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReporteHistoricoDetail;
