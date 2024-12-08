import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IParametri } from '@/shared/model/parametri.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import ParametriService from './parametri.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Parametri extends mixins(AlertMixin) {
  @Inject('parametriService') private parametriService: () => ParametriService;
  private removeId: number = null;

  public parametris: IParametri[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllParametris();
  }

  public clear(): void {
    this.retrieveAllParametris();
  }

  public retrieveAllParametris(): void {
    this.isFetching = true;

    this.parametriService()
      .retrieve()
      .then(
        res => {
          this.parametris = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IParametri): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeParametri(): void {
    this.parametriService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.parametri.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllParametris();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
