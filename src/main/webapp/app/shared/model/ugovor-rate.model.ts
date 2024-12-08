import { IStan } from '@/shared/model/stan.model';

export interface IUgovorRate {
  id?: number;
  brojRata?: number;
  datumSklapanja?: Date;
  datumDospeca?: Date;
  stan?: IStan;
}

export class UgovorRate implements IUgovorRate {
  constructor(
    public id?: number,
    public brojRata?: number,
    public datumSklapanja?: Date,
    public datumDospeca?: Date,
    public stan?: IStan
  ) {}
}
