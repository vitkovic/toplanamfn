import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVrstaTransakcije } from '@/shared/model/vrsta-transakcije.model';
import VrstaTransakcijeService from './vrsta-transakcije.service';

@Component
export default class VrstaTransakcijeDetails extends Vue {
  @Inject('vrstaTransakcijeService') private vrstaTransakcijeService: () => VrstaTransakcijeService;
  public vrstaTransakcije: IVrstaTransakcije = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vrstaTransakcijeId) {
        vm.retrieveVrstaTransakcije(to.params.vrstaTransakcijeId);
      }
    });
  }

  public retrieveVrstaTransakcije(vrstaTransakcijeId) {
    this.vrstaTransakcijeService()
      .find(vrstaTransakcijeId)
      .then(res => {
        this.vrstaTransakcije = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
