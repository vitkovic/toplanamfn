import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { IOstaliRacuni, OstaliRacuni } from '@/shared/model/ostali-racuni.model';
import OstaliRacuniService from './ostali-racuni.service';

const validations: any = {
  ostaliRacuni: {
    sifra: {},
    aktivan: {},    
    naziv: {},    
  },
};

@Component({
  validations,
})
export default class OstaliRacuniUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('ostaliRacuniService') private ostaliRacuniService: () => OstaliRacuniService;
  public ostaliRacuni: IOstaliRacuni = new OstaliRacuni();

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.ostaliRacuniId) {
        vm.retrieveOstaliRacuni(to.params.ostaliRacuniId);
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
    if (this.ostaliRacuni.id) {
      this.ostaliRacuniService()
        .update(this.ostaliRacuni)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.ostaliRacuni.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.ostaliRacuniService()
        .create(this.ostaliRacuni)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.ostaliRacuni.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveOstaliRacuni(opomenaId): void {
    this.ostaliRacuniService()
      .find(opomenaId)
      .then(res => {
        this.ostaliRacuni = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stanService()
      .retrieveWithoutPagination()
      .then(res => {
        debugger
        this.stans = res.data;
	
      });
  }
}
