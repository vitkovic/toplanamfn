import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ParametriService from '../parametri/parametri.service';
import { IParametri } from '@/shared/model/parametri.model';

import AlertService from '@/shared/alert/alert.service';
import { IParametriIstorija, ParametriIstorija } from '@/shared/model/parametri-istorija.model';
import ParametriIstorijaService from './parametri-istorija.service';

const validations: any = {
  parametriIstorija: {
    vrednost: {},
    vaziOd: {},
    vaziDo: {},
  },
};

@Component({
  validations,
})
export default class ParametriIstorijaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('parametriIstorijaService') private parametriIstorijaService: () => ParametriIstorijaService;
  public parametriIstorija: IParametriIstorija = new ParametriIstorija();

  @Inject('parametriService') private parametriService: () => ParametriService;

  public parametris: IParametri[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.parametriIstorijaId) {
        vm.retrieveParametriIstorija(to.params.parametriIstorijaId);
      }
      vm.initRelationships();
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
    if (this.parametriIstorija.id) {
      this.parametriIstorijaService()
        .update(this.parametriIstorija)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.parametriIstorija.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.parametriIstorijaService()
        .create(this.parametriIstorija)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.parametriIstorija.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveParametriIstorija(parametriIstorijaId): void {
    this.parametriIstorijaService()
      .find(parametriIstorijaId)
      .then(res => {
        this.parametriIstorija = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.parametriService()
      .retrieve()
      .then(res => {
        this.parametris = res.data;
      });
  }
}
