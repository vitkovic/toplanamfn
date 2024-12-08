import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStavkeUtuzenja } from '@/shared/model/stavke-utuzenja.model';
import StavkeUtuzenjaService from './stavke-utuzenja.service';

@Component
export default class StavkeUtuzenjaDetails extends Vue {
  @Inject('stavkeUtuzenjaService') private stavkeUtuzenjaService: () => StavkeUtuzenjaService;
  public stavkeUtuzenja: IStavkeUtuzenja = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stavkeUtuzenjaId) {
        vm.retrieveStavkeUtuzenja(to.params.stavkeUtuzenjaId);
      }
    });
  }

  public retrieveStavkeUtuzenja(stavkeUtuzenjaId) {
    this.stavkeUtuzenjaService()
      .find(stavkeUtuzenjaId)
      .then(res => {
        this.stavkeUtuzenja = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
