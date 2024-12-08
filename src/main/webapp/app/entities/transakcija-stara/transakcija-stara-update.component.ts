import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import VrstaTransakcijeService from '../vrsta-transakcije/vrsta-transakcije.service';
import { IVrstaTransakcije } from '@/shared/model/vrsta-transakcije.model';

import SifraDokumentaService from '../sifra-dokumenta/sifra-dokumenta.service';
import { ISifraDokumenta } from '@/shared/model/sifra-dokumenta.model';

import SifraPromeneService from '../sifra-promene/sifra-promene.service';
import { ISifraPromene } from '@/shared/model/sifra-promene.model';

import CeneStareService from '../cene-stare/cene-stare.service';
import { ICeneStare } from '@/shared/model/cene-stare.model';

import StavkeIzvodaService from '../stavke-izvoda/stavke-izvoda.service';
import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';

import StanService from '../stan/stan.service';
import { IStan } from '@/shared/model/stan.model';

import AlertService from '@/shared/alert/alert.service';
import { ITransakcijaStara, TransakcijaStara } from '@/shared/model/transakcija-stara.model';
import TransakcijaStaraService from './transakcija-stara.service';

const validations: any = {
  transakcijaStara: {
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
export default class TransakcijaStaraUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('transakcijaStaraService') private transakcijaStaraService: () => TransakcijaStaraService;
  public transakcijaStara: ITransakcijaStara = new TransakcijaStara();

  @Inject('vrstaTransakcijeService') private vrstaTransakcijeService: () => VrstaTransakcijeService;

  public vrstaTransakcijes: IVrstaTransakcije[] = [];

  @Inject('sifraDokumentaService') private sifraDokumentaService: () => SifraDokumentaService;

  public sifraDokumentas: ISifraDokumenta[] = [];

  @Inject('sifraPromeneService') private sifraPromeneService: () => SifraPromeneService;

  public sifraPromenes: ISifraPromene[] = [];

  @Inject('ceneStareService') private ceneStareService: () => CeneStareService;

  public ceneStares: ICeneStare[] = [];

  @Inject('stavkeIzvodaService') private stavkeIzvodaService: () => StavkeIzvodaService;

  public stavkeIzvodas: IStavkeIzvoda[] = [];

  @Inject('stanService') private stanService: () => StanService;

  public stans: IStan[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.transakcijaStaraId) {
        vm.retrieveTransakcijaStara(to.params.transakcijaStaraId);
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
    if (this.transakcijaStara.id) {
      this.transakcijaStaraService()
        .update(this.transakcijaStara)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.transakcijaStara.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.transakcijaStaraService()
        .create(this.transakcijaStara)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.transakcijaStara.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTransakcijaStara(transakcijaStaraId): void {
    this.transakcijaStaraService()
      .find(transakcijaStaraId)
      .then(res => {
        this.transakcijaStara = res;
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
    this.ceneStareService()
      .retrieve()
      .then(res => {
        this.ceneStares = res.data;
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
