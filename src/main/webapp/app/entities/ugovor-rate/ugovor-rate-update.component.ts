import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { IUgovorRate, UgovorRate } from '@/shared/model/ugovor-rate.model';
import UgovorRateService from './ugovor-rate.service';

const validations: any = {
  ugovorRate: {
    brojRata: {},
    datumSklapanja: {},
    datumDospeca: {},
  },
};

@Component({
  validations,
})
export default class UgovorRateUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('ugovorRateService') private ugovorRateService: () => UgovorRateService;
  public ugovorRate: IUgovorRate = new UgovorRate();

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ugovorRateId) {
        vm.retrieveUgovorRate(to.params.ugovorRateId);
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
    if (this.ugovorRate.id) {
      this.ugovorRateService()
        .update(this.ugovorRate)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.ugovorRate.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.ugovorRateService()
        .create(this.ugovorRate)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.ugovorRate.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUgovorRate(ugovorRateId): void {
    this.ugovorRateService()
      .find(ugovorRateId)
      .then(res => {
        this.ugovorRate = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stanService()
      .retrieveWithoutPagination()
      .then(res => {
        this.stans = res.data;
      });
  }
}
