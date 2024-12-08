import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStanjaPodstanice } from '@/shared/model/stanja-podstanice.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import StanjaPodstaniceService from './stanja-podstanice.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class StanjaPodstanice extends mixins(AlertMixin) {
  @Inject('stanjaPodstaniceService') private stanjaPodstaniceService: () => StanjaPodstaniceService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;

  public stanjaPodstanices: IStanjaPodstanice[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStanjaPodstanices();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllStanjaPodstanices();
  }

  public retrieveAllStanjaPodstanices(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.stanjaPodstaniceService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.stanjaPodstanices = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStanjaPodstanice): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStanjaPodstanice(): void {
    this.stanjaPodstaniceService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.stanjaPodstanice.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllStanjaPodstanices();
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
    this.retrieveAllStanjaPodstanices();
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