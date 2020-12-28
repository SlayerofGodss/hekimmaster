export interface IPatient {
  id?: number;
  firstName?: string;
  lastName?: string;
  age?: number;
}

export class Patient implements IPatient {
  constructor(public id?: number, public firstName?: string, public lastName?: string, public age?: number) {}
}
