import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITransakcija } from '@/shared/model/transakcija.model';
import TransakcijaService from './transakcija.service';

@Component
export default class TransakcijaDetails extends Vue {
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  public transakcija: ITransakcija = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transakcijaId) {
        vm.retrieveTransakcija(to.params.transakcijaId);
      }
    });
  }

  public retrieveTransakcija(transakcijaId) {
    this.transakcijaService()
      .find(transakcijaId)
      .then(res => {
        this.transakcija = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
