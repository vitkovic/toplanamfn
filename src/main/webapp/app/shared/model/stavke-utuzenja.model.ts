import { IUtuzenje } from '@/shared/model/utuzenje.model';

export interface IStavkeUtuzenja {
  id?: number;
  obracunskiPeriod?: string;
  datumIzdavanjaRacuna?: Date;
  datumDospecaRacuna?: Date;
  zaduzenje?: number;
  ukupnoZaUplatu?: number;
  utuzenje?: IUtuzenje;
}

export class StavkeUtuzenja implements IStavkeUtuzenja {
  constructor(
    public id?: number,
    public obracunskiPeriod?: string,
    public datumIzdavanjaRacuna?: Date,
    public datumDospecaRacuna?: Date,
    public zaduzenje?: number,
    public ukupnoZaUplatu?: number,
    public utuzenje?: IUtuzenje
  ) {}
}
