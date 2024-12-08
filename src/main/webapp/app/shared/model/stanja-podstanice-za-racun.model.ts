import { IPodstanica } from '@/shared/model/podstanica.model';
import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';
import { IStanjaPodstanice } from '@/shared/model/stanja-podstanice.model';

export interface IStanjaPodstaniceZaRacun {
  id?: number;
  staroStanje?: IStanjaPodstanice;  
  novoStanje?: IStanjaPodstanice;
  ukupnaPovrsina?: number;
  utrosakPoM2?: number;
  podstanicas?: IPodstanica[];
  nacrtRacunas?: INacrtRacuna[];
}

export class StanjaPodstaniceZaRacun implements IStanjaPodstaniceZaRacun {
  constructor(
    public id?: number,
    public staroStanje?: IStanjaPodstanice,
    public novoStanje?: IStanjaPodstanice,    
    public ukupnaPovrsina?: number,
    public utrosakPoM2?: number,
    public podstanicas?: IPodstanica[],
    public nacrtRacunas?: INacrtRacuna[]
  ) {}
}
