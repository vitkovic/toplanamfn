// @ts-nocheck
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
  stavkaUtuzenjaOpis: string;
};

class ITransakcijeZaStanZbirnoDTO {
    stan: IStan;
    ostaliRacuni: IOstaliRacuni;
	transakcije: ITransakcijaZaStanDTO[] = [];
  pregledUplata: any[] = [];
	dugujeUkupno: number = 0;
	potrazujeUkupno: number = 0;
	saldoUkupno: number = 0;
}

@Component
export default class TransakcijaSveDetails extends Vue {
  @Inject('transakcijaService') private transakcijaService: () => TransakcijaService;
  public transakcija: ITransakcija = {};
  public String sifra = null;
  public String previousSearch = null;
  public Boolean sve = false;
  public Boolean racuni = false;
  public Boolean uplate = false;
  public String opis = null;
  public List<Stan> prevnext = [];
  public Long left = 0, right = 0;
  public search = {
    datumOd: null ,
    datumDo:null,
    sifraStana: "" ,    
    ukljucen:  false,    
    podstanica: null, 
    prezime: "",
    reoni:[],
	sve:false,
	opis:null,
	racuni: false,
	uplate: false,
  }
  
  public prikazOtvorenihStavki = false;
  // Sortiranje iskljucivo pregleda uplata u otvorenim stavkama.
  public otvoreneUplateSortKolona = 'datum';
  public otvoreneUplateSortSmer = 1;

  public sortirajOtvoreneUplate(kolona: string): void {
    if (!this.prikazOtvorenihStavki) return;
    if (this.otvoreneUplateSortKolona === kolona) {
      this.otvoreneUplateSortSmer *= -1;
    } else {
      this.otvoreneUplateSortKolona = kolona;
      this.otvoreneUplateSortSmer = 1;
    }
  }

  get sortiraneOtvoreneUplate(): any[] {
    const uplate = this.transakcijaZbirno && this.transakcijaZbirno.pregledUplata;
    if (!Array.isArray(uplate)) return [];
    const kolona = this.otvoreneUplateSortKolona;
    const smer = this.otvoreneUplateSortSmer;
    return uplate.slice().sort((a, b) => {
      const av = a[kolona];
      const bv = b[kolona];
      if (av == null && bv == null) return 0;
      if (av == null) return 1;
      if (bv == null) return -1;
      if (kolona === 'originalniIznos' || kolona === 'nerasporedjeno') {
        return smer * (Number(av) - Number(bv));
      }
      return smer * String(av).localeCompare(String(bv), 'sr', { numeric: true });
    });
  }
  public transakcijaZbirno = new ITransakcijeZaStanZbirnoDTO();  

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.sifra) {
        vm.search.sifraStana = to.params.sifra;
		vm.sifra = to.params.sifra;
		vm.sve = to.query.sve;
		vm.search.sve = vm.sve;
		vm.retrieveSveTransakcije(to.params.sifra);
		if (to.query.search) {
		      vm.previousSearch = JSON.parse(to.query.search);
		}
      }
    });
  }
  
  onSveChange(event) {
    this.prikazOtvorenihStavki = false;
	this.search.sve = this.sve;

	 this.opis = '';
	 this.racuni = false;
	 this.uplate = false;

	 this.search.opis = '';
	 this.search.racuni = false;
	 this.search.uplate = false;

	 this.retrieveSveTransakcije(this.sifra);
  }
  
  
  public checkStanSifra() {
  console.log(this.prevnext);

  if (this.prevnext.length == 1) {
  	
  	if (this.prevnext[0].sifra < this.sifra) {
  		this.left = this.sifra;
  		this.right = this.prevnext[0].sifra;
  	} else (this.prevnext[0].sifra >this.sifra) {
  		this.left = this.prevnext[0].sifra;
  		this.right = this.sifra;
  	} 
  } else {
  	
  	this.left = this.prevnext[0].sifra
  	this.right = this.prevnext[1].sifra
  	
  }
  public previousStan() {

  	this.checkStanSifra();
  //	this.$router.push({name: 'TransakcijaSveDetails', params: {sifra: this.left} });
	     const target = { name: 'TransakcijaSveDetails', params: {sifra: this.left}, query: { r: Date.now() }};

		if (
		  this.$route.name !== target.name ||
		  this.$route.params.sifra !== target.params.sifra
		) {
		
		  this.$router.push(target);
		  const href = this.$router.resolve(target).href;
		  			  // or replace current history entry:
		  		  window.location.replace(href); 
		}

  
  }
    
  public nextStan() {
	
	    this.checkStanSifra();
	 	//this.$router.push({name: 'TransakcijaSveDetails', params: {sifra: this.right} });
		
		
		const target = { name: 'TransakcijaSveDetails', params: {sifra: this.right}, query: { r: Date.now() } };

		if (
		  this.$route.name !== target.name ||
		  this.$route.params.sifra !== target.params.sifra
		) {
		  this.$router.push(target);
		  const href = this.$router.resolve(target).href;
			  // or replace current history entry:
		  window.location.replace(href); 
		}
		
  }
   
  
  onEnterOpis() {
    this.prikazOtvorenihStavki = false;
	this.search.opis = this.opis;
	this.transakcijaService()
	     .findAllForStanOpis(this.sifra, this.sve, this.opis,	this.racuni, this.uplate)
	     .then(res => {        
	    this.transakcijaZbirno = res.data;
		console.log(this.transakcijaZbirno);
		this.transakcijaZbirno.stan.prevNextStan = this.transakcijaZbirno.prevNextTransakcije;
		this.prevnext = this.transakcijaZbirno.prevNextTransakcije;
		console.log(this.prevnext)
	       
	     });
  }
  
  
    // poziv novog REST metoda
	public prikaziOtvoreneStavke(): void {
      this.prikazOtvorenihStavki = true;
	  this.transakcijaService()
	    .findOtvoreneStavkeZaStan(this.sifra)
	    .then(res => {
	      this.transakcijaZbirno = res.data;

	      this.transakcijaZbirno.stan.prevNextStan =
	        this.transakcijaZbirno.prevNextTransakcije;

	      this.prevnext =
	        this.transakcijaZbirno.prevNextTransakcije;
	    });
	}
  
  
  public retrieveSveTransakcije(sifra) {
    this.prikazOtvorenihStavki = false;
	
    this.transakcijaService()
      .findAllForStan(sifra, this.sve)
      .then(res => {        
	    this.transakcijaZbirno = res.data;
		console.log(this.transakcijaZbirno);
		this.transakcijaZbirno.stan.prevNextStan = this.transakcijaZbirno.prevNextTransakcije;
		this.prevnext = this.transakcijaZbirno.prevNextTransakcije;
		console.log(this.prevnext)
        
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
    }else if(t.id){
      this.$router.push({name: 'TransakcijaView', params: {transakcijaId: JSON.stringify(t.id)}});
  }
  }

  previousState() {
    if (this.previousSearch) {
      this.$router.push({
        path: '/transakcija',
        query: {
          search: JSON.stringify(this.previousSearch)
        }
      });
    } else {
      this.$router.push('/transakcija');
    }
  }

  public stampanje(): void {
	this.search.sve = this.sve;
	this.search.opis = this.opis;
	this.search.racuni = this.racuni;
	this.search.uplate = this.uplate;
    
	  this.transakcijaService()
        [this.prikazOtvorenihStavki ? 'otvoreneStavkeStampanje' : 'analitickaKarticaStampanje'](this.search)
        .then(res => {
          var fileURL = window.URL.createObjectURL(new Blob([res]));
          var fileLink = document.createElement('a');
          fileLink.href = fileURL;
          fileLink.setAttribute('download', this.prikazOtvorenihStavki ? 'OtvoreneStavke.pdf' : 'AnalitickaKartica.pdf');
          document.body.appendChild(fileLink);
          fileLink.click();
        });
    
  }
}
