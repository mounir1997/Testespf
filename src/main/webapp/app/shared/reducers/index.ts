import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import utilisateur, {
  UtilisateurState
} from 'app/entities/utilisateur/utilisateur.reducer';
// prettier-ignore
import projet, {
  ProjetState
} from 'app/entities/projet/projet.reducer';
// prettier-ignore
import pivotModel, {
  PivotModelState
} from 'app/entities/pivot-model/pivot-model.reducer';
// prettier-ignore
import pivotSociete, {
  PivotSocieteState
} from 'app/entities/pivot-societe/pivot-societe.reducer';
// prettier-ignore
import eSPF, {
  ESPFState
} from 'app/entities/espf/espf.reducer';
// prettier-ignore
import valeurChamp, {
  ValeurChampState
} from 'app/entities/valeur-champ/valeur-champ.reducer';
// prettier-ignore
import ligneESPF, {
  LigneESPFState
} from 'app/entities/ligne-espf/ligne-espf.reducer';
// prettier-ignore
import champs, {
  ChampsState
} from 'app/entities/champs/champs.reducer';
// prettier-ignore
import attribut, {
  AttributState
} from 'app/entities/attribut/attribut.reducer';
// prettier-ignore
import valeurs, {
  ValeursState
} from 'app/entities/valeurs/valeurs.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly utilisateur: UtilisateurState;
  readonly projet: ProjetState;
  readonly pivotModel: PivotModelState;
  readonly pivotSociete: PivotSocieteState;
  readonly eSPF: ESPFState;
  readonly valeurChamp: ValeurChampState;
  readonly ligneESPF: LigneESPFState;
  readonly champs: ChampsState;
  readonly attribut: AttributState;
  readonly valeurs: ValeursState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  utilisateur,
  projet,
  pivotModel,
  pivotSociete,
  eSPF,
  valeurChamp,
  ligneESPF,
  champs,
  attribut,
  valeurs,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
