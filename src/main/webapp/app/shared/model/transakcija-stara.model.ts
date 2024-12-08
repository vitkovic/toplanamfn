import { IVrstaTransakcije } from '@/shared/model/vrsta-transakcije.model';
import { ISifraDokumenta } from '@/shared/model/sifra-dokumenta.model';
import { ISifraPromene } from '@/shared/model/sifra-promene.model';
import { ICeneStare } from '@/shared/model/cene-stare.model';
import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';
import { IStan } from '@/shared/model/stan.model';

export interface ITransakcijaStara {
  id?: number;
  datum?: Date;
  valuta?: Date;
  datumDokumenta?: Date;
  status?: string;
  opis?: string;
  duguje?: number;
  potrazuje?: number;
  vrstaTransakcije?: IVrstaTransakcije;
  sifraDokumenta?: string;
  sifraPromene?: ISifraPromene;
  cene?: ICeneStare;
  stavkaIzvoda?: IStavkeIzvoda;
  stan?: IStan;
}

export class TransakcijaStara implements ITransakcijaStara {
  constructor(
    public id?: number,
    public datum?: Date,
    public valuta?: Date,
    public datumDokumenta?: Date,
    public status?: string,
    public opis?: string,
    public duguje?: number,
    public potrazuje?: number,
    public vrstaTransakcije?: IVrstaTransakcije,
    public sifraDokumenta?: string,
    public sifraPromene?: ISifraPromene,
    public cene?: ICeneStare,
    public stavkaIzvoda?: IStavkeIzvoda,
    public stan?: IStan
  ) {}
}
