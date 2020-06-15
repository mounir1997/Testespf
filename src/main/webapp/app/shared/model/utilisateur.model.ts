export interface IUtilisateur {
  id?: number;
  nom?: string;
  mdp?: string;
  role?: string;
}

export const defaultValue: Readonly<IUtilisateur> = {};
