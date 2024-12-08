export interface ICene {
  id?: number;
  kwh?: number;
  fix?: number;
  fixIskljuceno?: number;
  odrzavanje?: number;
  ostalo?: number;
}

export class Cene implements ICene {
  constructor(public id?: number, public kwh?: number, 
    public fix?: number, 
    public fixIskljuceno?: number,
    public odrzavanje?: number, public ostalo?: number) {}
}
