import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVenta } from 'app/shared/model/venta.model';
import { getEntities } from './venta.reducer';

export const Venta = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const ventaList = useAppSelector(state => state.venta.entities);
  const loading = useAppSelector(state => state.venta.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="venta-heading" data-cy="VentaHeading">
        <Translate contentKey="franquiciaApp.venta.home.title">Ventas</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="franquiciaApp.venta.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/venta/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="franquiciaApp.venta.home.createLabel">Create new Venta</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ventaList && ventaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="franquiciaApp.venta.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.venta.fecha">Fecha</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.venta.precio">Precio</Translate>
                </th>
                <th>
                  <Translate contentKey="franquiciaApp.venta.menu">Menu</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ventaList.map((venta, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/venta/${venta.id}`} color="link" size="sm">
                      {venta.id}
                    </Button>
                  </td>
                  <td>{venta.fecha ? <TextFormat type="date" value={venta.fecha} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{venta.precio}</td>
                  <td>{venta.menu ? <Link to={`/menu/${venta.menu.id}`}>{venta.menu.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/venta/${venta.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/venta/${venta.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/venta/${venta.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="franquiciaApp.venta.home.notFound">No Ventas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Venta;
