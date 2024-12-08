import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ParametriIstorijaService from '../parametri-istorija/parametri-istorija.service';
import { IParametriIstorija } from '@/shared/model/parametri-istorija.model';

import AlertService from '@/shared/alert/alert.service';
import { IParametri, Parametri } from '@/shared/model/parametri.model';
import ParametriService from './parametri.service';

const validations: any = {
  parametri: {
    naziv: {},
    vrednost: {},
  },
};

@Component({
  validations,
})
export default class ParametriUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('parametriService') private parametriService: () => ParametriService;
  public parametri: IParametri = new Parametri();

  @Inject('parametriIstorijaService') private parametriIstorijaService: () => ParametriIstorijaService;

  public parametriIstorijas: IParametriIstorija[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.parametriId) {
        vm.retrieveParametri(to.params.parametriId);
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
    if (this.parametri.id) {
      this.parametriService()
        .update(this.parametri)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.parametri.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.parametriService()
        .create(this.parametri)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.parametri.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveParametri(parametriId): void {
    this.parametriService()
      .find(parametriId)
      .then(res => {
        this.parametri = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.parametriIstorijaService()
      .retrieve()
      .then(res => {
        this.parametriIstorijas = res.data;
      });
  }
}
