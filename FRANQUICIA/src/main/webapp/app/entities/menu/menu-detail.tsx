import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './menu.reducer';

export const MenuDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const menuEntity = useAppSelector(state => state.menu.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="menuDetailsHeading">
          <Translate contentKey="franquiciaApp.menu.detail.title">Menu</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="franquiciaApp.menu.id">Id</Translate>
            </span>
          </dt>
          <dd>{menuEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="franquiciaApp.menu.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{menuEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="franquiciaApp.menu.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{menuEntity.descripcion}</dd>
          <dt>
            <span id="precio">
              <Translate contentKey="franquiciaApp.menu.precio">Precio</Translate>
            </span>
          </dt>
          <dd>{menuEntity.precio}</dd>
          <dt>
            <span id="urlImagen">
              <Translate contentKey="franquiciaApp.menu.urlImagen">Url Imagen</Translate>
            </span>
          </dt>
          <dd>{menuEntity.urlImagen}</dd>
          <dt>
            <span id="activo">
              <Translate contentKey="franquiciaApp.menu.activo">Activo</Translate>
            </span>
          </dt>
          <dd>{menuEntity.activo ? 'true' : 'false'}</dd>
          <dt>
            <span id="creado">
              <Translate contentKey="franquiciaApp.menu.creado">Creado</Translate>
            </span>
          </dt>
          <dd>{menuEntity.creado ? <TextFormat value={menuEntity.creado} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="actualizado">
              <Translate contentKey="franquiciaApp.menu.actualizado">Actualizado</Translate>
            </span>
          </dt>
          <dd>{menuEntity.actualizado ? <TextFormat value={menuEntity.actualizado} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/menu" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/menu/${menuEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MenuDetail;
