import { IOpomena } from '@/shared/model/opomena.model';
import { IUtuzenje } from '@/shared/model/utuzenje.model';
import { IUgovorRate } from '@/shared/model/ugovor-rate.model';
import { ITransakcija } from '@/shared/model/transakcija.model';
import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';
import { IRacun } from '@/shared/model/racun.model';
import { ITipPotrosaca } from '@/shared/model/tip-potrosaca.model';
import { IPodstanica } from '@/shared/model/podstanica.model';
import { IVlasnik } from '@/shared/model/vlasnik.model';

export interface IStan {
  id?: number;
  povrsina?: number;
  ulica?: string;
  status?: string;
  ulaz?: number;
  broj?: number;
  ukljucen?: boolean;
  sifra?: string;
  grad?: string;
  postanskiBroj?: string;
  opomenas?: IOpomena[];
  utuzenjes?: IUtuzenje[];
  ugovorRates?: IUgovorRate[];
  transakcijas?: ITransakcija[];
  transakcijaStaras?: ITransakcijaStara[];
  racuns?: IRacun[];
  tipPotrosaca?: ITipPotrosaca;
  podstanica?: IPodstanica;
  vlasnik?: IVlasnik;
}

export class Stan implements IStan {
  constructor(
    public id?: number,
    public povrsina?: number,
    public ulica?: string,
    public status?: string,
    public ulaz?: number,
    public broj?: number,
    public ukljucen?: boolean,
    public sifra?: string,
    public grad?: string,
    public postanskiBroj?: string,
    public opomenas?: IOpomena[],
    public utuzenjes?: IUtuzenje[],
    public ugovorRates?: IUgovorRate[],
    public transakcijas?: ITransakcija[],
    public transakcijaStaras?: ITransakcijaStara[],
    public racuns?: IRacun[],
    public tipPotrosaca?: ITipPotrosaca,
    public podstanica?: IPodstanica,
    public vlasnik?: IVlasnik
  ) {
    this.ukljucen = this.ukljucen || false;
  }
}
