import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IParametriIstorija } from '@/shared/model/parametri-istorija.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ParametriIstorijaService from './parametri-istorija.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ParametriIstorija extends mixins(AlertMixin) {
  @Inject('parametriIstorijaService') private parametriIstorijaService: () => ParametriIstorijaService;
  private removeId: number = null;

  public parametriIstorijas: IParametriIstorija[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllParametriIstorijas();
  }

  public clear(): void {
    this.retrieveAllParametriIstorijas();
  }

  public retrieveAllParametriIstorijas(): void {
    this.isFetching = true;

    this.parametriIstorijaService()
      .retrieve()
      .then(
        res => {
          this.parametriIstorijas = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IParametriIstorija): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeParametriIstorija(): void {
    this.parametriIstorijaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.parametriIstorija.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllParametriIstorijas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
