import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import PodstanicaService from '../podstanica/podstanica.service';
import { IPodstanica } from '@/shared/model/podstanica.model';

import NacrtRacunaService from '../nacrt-racuna/nacrt-racuna.service';
import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';

import AlertService from '@/shared/alert/alert.service';
import { IStanjaPodstaniceZaRacun, StanjaPodstaniceZaRacun } from '@/shared/model/stanja-podstanice-za-racun.model';
import StanjaPodstaniceZaRacunService from './stanja-podstanice-za-racun.service';

const validations: any = {
  stanjaPodstaniceZaRacun: {
    staroStanje: {},
    novoStanje: {},
    ukupnaPovrsina: {},
    utrosakPoM2: {},
  },
};

@Component({
  validations,
})
export default class StanjaPodstaniceZaRacunUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stanjaPodstaniceZaRacunService') private stanjaPodstaniceZaRacunService: () => StanjaPodstaniceZaRacunService;
  public stanjaPodstaniceZaRacun: IStanjaPodstaniceZaRacun = new StanjaPodstaniceZaRacun();

  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;

  public podstanicas: IPodstanica[] = [];

  @Inject('nacrtRacunaService') private nacrtRacunaService: () => NacrtRacunaService;

  public nacrtRacunas: INacrtRacuna[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stanjaPodstaniceZaRacunId) {
        vm.retrieveStanjaPodstaniceZaRacun(to.params.stanjaPodstaniceZaRacunId);
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
    if (this.stanjaPodstaniceZaRacun.id) {
      this.stanjaPodstaniceZaRacunService()
        .update(this.stanjaPodstaniceZaRacun)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stanjaPodstaniceZaRacun.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stanjaPodstaniceZaRacunService()
        .create(this.stanjaPodstaniceZaRacun)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stanjaPodstaniceZaRacun.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveStanjaPodstaniceZaRacun(stanjaPodstaniceZaRacunId): void {
    this.stanjaPodstaniceZaRacunService()
      .find(stanjaPodstaniceZaRacunId)
      .then(res => {
        this.stanjaPodstaniceZaRacun = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.podstanicaService()
      .retrieve()
      .then(res => {
        this.podstanicas = res.data;
      });
    this.nacrtRacunaService()
      .retrieve()
      .then(res => {
        this.nacrtRacunas = res.data;
      });
  }
}
