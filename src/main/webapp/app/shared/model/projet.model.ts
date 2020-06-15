import { Moment } from 'moment';
import { IUtilisateur } from './utilisateur.model';

export interface IProjet {
  id?: number;
  nom?: string;
  devise?: string;
  periodicite?: string;
  date?: Moment;
  utilisateur?: IUtilisateur;
}

export const defaultValue: Readonly<IProjet> = {};
