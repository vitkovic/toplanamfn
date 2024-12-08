import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITransakcija } from '@/shared/model/transakcija.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PodstanicaService from '../podstanica/podstanica.service';
import { IPodstanica } from '@/shared/model/podstanica.model';
import TipPotrosacaService from '../tip-potrosaca/tip-potrosaca.service';
import { ITipPotrosaca } from '@/shared/model/tip-potrosaca.model';


import TransakcijaService from './transakcija.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class KnjigovodstveniDnevnikAnaliticki extends mixins(AlertMixin) {
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  private removeId: number = null;
  public itemsPerPage = 1;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public transakcije: ITransakcija[] = [];
  

  public isFetching = false;

  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;

  @Inject('tipPotrosacaService') private tipPotrosacaService: () => TipPotrosacaService;

  public reoni: ITipPotrosaca[] = [];
  public filter:string = "";
  public perPage = 20;
  public currentPage = 1;
  public isSaving = false;


  public podstanice: IPodstanica[] = [];
  public margina = true;
  public selected = [];
  public options: {
    text: string; 
    value: string;  
  }[] = [];


  public search = {
    datumOd: null ,
    datumDo:null,
    sifraStana: "" ,    
    ukljucen:  false,    
    podstanica: null, 
    prezime: "",
    reoni:[]
  }
  
  public lokali = {
    duguje: '',
    potrazuje: '',
  }

  public ostali = {
    duguje: '',
    potrazuje: '',
  }

  public ukupno = {
    duguje: '',
    potrazuje: '',
  }
  

  public fields: any[] = [];
  //computed properties
  get rows() {      
    return this.transakcije.length;
  }

  get potrazi() {
    return this.$t('toplanaApp.transakcija.potrazi');
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {      
      vm.initRelationships();
    });
  }

  public initRelationships(): void {   
    this.tipPotrosacaService()    
      .retrieve()
      .then(res => {
        this.reoni = res.data;
        for(let i = 0; i < this.reoni.length; i++){          
          let opcija: {
            text: string;
            value:string;
          } = {text:JSON.stringify(this.reoni[i].tip),
              value: JSON.stringify(this.reoni[i].id)
            };
          this.options.push(opcija);

        }
      });

      this.podstanicaService()
          .retrieve()
          .then(res => {
            this.podstanice = res.data;
      });      
  }
  public mounted(): void {
    //this.retrieveAllTransakcijas();
    this.fields = [
      {key:'datumKnjizenja', label:this.$t('toplanaApp.transakcija.datumKnjizenja'), sortable:true,
      formatter: (value, key, item) => {
        const dt = new Date(value);
        return dt.toLocaleDateString('sr');        
      }},  
      {key:'sifraDokumenta', label:this.$t('toplanaApp.transakcija.sifraDokumenta'), sortable:true},  
      {key:'sifraPromene', label:this.$t('toplanaApp.transakcija.sifraPromene'), sortable:true},  
      {key:'opis', label:this.$t('toplanaApp.transakcija.opis'), sortable:true},        
      {key:'sifraStana', label:this.$t('toplanaApp.transakcija.maticniBroj'), sortable:true},  
      {key:'punoIme', label:this.$t('toplanaApp.vlasnik.prezime')},
      {key:'adresa', label:this.$t('toplanaApp.stan.adresa')},
      {key:'duguje', label:this.$t('toplanaApp.transakcija.duguje'), sortable:true, 
      formatter: (value, key, item) => {
        return Intl.NumberFormat().format(value);        
      }},
      {key:'potrazuje', label:this.$t('toplanaApp.transakcija.potrazuje'), sortable:true, 
      formatter: (value, key, item) => {
        return Intl.NumberFormat().format(value);        
      }},
      {key:'status', label:this.$t('toplanaApp.transakcija.status'),sortable:true},
      //{key:'actions', label:""}
    ]
  }

 
  public send(): void {
    this.search.reoni = [];
    for(let i = 0; i < this.selected.length;i++){
      this.search.reoni.push(this.selected[i]);
    }
    this.isFetching = true;    
    this.transakcijaService().retrieveCriteriaAnalitickiDnevnik(this.search)
    .then(res => {            
      this.margina = false;
      this.transakcije = res.data.transakcije;
      let reoni = res.data.reoniDugovi;
      for(let i = 0; i < reoni.length; i++){
        if(reoni[i].tipPotrosaca.tip == 5){
            this.lokali.duguje =  Intl.NumberFormat().format(reoni[i].duguje);
            this.lokali.potrazuje = Intl.NumberFormat().format(reoni[i].potrazuje);
        }else if(reoni[i].tipPotrosaca.tip == 0){            
            this.ostali.duguje =  Intl.NumberFormat().format(reoni[i].duguje);
            this.ostali.potrazuje = Intl.NumberFormat().format(reoni[i].potrazuje);
        }else{
            this.ukupno.duguje =  Intl.NumberFormat().format(reoni[i].duguje);
            this.ukupno.potrazuje = Intl.NumberFormat().format(reoni[i].potrazuje);
        }
      }  
     // this.ostali.potrazuje = this.ostali.potrazuje.toFixed(2);
      this.isFetching = false;
    });
  }

  public stampanje(): void {
    this.isSaving = true;    
      this.transakcijaService()
        .retrieveCriteriaAnalitickiDnevnikStampanje(this.search)
        .then(res => {
          this.isSaving = false;
          var fileURL = window.URL.createObjectURL(new Blob([res]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', 'AnalitickiDnevnik.pdf');
          document.body.appendChild(fileLink);
          fileLink.click();
        });
    
  }


}
