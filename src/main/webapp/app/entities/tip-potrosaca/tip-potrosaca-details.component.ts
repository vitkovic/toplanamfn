import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITipPotrosaca } from '@/shared/model/tip-potrosaca.model';
import TipPotrosacaService from './tip-potrosaca.service';

@Component
export default class TipPotrosacaDetails extends Vue {
  @Inject('tipPotrosacaService') private tipPotrosacaService: () => TipPotrosacaService;
  public tipPotrosaca: ITipPotrosaca = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipPotrosacaId) {
        vm.retrieveTipPotrosaca(to.params.tipPotrosacaId);
      }
    });
  }

  public retrieveTipPotrosaca(tipPotrosacaId) {
    this.tipPotrosacaService()
      .find(tipPotrosacaId)
      .then(res => {
        this.tipPotrosaca = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
