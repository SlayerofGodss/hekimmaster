import { Moment } from 'moment';

export interface IDay {
  id?: number;
  randevuDate?: Moment;
}

export class Day implements IDay {
  constructor(public id?: number, public randevuDate?: Moment) {}
}
