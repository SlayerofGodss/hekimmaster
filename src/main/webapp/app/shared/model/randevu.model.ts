export interface IRandevu {
  id?: number;
  randevu?: string;
  patientLastName?: string;
  patientId?: number;
}

export class Randevu implements IRandevu {
  constructor(public id?: number, public randevu?: string, public patientLastName?: string, public patientId?: number) {}
}
