import { Component, Vue, Inject } from 'vue-property-decorator';

import { IIzvod } from '@/shared/model/izvod.model';
import IzvodService from './izvod.service';
import { RACUN_ZADUZENJA } from '@/constants';


@Component
export default class IzvodDetails extends Vue {
  @Inject('izvodService') private izvodService: () => IzvodService;
  public izvod: IIzvod = {};
  public RACUN_ZADUZENJA = RACUN_ZADUZENJA;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.izvodId) {
        debugger
        vm.retrieveIzvod(to.params.izvodId);
      }
    });
  }

  public retrieveIzvod(izvodId) {
    this.izvodService()
      .find(izvodId)
      .then(res => {        
        this.izvod = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
