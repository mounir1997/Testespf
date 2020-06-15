import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Champs from './champs';
import ChampsDetail from './champs-detail';
import ChampsUpdate from './champs-update';
import ChampsDeleteDialog from './champs-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ChampsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ChampsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ChampsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Champs} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ChampsDeleteDialog} />
  </>
);

export default Routes;
