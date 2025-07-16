// @ts-nocheck
import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue, decimal } from 'vuelidate/lib/validators';

import OpomenaService from '../opomena/opomena.service';
import { IOpomena } from '@/shared/model/opomena.model';

import UtuzenjeService from '../utuzenje/utuzenje.service';
import { IUtuzenje } from '@/shared/model/utuzenje.model';

import UgovorRateService from '../ugovor-rate/ugovor-rate.service';
import { IUgovorRate } from '@/shared/model/ugovor-rate.model';

import TransakcijaService from '../transakcija/transakcija.service';
import { ITransakcija } from '@/shared/model/transakcija.model';

import TransakcijaStaraService from '../transakcija-stara/transakcija-stara.service';
import { ITransakcijaStara } from '@/shared/model/transakcija-stara.model';

import RacunService from '../racun/racun.service';
import { IRacun } from '@/shared/model/racun.model';

import TipPotrosacaService from '../tip-potrosaca/tip-potrosaca.service';
import { ITipPotrosaca } from '@/shared/model/tip-potrosaca.model';

import PodstanicaService from '../podstanica/podstanica.service';
import { IPodstanica } from '@/shared/model/podstanica.model';

import VlasnikService from '../vlasnik/vlasnik.service';
import { IVlasnik } from '@/shared/model/vlasnik.model';

import AlertService from '@/shared/alert/alert.service';
import { IStan, Stan } from '@/shared/model/stan.model';
import StanService from './stan.service';

const validations: any = {
  stan: {
    povrsina: {
      required,
      decimal,
    },
    ulica: {},
    ulaz: {},
    broj: {},
    ukljucen: {},
    sifra: {},
    grad: {},
    postanskiBroj: {},
	brojMerila: {},
  },
};

@Component({
  validations,
})
export default class StanUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stanService') private stanService: () => StanService;
  public stan: IStan = new Stan();

  @Inject('opomenaService') private opomenaService: () => OpomenaService;

  public opomenas: IOpomena[] = [];

  @Inject('utuzenjeService') private utuzenjeService: () => UtuzenjeService;

  public utuzenjes: IUtuzenje[] = [];

  @Inject('ugovorRateService') private ugovorRateService: () => UgovorRateService;

  public ugovorRates: IUgovorRate[] = [];

  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;

  public transakcijas: ITransakcija[] = [];

  @Inject('transakcijaStaraService') private transakcijaStaraService: () => TransakcijaStaraService;

  public transakcijaStaras: ITransakcijaStara[] = [];

  @Inject('racunService') private racunService: () => RacunService;

  public racuns: IRacun[] = [];

  @Inject('tipPotrosacaService') private tipPotrosacaService: () => TipPotrosacaService;

  public tipPotrosacas: ITipPotrosaca[] = [];

  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;

  public podstanicas: IPodstanica[] = [];

  @Inject('vlasnikService') private vlasnikService: () => VlasnikService;

  public vlasniks: IVlasnik[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stanId) {
        vm.retrieveStan(to.params.stanId);
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
    if (this.stan.id) {
      this.stanService()
        .update(this.stan)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stan.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.stanService()
        .create(this.stan)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stan.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveStan(stanId): void {
    debugger
    this.stanService()
      .find(stanId)
      .then(res => {
        this.stan = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.opomenaService()
      .retrieve()
      .then(res => {
        this.opomenas = res.data;
      });
    this.utuzenjeService()
      .retrieve()
      .then(res => {
        this.utuzenjes = res.data;
      });
    this.ugovorRateService()
      .retrieve()
      .then(res => {
        this.ugovorRates = res.data;
      });
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
    this.racunService()
      .retrieve()
      .then(res => {
        this.racuns = res.data;
      });
    this.tipPotrosacaService()
      .retrieve()
      .then(res => {
        this.tipPotrosacas = res.data;
      });
    this.podstanicaService()
      .retrieve()
      .then(res => {
        this.podstanicas = res.data;
      });
    this.vlasnikService()  
      .retrieveAll()
      .then(res => {
        this.vlasniks = res.data;
      });
  }
}
