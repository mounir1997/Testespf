import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PivotSociete from './pivot-societe';
import PivotSocieteDetail from './pivot-societe-detail';
import PivotSocieteUpdate from './pivot-societe-update';
import PivotSocieteDeleteDialog from './pivot-societe-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PivotSocieteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PivotSocieteUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PivotSocieteDetail} />
      <ErrorBoundaryRoute path={match.url} component={PivotSociete} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PivotSocieteDeleteDialog} />
  </>
);

export default Routes;
