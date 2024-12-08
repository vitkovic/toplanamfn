import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStanjaPodstaniceZaRacun } from '@/shared/model/stanja-podstanice-za-racun.model';
import StanjaPodstaniceZaRacunService from './stanja-podstanice-za-racun.service';

@Component
export default class StanjaPodstaniceZaRacunDetails extends Vue {
  @Inject('stanjaPodstaniceZaRacunService') private stanjaPodstaniceZaRacunService: () => StanjaPodstaniceZaRacunService;
  public stanjaPodstaniceZaRacun: IStanjaPodstaniceZaRacun = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stanjaPodstaniceZaRacunId) {
        vm.retrieveStanjaPodstaniceZaRacun(to.params.stanjaPodstaniceZaRacunId);
      }
    });
  }

  public retrieveStanjaPodstaniceZaRacun(stanjaPodstaniceZaRacunId) {
    this.stanjaPodstaniceZaRacunService()
      .find(stanjaPodstaniceZaRacunId)
      .then(res => {
        this.stanjaPodstaniceZaRacun = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
