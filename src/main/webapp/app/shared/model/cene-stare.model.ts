export interface ICeneStare {
  id?: number;
  kwh?: number;
  fix?: number;
  fixIskljuceno?: number;
  odrzavanje?: number;
  ostalo?: number;
}

export class CeneStare implements ICeneStare {
  constructor(public id?: number, public kwh?: number, 
    public fix?: number, 
    public fixIskljuceno?: number,
    public odrzavanje?: number, public ostalo?: number) {}
}
