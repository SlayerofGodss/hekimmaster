export interface IPatient {
  id?: number;
  firstName?: string;
  lastName?: string;
  age?: number;
  tc?: string;
  sigorta?: string;
}

export class Patient implements IPatient {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public age?: number,
    public tc?: string,
    public sigorta?: string
  ) {}
}
