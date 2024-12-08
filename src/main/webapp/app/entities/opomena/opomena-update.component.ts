import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { IOpomena, Opomena } from '@/shared/model/opomena.model';
import OpomenaService from './opomena.service';

const validations: any = {
  opomena: {
    datumOpomene: {
      required,
    },
    zakljucniDatum: {
      required,
    },
    iznos: {
      required,
      numeric,
    },
    datumIzmirenja: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class OpomenaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('opomenaService') private opomenaService: () => OpomenaService;
  public opomena: IOpomena = new Opomena();

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.opomenaId) {
        vm.retrieveOpomena(to.params.opomenaId);
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
    if (this.opomena.id) {
      this.opomenaService()
        .update(this.opomena)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.opomena.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.opomenaService()
        .create(this.opomena)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.opomena.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveOpomena(opomenaId): void {
    this.opomenaService()
      .find(opomenaId)
      .then(res => {
        this.opomena = res;
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
