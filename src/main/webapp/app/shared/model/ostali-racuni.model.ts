import { IStan } from '@/shared/model/stan.model';
import { ITransakcija } from '@/shared/model/transakcija.model';
import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';

export interface IOstaliRacuni {
  id?: number;
  naziv?: string;
  sifra?: string;  
  stan?: IStan;
  aktivan?: boolean;
  transakcijas?: ITransakcija[];
  transakcijaStaras?: ITransakcijaStara[];
}

export class OstaliRacuni implements IOstaliRacuni {
  constructor(
    public id?: number,    
    public naziv?: string,
    public sifra?: string,  
    public stan?: IStan,
    public aktivan?: boolean,
    public transakcijas?: ITransakcija[],
    public transakcijaStaras?: ITransakcijaStara[],
  ) {}
}
