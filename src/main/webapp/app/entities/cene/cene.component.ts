import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICene } from '@/shared/model/cene.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CeneService from './cene.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Cene extends mixins(AlertMixin) {
  @Inject('ceneService') private ceneService: () => CeneService;
  private removeId: number = null;

  public cenes: ICene[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCenes();
  }

  public clear(): void {
    this.retrieveAllCenes();
  }

  public retrieveAllCenes(): void {
    this.isFetching = true;

    this.ceneService()
      .retrieve()
      .then(
        res => {
          this.cenes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICene): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCene(): void {
    this.ceneService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.cene.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCenes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
