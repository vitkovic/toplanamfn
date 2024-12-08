import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import PodstanicaService from '../podstanica/podstanica.service';
import { IPodstanica } from '@/shared/model/podstanica.model';


import AlertService from '@/shared/alert/alert.service';
import { IStanjaPodstanice, StanjaPodstanice } from '@/shared/model/stanja-podstanice.model';
import StanjaPodstaniceService from './stanja-podstanice.service';

const validations: any = {
  stanja: {    
    datumOcitavanja: {
      required,
    },
    
  },
};

class StanjaZaSlanje{    
    stanjaPodstanice: IStanjaPodstanice[];
    constructor(stanja: IStanjaPodstanice[]){
        this.stanjaPodstanice = stanja;
    }
}

@Component({
  validations,
})
export default class StanjaPodstaniceZbirnoKreiranje extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('stanjaPodstaniceService') private stanjaPodstaniceService: () => StanjaPodstaniceService;
  public stanjaPodstanice: IStanjaPodstanice = new StanjaPodstanice();

  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;

  public podstanicas: IPodstanica[] = [];
  public isSaving = false;
  public currentLanguage = '';
  public stanjaNaPodstanicama: IStanjaPodstanice[] = [];
  public stanja = {
    datumOcitavanja: null,
    stanjaPodstanice: []
  }

  public stanjaSlanje: StanjaZaSlanje = new StanjaZaSlanje([]); 

  public datumCitanja:Date;
  public svaStanjaZaStanje = {
    
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
     /* if (to.params.stanjaPodstaniceId) {
        vm.retrieveStanjaPodstanice(to.params.stanjaPodstaniceId);
      }
      vm.initRelationships();
    */
      vm.vratiPraznaStanja();
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
    if (this.stanjaPodstanice.id) {
      this.stanjaPodstaniceService()
        .update(this.stanjaPodstanice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.stanjaPodstanice.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
        for(let i = 0; i < this.stanjaNaPodstanicama.length; ++i){
            this.stanjaNaPodstanicama[i].datumOcitavanja = this.stanja.datumOcitavanja;
        }
        this.stanjaSlanje.stanjaPodstanice = this.stanjaNaPodstanicama;
        this.stanjaPodstaniceService()
            .createBatch(this.stanjaSlanje)
            .then(res => {
            this.isSaving = false;
            this.$router.go(-1);
            const message = this.$t('toplanaApp.stanjaPodstanice.uspesnoKreiranaStanja');
            this.alertService().showAlert(message, 'success');
        });
    }
  }

 

  public vratiPraznaStanja(): void {    
    this.stanjaPodstaniceService()
      .vratiPraznaStanja()
      .then(res => {
        this.stanjaNaPodstanicama = res.data;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.podstanicaService()
      .retrieve()
      .then(res => {
        this.podstanicas = res.data;
      });
  }
}
