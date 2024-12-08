import { IStavkeUtuzenja } from '@/shared/model/stavke-utuzenja.model';
import { IStan } from '@/shared/model/stan.model';

export interface IUtuzenje {
  id?: number;
  datum?: Date;
  stavkeUtuzenjas?: IStavkeUtuzenja[];
  stan?: IStan;
}

export class Utuzenje implements IUtuzenje {
  constructor(public id?: number, public datum?: Date, public stavkeUtuzenjas?: IStavkeUtuzenja[], public stan?: IStan) {}
}
