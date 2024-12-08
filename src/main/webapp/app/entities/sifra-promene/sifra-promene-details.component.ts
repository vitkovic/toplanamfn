import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISifraPromene } from '@/shared/model/sifra-promene.model';
import SifraPromeneService from './sifra-promene.service';

@Component
export default class SifraPromeneDetails extends Vue {
  @Inject('sifraPromeneService') private sifraPromeneService: () => SifraPromeneService;
  public sifraPromene: ISifraPromene = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sifraPromeneId) {
        vm.retrieveSifraPromene(to.params.sifraPromeneId);
      }
    });
  }

  public retrieveSifraPromene(sifraPromeneId) {
    this.sifraPromeneService()
      .find(sifraPromeneId)
      .then(res => {
        this.sifraPromene = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
