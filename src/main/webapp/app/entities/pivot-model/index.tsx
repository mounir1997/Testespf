import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PivotModel from './pivot-model';
import PivotModelDetail from './pivot-model-detail';
import PivotModelUpdate from './pivot-model-update';
import PivotModelDeleteDialog from './pivot-model-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PivotModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PivotModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PivotModelDetail} />
      <ErrorBoundaryRoute path={match.url} component={PivotModel} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PivotModelDeleteDialog} />
  </>
);

export default Routes;
