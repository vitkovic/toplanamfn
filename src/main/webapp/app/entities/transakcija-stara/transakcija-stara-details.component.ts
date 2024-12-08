import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';
import TransakcijaStaraService from './transakcija-stara.service';

@Component
export default class TransakcijaStaraDetails extends Vue {
  @Inject('transakcijaStaraService') private transakcijaStaraService: () => TransakcijaStaraService;
  public transakcijaStara: ITransakcijaStara = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transakcijaStaraId) {
        vm.retrieveTransakcijaStara(to.params.transakcijaStaraId);
      }
    });
  }

  public retrieveTransakcijaStara(transakcijaStaraId) {
    this.transakcijaStaraService()
      .find(transakcijaStaraId)
      .then(res => {
        this.transakcijaStara = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
