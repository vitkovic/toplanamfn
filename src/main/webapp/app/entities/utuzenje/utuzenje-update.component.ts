import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StavkeUtuzenjaService from '../stavke-utuzenja/stavke-utuzenja.service';
import { IStavkeUtuzenja } from '@/shared/model/stavke-utuzenja.model';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { IUtuzenje, Utuzenje } from '@/shared/model/utuzenje.model';
import UtuzenjeService from './utuzenje.service';

const validations: any = {
  utuzenje: {
    datum: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class UtuzenjeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('utuzenjeService') private utuzenjeService: () => UtuzenjeService;
  public utuzenje: IUtuzenje = new Utuzenje();

  @Inject('stavkeUtuzenjaService') private stavkeUtuzenjaService: () => StavkeUtuzenjaService;

  public stavkeUtuzenjas: IStavkeUtuzenja[] = [];

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.utuzenjeId) {
        vm.retrieveUtuzenje(to.params.utuzenjeId);
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
    if (this.utuzenje.id) {
      this.utuzenjeService()
        .update(this.utuzenje)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.utuzenje.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.utuzenjeService()
        .create(this.utuzenje)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.utuzenje.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUtuzenje(utuzenjeId): void {
    this.utuzenjeService()
      .find(utuzenjeId)
      .then(res => {
        this.utuzenje = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stavkeUtuzenjaService()
      .retrieve()
      .then(res => {
        this.stavkeUtuzenjas = res.data;
      });
    this.stanService()
      .retrieveWithoutPagination()
      .then(res => {
        this.stans = res.data;
      });
  }
}
