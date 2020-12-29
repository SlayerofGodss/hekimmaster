export interface IShortPatientNote {
  id?: number;
  shortPatientNote?: string;
}

export class ShortPatientNote implements IShortPatientNote {
  constructor(public id?: number, public shortPatientNote?: string) {}
}
