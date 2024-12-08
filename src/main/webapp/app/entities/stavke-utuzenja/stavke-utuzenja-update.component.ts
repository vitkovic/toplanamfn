import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import UtuzenjeService from '../utuzenje/utuzenje.service';
import { IUtuzenje } from '@/shared/model/utuzenje.model';

import AlertService from '@/shared/alert/alert.service';
import { IStavkeUtuzenja, StavkeUtuzenja } from '@/shared/model/stavke-utuzenja.model';
import StavkeUtuzenjaService from './stavke-utuzenja.service';

const validations: any = {
  stavkeUtuzenja: {
    obracunskiPeriod: {
      required,
    },
    datumIzdavanjaRacuna: {
      required,
    },
    datumDospecaRacuna: {
      required,
    },
    zaduzenje: {
      required,
      numeric,
    },
    ukupnoZaUplatu: {
      required,
      numeric,
    },
  },
};

@Component({
  validations,
})
export default class StavkeUtuzenjaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stavkeUtuzenjaService') private stavkeUtuzenjaService: () => StavkeUtuzenjaService;
  public stavkeUtuzenja: IStavkeUtuzenja = new StavkeUtuzenja();

  @Inject('utuzenjeService') private utuzenjeService: () => UtuzenjeService;

  public utuzenjes: IUtuzenje[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stavkeUtuzenjaId) {
        vm.retrieveStavkeUtuzenja(to.params.stavkeUtuzenjaId);
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
    if (this.stavkeUtuzenja.id) {
      this.stavkeUtuzenjaService()
        .update(this.stavkeUtuzenja)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stavkeUtuzenja.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stavkeUtuzenjaService()
        .create(this.stavkeUtuzenja)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stavkeUtuzenja.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveStavkeUtuzenja(stavkeUtuzenjaId): void {
    this.stavkeUtuzenjaService()
      .find(stavkeUtuzenjaId)
      .then(res => {
        this.stavkeUtuzenja = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.utuzenjeService()
      .retrieve()
      .then(res => {
        this.utuzenjes = res.data;
      });
  }
}
