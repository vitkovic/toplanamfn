import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import TransakcijaService from '../transakcija/transakcija.service';
import { ITransakcija } from '@/shared/model/transakcija.model';

import TransakcijaStaraService from '../transakcija-stara/transakcija-stara.service';
import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';

import IzvodService from '../izvod/izvod.service';
import { IIzvod } from '@/shared/model/izvod.model';

import AlertService from '@/shared/alert/alert.service';
import { IStavkeIzvodaPostanska, StavkeIzvodaPostanska } from '@/shared/model/stavke-izvoda-postanska.model';
import StavkeIzvodaPostanskaService from './stavke-izvoda-postanska.service';
import { RACUN_ZADUZENJA } from '@/constants';


const validations: any = {
  stavkeIzvoda: {
    rasporedjena: {},
    datum: {},
    status: {},
    iznos: {},
    sifra: {},
    opis: {},
  },
};

@Component({
  validations,
})
export default class StavkeIzvodaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stavkeIzvodaPostanskaService') private stavkeIzvodaPostanskaService: () => StavkeIzvodaPostanskaService;
  public stavkeIzvoda: IStavkeIzvodaPostanska = new StavkeIzvodaPostanska();

  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;

  public transakcijas: ITransakcija[] = [];

  @Inject('transakcijaStaraService') private transakcijaStaraService: () => TransakcijaStaraService;

  public transakcijaStaras: ITransakcijaStara[] = [];

  @Inject('izvodService') private izvodService: () => IzvodService;

  public izvods: IIzvod[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public RACUN_ZADUZENJA = RACUN_ZADUZENJA;

  beforeRouteEnter(to, from, next) {
    next(vm => {      
      if (to.params.stavkeIzvodaId) {
        vm.retrieveStavkeIzvodaPostanska(to.params.stavkeIzvodaId);
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


  public knjizenje(): void {
    this.isSaving = true;
    if (this.stavkeIzvoda.id) {
      this.stavkeIzvodaPostanskaService()
        .knjizenje(this.stavkeIzvoda)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stavkeIzvoda.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          var message:string = this.getMessageFromHeader(error.response);
          this.$notify({text:message, type:'error', duration:10000});
          this.isSaving = false;
        });;
    } 
  }

  private getMessageFromHeader(res: any): any {
    if(res.headers['x-toplanaapp-alert'])
      return this.$t(res.headers['x-toplanaapp-alert'], { param: decodeURIComponent(res.headers['x-toplanaapp-params'].replace(/\+/g, ' ')) });
    else
      return;  
  }

  public retrieveStavkeIzvodaPostanska(stavkeIzvodaId): void {
    debugger
    this.stavkeIzvodaPostanskaService()
      .find(stavkeIzvodaId)
      .then(res => {
        this.stavkeIzvoda = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.transakcijaService()
      .retrieve()
      .then(res => {
        this.transakcijas = res.data;
      });
    this.transakcijaStaraService()
      .retrieve()
      .then(res => {
        this.transakcijaStaras = res.data;
      });
    this.izvodService()
      .retrieve()
      .then(res => {
        this.izvods = res.data;
      });
  }
}
