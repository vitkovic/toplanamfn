import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';

export interface IIzvod {
  id?: number;
  datumIzvodaZaglavlje?: Date;
  brojIzvoda?: number;
  stavkaIzvodas?: IStavkeIzvoda[];
}

export class Izvod implements IIzvod {
  constructor(public id?: number, public datumIzvodaZaglavlje?: Date, public brojIzvoda?: number, public stavkaIzvodas?: IStavkeIzvoda[]) {}
}
