import { Component, Vue, Inject } from 'vue-property-decorator';

import { IParametriIstorija } from '@/shared/model/parametri-istorija.model';
import ParametriIstorijaService from './parametri-istorija.service';

@Component
export default class ParametriIstorijaDetails extends Vue {
  @Inject('parametriIstorijaService') private parametriIstorijaService: () => ParametriIstorijaService;
  public parametriIstorija: IParametriIstorija = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.parametriIstorijaId) {
        vm.retrieveParametriIstorija(to.params.parametriIstorijaId);
      }
    });
  }

  public retrieveParametriIstorija(parametriIstorijaId) {
    this.parametriIstorijaService()
      .find(parametriIstorijaId)
      .then(res => {
        this.parametriIstorija = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
