// @ts-nocheck
import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { IVlasnik, Vlasnik } from '@/shared/model/vlasnik.model';
import VlasnikService from './vlasnik.service';

import { required, email } from 'vuelidate/lib/validators';

const validations: any = {
  vlasnik: {
    ime: {},
    prezime: {},
    brojRacuna: {},
    partijaRacuna: {},
    naziv: {},
    email: {email},
    psr: {},
  },
};

@Component({
  validations,
})
export default class VlasnikUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('vlasnikService') private vlasnikService: () => VlasnikService;
  public vlasnik: IVlasnik = new Vlasnik();

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.vlasnikId) {
        vm.retrieveVlasnik(to.params.vlasnikId);
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
    if (this.vlasnik.id) {
      this.vlasnikService()
        .update(this.vlasnik)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.vlasnik.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.vlasnikService()
        .create(this.vlasnik)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.vlasnik.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveVlasnik(vlasnikId): void {
    this.vlasnikService()
      .find(vlasnikId)
      .then(res => {
        this.vlasnik = res;
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
