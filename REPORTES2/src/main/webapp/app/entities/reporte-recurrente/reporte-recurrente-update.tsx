import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IReporteRecurrente } from 'app/shared/model/reporte-recurrente.model';
import { getEntity, updateEntity, createEntity, reset } from './reporte-recurrente.reducer';

export const ReporteRecurrenteUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const reporteRecurrenteEntity = useAppSelector(state => state.reporteRecurrente.entity);
  const loading = useAppSelector(state => state.reporteRecurrente.loading);
  const updating = useAppSelector(state => state.reporteRecurrente.updating);
  const updateSuccess = useAppSelector(state => state.reporteRecurrente.updateSuccess);

  const handleClose = () => {
    navigate('/reporte-recurrente');
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
      ...reporteRecurrenteEntity,
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
          ...reporteRecurrenteEntity,
          fechaInicio: convertDateTimeFromServer(reporteRecurrenteEntity.fechaInicio),
          fechaFin: convertDateTimeFromServer(reporteRecurrenteEntity.fechaFin),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="reportes2App.reporteRecurrente.home.createOrEditLabel" data-cy="ReporteRecurrenteCreateUpdateHeading">
            <Translate contentKey="reportes2App.reporteRecurrente.home.createOrEditLabel">Create or edit a ReporteRecurrente</Translate>
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
                  id="reporte-recurrente-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('reportes2App.reporteRecurrente.accion')}
                id="reporte-recurrente-accion"
                name="accion"
                data-cy="accion"
                type="text"
              />
              <ValidatedField
                label={translate('reportes2App.reporteRecurrente.franquiciaUUID')}
                id="reporte-recurrente-franquiciaUUID"
                name="franquiciaUUID"
                data-cy="franquiciaUUID"
                type="text"
              />
              <ValidatedField
                label={translate('reportes2App.reporteRecurrente.tipo')}
                id="reporte-recurrente-tipo"
                name="tipo"
                data-cy="tipo"
                type="text"
              />
              <ValidatedField
                label={translate('reportes2App.reporteRecurrente.fechaInicio')}
                id="reporte-recurrente-fechaInicio"
                name="fechaInicio"
                data-cy="fechaInicio"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('reportes2App.reporteRecurrente.fechaFin')}
                id="reporte-recurrente-fechaFin"
                name="fechaFin"
                data-cy="fechaFin"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('reportes2App.reporteRecurrente.intervalo')}
                id="reporte-recurrente-intervalo"
                name="intervalo"
                data-cy="intervalo"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reporte-recurrente" replace color="info">
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

export default ReporteRecurrenteUpdate;
