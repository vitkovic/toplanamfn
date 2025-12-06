//@ts-nocheck
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


import { IRacun } from '@/shared/model/racun.model';
import RacunService from './racun.service';

import { IStan } from '@/shared/model/stan.model';
import StanService from './stan.service';


@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RekapitulacijaSifraPromeneDatum extends mixins(AlertMixin) {
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  public racuns: IRacun = {};
  public stan: IStan = {};
  
  public isFetching = false;
  public margina = true;
  public filter:string = "";
  public perPage = 20;
  public currentPage = 1;


  public rekapitulacije = [{
    datum:null,
    duguje:null,
    potrazuje: null,
    sifra: "",
  }] ;
  
  
  public search = {
    datumDo:null,
    sifraStana: "" ,   
	sifraOd: "010230001" ,   // prvi stan ya podstanicu 1
	sifraDo: "040590004" , // zadnji stan za pod. 5    
    ukljucen:  false,    
    podstanica: null, 
	podstanicaOd: 1,
	podstanicaDo: 5, 
    prezime: "",
    reoni:[]
  }

  public fields: any[] = [];
  //computed properties
  get rows() {      
    return this.rekapitulacije.length;
  }

  get totalDuguje() {
    return this.rekapitulacije.reduce(function(a, c){return a + Number((c.duguje) || 0.00)}, 0.00)
  }

  get totalPotrazuje() {
    return this.rekapitulacije.reduce(function(a, c){return a + Number((c.potrazuje) || 0.00)}, 0.00)
  }

  get potrazi() {
    return this.$t('toplanaApp.transakcija.potrazi');
  }
  
  
  public mounted(): void {
    //this.retrieveAllTransakcijas();
    this.fields = [
      {key:'datum', label:this.$t('toplanaApp.transakcija.datum'), sortable:true,
      formatter: (value, key, item) => {
        const dt = new Date(value);
        return dt.toLocaleDateString('sr');        
      }},        
      {key:'sifra', label:this.$t('toplanaApp.transakcija.sifraPromene'), sortable:true},        
      {key:'duguje', label:this.$t('toplanaApp.transakcija.duguje'), sortable:true, 
      formatter: (value, key, item) => {
        //return Intl.NumberFormat().format(value);        
        return this.$options.filters.currency(value, ['']);
      }},
      {key:'potrazuje', label:this.$t('toplanaApp.transakcija.potrazuje'), sortable:true, 
      formatter: (value, key, item) => {
        //return Intl.NumberFormat().format(value);        
        return this.$options.filters.currency(value, ['']);
        //Vue2Filters.filters('currency')(value,'');
      }},      
      //{key:'actions', label:""}
    ]
  }

 
  public send(): void {    
    this.isFetching = true;    
    this.transakcijaService().rekapitulacijaSifraPromeneDatum(this.search)
    .then(res => {            
      this.margina = false;
      this.racuns = res.data;
      console.log(this.racuns);
      this.isFetching = false;
    });
  }

  public stampanje(): void {    
    this.isFetching = true;    
      this.transakcijaService()
        .retrieveRekapitulacijaSifrePromeneDatumStampanje(this.search)
        .then(res => {
          this.isFetching = false;
          var fileURL = window.URL.createObjectURL(new Blob([res]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', 'RekapitulacijaPoVrstiObavezeIDatumu.pdf');
          document.body.appendChild(fileLink);
          fileLink.click();
        });
    
  }


}
