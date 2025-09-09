import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StavkeIzvodaService from '../stavke-izvoda/stavke-izvoda.service';
import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';

import AlertService from '@/shared/alert/alert.service';
import { IIzvod, Izvod } from '@/shared/model/izvod.model';
import IzvodService from '@/entities/izvod/izvod.service';

import { IStanjaPodstanice } from '@/shared/model/stanja-podstanice.model';
import StanjaPodstaniceService from './stanja-podstanice.service';

const validations: any = {
  izvod: {
    datum: {},
    broj: {},
  },
};

@Component({
  validations,
})
export default class IzvodUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('izvodService') private izvodService: () => IzvodService;
  @Inject('stanjaPodstaniceService') private stanjaPodstaniceService: () => StanjaPodstaniceService;
  public stanjaPodstanice: IStanjaPodstanice = {};
  public izvod: IIzvod = new Izvod();

  @Inject('stavkeIzvodaService') private stavkeIzvodaService: () => StavkeIzvodaService;

  public stavkeIzvodas: IStavkeIzvoda[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public file1: File | null = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.izvodId) {
        vm.retrieveIzvod(to.params.izvodId);
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
    let formData = new FormData();
    formData.append('file', this.file1);
    this.stanjaPodstaniceService()
      .sendFile(formData)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = this.$t('toplanaApp.stanjaPodstanice.uploaded', { param: param.id });
        this.alertService().showAlert(message, 'info');
      })
	  .catch(err => {
		this.isSaving = false;
		console.log(err.response)
		   if (err?.response?.status === 400) {
			const msg =  (this.$t('toplanaApp.error.badRequest') as string);

			this.alertService().showAlert(String(msg), 'danger'); // or showHttpError(this, err.response)
		    
		   }
		   // Generic handler
		 //  this.alertService().showAlert(err?.message || 'Unexpected error', 'danger');
	      
		   this.$nextTick(() => setTimeout(() => this.$router.go(-1), 50));
       });
	    
  }

  public retrieveIzvod(izvodId): void {
    this.izvodService()
      .find(izvodId)
      .then(res => {
        this.izvod = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stavkeIzvodaService()
      .retrieve()
      .then(res => {
        this.stavkeIzvodas = res.data;
      });
  }
}
