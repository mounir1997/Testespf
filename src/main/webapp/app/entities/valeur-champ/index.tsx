import * as React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ValeurChamp from './valeur-champ';
import ValeurChampDetail from './valeur-champ-detail';
import ValeurChampUpdate from './valeur-champ-update';
import ValeurChampDeleteDialog from './valeur-champ-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ValeurChampUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ValeurChampUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ValeurChampDetail} />
      <ErrorBoundaryRoute path={match.url} component={ValeurChamp} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ValeurChampDeleteDialog} />
  </>
);

export default Routes;
