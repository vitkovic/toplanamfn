import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITransakcija } from '@/shared/model/transakcija.model';
import TransakcijaService from './transakcija.service';

@Component
export default class KnjigovodstveniDnevnik extends Vue {
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  public transakcija: ITransakcija = {};

  
}
