import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISifraDokumenta } from '@/shared/model/sifra-dokumenta.model';
import SifraDokumentaService from './sifra-dokumenta.service';

@Component
export default class SifraDokumentaDetails extends Vue {
  @Inject('sifraDokumentaService') private sifraDokumentaService: () => SifraDokumentaService;
  public sifraDokumenta: ISifraDokumenta = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sifraDokumentaId) {
        vm.retrieveSifraDokumenta(to.params.sifraDokumentaId);
      }
    });
  }

  public retrieveSifraDokumenta(sifraDokumentaId) {
    this.sifraDokumentaService()
      .find(sifraDokumentaId)
      .then(res => {
        this.sifraDokumenta = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
