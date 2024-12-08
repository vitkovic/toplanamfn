import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { ITipPotrosaca, TipPotrosaca } from '@/shared/model/tip-potrosaca.model';
import TipPotrosacaService from './tip-potrosaca.service';

const validations: any = {
  tipPotrosaca: {
    tip: {},
  },
};

@Component({
  validations,
})
export default class TipPotrosacaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('tipPotrosacaService') private tipPotrosacaService: () => TipPotrosacaService;
  public tipPotrosaca: ITipPotrosaca = new TipPotrosaca();

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipPotrosacaId) {
        vm.retrieveTipPotrosaca(to.params.tipPotrosacaId);
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
    if (this.tipPotrosaca.id) {
      this.tipPotrosacaService()
        .update(this.tipPotrosaca)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.tipPotrosaca.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.tipPotrosacaService()
        .create(this.tipPotrosaca)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.tipPotrosaca.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTipPotrosaca(tipPotrosacaId): void {
    this.tipPotrosacaService()
      .find(tipPotrosacaId)
      .then(res => {
        this.tipPotrosaca = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stanService()
      .retrieve()
      .then(res => {
        this.stans = res.data;
      });
  }
}
