export interface IVrstaTransakcije {
  id?: number;
  vrsta?: string;
  dodajeVrednost?: number;
}

export class VrstaTransakcije implements IVrstaTransakcije {
  constructor(public id?: number, public vrsta?: string, public dodajeVrednost?: number) {}
}
