import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRacun } from '@/shared/model/racun.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import RacunService from './racun.service';

import PodstanicaService from '../podstanica/podstanica.service';
import { IPodstanica } from '@/shared/model/podstanica.model';

import { MonthPickerInput } from 'vue-month-picker';


@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Racun extends mixins(AlertMixin) {  
  @Inject('racunService') private racunService: () => RacunService;
  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;

  public podstanice: IPodstanica[] = [];


  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public search = {
    datumOd: null ,
    datumDo:null,
    sifraStana: "" ,
    azuriran:  false,
    proknjizen:  false,
    podstanica: null, 
    prezime: "",
	ime:""
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {      
      vm.initRelationships();
    });
  }



  

  public isSaving = false; 

  public racuns: IRacun[] = [];


  public months =["јан", "феб", "март", "апр", "мај", "јун", "јул", "авг", "сеп", "окт", "нов", "дец"];

  public mounted(): void {
   // this.retrieveAllRacuns();
  }

  public send(): void {   
   /* if(this.formDisabled()){
      const message = this.$t('toplanaApp.racun.morateUnetiNekiPodatak');
      this.$notify({text:message, type:'error', duration:10000});
      this.isSaving = false;
      return;
    }
    */
	console.log(this.search);
	if (this.search.sifraStana.length > 0) {
		this.search.sifraStana = this.search.sifraStana.replace(/\D/g, '');
	}
	//numbersOnly = input.replace(/\D/g, '');
	
	console.log(this.search);
    this.isSaving = true;
    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.racunService().retrieveCriteria(this.search, paginationQuery)
    .then(res => {    
      this.margina = false;
      this.racuns = res.data;
      this.totalItems = Number(res.headers['x-total-count']);
      this.queryCount = this.totalItems;      
      this.isSaving = false;
    });
    
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllRacuns();
  }

  public margina = true;
  public retrieveAllRacuns(): void {
    this.isSaving = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.racunService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.margina = false;
          this.racuns = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isSaving = false;
        },
        err => {
          this.isSaving = false;
        }
      );
  }

  public prepareRemove(instance: IRacun): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRacun(): void {
    this.racunService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.racun.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllRacuns();
        this.closeDialog();
      });
  }

  public stampanje(): void {
    this.racunService()
      .stampanje(this.search)
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

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
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
    this.retrieveAllRacuns();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public initRelationships(): void {   
    this.podstanicaService()
      .retrieve()
      .then(res => {
		console.log(res.data);
        this.podstanice = res.data;
      });    
  }
}
