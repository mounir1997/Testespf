import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ESPF from './espf';
import ESPFDetail from './espf-detail';
import ESPFUpdate from './espf-update';
import ESPFDeleteDialog from './espf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ESPFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ESPFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ESPFDetail} />
      <ErrorBoundaryRoute path={match.url} component={ESPF} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ESPFDeleteDialog} />
  </>
);

export default Routes;
