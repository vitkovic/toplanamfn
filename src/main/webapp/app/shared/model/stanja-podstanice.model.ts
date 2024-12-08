import { IPodstanica } from '@/shared/model/podstanica.model';

export interface IStanjaPodstanice {
  id?: number;
  stanje?: number;
  datumOcitavanja?: Date;
  podstanica?: IPodstanica;
}

export class StanjaPodstanice implements IStanjaPodstanice {
  constructor(public id?: number, public stanje?: number, public datumOcitavanja?: Date, public podstanica?: IPodstanica) {}
}
