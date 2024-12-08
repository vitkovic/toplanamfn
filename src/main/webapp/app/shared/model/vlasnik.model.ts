import { IStan } from '@/shared/model/stan.model';

export interface IVlasnik {
  id?: number;
  ime?: string;
  prezime?: string;
  brojRacuna?: string;
  partijaRacuna?: string;
  naziv?: string;
  stans?: IStan[];
}

export class Vlasnik implements IVlasnik {
  constructor(
    public id?: number,
    public ime?: string,
    public prezime?: string,
    public brojRacuna?: string,
    public partijaRacuna?: string,
    public naziv?: string,
    public stans?: IStan[]
  ) {}
}
