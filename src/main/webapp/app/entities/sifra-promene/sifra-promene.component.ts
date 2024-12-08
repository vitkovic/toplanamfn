import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISifraPromene } from '@/shared/model/sifra-promene.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import SifraPromeneService from './sifra-promene.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SifraPromene extends mixins(AlertMixin) {
  @Inject('sifraPromeneService') private sifraPromeneService: () => SifraPromeneService;
  private removeId: number = null;

  public sifraPromenes: ISifraPromene[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSifraPromenes();
  }

  public clear(): void {
    this.retrieveAllSifraPromenes();
  }

  public retrieveAllSifraPromenes(): void {
    this.isFetching = true;

    this.sifraPromeneService()
      .retrieve()
      .then(
        res => {
          this.sifraPromenes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISifraPromene): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSifraPromene(): void {
    this.sifraPromeneService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.sifraPromene.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllSifraPromenes();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
