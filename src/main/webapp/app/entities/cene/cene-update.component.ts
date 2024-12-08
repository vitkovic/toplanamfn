import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ICene, Cene } from '@/shared/model/cene.model';
import CeneService from './cene.service';

const validations: any = {
  cene: {
    kwh: {},
    fix: {},
    fixIskljuceno:{},
    odrzavanje: {},
    ostalo: {},
    pdv1: {},
    pdv2: {},
    popust1: {},
  },
};

@Component({
  validations,
})
export default class CeneUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('ceneService') private ceneService: () => CeneService;
  public cene: ICene = new Cene();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ceneId) {
        vm.retrieveCene(to.params.ceneId);
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
    if (this.cene.id) {
      this.ceneService()
        .update(this.cene)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.cene.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.ceneService()
        .create(this.cene)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.cene.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCene(ceneId): void {
    this.ceneService()
      .find(ceneId)
      .then(res => {
        this.cene = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
