import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReporteHistorico } from 'app/shared/model/reporte-historico.model';
import { getEntity, updateEntity, createEntity, reset } from './reporte-historico.reducer';

export const ReporteHistoricoUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const reporteHistoricoEntity = useAppSelector(state => state.reporteHistorico.entity);
  const loading = useAppSelector(state => state.reporteHistorico.loading);
  const updating = useAppSelector(state => state.reporteHistorico.updating);
  const updateSuccess = useAppSelector(state => state.reporteHistorico.updateSuccess);

  const handleClose = () => {
    navigate('/reporte-historico');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.fechaInicio = convertDateTimeToServer(values.fechaInicio);
    values.fechaFin = convertDateTimeToServer(values.fechaFin);

    const entity = {
      ...reporteHistoricoEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          fechaInicio: displayDefaultDateTime(),
          fechaFin: displayDefaultDateTime(),
        }
      : {
          ...reporteHistoricoEntity,
          fechaInicio: convertDateTimeFromServer(reporteHistoricoEntity.fechaInicio),
          fechaFin: convertDateTimeFromServer(reporteHistoricoEntity.fechaFin),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reportes2App.reporteHistorico.home.createOrEditLabel" data-cy="ReporteHistoricoCreateUpdateHeading">
            <Translate contentKey="reportes2App.reporteHistorico.home.createOrEditLabel">Create or edit a ReporteHistorico</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="reporte-historico-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reportes2App.reporteHistorico.accion')}
                id="reporte-historico-accion"
                name="accion"
                data-cy="accion"
                type="text"
              />
              <ValidatedField
                label={translate('reportes2App.reporteHistorico.franquiciaUUID')}
                id="reporte-historico-franquiciaUUID"
                name="franquiciaUUID"
                data-cy="franquiciaUUID"
                type="text"
              />
              <ValidatedField
                label={translate('reportes2App.reporteHistorico.tipo')}
                id="reporte-historico-tipo"
                name="tipo"
                data-cy="tipo"
                type="text"
              />
              <ValidatedField
                label={translate('reportes2App.reporteHistorico.fechaInicio')}
                id="reporte-historico-fechaInicio"
                name="fechaInicio"
                data-cy="fechaInicio"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('reportes2App.reporteHistorico.fechaFin')}
                id="reporte-historico-fechaFin"
                name="fechaFin"
                data-cy="fechaFin"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reporte-historico" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReporteHistoricoUpdate;
