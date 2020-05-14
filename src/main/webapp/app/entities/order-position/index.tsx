import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import OrderPosition from './order-position';
import OrderPositionDetail from './order-position-detail';
import OrderPositionUpdate from './order-position-update';
import OrderPositionDeleteDialog from './order-position-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrderPositionDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrderPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrderPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrderPositionDetail} />
      <ErrorBoundaryRoute path={match.url} component={OrderPosition} />
    </Switch>
  </>
);

export default Routes;
