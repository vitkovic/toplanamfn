import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import PodstanicaService from '../podstanica/podstanica.service';
import { IPodstanica } from '@/shared/model/podstanica.model';

import AlertService from '@/shared/alert/alert.service';
import { IStanjaPodstanice, StanjaPodstanice } from '@/shared/model/stanja-podstanice.model';
import StanjaPodstaniceService from './stanja-podstanice.service';

const validations: any = {
  stanjaPodstanice: {
    stanje: {
      required,
      numeric,
    },
    datumOcitavanja: {
      required,
    },
  },
};

@Component({
  validations,
})
export default class StanjaPodstaniceUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stanjaPodstaniceService') private stanjaPodstaniceService: () => StanjaPodstaniceService;
  public stanjaPodstanice: IStanjaPodstanice = new StanjaPodstanice();

  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;

  public podstanicas: IPodstanica[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stanjaPodstaniceId) {
        vm.retrieveStanjaPodstanice(to.params.stanjaPodstaniceId);
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
    if (this.stanjaPodstanice.id) {
      this.stanjaPodstaniceService()
        .update(this.stanjaPodstanice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stanjaPodstanice.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stanjaPodstaniceService()
        .create(this.stanjaPodstanice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stanjaPodstanice.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveStanjaPodstanice(stanjaPodstaniceId): void {
    this.stanjaPodstaniceService()
      .find(stanjaPodstaniceId)
      .then(res => {
        this.stanjaPodstanice = res;
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
  }
}
