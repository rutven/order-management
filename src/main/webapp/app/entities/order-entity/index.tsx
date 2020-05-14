import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderEntity from './order-entity';
import OrderEntityDetail from './order-entity-detail';
import OrderEntityUpdate from './order-entity-update';
import OrderEntityDeleteDialog from './order-entity-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrderEntityDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderEntityUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderEntityDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderEntity} />
    </Switch>
  </>
);

export default Routes;
