import { Component, Vue, Inject } from 'vue-property-decorator';

import { IParametri } from '@/shared/model/parametri.model';
import ParametriService from './parametri.service';

@Component
export default class ParametriDetails extends Vue {
  @Inject('parametriService') private parametriService: () => ParametriService;
  public parametri: IParametri = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.parametriId) {
        vm.retrieveParametri(to.params.parametriId);
      }
    });
  }

  public retrieveParametri(parametriId) {
    this.parametriService()
      .find(parametriId)
      .then(res => {
        this.parametri = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
