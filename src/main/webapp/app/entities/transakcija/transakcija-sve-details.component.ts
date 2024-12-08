import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITransakcija } from '@/shared/model/transakcija.model';
import TransakcijaService from './transakcija.service';
import { IStan } from '@/shared/model/stan.model';
import { IOstaliRacuni } from '@/shared/model/ostali-racuni.model';


interface ITransakcijaZaStanDTO {
    datumKnjizenja: Date;
	datumDokumenta: Date;
	valuta: Date;
	sifraDokumenta: string;
	sifraPromene: string;
	opis: string;
	duguje: number;
	potrazuje: number;
	saldo: number;
	racunId: number;
	stavkaIzvodaId: number;
  stavkaIzvodaPostanskaId: number;
};

class ITransakcijeZaStanZbirnoDTO {
    stan: IStan;
    ostaliRacuni: IOstaliRacuni;
	transakcije: ITransakcijaZaStanDTO[] = [];
	dugujeUkupno: number = 0;
	potrazujeUkupno: number = 0;
	saldoUkupno: number = 0;
}

@Component
export default class TransakcijaSveDetails extends Vue {
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  public transakcija: ITransakcija = {};
  public sifra = '';

  public search = {
    datumOd: null ,
    datumDo:null,
    sifraStana: "" ,    
    ukljucen:  false,    
    podstanica: null, 
    prezime: "",
    reoni:[]
  }
  
  public transakcijaZbirno = new ITransakcijeZaStanZbirnoDTO();  

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sifra) {
        vm.search.sifraStana = to.params.sifra;
        vm.retrieveSveTransakcije(to.params.sifra);
      }
    });
  }

  public retrieveSveTransakcije(sifra) {
    this.transakcijaService()
      .findAllForStan(sifra)
      .then(res => {        
        this.transakcijaZbirno = res.data;
        
      });
  }

  public pogledaj(index: number){
    let t = this.transakcijaZbirno.transakcije[index];
    if(t.racunId){
        this.$router.push({name: 'RacunView', params: {racunId: JSON.stringify(t.racunId)}});

    }else if(t.stavkaIzvodaId){
        this.$router.push({name: 'StavkeIzvodaView', params: {stavkeIzvodaId: JSON.stringify(t.stavkaIzvodaId)}});
    }else if(t.stavkaIzvodaPostanskaId){
      this.$router.push({name: 'StavkeIzvodaPostanskaView', params: {stavkeIzvodaId: JSON.stringify(t.stavkaIzvodaPostanskaId)}});
  }
  }

  public previousState() {
    this.$router.go(-1);
  }

  public stampanje(): void {
      this.transakcijaService()
        .analitickaKarticaStampanje(this.search)
        .then(res => {
          var fileURL = window.URL.createObjectURL(new Blob([res]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', 'AnalitickaKartica.pdf');
          document.body.appendChild(fileLink);
          fileLink.click();
        });
    
  }
}
