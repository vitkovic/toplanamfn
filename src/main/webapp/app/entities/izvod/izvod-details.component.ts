import { Component, Vue, Inject } from 'vue-property-decorator';

import { IIzvod } from '@/shared/model/izvod.model';
import IzvodService from './izvod.service';
import { RACUN_ZADUZENJA } from '@/constants';

import AlertDialog from './AlertDialog.vue'




@Component({
    components: {
      AlertDialog, // Register it here
    },
})
export default class IzvodDetails extends Vue {
  @Inject('izvodService') private izvodService: () => IzvodService;
  public izvod: IIzvod = {};
  public RACUN_ZADUZENJA = RACUN_ZADUZENJA;
  
  public izvodid = 0;
  public show = true;
  public knjizi = 0;
  public doit = false;
  public isAlertVisible = false
  public alertMessage = 'Да ли сте сигурни да желите да наставите даље?'

   public showAlert(val): void {
  	 this.knjizi = val
	 this.isAlertVisible = true
   }
  


     public onOk(): void {
       this.isAlertVisible = false
       console.log('User confirmed (OK)')
       this.doit = true;
	   switch(this.knjizi) {
		case 0:
			this.rasknjiziIzvodOK();
			break;
		case 1:
			this.knjiziIzvodOK();
			break;
	   }
	   
	   
     }

     public onCancel(): void {
       this.isAlertVisible = false
       console.log('User cancelled')
       this.doit = false;
	  
     }
  
  
  
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.izvodId) {
        debugger
        vm.retrieveIzvod(to.params.izvodId);
      }
    });
  }

  public retrieveIzvod(izvodId) {
    this.izvodService()
      .find(izvodId)
      .then(res => {        
        this.izvod = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
  
  
  
  public knjiziIzvod(): void {
  	 	
	this.showAlert(1);
  		 
  }	
     

  public rasknjiziIzvod(): void {
  	 
	this.showAlert(0);
			
  }
  
  
  

  public knjiziIzvodOK(): void {
	 this.izvodService()
	       .knjiziIzvod(this.izvod.id)
	       .then(res => {        
	         this.izvod = res;
			});
		 
  }	
   

   public rasknjiziIzvodOK(): void {
	 this.izvodService()
	        .rasknjiziIzvod(this.izvod.id)
	        .then(res => {        
	          this.izvod = res;
			});
    }
		
    
  
}

