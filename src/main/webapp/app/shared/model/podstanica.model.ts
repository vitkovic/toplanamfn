import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';
import { IStan } from '@/shared/model/stan.model';
import { IStanjaPodstanice } from '@/shared/model/stanja-podstanice.model';

export interface IPodstanica {
  id?: number;
  naziv?: string;
  nacrtRacunas?: INacrtRacuna[];
  stans?: IStan[];
  stanjePodstanices?: IStanjaPodstanice[];
  broj?: number;
}

export class Podstanica implements IPodstanica { 
  constructor(
    public id?: number,
    public naziv?: string,
    public nacrtRacunas?: INacrtRacuna[],
    public stans?: IStan[],
    public stanjePodstanices?: IStanjaPodstanice[],
    public broj?: number,
  ) {}
}
