import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMenu } from 'app/shared/model/menu.model';
import { getEntity, updateEntity, createEntity, reset } from './menu.reducer';

export const MenuUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const menuEntity = useAppSelector(state => state.menu.entity);
  const loading = useAppSelector(state => state.menu.loading);
  const updating = useAppSelector(state => state.menu.updating);
  const updateSuccess = useAppSelector(state => state.menu.updateSuccess);

  const handleClose = () => {
    navigate('/menu');
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
    values.creado = convertDateTimeToServer(values.creado);
    values.actualizado = convertDateTimeToServer(values.actualizado);

    const entity = {
      ...menuEntity,
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
          creado: displayDefaultDateTime(),
          actualizado: displayDefaultDateTime(),
        }
      : {
          ...menuEntity,
          creado: convertDateTimeFromServer(menuEntity.creado),
          actualizado: convertDateTimeFromServer(menuEntity.actualizado),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="franquiciaApp.menu.home.createOrEditLabel" data-cy="MenuCreateUpdateHeading">
            <Translate contentKey="franquiciaApp.menu.home.createOrEditLabel">Create or edit a Menu</Translate>
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
                  id="menu-id"
                  label={translate('franquiciaApp.menu.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('franquiciaApp.menu.nombre')} id="menu-nombre" name="nombre" data-cy="nombre" type="text" />
              <ValidatedField
                label={translate('franquiciaApp.menu.descripcion')}
                id="menu-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField label={translate('franquiciaApp.menu.precio')} id="menu-precio" name="precio" data-cy="precio" type="text" />
              <ValidatedField
                label={translate('franquiciaApp.menu.urlImagen')}
                id="menu-urlImagen"
                name="urlImagen"
                data-cy="urlImagen"
                type="text"
              />
              <ValidatedField
                label={translate('franquiciaApp.menu.activo')}
                id="menu-activo"
                name="activo"
                data-cy="activo"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('franquiciaApp.menu.creado')}
                id="menu-creado"
                name="creado"
                data-cy="creado"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('franquiciaApp.menu.actualizado')}
                id="menu-actualizado"
                name="actualizado"
                data-cy="actualizado"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/menu" replace color="info">
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

export default MenuUpdate;
