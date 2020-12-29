export interface IPatientNote {
  id?: number;
  patientNote?: string;
}

export class PatientNote implements IPatientNote {
  constructor(public id?: number, public patientNote?: string) {}
}
