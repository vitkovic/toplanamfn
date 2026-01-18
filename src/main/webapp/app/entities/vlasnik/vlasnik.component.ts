import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVlasnik } from '@/shared/model/vlasnik.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import VlasnikService from './vlasnik.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Vlasnik extends mixins(AlertMixin) {
  @Inject('vlasnikService') private vlasnikService: () => VlasnikService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public margina = false;
  public vlasniks: IVlasnik[] = [];

  public search = {
     sifraStana: "" ,
     prezime: "",
  	 ime:""
   }

  
  
  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVlasniks();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllVlasniks();
  }

  public retrieveAllVlasniks(): void {
    
	this.send();
	/*
	this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.vlasnikService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.vlasniks = res.data;
		  console.log(res.data);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
	  
	  */
  }
  
  public firstStanSifra(vlasnik): void {
       if (!vlasnik.stans || vlasnik.stans.length === 0) {
         return '-';
       }
       return vlasnik.stans[0].sifra || '-';
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
      this.isFetching = true;
      const paginationQuery = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
      this.vlasnikService().retrieveCriteria(this.search, paginationQuery)
      .then(res => {    
        this.margina = false;
        this.vlasniks = res.data;
		console.log(res.data);
        this.totalItems = Number(res.headers['x-total-count']);
        this.queryCount = this.totalItems;      
        this.isFetching = false;
      });
      
    }

  
  public prepareRemove(instance: IVlasnik): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVlasnik(): void {
    this.vlasnikService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.vlasnik.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllVlasniks();
        this.closeDialog();
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
    this.retrieveAllVlasniks();
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
