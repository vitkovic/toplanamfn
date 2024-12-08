import { IParametriIstorija } from '@/shared/model/parametri-istorija.model';

export interface IParametri {
  id?: number;
  naziv?: string;
  vrednost?: number;
  istorijas?: IParametriIstorija[];
}

export class Parametri implements IParametri {
  constructor(public id?: number, public naziv?: string, public vrednost?: number, public istorijas?: IParametriIstorija[]) {}
}
