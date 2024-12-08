import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRacun } from '@/shared/model/racun.model';
import RacunService from './racun.service';

@Component
export default class RacunDetails extends Vue {
  @Inject('racunService') private racunService: () => RacunService;
  public racun: IRacun = {};
  public loaded: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.racunId) {
        vm.retrieveRacun(to.params.racunId);
      }
    });
  }

  public retrieveRacun(racunId) {
    this.racunService()
      .find(racunId)
      .then(res => {
        this.racun = res;
        this.loaded = true;
      });
  }

  public stampanje(): void {
    this.racunService()
      .stampanjeZaJedanRacun(this.racun.id)
        .then(res => {
          //this.isSaving = false;
          var fileURL = window.URL.createObjectURL(new Blob([res]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', 'file.pdf');
          document.body.appendChild(fileLink);
          fileLink.click();
        });      
  }

  public previousState() {
    this.$router.go(-1);
  }
}
