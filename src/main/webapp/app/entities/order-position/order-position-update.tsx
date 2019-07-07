import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IOrderEntity } from 'app/shared/model/order-entity.model';
import { getEntities as getOrderEntities } from 'app/entities/order-entity/order-entity.reducer';
import { getEntity, updateEntity, createEntity, reset } from './order-position.reducer';
import { IOrderPosition } from 'app/shared/model/order-position.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOrderPositionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IOrderPositionUpdateState {
  isNew: boolean;
  productId: string;
  orderEntityId: string;
}

export class OrderPositionUpdate extends React.Component<IOrderPositionUpdateProps, IOrderPositionUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      productId: '0',
      orderEntityId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getProducts();
    this.props.getOrderEntities();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { orderPositionEntity } = this.props;
      const entity = {
        ...orderPositionEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/order-position');
  };

  render() {
    const { orderPositionEntity, products, orderEntities, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="mainApp.orderPosition.home.createOrEditLabel">
              <Translate contentKey="mainApp.orderPosition.home.createOrEditLabel">Create or edit a OrderPosition</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : orderPositionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="order-position-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="order-position-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="quantityLabel" for="order-position-quantity">
                    <Translate contentKey="mainApp.orderPosition.quantity">Quantity</Translate>
                  </Label>
                  <AvField
                    id="order-position-quantity"
                    type="string"
                    className="form-control"
                    name="quantity"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="order-position-product">
                    <Translate contentKey="mainApp.orderPosition.product">Product</Translate>
                  </Label>
                  <AvInput id="order-position-product" type="select" className="form-control" name="product.id">
                    <option value="" key="0" />
                    {products
                      ? products.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="order-position-orderEntity">
                    <Translate contentKey="mainApp.orderPosition.orderEntity">Order Entity</Translate>
                  </Label>
                  <AvInput id="order-position-orderEntity" type="select" className="form-control" name="orderEntity.id">
                    <option value="" key="0" />
                    {orderEntities
                      ? orderEntities.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/order-position" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  products: storeState.product.entities,
  orderEntities: storeState.orderEntity.entities,
  orderPositionEntity: storeState.orderPosition.entity,
  loading: storeState.orderPosition.loading,
  updating: storeState.orderPosition.updating,
  updateSuccess: storeState.orderPosition.updateSuccess
});

const mapDispatchToProps = {
  getProducts,
  getOrderEntities,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderPositionUpdate);
