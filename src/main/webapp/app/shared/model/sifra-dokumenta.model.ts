export interface ISifraDokumenta {
  id?: number;
  sifra?: string;
}

export class SifraDokumenta implements ISifraDokumenta {
  constructor(public id?: number, public sifra?: string) {}
}
