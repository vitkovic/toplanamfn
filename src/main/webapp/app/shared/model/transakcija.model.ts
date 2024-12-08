import { IVrstaTransakcije } from '@/shared/model/vrsta-transakcije.model';
import { ISifraDokumenta } from '@/shared/model/sifra-dokumenta.model';
import { ISifraPromene } from '@/shared/model/sifra-promene.model';
import { ICene } from '@/shared/model/cene.model';
import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';
import { IStan } from '@/shared/model/stan.model';
import { IOstaliRacuni } from '@/shared/model/ostali-racuni.model';

export interface ITransakcija {
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
  cene?: ICene;
  stavkaIzvoda?: IStavkeIzvoda;
  stan?: IStan;
  ostaliRacuni?: IOstaliRacuni;
}

export class Transakcija implements ITransakcija {
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
    public cene?: ICene,
    public stavkaIzvoda?: IStavkeIzvoda,
    public stan?: IStan,
    public ostaliRacuni?: IOstaliRacuni,
  ) {}
}
