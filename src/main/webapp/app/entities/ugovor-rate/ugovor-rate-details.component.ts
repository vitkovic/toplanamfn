import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUgovorRate } from '@/shared/model/ugovor-rate.model';
import UgovorRateService from './ugovor-rate.service';

@Component
export default class UgovorRateDetails extends Vue {
  @Inject('ugovorRateService') private ugovorRateService: () => UgovorRateService;
  public ugovorRate: IUgovorRate = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ugovorRateId) {
        vm.retrieveUgovorRate(to.params.ugovorRateId);
      }
    });
  }

  public retrieveUgovorRate(ugovorRateId) {
    this.ugovorRateService()
      .find(ugovorRateId)
      .then(res => {
        this.ugovorRate = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
