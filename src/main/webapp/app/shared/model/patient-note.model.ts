export interface IPatientNote {
  id?: number;
  patientNote?: string;
  patientLastName?: string;
  patientId?: number;
}

export class PatientNote implements IPatientNote {
  constructor(public id?: number, public patientNote?: string, public patientLastName?: string, public patientId?: number) {}
}
