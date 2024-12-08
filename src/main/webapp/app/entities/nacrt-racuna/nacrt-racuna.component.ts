import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import NacrtRacunaService from './nacrt-racuna.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class NacrtRacuna extends mixins(AlertMixin) {
  @Inject('nacrtRacunaService') private nacrtRacunaService: () => NacrtRacunaService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public nacrtRacunas: INacrtRacuna[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllNacrtRacunas();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllNacrtRacunas();
  }

  public retrieveAllNacrtRacunas(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.nacrtRacunaService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.nacrtRacunas = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: INacrtRacuna): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeNacrtRacuna(): void {
    this.nacrtRacunaService()
      .delete(this.removeId)
      .then(res => {
        var porukaSaServera:string = this.getMessageFromHeader(res);
        //const por = this.$t('toplanaApp.nacrtRacuna.stanja.nisu.ubacena');
        if(porukaSaServera){
          this.closeDialog();
          this.$notify({text:porukaSaServera, type:'error', duration:5000}); 
          this.removeId = null;
          this.retrieveAllNacrtRacunas();         
        }else{ 
          const message = this.$t('toplanaApp.nacrtRacuna.deleted', { param: this.removeId });
          this.alertService().showAlert(message, 'danger');
          this.getAlertFromStore();
          this.removeId = null;
          this.retrieveAllNacrtRacunas();
          this.closeDialog();
        }
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
    this.retrieveAllNacrtRacunas();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  private getMessageFromHeader(res: any): any {
    if(res.headers['x-toplanaapp-alert'])
      return this.$t(res.headers['x-toplanaapp-alert'], { param: decodeURIComponent(res.headers['x-toplanaapp-params'].replace(/\+/g, ' ')) });
    else
      return;  
  }
}
