import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IVrstaTransakcije, VrstaTransakcije } from '@/shared/model/vrsta-transakcije.model';
import VrstaTransakcijeService from './vrsta-transakcije.service';

const validations: any = {
  vrstaTransakcije: {
    vrsta: {},
    dodajeVrednost: {},
  },
};

@Component({
  validations,
})
export default class VrstaTransakcijeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('vrstaTransakcijeService') private vrstaTransakcijeService: () => VrstaTransakcijeService;
  public vrstaTransakcije: IVrstaTransakcije = new VrstaTransakcije();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vrstaTransakcijeId) {
        vm.retrieveVrstaTransakcije(to.params.vrstaTransakcijeId);
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
    if (this.vrstaTransakcije.id) {
      this.vrstaTransakcijeService()
        .update(this.vrstaTransakcije)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.vrstaTransakcije.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.vrstaTransakcijeService()
        .create(this.vrstaTransakcije)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.vrstaTransakcije.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveVrstaTransakcije(vrstaTransakcijeId): void {
    this.vrstaTransakcijeService()
      .find(vrstaTransakcijeId)
      .then(res => {
        this.vrstaTransakcije = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
