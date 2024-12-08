import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOpomena } from '@/shared/model/opomena.model';
import OpomenaService from './opomena.service';

@Component
export default class OpomenaDetails extends Vue {
  @Inject('opomenaService') private opomenaService: () => OpomenaService;
  public opomena: IOpomena = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.opomenaId) {
        vm.retrieveOpomena(to.params.opomenaId);
      }
    });
  }

  public retrieveOpomena(opomenaId) {
    this.opomenaService()
      .find(opomenaId)
      .then(res => {
        this.opomena = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
