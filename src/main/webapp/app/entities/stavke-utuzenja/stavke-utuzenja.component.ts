import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStavkeUtuzenja } from '@/shared/model/stavke-utuzenja.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import StavkeUtuzenjaService from './stavke-utuzenja.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class StavkeUtuzenja extends mixins(AlertMixin) {
  @Inject('stavkeUtuzenjaService') private stavkeUtuzenjaService: () => StavkeUtuzenjaService;
  private removeId: number = null;

  public stavkeUtuzenjas: IStavkeUtuzenja[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStavkeUtuzenjas();
  }

  public clear(): void {
    this.retrieveAllStavkeUtuzenjas();
  }

  public retrieveAllStavkeUtuzenjas(): void {
    this.isFetching = true;

    this.stavkeUtuzenjaService()
      .retrieve()
      .then(
        res => {
          this.stavkeUtuzenjas = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStavkeUtuzenja): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStavkeUtuzenja(): void {
    this.stavkeUtuzenjaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.stavkeUtuzenja.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllStavkeUtuzenjas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
