import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';
import StavkeIzvodaService from '../stavke-izvoda/stavke-izvoda.service';

@Component
export default class StavkeIzvodaDetails extends Vue {
  @Inject('stavkeIzvodaService') private stavkeIzvodaService: () => StavkeIzvodaService;
  public stavkeIzvoda: IStavkeIzvoda = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stavkeIzvodaId) {
        vm.retrieveStavkeIzvoda(to.params.stavkeIzvodaId);
      }
    });
  }

  public retrieveStavkeIzvoda(stavkeIzvodaId) {
    this.stavkeIzvodaService()
      .find(stavkeIzvodaId)
      .then(res => {
        this.stavkeIzvoda = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
