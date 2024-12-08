import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ISifraDokumenta, SifraDokumenta } from '@/shared/model/sifra-dokumenta.model';
import SifraDokumentaService from './sifra-dokumenta.service';

const validations: any = {
  sifraDokumenta: {
    sifra: {},
  },
};

@Component({
  validations,
})
export default class SifraDokumentaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sifraDokumentaService') private sifraDokumentaService: () => SifraDokumentaService;
  public sifraDokumenta: ISifraDokumenta = new SifraDokumenta();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sifraDokumentaId) {
        vm.retrieveSifraDokumenta(to.params.sifraDokumentaId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.sifraDokumenta.id) {
      this.sifraDokumentaService()
        .update(this.sifraDokumenta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.sifraDokumenta.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sifraDokumentaService()
        .create(this.sifraDokumenta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.sifraDokumenta.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSifraDokumenta(sifraDokumentaId): void {
    this.sifraDokumentaService()
      .find(sifraDokumentaId)
      .then(res => {
        this.sifraDokumenta = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
