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
export default class SintetickiDnevnik extends mixins(AlertMixin) {
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  
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
    datumOd: null ,
    datumDo:null,
    sifraStana: "" ,    
    ukljucen:  false,    
    podstanica: null, 
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
    this.transakcijaService().sintetickiDnevnik(this.search)
    .then(res => {            
      this.margina = false;
      this.rekapitulacije = res.data;
      
      this.isFetching = false;
    });
  }

  public stampanje(): void {
    this.isFetching = true;    
      this.transakcijaService()
        .sintetickiDnevnikStampanje(this.search)
        .then(res => {
          this.isFetching = false;
          var fileURL = window.URL.createObjectURL(new Blob([res]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', 'SintetickiDnevnik.pdf');
          document.body.appendChild(fileLink);
          fileLink.click();
        });
    
  }


}
