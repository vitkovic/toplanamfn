import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICeneStare } from '@/shared/model/cene-stare.model';
import CeneStareService from './cene-stare.service';

@Component
export default class CeneStareDetails extends Vue {
  @Inject('ceneStareService') private ceneStareService: () => CeneStareService;
  public ceneStare: ICeneStare = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ceneStareId) {
        vm.retrieveCeneStare(to.params.ceneStareId);
      }
    });
  }

  public retrieveCeneStare(ceneStareId) {
    this.ceneStareService()
      .find(ceneStareId)
      .then(res => {
        this.ceneStare = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
