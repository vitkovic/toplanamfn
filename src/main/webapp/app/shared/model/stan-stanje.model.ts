import { IStan } from '@/shared/model/stan.model';

export interface IStanStanje {
  id?: number;
  sifra?: string;
  datum?: Date;
  vrednost?: number;
  stan_id?: number;
  stan?: IStan;
}

export class StanStanje implements IStanStanje {
  constructor(
    public id?: number,
    public sifra?: string,
    public datum?: Date,
    public vrednost?: number,
    public stan_id?: number,
    public stan?: IStan
  ) {}
}
