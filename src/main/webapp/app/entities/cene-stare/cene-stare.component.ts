import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICeneStare } from '@/shared/model/cene-stare.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import CeneStareService from './cene-stare.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CeneStare extends mixins(AlertMixin) {
  @Inject('ceneStareService') private ceneStareService: () => CeneStareService;
  private removeId: number = null;

  public ceneStares: ICeneStare[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCeneStares();
  }

  public clear(): void {
    this.retrieveAllCeneStares();
  }

  public retrieveAllCeneStares(): void {
    this.isFetching = true;

    this.ceneStareService()
      .retrieve()
      .then(
        res => {
          this.ceneStares = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ICeneStare): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCeneStare(): void {
    this.ceneStareService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.ceneStare.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllCeneStares();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
