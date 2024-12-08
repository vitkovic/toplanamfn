import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import NacrtRacunaService from '../nacrt-racuna/nacrt-racuna.service';
import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import StanjaPodstaniceService from '../stanja-podstanice/stanja-podstanice.service';
import { IStanjaPodstanice } from '@/shared/model/stanja-podstanice.model';

import AlertService from '@/shared/alert/alert.service';
import { IPodstanica, Podstanica } from '@/shared/model/podstanica.model';
import PodstanicaService from './podstanica.service';

const validations: any = {
  podstanica: {
    naziv: {},
  },
};

@Component({
  validations,
})
export default class PodstanicaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;
  public podstanica: IPodstanica = new Podstanica();

  @Inject('nacrtRacunaService') private nacrtRacunaService: () => NacrtRacunaService;

  public nacrtRacunas: INacrtRacuna[] = [];

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];

  @Inject('stanjaPodstaniceService') private stanjaPodstaniceService: () => StanjaPodstaniceService;

  public stanjaPodstanices: IStanjaPodstanice[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.podstanicaId) {
        vm.retrievePodstanica(to.params.podstanicaId);
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
    if (this.podstanica.id) {
      this.podstanicaService()
        .update(this.podstanica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.podstanica.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.podstanicaService()
        .create(this.podstanica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.podstanica.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePodstanica(podstanicaId): void {
    this.podstanicaService()
      .find(podstanicaId)
      .then(res => {
        this.podstanica = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.nacrtRacunaService()
      .retrieve()
      .then(res => {
        this.nacrtRacunas = res.data;
      });
    this.stanService()
      .retrieve()
      .then(res => {
        this.stans = res.data;
      });
    this.stanjaPodstaniceService()
      .retrieve()
      .then(res => {
        this.stanjaPodstanices = res.data;
      });
  }
}
