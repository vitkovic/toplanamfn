import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StavkeIzvodaService from '../stavke-izvoda/stavke-izvoda.service';
import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';

import AlertService from '@/shared/alert/alert.service';
import { IIzvod, Izvod } from '@/shared/model/izvod.model';
import IzvodService from './izvod.service';

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
  public file1:Blob = new Blob();

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
		  if (err?.response?.status === 400) {
		        // Try to show a useful backend message if present
		        const msg =
		          err.response.data?.message ||
		          err.response.data?.title ||
		          this.$t('toplanaApp.error.badRequest');
		        this.alertService().showAlert(String(msg), 'danger');
	
		        // Optional: show field validation details if your backend sends them
		        const fieldErrors = err.response.data?.fieldErrors;
		        if (Array.isArray(fieldErrors) && fieldErrors.length) {
		          const details = fieldErrors
		            .map((fe: any) => `${fe.field}: ${fe.message}`)
		            .join('\n');
		          this.alertService().showAlert(details, 'danger');
		        }
		      } else if (this.alertService && typeof this.alertService().showHttpError === 'function') {
		        this.alertService().showHttpError(this, err.response);
		      } else {
		        this.alertService().showAlert(err?.message || 'Unexpected error', 'danger');
		      }
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
