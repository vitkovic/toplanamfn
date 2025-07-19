import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRacun } from '@/shared/model/racun.model';
import RacunService from './racun.service';

@Component
export default class RacunDetails extends Vue {
  @Inject('racunService') private racunService: () => RacunService;
  public racun: IRacun = {};
  public loaded: boolean = false;
  public List<Racun> prevnext = null;
  public Long left = 0, right = 0;

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
		this.prevnext = res.prevNextRacuni;
        this.loaded = true;
		this.checkRacunId();
      });
  }

  public checkRacunId() {
	
	if (this.prevnext.length == 1) {
		
		if (this.prevnext[0].id < this.racun.id) {
			this.left = this.prevnext[0].id
			this.right = this.racun.id;
		} else (this.prevnext[0].id > this.racun.id) {
			this.left = this.racun.id;
			this.right = this.prevnext[0].id;
		} 
	} else {
		
		this.left = this.prevnext[0].id
		this.right = this.prevnext[1].id
		
	}
	
  } 
  public previousRacun() {
	
	console.log(this.prevnext);
     this.racunService()
       .find(this.left)
       .then(res => {
         this.racun = res;
  	     this.prevnext = res.prevNextRacuni;
         this.loaded = true;
		 this.checkRacunId();
       });
   }
   
   public nextRacun() {
	  this.checkRacunId();
	  console.log(this.prevnext);
      this.racunService()
        .find(this.right)
        .then(res => {
          this.racun = res;
   	      this.prevnext = res.prevNextRacuni;
          this.loaded = true;
		  this.checkRacunId();
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
          fileLink.setAttribute('download', this.racun.stan.sifra + ".pdf");
          document.body.appendChild(fileLink);
          fileLink.click();
        });      
  }

  public previousState() {
    this.$router.go(-1);
  }
}
