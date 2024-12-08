export interface ISifraPromene {
  id?: number;
  sifra?: string;
  broj?: number;
  tipPromene?: string;
}

export class SifraPromene implements ISifraPromene {
  constructor(public id?: number, public sifra?: string,
    public broj?: number,
    public tipPromene?: string) {}
}
