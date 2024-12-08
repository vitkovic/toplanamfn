import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import NacrtRacunaService from '../nacrt-racuna/nacrt-racuna.service';
import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';

import AlertService from '@/shared/alert/alert.service';
import { IRacun, Racun } from '@/shared/model/racun.model';
import RacunService from './racun.service';

const validations: any = {
  racun: {
    datumRacuna: {},
    period: {},
    utrosakVarijabilni: {},
    utrosakFiksni: {},
    utrosakOdrzavanje: {},
  },
};

@Component({
  validations,
})
export default class RacunUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('racunService') private racunService: () => RacunService;
  public racun: IRacun = new Racun();

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];

  @Inject('nacrtRacunaService') private nacrtRacunaService: () => NacrtRacunaService;

  public nacrtRacunas: INacrtRacuna[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.racunId) {
        vm.retrieveRacun(to.params.racunId);
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
    if (this.racun.id) {
      this.racunService()
        .update(this.racun)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.racun.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.racunService()
        .create(this.racun)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.racun.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRacun(racunId): void {
    this.racunService()
      .find(racunId)
      .then(res => {
        this.racun = res;
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
    this.nacrtRacunaService()
      .retrieve()
      .then(res => {
        this.nacrtRacunas = res.data;
      });
  }
}
