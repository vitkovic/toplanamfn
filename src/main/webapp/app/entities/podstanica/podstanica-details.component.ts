import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPodstanica } from '@/shared/model/podstanica.model';
import PodstanicaService from './podstanica.service';

@Component
export default class PodstanicaDetails extends Vue {
  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;
  public podstanica: IPodstanica = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.podstanicaId) {
        vm.retrievePodstanica(to.params.podstanicaId);
      }
    });
  }

  public retrievePodstanica(podstanicaId) {
    this.podstanicaService()
      .find(podstanicaId)
      .then(res => {
        this.podstanica = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
