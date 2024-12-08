import { ITransakcija } from '@/shared/model/transakcija.model';
import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';
import { IIzvod } from '@/shared/model/izvod.model';

export interface IStavkeIzvoda {
  id?: number;
  rasporedjena?: boolean;
  datum?: Date;
  status?: string;
  iznos?: number;
  sifra?: string;
  opis?: string;
  transakcija?: ITransakcija;
  transakcijaStara?: ITransakcijaStara;
  izvod?: IIzvod;
}

export class StavkeIzvoda implements IStavkeIzvoda {
  constructor(
    public id?: number,
    public rasporedjena?: boolean,
    public datum?: Date,
    public status?: string,
    public iznos?: number,
    public sifra?: string,
    public opis?: string,
    public transakcija?: ITransakcija,
    public transakcijaStara?: ITransakcijaStara,
    public izvod?: IIzvod
  ) {
    this.rasporedjena = this.rasporedjena || false;
  }
}
