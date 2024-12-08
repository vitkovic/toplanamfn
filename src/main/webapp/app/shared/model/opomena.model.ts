import { IStan } from '@/shared/model/stan.model';

export interface IOpomena {
  id?: number;
  datumOpomene?: Date;
  zakljucniDatum?: Date;
  iznos?: number;
  datumIzmirenja?: Date;
  stan?: IStan;
}

export class Opomena implements IOpomena {
  constructor(
    public id?: number,
    public datumOpomene?: Date,
    public zakljucniDatum?: Date,
    public iznos?: number,
    public datumIzmirenja?: Date,
    public stan?: IStan
  ) {}
}
