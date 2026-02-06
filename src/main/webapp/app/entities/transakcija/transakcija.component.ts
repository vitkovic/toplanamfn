// @ts-nocheck
import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITransakcija } from '@/shared/model/transakcija.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PodstanicaService from '../podstanica/podstanica.service';
import { IPodstanica } from '@/shared/model/podstanica.model';
import TipPotrosacaService from '../tip-potrosaca/tip-potrosaca.service';
import { ITipPotrosaca } from '@/shared/model/tip-potrosaca.model';
import { RACUN_OSTALI_TROSKOVI_BEZ_STANA } from '@/constants';

import TransakcijaService from './transakcija.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Transakcija extends mixins(AlertMixin) {
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  private removeId: number = null;
  public itemsPerPage = 1;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'stan.sifra';
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
    ukljucen:  true,    
    podstanica: null, 
    prezime: "",
    reoni:[],
	sifraOd:null,
	sifraDo:null,
	saldoOd:null,
	saldoDo:null
  }

  
  public fieldsbase: any[] = [];
  public fields: any[] = [];
  public fieldsort: any[] = [];
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
	
	
   /*
    this.fields = [
      {key:'sifra', label:this.$t('toplanaApp.transakcija.sifra'), sortable:true},
      {key:'prezime', label:this.$t('toplanaApp.vlasnik.prezime'), sortable:true},
      {key:'duguje', label:this.$t('toplanaApp.transakcija.duguje'), sortable:true},
      {key:'potrazuje', label:this.$t('toplanaApp.transakcija.potrazuje'), sortable:true},
      {key:'stanje', label:this.$t('toplanaApp.transakcija.stanje'),sortable:true},
      {key:'actions', label:""}
    ]
	*/
	
	this.fieldsbase = [
	      {key:'sifra', label:this.$t('toplanaApp.transakcija.sifra'), sortable:true},
	      {key:'prezime', label:this.$t('toplanaApp.vlasnik.prezime'), sortable:true,thClass:'fieldWidth'},
	      {key:'duguje', label:this.$t('toplanaApp.transakcija.duguje'), sortable:true},
	      {key:'potrazuje', label:this.$t('toplanaApp.transakcija.potrazuje'), sortable:true},
	      {key:'stanje', label:this.$t('toplanaApp.transakcija.stanje'),sortable:true},
	      {key:'actions', label:this.$t('toplanaApp.transakcija.akcije'),sortable:false}
	    ]
	
	this.fieldsort = [
	     {key:'sifra', label:this.$t('toplanaApp.transakcija.sifra'), sortable:true},
	     {key:'opis', label:this.$t('toplanaApp.transakcija.opis'), sortable:true, thClass:'fieldWidth'},
	     {key:'duguje', label:this.$t('toplanaApp.transakcija.duguje'), sortable:true},
	     {key:'potrazuje', label:this.$t('toplanaApp.transakcija.potrazuje'), sortable:true},
	     {key:'actions', label:this.$t('toplanaApp.transakcija.akcije'),sortable:false}
	   ]
	
	   this.margina = false;
	   this.retrieveAllTransakcijas();
  }

  public prikaziDetalje(sifra:string): void {
    this.$router.push({ path: `/transakcija/sve-prikaz/` + sifra });
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllTransakcijas();
  }

  public retrieveAllTransakcijas(): void {    
     
      

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
	
	
	console.log('SORT SEND:', paginationQuery.sort);
	
    this.transakcijaService()
      .retrieveCriteria(this.search, paginationQuery)
      .then(
        res => {
		  this.margina = false;
          this.transakcije = res.data;
	      this.totalItems = Number(res.headers['x-total-count']);
    	  this.queryCount = this.totalItems;
          this.isFetching = false;
		  this.fields = this.fieldsbase;
		  this.$nextTick(() => {
		     this.simulateSifraClick();
		   });
		
        },
        err => {
          this.isFetching = false;                                       
        }
      );
      
  }
  
  public simulateSifraClick():void {
      const table = this.$refs.tbl;

      if (table && typeof table.sortBy === 'function') {
        // false = ASC, true = DESC
        table.sortBy('sifra', false);
      }
    }

  public send(): void {
    this.search.reoni = [];
    for(let i = 0; i < this.selected.length;i++){
      this.search.reoni.push(this.selected[i]);
    }
	
	if (this.search.sifraStana.length > 0) {
			this.search.sifraStana = this.search.sifraStana.replace(/\D/g, '');
	}
	
	
    this.isFetching = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.transakcijaService().retrieveCriteria(this.search, paginationQuery)
    .then(res => {    
      this.margina = false;
      this.transakcije = res.data;
	  console.log(this.transakcije);
      this.isFetching = false;
      if (RACUN_OSTALI_TROSKOVI_BEZ_STANA == this.search.sifraStana) {
		this.fields = this.fieldsort;
	  } else {
		this.fields = this.fieldsbase;
	  }
      //this.queryCount = this.totalItems;      
      //this.isFetching = false;
    });
  }

  public prepareRemove(instance: ITransakcija): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTransakcija(): void {
    this.transakcijaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.transakcija.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllTransakcijas();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
	const dir = this.reverse ? 'desc' : 'asc';
	 const result = [`${this.propOrder},${dir}`];

	 // sekundarni sort samo ako nije već isti
	 if (this.propOrder !== 'id' && this.propOrder !== 'stan.sifra') {
	   result.push('stan.sifra,asc');
	 }

	 return result;
   
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllTransakcijas();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
