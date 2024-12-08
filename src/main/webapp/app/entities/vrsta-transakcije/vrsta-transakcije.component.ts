import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVrstaTransakcije } from '@/shared/model/vrsta-transakcije.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import VrstaTransakcijeService from './vrsta-transakcije.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class VrstaTransakcije extends mixins(AlertMixin) {
  @Inject('vrstaTransakcijeService') private vrstaTransakcijeService: () => VrstaTransakcijeService;
  private removeId: number = null;

  public vrstaTransakcijes: IVrstaTransakcije[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVrstaTransakcijes();
  }

  public clear(): void {
    this.retrieveAllVrstaTransakcijes();
  }

  public retrieveAllVrstaTransakcijes(): void {
    this.isFetching = true;

    this.vrstaTransakcijeService()
      .retrieve()
      .then(
        res => {
          this.vrstaTransakcijes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IVrstaTransakcije): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVrstaTransakcije(): void {
    this.vrstaTransakcijeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.vrstaTransakcije.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllVrstaTransakcijes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
