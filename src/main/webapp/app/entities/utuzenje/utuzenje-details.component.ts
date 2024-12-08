import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUtuzenje } from '@/shared/model/utuzenje.model';
import UtuzenjeService from './utuzenje.service';

@Component
export default class UtuzenjeDetails extends Vue {
  @Inject('utuzenjeService') private utuzenjeService: () => UtuzenjeService;
  public utuzenje: IUtuzenje = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.utuzenjeId) {
        vm.retrieveUtuzenje(to.params.utuzenjeId);
      }
    });
  }

  public retrieveUtuzenje(utuzenjeId) {
    this.utuzenjeService()
      .find(utuzenjeId)
      .then(res => {
        this.utuzenje = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
