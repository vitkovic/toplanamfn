import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStavkeIzvoda } from '@/shared/model/stavke-izvoda.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import StavkeIzvodaService from './stavke-izvoda.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class StavkeIzvoda extends mixins(AlertMixin) {
  @Inject('stavkeIzvodaService') private stavkeIzvodaService: () => StavkeIzvodaService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public stavkeIzvodas: IStavkeIzvoda[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStavkeIzvodas();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllStavkeIzvodas();
  }

  public retrieveAllStavkeIzvodas(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.stavkeIzvodaService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.stavkeIzvodas = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStavkeIzvoda): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStavkeIzvoda(): void {
    this.stavkeIzvodaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.stavkeIzvoda.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllStavkeIzvodas();
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
    this.retrieveAllStavkeIzvodas();
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
