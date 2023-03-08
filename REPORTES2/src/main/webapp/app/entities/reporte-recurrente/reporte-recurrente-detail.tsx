import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reporte-recurrente.reducer';

export const ReporteRecurrenteDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const reporteRecurrenteEntity = useAppSelector(state => state.reporteRecurrente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="reporteRecurrenteDetailsHeading">
          <Translate contentKey="reportes2App.reporteRecurrente.detail.title">ReporteRecurrente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{reporteRecurrenteEntity.id}</dd>
          <dt>
            <span id="accion">
              <Translate contentKey="reportes2App.reporteRecurrente.accion">Accion</Translate>
            </span>
          </dt>
          <dd>{reporteRecurrenteEntity.accion}</dd>
          <dt>
            <span id="franquiciaUUID">
              <Translate contentKey="reportes2App.reporteRecurrente.franquiciaUUID">Franquicia UUID</Translate>
            </span>
          </dt>
          <dd>{reporteRecurrenteEntity.franquiciaUUID}</dd>
          <dt>
            <span id="tipo">
              <Translate contentKey="reportes2App.reporteRecurrente.tipo">Tipo</Translate>
            </span>
          </dt>
          <dd>{reporteRecurrenteEntity.tipo}</dd>
          <dt>
            <span id="fechaInicio">
              <Translate contentKey="reportes2App.reporteRecurrente.fechaInicio">Fecha Inicio</Translate>
            </span>
          </dt>
          <dd>
            {reporteRecurrenteEntity.fechaInicio ? (
              <TextFormat value={reporteRecurrenteEntity.fechaInicio} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="fechaFin">
              <Translate contentKey="reportes2App.reporteRecurrente.fechaFin">Fecha Fin</Translate>
            </span>
          </dt>
          <dd>
            {reporteRecurrenteEntity.fechaFin ? (
              <TextFormat value={reporteRecurrenteEntity.fechaFin} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="intervalo">
              <Translate contentKey="reportes2App.reporteRecurrente.intervalo">Intervalo</Translate>
            </span>
          </dt>
          <dd>{reporteRecurrenteEntity.intervalo}</dd>
        </dl>
        <Button tag={Link} to="/reporte-recurrente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reporte-recurrente/${reporteRecurrenteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReporteRecurrenteDetail;
