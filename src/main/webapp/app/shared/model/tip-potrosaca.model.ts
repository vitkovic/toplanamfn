import { IStan } from '@/shared/model/stan.model';

export interface ITipPotrosaca {
  id?: number;
  tip?: number;
  stans?: IStan[];
}

export class TipPotrosaca implements ITipPotrosaca {
  constructor(public id?: number, public tip?: number, public stans?: IStan[]) {}
}
