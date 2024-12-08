import { IRacun } from '@/shared/model/racun.model';
import { IPodstanica } from '@/shared/model/podstanica.model';
import { IStanjaPodstaniceZaRacun } from '@/shared/model/stanja-podstanice-za-racun.model';

export interface INacrtRacuna {
  id?: number;
  datumRacuna?: Date;
  period?: string;
  valutaPlacanja?: Date;
  cenaKwh?: number;
  cenaFix?: number;
  cenaFixIskljucen?: number;
  cenaOdrzavanje?: number;
  cenaOStalo?: number;
  popust?: number;  
  pdv1?: number;
  pdv2?: number;
  proknjizen?: boolean;
  stanjaPodstaniceZaRacune?: IStanjaPodstaniceZaRacun[];
  ukupnoStanjeStaro?: number,
  ukupnoStanjeNovo?: number,
  potrosnja?: number
 // racuns?: IRacun[];
 // podstanica?: IPodstanica;
}

export class NacrtRacuna implements INacrtRacuna {
  constructor(
    public id?: number,
    public datumRacuna?: Date,
    public period?: string,
    public valutaPlacanja?: Date,
    public cenaKwh?: number,
    public cenaFix?: number,
    public cenaFixIskljucen?: number,
    public cenaOdrzavanje?: number,
    public cenaOStalo?: number,
    public popust?: number,   
    public pdv1?: number,
    public pdv2?: number,
    public proknjizen?: boolean,
    public stanjaPodstaniceZaRacune?: IStanjaPodstaniceZaRacun[],
    public ukupnoStanjeStaro?: number,
    public ukupnoStanjeNovo?: number,
    public potrosnja?: number

   // public racuns?: IRacun[],
   // public podstanica?: IPodstanica
  ) {}
}
