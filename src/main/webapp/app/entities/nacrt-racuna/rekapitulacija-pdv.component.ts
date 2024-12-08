import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { INacrtRacuna } from '@/shared/model/nacrt-racuna.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import NacrtRacunaService from './nacrt-racuna.service';
import { debug } from 'console';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class RekapitulacijaPdv extends mixins(AlertMixin) {
  @Inject('nacrtRacunaService') private nacrtRacunaService: () => NacrtRacunaService;

  public rekapitulacija:any = null;
  public isSaving = false;
  
  beforeRouteEnter(to, from, next) {
    next(vm => {      
      if (to.params.nacrtRacunaId) {
        vm.getSume(to.params.nacrtRacunaId);
      }      
    });
  }
  
  public getSume(nacrtRacunaId:number): void {
    this.nacrtRacunaService()
      .rekapitulacijaPdv(nacrtRacunaId)
      .then(
        res => {
          this.rekapitulacija = res;
        },
        err => {

        }
      );
  } 
  public stampanje(): void {
    this.isSaving = true;    
      this.nacrtRacunaService()
        .stampanjeRekapitulacije(this.rekapitulacija.nacrtRacunaId)
        .then(res => {
          this.isSaving = false;
          var fileURL = window.URL.createObjectURL(new Blob([res]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', 'rekapitulacija.pdf');
          document.body.appendChild(fileLink);
          fileLink.click();
        });
    
  }

  private getMessageFromHeader(res: any): any {
    if(res.headers['x-toplanaapp-alert'])
      return this.$t(res.headers['x-toplanaapp-alert'], { param: decodeURIComponent(res.headers['x-toplanaapp-params'].replace(/\+/g, ' ')) });
    else
      return;  
  }
}
