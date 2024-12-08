import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IStanjaPodstaniceZaRacun } from '@/shared/model/stanja-podstanice-za-racun.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import StanjaPodstaniceZaRacunService from './stanja-podstanice-za-racun.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class StanjaPodstaniceZaRacun extends mixins(AlertMixin) {
  @Inject('stanjaPodstaniceZaRacunService') private stanjaPodstaniceZaRacunService: () => StanjaPodstaniceZaRacunService;
  private removeId: number = null;

  public stanjaPodstaniceZaRacuns: IStanjaPodstaniceZaRacun[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStanjaPodstaniceZaRacuns();
  }

  public clear(): void {
    this.retrieveAllStanjaPodstaniceZaRacuns();
  }

  public retrieveAllStanjaPodstaniceZaRacuns(): void {
    this.isFetching = true;

    this.stanjaPodstaniceZaRacunService()
      .retrieve()
      .then(
        res => {
          this.stanjaPodstaniceZaRacuns = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IStanjaPodstaniceZaRacun): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeStanjaPodstaniceZaRacun(): void {
    this.stanjaPodstaniceZaRacunService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.stanjaPodstaniceZaRacun.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllStanjaPodstaniceZaRacuns();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
