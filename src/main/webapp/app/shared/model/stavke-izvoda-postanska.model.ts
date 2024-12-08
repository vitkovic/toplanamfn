import { ITransakcija } from '@/shared/model/transakcija.model';
import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';
import { IIzvod } from '@/shared/model/izvod.model';

export interface IStavkeIzvodaPostanska {
  id?: number;
  broj?:number;
  rasporedjena?: boolean;
  valuta?: Date;
  npl?: string;
  iznos?: number;
  sifra?: string;
  brojPartijePoverioc?: string;
  brojTekucegRacuna?: string;
  ime?: string;
  adresa?: string;
  posta?: string;
  transakcija?: ITransakcija;
  transakcijaStara?: ITransakcijaStara;
  izvod?: IIzvod;
}

export class StavkeIzvodaPostanska implements IStavkeIzvodaPostanska {
  constructor(
    public id?: number,
    public broj?: number,
    public rasporedjena?: boolean,
    public valuta?: Date,
    public npl?: string,
    public iznos?: number,
    public brojPartijePoverioc?: string,
    public brojTekucegRacuna?: string,
    public ime?: string,
    public adresa?: string,
    public posta?: string,
    public transakcija?: ITransakcija,
    public transakcijaStara?: ITransakcijaStara,
    public izvod?: IIzvod
  ) {
    this.rasporedjena = this.rasporedjena || false;
  }
}
