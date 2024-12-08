import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import StavkeIzvodaService from '../stavke-izvoda/stavke-izvoda.service';
import { IStavkeIzvodaPostanska } from '@/shared/model/stavke-izvoda-postanska.model';

import AlertService from '@/shared/alert/alert.service';
import { IIzvod, Izvod } from '@/shared/model/izvod.model';
import IzvodService from './izvod.service';

const validations: any = {
  izvod: {
    datum: {},
    broj: {},
  },
};

@Component({
  validations,
})
export default class IzvodPostanskaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('izvodService') private izvodService: () => IzvodService;
  public izvod: IIzvod = new Izvod();


  public stavkeIzvodas: IStavkeIzvodaPostanska[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public file1:File = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.izvodId) {
        vm.retrieveIzvod(to.params.izvodId);
      }
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
    debugger
    this.izvodService()
      .sendFilePostanska(formData)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = this.$t('toplanaApp.izvod.updated', { param: param.id });
        this.alertService().showAlert(message, 'info');
      });

  }

  public retrieveIzvod(izvodId): void {
    this.izvodService()
      .findPostanska(izvodId)
      .then(res => {
        this.izvod = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

 
}
