import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStan } from '@/shared/model/stan.model';
import StanService from './stan.service';

@Component
export default class StanDetails extends Vue {
  @Inject('stanService') private stanService: () => StanService;
  public stan: IStan = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stanId) {
        vm.retrieveStan(to.params.stanId);
      }
    });
  }

  public retrieveStan(stanId) {
    this.stanService()
      .find(stanId)
      .then(res => {
        this.stan = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
