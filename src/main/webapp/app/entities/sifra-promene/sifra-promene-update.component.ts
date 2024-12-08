import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ISifraPromene, SifraPromene } from '@/shared/model/sifra-promene.model';
import SifraPromeneService from './sifra-promene.service';

const validations: any = {
  sifraPromene: {
    sifra: {},
    broj: {},
    tipPromene: {},
  },
};

@Component({
  validations,
})
export default class SifraPromeneUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('sifraPromeneService') private sifraPromeneService: () => SifraPromeneService;
  public sifraPromene: ISifraPromene = new SifraPromene();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sifraPromeneId) {
        vm.retrieveSifraPromene(to.params.sifraPromeneId);
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
    if (this.sifraPromene.id) {
      this.sifraPromeneService()
        .update(this.sifraPromene)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.sifraPromene.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.sifraPromeneService()
        .create(this.sifraPromene)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.sifraPromene.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveSifraPromene(sifraPromeneId): void {
    this.sifraPromeneService()
      .find(sifraPromeneId)
      .then(res => {
        this.sifraPromene = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
