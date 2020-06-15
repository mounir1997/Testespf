import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Valeurs from './valeurs';
import ValeursDetail from './valeurs-detail';
import ValeursUpdate from './valeurs-update';
import ValeursDeleteDialog from './valeurs-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ValeursUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ValeursUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ValeursDetail} />
      <ErrorBoundaryRoute path={match.url} component={Valeurs} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ValeursDeleteDialog} />
  </>
);

export default Routes;
