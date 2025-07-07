// @ts-nocheck
import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import VrstaTransakcijeService from '../vrsta-transakcije/vrsta-transakcije.service';
import { IVrstaTransakcije } from '@/shared/model/vrsta-transakcije.model';

import SifraDokumentaService from '../sifra-dokumenta/sifra-dokumenta.service';
import { ISifraDokumenta } from '@/shared/model/sifra-dokumenta.model';

import SifraPromeneService from '../sifra-promene/sifra-promene.service';
import { ISifraPromene } from '@/shared/model/sifra-promene.model';

import CeneService from '../cene/cene.service';
import { ICene } from '@/shared/model/cene.model';

import StavkeIzvodaService from '../stavke-izvoda/stavke-izvoda.service';
import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';

import StanService from '../stan/stan.service';
import { IStan, Stan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { ITransakcija, Transakcija } from '@/shared/model/transakcija.model';
import TransakcijaService from './transakcija.service';

const validations: any = {
  sifra:{required,},
  transakcija: {
    datum: {
      required,
    },    
    valuta: {},
    status: {},
    opis: {},
    duguje: {required,},
    potrazuje: {},
    
  },
 
};

@Component({
  validations,
})
export default class TransakcijaZaduzenje extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;

  public transakcija: ITransakcija = new Transakcija(); 
  public sifra = null;

  @Inject('vrstaTransakcijeService') private vrstaTransakcijeService: () => VrstaTransakcijeService;

  public vrstaTransakcijes: IVrstaTransakcije[] = [];

  @Inject('sifraDokumentaService') private sifraDokumentaService: () => SifraDokumentaService;

  public sifraDokumentas: ISifraDokumenta[] = [];

  @Inject('sifraPromeneService') private sifraPromeneService: () => SifraPromeneService;

  public sifraPromenes: ISifraPromene[] = [];

  @Inject('ceneService') private ceneService: () => CeneService;

  public cenes: ICene[] = [];

  @Inject('stavkeIzvodaService') private stavkeIzvodaService: () => StavkeIzvodaService;

  public stavkeIzvodas: IStavkeIzvoda[] = [];

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public ispravnaSifra = false;
  public stan = {
    id:null,
    sifra:'',
    postanskiBroj:'',
    grad:'',
    ulica:'',
    ulaz:'',
    broj:'',
    vlasnik:{}
  };

  public dugujePotrazujeDto  = {
    saldoDuguje:null,
    saldoPotrazuje:null,
    stanjeDuguje:null,
    stanjePotrazuje:null,
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transakcijaId) {
        vm.retrieveTransakcija(to.params.transakcijaId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.transakcija.stan = {
      id:null,
      sifra:'',
      postanskiBroj:'',
      grad:'',
      ulica:'',
      ulaz:0,
      broj:0,
      vlasnik:{}
    };
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public clear(): void{
    this.dugujePotrazujeDto.saldoDuguje = null;        
    this.dugujePotrazujeDto.saldoPotrazuje = null;
    this.dugujePotrazujeDto.stanjeDuguje = null;
    this.dugujePotrazujeDto.stanjePotrazuje = null;
    
    this.transakcija.stan.sifra = '';
    this.transakcija.stan.postanskiBroj = '';
    this.transakcija.stan.grad = '';
    this.transakcija.stan.ulica = '';
    this.transakcija.stan.ulaz = 0;
    this.transakcija.stan.broj = 0;
    this.transakcija.stan.vlasnik = {};
    this.transakcija.stan.status = '';
    this.transakcija.stan.id = null;
    this.transakcija.datum = null;
    this.transakcija.sifraDokumenta = null;
    this.transakcija.datumDokumenta = null;
    this.transakcija.sifraPromene = null;
    this.transakcija.opis = null;
    this.transakcija.duguje = null;
    this.transakcija.ostaliRacuni = null;
  }

  public getStan(): void{
    this.ispravnaSifra = false;
    this.stanService()
      .findZaSifru(this.sifra)
      .then(res => {    
        this.clear();
        if(res.stan){   
          this.transakcija.stan = res.stan;
          this.transakcija.status = this.transakcija.stan.status;
          this.ispravnaSifra = true;
        }else if(res.ostaliRacuni){
          this.transakcija.ostaliRacuni = res.ostaliRacuni;          
          if(this.transakcija.ostaliRacuni.stan){
            this.transakcija.stan = this.transakcija.ostaliRacuni.stan;
            this.transakcija.stan.sifra = this.transakcija.ostaliRacuni.sifra;
            this.transakcija.status = this.transakcija.ostaliRacuni.stan.status;
            this.ispravnaSifra = true;
          }
        }else{
          //this.clear();
          this.ispravnaSifra = false;
          return;
        }
        this.dugujePotrazujeDto = res.dugujePotrazujeDto;
      })
      .catch(error => {
        this.ispravnaSifra = false;
        var message:string = this.getMessageFromHeader(error.response);
        this.$notify({text:message, type:'error', duration:10000});
      });
    //console.log(this.stan.sifra);
  }

  public save(): void {
    this.isSaving = true;
    if (this.transakcija.id) {
      this.transakcijaService()
        .update(this.transakcija)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.transakcija.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.transakcijaService()
        .create(this.transakcija)
        .then(param => {
          this.isSaving = false;
          this.$router.push("/transakcija");
          const message = this.$t('toplanaApp.transakcija.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTransakcija(transakcijaId): void {
    this.transakcijaService()
      .find(transakcijaId)
      .then(res => {
        this.transakcija = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
   
   /* this.vrstaTransakcijeService()
      .retrieve()
      .then(res => {
        this.vrstaTransakcijes = res.data;
      });
    this.sifraDokumentaService()
      .retrieve()
      .then(res => {
        this.sifraDokumentas = res.data;
      });
      */
      
    this.sifraPromeneService()    
      .retrieveZaduzenje()
      .then(res => {
        this.sifraPromenes = res.data;
      });
      /*
    this.ceneService()
      .retrieve()
      .then(res => {
        this.cenes = res.data;
      });
    this.stavkeIzvodaService()
      .retrieve()
      .then(res => {
        this.stavkeIzvodas = res.data;
      });
    this.stanService()
      .retrieve()
      .then(res => {
        this.stans = res.data;
      });
      */
  }

  private getMessageFromHeader(res: any): any {
    if(res.headers['x-toplanaapp-alert'])
      return this.$t(res.headers['x-toplanaapp-alert'], { param: decodeURIComponent(res.headers['x-toplanaapp-params'].replace(/\+/g, ' ')) });
    else
      return;  
  }
}
