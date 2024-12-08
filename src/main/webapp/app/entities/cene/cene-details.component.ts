import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICene } from '@/shared/model/cene.model';
import CeneService from './cene.service';

@Component
export default class CeneDetails extends Vue {
  @Inject('ceneService') private ceneService: () => CeneService;
  public cene: ICene = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ceneId) {
        vm.retrieveCene(to.params.ceneId);
      }
    });
  }

  public retrieveCene(ceneId) {
    this.ceneService()
      .find(ceneId)
      .then(res => {
        this.cene = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
