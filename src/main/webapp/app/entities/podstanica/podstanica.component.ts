import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPodstanica } from '@/shared/model/podstanica.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import PodstanicaService from './podstanica.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Podstanica extends mixins(AlertMixin) {
  @Inject('podstanicaService') private podstanicaService: () => PodstanicaService;
  private removeId: number = null;

  public podstanicas: IPodstanica[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPodstanicas();
  }

  public clear(): void {
    this.retrieveAllPodstanicas();
  }

  public retrieveAllPodstanicas(): void {
    this.isFetching = true;

    this.podstanicaService()
      .retrieve()
      .then(
        res => {
          this.podstanicas = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IPodstanica): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePodstanica(): void {
    this.podstanicaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('toplanaApp.podstanica.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllPodstanicas();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
