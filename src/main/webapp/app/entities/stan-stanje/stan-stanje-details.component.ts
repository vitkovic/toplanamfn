import { Component, Vue, Inject } from 'vue-property-decorator';

import { IStanStanje } from '@/shared/model/stan-stanje.model';
import StanStanjeService from './stan-stanje.service';

@Component
export default class StanStanjeDetails extends Vue {
  @Inject('stanStanjeService') private stanStanjeService: () => StanStanjeService;
  public stanStanje: IStanStanje = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stanStanjeId) {
        vm.retrieveStanStanje(to.params.stanStanjeId);
      }
    });
  }

  public retrieveStanStanje(stanStanjeId) {
    this.stanStanjeService()
      .find(stanStanjeId)
      .then(res => {
        this.stanStanje = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
