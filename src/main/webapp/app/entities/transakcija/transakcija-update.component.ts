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
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { ITransakcija, Transakcija } from '@/shared/model/transakcija.model';
import TransakcijaService from './transakcija.service';

const validations: any = {
  transakcija: {
    datum: {},
    valuta: {},
    status: {},
    opis: {},
    duguje: {},
    potrazuje: {},
  },
};

@Component({
  validations,
})
export default class TransakcijaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  public transakcija: ITransakcija = new Transakcija();

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

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transakcijaId) {
        vm.retrieveTransakcija(to.params.transakcijaId);
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
          this.$router.go(-1);
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
    this.vrstaTransakcijeService()
      .retrieve()
      .then(res => {
        this.vrstaTransakcijes = res.data;
      });
    this.sifraDokumentaService()
      .retrieve()
      .then(res => {
        this.sifraDokumentas = res.data;
      });
    this.sifraPromeneService()
      .retrieve()
      .then(res => {
        this.sifraPromenes = res.data;
      });
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
  }
}
