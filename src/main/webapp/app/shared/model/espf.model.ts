import { Moment } from 'moment';

export interface IESPF {
  id?: number;
  code?: string;
  date?: Moment;
}

export const defaultValue: Readonly<IESPF> = {};
