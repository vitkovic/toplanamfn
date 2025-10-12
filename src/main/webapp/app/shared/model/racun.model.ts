import { IStan } from '@/shared/model/stan.model';
import { IUser } from '@/shared/model/user.model';
import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';
import { IStanjaPodstaniceZaRacun } from '@/shared/model/stanja-podstanice-za-racun.model';

export interface IRacun {
  id?: number;
  datumRacuna?: Date;
  period?: string;
  utrosakVarijabilni?: number;
  utrosakFiksni?: number;
  utrosakOdrzavanje?: number;
  proknjizen?: boolean;
  stan?: IStan;
  nacrtRacuna?: INacrtRacuna;
  azuriran?: boolean;
  valutaPlacanja?: Date;
  cenaFix?: number;
  cenaFixIskljucen?: number;
  cenaKwh?: number;
  cenaOdrzavanje?: number;
  cenaOStalo?: number;
  kreirao?: IUser;
  pdv1?: number;
  pdv2?: number;    	  
  ukupnoZaduzenje?: number;    	 
  utrosakUKwh?: number;
  popust?: number;
  zaPlacanje?: number;
  utrosakVarijabilniBezPopusta?: number;
	utrosakFiksniBezPopusta?: number;	 
	utrosakVarijabilniPopust?: number;
	utrosakFiksniPopust?: number;
  utrosakVarijabilniBezPdv?: number;
  utrosakFiksniBezPdv?: number;
  utrosakOdrzavanjeBezPdv?: number;
  utrosakVarijabilniPdv?: number;
  utrosakFiksniPdv?: number;
  utrosakOdrzavanjePdv?: number;
  datumSaldiranja?: Date;
  stanjeZaracun?: IStanjaPodstaniceZaRacun;
  prevNextRacuni?:IRacun;

}

export class Racun implements IRacun {
  constructor(
    public id?: number,
    public datumRacuna?: Date,
    public period?: string,
    public utrosakVarijabilni?: number,
    public utrosakFiksni?: number,
    public utrosakOdrzavanje?: number,
    public proknjizen?: boolean,
    public stan?: IStan,
    public nacrtRacuna?: INacrtRacuna,
    public azuriran?: boolean,
    public valutaPlacanja?: Date,
    public cenaFix?: number,
    public cenaFixIskljucen?: number,
    public cenaKwh?: number,
    public cenaOdrzavanje?: number,
    public cenaOStalo?: number,
    public kreirao?: IUser,
    public pdv1?: number,
    public pdv2?: number,    	  
    public ukupnoZaduzenje?: number,    	 
    public utrosakUKwh?: number,
    public popust?: number,
    public zaPlacanje?: number,
    public utrosakVarijabilniBezPopusta?: number,
	  public utrosakFiksniBezPopusta?: number,	 
	  public utrosakVarijabilniPopust?: number,
	  public utrosakFiksniPopust?: number,
    public utrosakVarijabilniBezPdv?: number,
    public utrosakFiksniBezPdv?: number,
    public utrosakOdrzavanjeBezPdv?: number,
    public utrosakVarijabilniPdv?: number,
    public utrosakFiksniPdv?: number,
    public utrosakOdrzavanjePdv?: number,
    public datumSaldiranja?: Date,
    public stanjeZaracun?: IStanjaPodstaniceZaRacun,
	prevNextRacuni?:IRacun,
  ) {}
}
