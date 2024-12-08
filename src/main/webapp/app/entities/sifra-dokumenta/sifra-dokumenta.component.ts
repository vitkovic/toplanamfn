import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ISifraDokumenta } from '@/shared/model/sifra-dokumenta.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import SifraDokumentaService from './sifra-dokumenta.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class SifraDokumenta extends mixins(AlertMixin) {
  @Inject('sifraDokumentaService') private sifraDokumentaService: () => SifraDokumentaService;
  private removeId: number = null;

  public sifraDokumentas: ISifraDokumenta[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllSifraDokumentas();
  }

  public clear(): void {
    this.retrieveAllSifraDokumentas();
  }

  public retrieveAllSifraDokumentas(): void {
    this.isFetching = true;

    this.sifraDokumentaService()
      .retrieve()
      .then(
        res => {
          this.sifraDokumentas = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ISifraDokumenta): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeSifraDokumenta(): void {
    this.sifraDokumentaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.sifraDokumenta.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllSifraDokumentas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
