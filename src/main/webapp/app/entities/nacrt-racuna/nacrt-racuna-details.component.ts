import { Component, Vue, Inject } from 'vue-property-decorator';

import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';
import NacrtRacunaService from './nacrt-racuna.service';

@Component
export default class NacrtRacunaDetails extends Vue {
  @Inject('nacrtRacunaService') private nacrtRacunaService: () => NacrtRacunaService;
  public nacrtRacuna: INacrtRacuna = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.nacrtRacunaId) {
        vm.retrieveNacrtRacuna(to.params.nacrtRacunaId);
      }
    });
  }

  public retrieveNacrtRacuna(nacrtRacunaId) {
    this.nacrtRacunaService()
      .find(nacrtRacunaId)
      .then(res => {
        this.nacrtRacuna = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
