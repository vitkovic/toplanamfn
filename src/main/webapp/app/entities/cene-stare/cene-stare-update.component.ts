import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ICeneStare, CeneStare } from '@/shared/model/cene-stare.model';
import CeneStareService from './cene-stare.service';

const validations: any = {
  ceneStare: {
    kwh: {},
    fix: {},
    odrzavanje: {},
    ostalo: {},
  },
};

@Component({
  validations,
})
export default class CeneStareUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('ceneStareService') private ceneStareService: () => CeneStareService;
  public ceneStare: ICeneStare = new CeneStare();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ceneStareId) {
        vm.retrieveCeneStare(to.params.ceneStareId);
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
    if (this.ceneStare.id) {
      this.ceneStareService()
        .update(this.ceneStare)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.ceneStare.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.ceneStareService()
        .create(this.ceneStare)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.ceneStare.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCeneStare(ceneStareId): void {
    this.ceneStareService()
      .find(ceneStareId)
      .then(res => {
        this.ceneStare = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
