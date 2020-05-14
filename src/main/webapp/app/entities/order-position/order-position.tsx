import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './order-position.reducer';
import { IOrderPosition } from 'app/shared/model/order-position.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderPositionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const OrderPosition = (props: IOrderPositionProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { orderPositionList, match, loading } = props;
  return (
    <div>
      <h2 id="order-position-heading">
        <Translate contentKey="mainApp.orderPosition.home.title">Order Positions</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="mainApp.orderPosition.home.createLabel">Create new Order Position</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {orderPositionList && orderPositionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="mainApp.orderPosition.quantity">Quantity</Translate>
                </th>
                <th>
                  <Translate contentKey="mainApp.orderPosition.product">Product</Translate>
                </th>
                <th>
                  <Translate contentKey="mainApp.orderPosition.orderEntity">Order Entity</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orderPositionList.map((orderPosition, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${orderPosition.id}`} color="link" size="sm">
                      {orderPosition.id}
                    </Button>
                  </td>
                  <td>{orderPosition.quantity}</td>
                  <td>{orderPosition.product ? <Link to={`product/${orderPosition.product.id}`}>{orderPosition.product.id}</Link> : ''}</td>
                  <td>
                    {orderPosition.orderEntity ? (
                      <Link to={`order-entity/${orderPosition.orderEntity.id}`}>{orderPosition.orderEntity.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${orderPosition.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderPosition.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${orderPosition.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="mainApp.orderPosition.home.notFound">No Order Positions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ orderPosition }: IRootState) => ({
  orderPositionList: orderPosition.entities,
  loading: orderPosition.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderPosition);