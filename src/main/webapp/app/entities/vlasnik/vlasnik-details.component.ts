import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVlasnik } from '@/shared/model/vlasnik.model';
import VlasnikService from './vlasnik.service';

@Component
export default class VlasnikDetails extends Vue {
  @Inject('vlasnikService') private vlasnikService: () => VlasnikService;
  public vlasnik: IVlasnik = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vlasnikId) {
        vm.retrieveVlasnik(to.params.vlasnikId);
      }
    });
  }

  public retrieveVlasnik(vlasnikId) {
    this.vlasnikService()
      .find(vlasnikId)
      .then(res => {
        this.vlasnik = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
