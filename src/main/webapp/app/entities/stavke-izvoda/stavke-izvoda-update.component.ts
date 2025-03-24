import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import TransakcijaService from '../transakcija/transakcija.service';
import { ITransakcija } from '@/shared/model/transakcija.model';

import TransakcijaStaraService from '../transakcija-stara/transakcija-stara.service';
import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';

import IzvodService from '../izvod/izvod.service';
import { IIzvod } from '@/shared/model/izvod.model';

import AlertService from '@/shared/alert/alert.service';
import { IStavkeIzvoda, StavkeIzvoda } from '@/shared/model/stavke-izvoda.model';
import StavkeIzvodaService from './stavke-izvoda.service';
import { RACUN_ZADUZENJA } from '@/constants';

import SifraPromeneService from '../sifra-promene/sifra-promene.service';

import { ISifraPromene, SifraPromene } from '@/shared/model/sifra-promene.model';



const validations: any = {
  stavkeIzvoda: {
    rasporedjena: {},
    datum: {},
    status: {},
    iznos: {},
    sifra: {},
    opis: {},
    pozivOdobrenja:{
      required
    },    
  },
  sifraPromene:{
    required
  }
};

@Component({
  validations,
})
export default class StavkeIzvodaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stavkeIzvodaService') private stavkeIzvodaService: () => StavkeIzvodaService;
  public stavkeIzvoda: IStavkeIzvoda = new StavkeIzvoda();

  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;

  public transakcijas: ITransakcija[] = [];

  @Inject('transakcijaStaraService') private transakcijaStaraService: () => TransakcijaStaraService;

  public transakcijaStaras: ITransakcijaStara[] = [];

  @Inject('izvodService') private izvodService: () => IzvodService;

  @Inject('sifraPromeneService') private sifraPromeneService: () => SifraPromeneService;

  public sifraPromenes: ISifraPromene[] = [];
  public sifraPromene: ISifraPromene = new SifraPromene();

  public izvods: IIzvod[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public RACUN_ZADUZENJA = RACUN_ZADUZENJA;
  public stavkeIzvodaTransakcija = {
    stavkeIzvoda: null,
    sifraPromene: null,
  };

  beforeRouteEnter(to, from, next) {
    next(vm => {      
      if (to.params.stavkeIzvodaId) {
        vm.retrieveStavkeIzvoda(to.params.stavkeIzvodaId);
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
    this.stavkeIzvodaTransakcija.stavkeIzvoda = this.stavkeIzvoda;
    this.stavkeIzvodaTransakcija.sifraPromene = this.sifraPromene;
    
    
	if (this.stavkeIzvoda.sifra.length > 0) {
			this.stavkeIzvoda.sifra = this.stavkeIzvoda.sifra.replace(/\D/g, '');
	}
    
    
    debugger
    if (this.stavkeIzvoda.id) {
      this.stavkeIzvodaService()
        .knjizenje(this.stavkeIzvodaTransakcija)
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

  public retrieveStavkeIzvoda(stavkeIzvodaId): void {
    this.stavkeIzvodaService()
      .find(stavkeIzvodaId)
      .then(res => {
        this.stavkeIzvoda = res.stavkeIzvoda;
        this.sifraPromene = res.sifraPromene;
        if(!this.sifraPromene){
          this.sifraPromene = this.sifraPromenes[0];
        }
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

    this.sifraPromeneService()
      .retrieveRazduzenje()
      .then(res => {
        this.sifraPromenes = res.data;
    });  
    
  }
}
