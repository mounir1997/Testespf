import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import LigneESPF from './ligne-espf';
import LigneESPFDetail from './ligne-espf-detail';
import LigneESPFUpdate from './ligne-espf-update';
import LigneESPFDeleteDialog from './ligne-espf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LigneESPFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LigneESPFUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LigneESPFDetail} />
      <ErrorBoundaryRoute path={match.url} component={LigneESPF} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LigneESPFDeleteDialog} />
  </>
);

export default Routes;
