import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-position.reducer';
import { IOrderPosition } from 'app/shared/model/order-position.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderPositionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OrderPositionDetail = (props: IOrderPositionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { orderPositionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="mainApp.orderPosition.detail.title">OrderPosition</Translate> [<b>{orderPositionEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="quantity">
              <Translate contentKey="mainApp.orderPosition.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{orderPositionEntity.quantity}</dd>
          <dt>
            <Translate contentKey="mainApp.orderPosition.product">Product</Translate>
          </dt>
          <dd>{orderPositionEntity.product ? orderPositionEntity.product.id : ''}</dd>
          <dt>
            <Translate contentKey="mainApp.orderPosition.orderEntity">Order Entity</Translate>
          </dt>
          <dd>{orderPositionEntity.orderEntity ? orderPositionEntity.orderEntity.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-position" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-position/${orderPositionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ orderPosition }: IRootState) => ({
  orderPositionEntity: orderPosition.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OrderPositionDetail);
