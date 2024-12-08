import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStanjaPodstanice } from '@/shared/model/stanja-podstanice.model';
import StanjaPodstaniceService from './stanja-podstanice.service';

@Component
export default class StanjaPodstaniceDetails extends Vue {
  @Inject('stanjaPodstaniceService') private stanjaPodstaniceService: () => StanjaPodstaniceService;
  public stanjaPodstanice: IStanjaPodstanice = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stanjaPodstaniceId) {
        vm.retrieveStanjaPodstanice(to.params.stanjaPodstaniceId);
      }
    });
  }

  public retrieveStanjaPodstanice(stanjaPodstaniceId) {
    this.stanjaPodstaniceService()
      .find(stanjaPodstaniceId)
      .then(res => {
        this.stanjaPodstanice = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
