import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITipPotrosaca } from '@/shared/model/tip-potrosaca.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import TipPotrosacaService from './tip-potrosaca.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class TipPotrosaca extends mixins(AlertMixin) {
  @Inject('tipPotrosacaService') private tipPotrosacaService: () => TipPotrosacaService;
  private removeId: number = null;

  public tipPotrosacas: ITipPotrosaca[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTipPotrosacas();
  }

  public clear(): void {
    this.retrieveAllTipPotrosacas();
  }

  public retrieveAllTipPotrosacas(): void {
    this.isFetching = true;

    this.tipPotrosacaService()
      .retrieve()
      .then(
        res => {
          this.tipPotrosacas = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITipPotrosaca): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTipPotrosaca(): void {
    this.tipPotrosacaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.tipPotrosaca.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllTipPotrosacas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
