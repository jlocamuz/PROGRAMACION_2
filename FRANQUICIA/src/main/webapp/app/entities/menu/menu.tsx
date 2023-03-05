import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMenu } from 'app/shared/model/menu.model';
import { getEntities } from './menu.reducer';

export const Menu = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const menuList = useAppSelector(state => state.menu.entities);
  const loading = useAppSelector(state => state.menu.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="menu-heading" data-cy="MenuHeading">
        <Translate contentKey="franquiciaApp.menu.home.title">Menus</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="franquiciaApp.menu.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/menu/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="franquiciaApp.menu.home.createLabel">Create new Menu</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {menuList && menuList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="franquiciaApp.menu.id">Id</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.menu.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.menu.descripcion">Descripcion</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.menu.precio">Precio</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.menu.urlImagen">Url Imagen</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.menu.activo">Activo</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.menu.creado">Creado</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.menu.actualizado">Actualizado</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {menuList.map((menu, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/menu/${menu.id}`} color="link" size="sm">
                      {menu.id}
                    </Button>
                  </td>
                  <td>{menu.nombre}</td>
                  <td>{menu.descripcion}</td>
                  <td>{menu.precio}</td>
                  <td>{menu.urlImagen}</td>
                  <td>{menu.activo ? 'true' : 'false'}</td>
                  <td>{menu.creado ? <TextFormat type="date" value={menu.creado} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{menu.actualizado ? <TextFormat type="date" value={menu.actualizado} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/menu/${menu.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/menu/${menu.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/menu/${menu.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="franquiciaApp.menu.home.notFound">No Menus found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Menu;
