import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderEntity from './order-entity';
import OrderPosition from './order-position';
import Product from './product';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/order-entity`} component={OrderEntity} />
      <ErrorBoundaryRoute path={`${match.url}/order-position`} component={OrderPosition} />
      <ErrorBoundaryRoute path={`${match.url}/product`} component={Product} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
