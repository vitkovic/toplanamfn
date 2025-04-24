import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { IStanStanje, StanStanje } from '@/shared/model/stan-stanje.model';
import StanStanjeService from './stan-stanje.service';

const validations: any = {
  stanStanje: {
    sifra: {
      required,
    },
    datum: {
      required,
    },
    vrednost: {
      required,
      numeric,
    },
    stan_id: {},
  },
};

@Component({
  validations,
})
export default class StanStanjeUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stanStanjeService') private stanStanjeService: () => StanStanjeService;
  public stanStanje: IStanStanje = new StanStanje();

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stanStanjeId) {
        vm.retrieveStanStanje(to.params.stanStanjeId);
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
    if (this.stanStanje.id) {
      this.stanStanjeService()
        .update(this.stanStanje)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stanStanje.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stanStanjeService()
        .create(this.stanStanje)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stanStanje.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveStanStanje(stanStanjeId): void {
    this.stanStanjeService()
      .find(stanStanjeId)
      .then(res => {
        this.stanStanje = res;
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
