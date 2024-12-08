import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IIzvod } from '@/shared/model/izvod.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import IzvodService from './izvod.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class IzvodPostanska extends mixins(AlertMixin) {
  @Inject('izvodService') private izvodService: () => IzvodService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public izvods: IIzvod[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllIzvods();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllIzvods();
  }

  public retrieveAllIzvods(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.izvodService()
      .retrievePostanska(paginationQuery)
      .then(
        res => {
          this.izvods = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IIzvod): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeIzvod(): void {
    debugger
    this.izvodService()
      .deletePostanska(this.removeId)
      .then(res => {
        var porukaSaServera:string = this.getMessageFromHeader(res);        
        if(porukaSaServera){
          this.closeDialog();
          this.$notify({text:porukaSaServera, type:'info', duration:5000}); 
          this.removeId = null;
          this.retrieveAllIzvods();    
          this.closeDialog();     
        }else{ 
          const message = this.$t('toplanaApp.izvod.deleted', { param: this.removeId });
          this.alertService().showAlert(message, 'danger');
          this.getAlertFromStore();
          this.removeId = null;
          this.retrieveAllIzvods();
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
    this.retrieveAllIzvods();
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
