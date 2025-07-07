// @ts-nocheck
import { format, parseISO } from 'date-fns';

import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import RacunService from '../racun/racun.service';
import { IRacun } from '@/shared/model/racun.model';

import PodstanicaService from '../podstanica/podstanica.service';
import { IPodstanica } from '@/shared/model/podstanica.model';


import { IStanjaPodstaniceZaRacun } from '@/shared/model/stanja-podstanice-za-racun.model';

import AlertService from '@/shared/alert/alert.service';
import { INacrtRacuna, NacrtRacuna } from '@/shared/model/nacrt-racuna.model';
import NacrtRacunaService from './nacrt-racuna.service';

const validations: any = {
  nacrtRacuna: {
    datumRacuna: {
      required,
    },
    period: {
      required,
    },
    valutaPlacanja: {
      required,
    },
    cenaKwh: {
      required
    },
    cenaFix: {
      required
    },
    cenaOdrzavanje: {
      required
    },
    cenaOStalo: {},
    popust: {},    
    pdv1: {
      required
    },
    pdv2: {
      required
    }
  }
  }
};

@Component({
  validations,
})
export default class NacrtRacunaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('nacrtRacunaService') private nacrtRacunaService: () => NacrtRacunaService;
  public nacrtRacuna: INacrtRacuna = new NacrtRacuna();

  @Inject('racunService') private racunService: () => RacunService;

  public racuns: IRacun[] = [];

  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;

  public stanjaPodstaniceZaRacun: IStanjaPodstaniceZaRacun[] = [];

  public podstanicas: IPodstanica[] = [];
  public isSaving = false;
  public currentLanguage = '';

  public isFormDisabled = true;

  

  beforeRouteEnter(to, from, next) {
    next(vm => {      
      if (to.params.nacrtRacunaId) {
        vm.retrieveNacrtRacuna(to.params.nacrtRacunaId);
      }else{
        vm.pripremaRacuna();        
      }
      
      //vm.initRelationships();
    });
  }

  mounted() {
    
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
    //this.checkForm();
  }

  public proba(){
    //this.$notify({text:JSON.stringify("PORUKA").replace(/['"]+/g, ''), type:'error', duration:5000});
    this.$notify({      
      text: JSON.stringify('Hello user! This is a notification!'), type:"error", duration:-1
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.nacrtRacuna.id) {
      this.nacrtRacunaService()
        .update(this.nacrtRacuna)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.nacrtRacuna.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          var message:string = this.getMessageFromHeader(error.response);
          this.$notify({text:message, type:'error', duration:10000});
        });
    } else {
      this.nacrtRacunaService()
        .create(this.nacrtRacuna)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.nacrtRacuna.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        //}
        })
        .catch(error => {
          var message:string = this.getMessageFromHeader(error.response);
          if(message)
            this.$notify({text:message, type:'error', duration:10000});
          else{  
            const por = this.$t('toplanaApp.nacrtRacuna.upisNijeUspeo')
            this.$notify({text:JSON.stringify(por), type:'error', duration:-15000});
          }
        });
    }
  }


  public knjizenje(): void {
    this.isSaving = true;
    if (this.nacrtRacuna.id) {
      this.nacrtRacunaService()
        .knjizenje(this.nacrtRacuna)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('toplanaApp.nacrtRacuna.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } 
  }

  public stampanje(): void {
    this.isSaving = true;
    if (this.nacrtRacuna.id) {
      this.nacrtRacunaService()
        .stampanje(this.nacrtRacuna.id)
        .then(res => {
          this.isSaving = false;

          var fileURL = window.URL.createObjectURL(new Blob([res]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', 'file.pdf');
          document.body.appendChild(fileLink);
          fileLink.click();
        });
    } 
  }

  public retrieveNacrtRacuna(nacrtRacunaId): void {
    this.nacrtRacunaService()
      .find(nacrtRacunaId)
      .then(res => {
        this.nacrtRacuna = res;
        this.checkForm();
        //this.nacrtRacuna.datumRacuna = format(parseISO(this.nacrtRacuna.datumRacuna), 'dd.MM.yyyy');
        //this.nacrtRacuna.datumRacuna = new Date(this.nacrtRacuna.datumRacuna);
        //format(this.nacrtRacuna.datumRacuna, 'dd-MM-yyyy');
        //this.nacrtRacuna.datumRacuna = $d(Date.parse(this.nacrtRacuna.datumRacuna), 'short') ;
        //this.checkForm();
      });
  }

  public pripremaRacuna(): void {
    debugger
    this.nacrtRacunaService()
      .pripremaRacuna()
      .then(res => {
        var message:string = this.getMessageFromHeader(res);
        //const por = this.$t('toplanaApp.nacrtRacuna.stanja.nisu.ubacena');
        if(message)
          this.$notify({text:message, type:'error', duration:-15000});
        this.nacrtRacuna = res.data;
        this.stanjaPodstaniceZaRacun = this.nacrtRacuna.stanjaPodstaniceZaRacune;                
      }).catch(err => {        
        
    });;
  }

  
  public checkForm(): void {   
    this.isFormDisabled = false;     
    var self = this;
    for(var stanje of this.nacrtRacuna.stanjaPodstaniceZaRacune){
      if(!stanje.staroStanje.stanje){
        self.isFormDisabled = true;
        return;
      }
      if(stanje.novoStanje.stanje){
        self.isFormDisabled = true;
        console.log( self.isFormDisabled);
        return;
      }       
      if(!stanje.ukupnaPovrsina){
        self.isFormDisabled = true;
        return;
      }       
      if(!stanje.utrosakPoM2){
        self.isFormDisabled = true;
        return;
      }       
    });

    return;
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.racunService()
      .retrieve()
      .then(res => {
        this.racuns = res.data;
      });
    this.podstanicaService()
      .retrieve()
      .then(res => {
        this.podstanicas = res.data;
      });
  }

  private getMessageFromHeader(res: any): any {
    if(res.headers['x-toplanaapp-alert']){
      let a =  this.$t(res.headers['x-toplanaapp-alert'], { param: decodeURIComponent(res.headers['x-toplanaapp-params'].replace(/\+/g, ' ')) });
      return a;
    }else
      return;  
  }
}
