import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Utilisateur from './utilisateur';
import Projet from './projet';
import PivotModel from './pivot-model';
import PivotSociete from './pivot-societe';
import ESPF from './espf';
import ValeurChamp from './valeur-champ';
import LigneESPF from './ligne-espf';
import Champs from './champs';
import Attribut from './attribut';
import Valeurs from './valeurs';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/utilisateur`} component={Utilisateur} />
      <ErrorBoundaryRoute path={`${match.url}/projet`} component={Projet} />
      <ErrorBoundaryRoute path={`${match.url}/pivot-model`} component={PivotModel} />
      <ErrorBoundaryRoute path={`${match.url}/pivot-societe`} component={PivotSociete} />
      <ErrorBoundaryRoute path={`${match.url}/espf`} component={ESPF} />
      <ErrorBoundaryRoute path={`${match.url}/valeur-champ`} component={ValeurChamp} />
      <ErrorBoundaryRoute path={`${match.url}/ligne-espf`} component={LigneESPF} />
      <ErrorBoundaryRoute path={`${match.url}/champs`} component={Champs} />
      <ErrorBoundaryRoute path={`${match.url}/attribut`} component={Attribut} />
      <ErrorBoundaryRoute path={`${match.url}/valeurs`} component={Valeurs} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
