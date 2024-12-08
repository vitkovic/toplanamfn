import { IParametri } from '@/shared/model/parametri.model';

export interface IParametriIstorija {
  id?: number;
  vrednost?: number;
  vaziOd?: Date;
  vaziDo?: Date;
  parametri?: IParametri;
}

export class ParametriIstorija implements IParametriIstorija {
  constructor(public id?: number, public vrednost?: number, public vaziOd?: Date, public vaziDo?: Date, public parametri?: IParametri) {}
}
