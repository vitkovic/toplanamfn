package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import toplana.domain.NacrtRacuna;
import toplana.domain.Racun;
import toplana.domain.Stan;
import toplana.domain.StanjaPodstaniceZaRacun;
import toplana.domain.Transakcija;
import toplana.domain.TransakcijaStara;
import toplana.domain.User;

public class RacunDTO {
	 private Long id;
	 private LocalDate datumRacuna;
	 private LocalDate valutaPlacanja;
	 private String period;
	 private BigDecimal utrosakVarijabilni;
	 private BigDecimal utrosakFiksni;
	 private BigDecimal utrosakOdrzavanje;
	    //potrosena kolicina u kilovatima, za stan (dobija se kao potrosnja po m2 x kvadratura stana)
	 private BigDecimal utrosakUKwh;
	 private BigDecimal cenaKwh;
	 private BigDecimal cenaFix;
	 private BigDecimal cenaFixIskljucen;
	 private BigDecimal cenaOdrzavanje;
	 private BigDecimal cenaOStalo;
	 private BigDecimal pdv1;
	 private BigDecimal pdv2;
	 private BigDecimal popust;	    
	 private BigDecimal ukupno;
	
	 //Ukupno zaduzenje za prethodni period (saldo u trenutku izdavanja racuna) ovo moze da se promeni ako se kasnije doda neki izvod koji nije bio upisan
	 private BigDecimal ukupnoZaduzenje;
	 private Boolean proknjizen;
	    // pokazuje da li je racun manuelno azuriran
	 private Boolean azuriran;
	 private User azurirao;
	 private User kreirao;
	 private Stan stan;
	 private NacrtRacuna nacrtRacuna;	 	
	 private Transakcija transakcija;
	 private TransakcijaStara transakcijaStara;
	 
	 //dodato da bi se kod liste racuna prikazala ukupna suma
	 private BigDecimal zaduzenjePoRacunu;
	 //koliko treba platiti ukupno
	 private BigDecimal zaPlacanje;
	 
	 private BigDecimal utrosakVarijabilniBezPopusta;
	 private BigDecimal utrosakFiksniBezPopusta;
	 
	 private BigDecimal utrosakVarijabilniPopust;
	 private BigDecimal utrosakFiksniPopust;
	 
	 private BigDecimal utrosakVarijabilniBezPdv;
	 private BigDecimal utrosakFiksniBezPdv;
	 private BigDecimal utrosakOdrzavanjeBezPdv;
	 
	 private BigDecimal utrosakVarijabilniPdv;
	 private BigDecimal utrosakFiksniPdv;
	 private BigDecimal utrosakOdrzavanjePdv;
	 
	 private LocalDate datumSaldiranja;
	 
	 private StanjaPodstaniceZaRacun stanjeZaRacun;
	 
	private String slikaQrStan, pozivNaBroj;
	
	private java.awt.image.BufferedImage imgQr;
	
	private Double NoviStaroStanje;
	private Double NoviNovoStanje;
	private Double NovipotrosnjazaPeriod;
	private Double NoviUdeoUZajednickoj;
	private Double NoviPotrosnjaPoSvimMerilima;
	private Double NoviZajednickaPotrosnja;
	
	
	private List<Racun> prevNextRacuni;
	

	public List<Racun> getPrevNextRacuni() {
		return prevNextRacuni;
	}

	public void setPrevNextRacuni(List<Racun> prevNextRacuni) {
		this.prevNextRacuni = prevNextRacuni;
	}

	public Double getNoviUdeoUZajednickoj() {
		return NoviUdeoUZajednickoj;
	}

	public void setNoviUdeoUZajednickoj(Double noviUdeoUZajednickoj) {
		NoviUdeoUZajednickoj = noviUdeoUZajednickoj;
	}

	public Double getNoviPotrosnjaPoSvimMerilima() {
		return NoviPotrosnjaPoSvimMerilima;
	}

	public void setNoviPotrosnjaPoSvimMerilima(Double noviPotrosnjaPoSvimMerilima) {
		NoviPotrosnjaPoSvimMerilima = noviPotrosnjaPoSvimMerilima;
	}

	public Double getNoviZajednickaPotrosnja() {
		return NoviZajednickaPotrosnja;
	}

	public void setNoviZajednickaPotrosnja(Double noviZajednickaPotrosnja) {
		NoviZajednickaPotrosnja = noviZajednickaPotrosnja;
	}

	public Double getNovipotrosnjazaPeriod() {
		return NovipotrosnjazaPeriod;
	}

	public void setNovipotrosnjazaPeriod(Double novipotrosnjazaPeriod) {
		NovipotrosnjazaPeriod = novipotrosnjazaPeriod;
	}

	public RacunDTO() {
		super();
	}

	public RacunDTO(Racun r) {
		this.id = r.getId();
		this.datumRacuna = r.getDatumRacuna();
		this.valutaPlacanja = r.getValutaPlacanja();
		this.period = r.getPeriod();
		this.utrosakVarijabilni = r.getUtrosakVarijabilni();
		this.utrosakFiksni = r.getUtrosakFiksni();
		this.utrosakOdrzavanje = r.getUtrosakOdrzavanje();
		this.utrosakUKwh = r.getUtrosakUKwh();
		this.cenaKwh = r.getCenaKwh();
		this.cenaFix = r.getCenaFix();
		this.cenaFixIskljucen = r.getCenaFixIskljucen();
		this.cenaOdrzavanje = r.getCenaOdrzavanje();
		this.cenaOStalo = r.getCenaOStalo();
		this.pdv1 = r.getPdv1();
		this.pdv2 = r.getPdv2();
		this.popust = r.getPopust();
		this.ukupnoZaduzenje = r.getUkupnoZaduzenje();
		this.proknjizen = r.getProknjizen();
		this.azuriran = r.getAzuriran();
		this.azurirao = r.getAzurirao();
		this.kreirao = r.getKreirao();
		this.stan = r.getStan();
		this.nacrtRacuna = r.getNacrtRacuna();
		this.transakcija = r.getTransakcija();
		this.transakcijaStara = r.getTransakcijaStara();
		this.zaduzenjePoRacunu = this.utrosakFiksni.add(this.utrosakVarijabilni).add(this.utrosakOdrzavanje);
		this.zaPlacanje = this.zaduzenjePoRacunu.add(this.ukupnoZaduzenje);
		
		this.utrosakVarijabilniBezPopusta = this.utrosakUKwh.multiply(this.cenaKwh).setScale(2,RoundingMode.HALF_UP);
    	this.utrosakFiksniBezPopusta = stan.getPovrsina().multiply(this.cenaFix).setScale(2,RoundingMode.HALF_UP);
    	this.utrosakOdrzavanjeBezPdv = stan.getPovrsina().multiply(this.cenaOdrzavanje).setScale(2,RoundingMode.HALF_UP);
    	if(this.popust != null) {
    		this.utrosakVarijabilniPopust = this.utrosakVarijabilniBezPopusta.multiply(this.popust.divide(new BigDecimal("100."))).setScale(2,RoundingMode.HALF_UP);
    		this.utrosakFiksniPopust = this.utrosakFiksniBezPopusta.multiply(this.popust.divide(new BigDecimal("100."))).setScale(2,RoundingMode.HALF_UP);
    		this.utrosakVarijabilniBezPdv = this.utrosakVarijabilniBezPopusta.subtract(this.utrosakVarijabilniPopust); 
    		this.utrosakFiksniBezPdv = this.utrosakFiksniBezPopusta.subtract(this.utrosakFiksniPopust);
    	}else {
    		this.utrosakVarijabilniBezPdv = this.utrosakVarijabilniBezPopusta; 
    		this.utrosakFiksniBezPdv = this.utrosakFiksniBezPopusta;
    	}
    	
    	this.ukupno = this.utrosakVarijabilniBezPopusta.subtract(this.utrosakVarijabilniPopust).add(this.utrosakFiksniBezPopusta).subtract(this.utrosakFiksniPopust);
    	
    	
    	this.utrosakVarijabilniPdv = this.utrosakVarijabilniBezPdv.multiply(this.pdv2.divide(new BigDecimal("100."))).setScale(2,RoundingMode.HALF_UP);
    	this.utrosakFiksniPdv = this.utrosakFiksniBezPdv.multiply(this.pdv2.divide(new BigDecimal("100."))).setScale(2,RoundingMode.HALF_UP);
    	this.utrosakOdrzavanjePdv = this.utrosakOdrzavanjeBezPdv.multiply(this.pdv1.divide(new BigDecimal("100."))).setScale(2,RoundingMode.HALF_UP);   
    	
    	this.datumSaldiranja = r.getDatumSaldiranja();
    	
    	for(StanjaPodstaniceZaRacun s : r.getNacrtRacuna().getStanjaPodstaniceZaRacune()) {
    		if(s.getPodstanica().getId().longValue() == r.getStan().getPodstanica().getId().longValue()) {
    			this.stanjeZaRacun = s;
    			break;
    		}
    	}
    	
    	this.slikaQrStan = null;
    	this.pozivNaBroj = "289";
    	this.imgQr = null;
    	this.NoviStaroStanje =  0.0;
    	this.NoviNovoStanje = 0.0;
    	this.NovipotrosnjazaPeriod = 0.0;
    	this.NoviUdeoUZajednickoj = 0.0;
    	this.NoviPotrosnjaPoSvimMerilima = 0.0;
    	this.NoviZajednickaPotrosnja = 0.0;
    	this.prevNextRacuni = null;
	}

	 

	
	
	public Double getNoviStaroStanje() {
		return NoviStaroStanje;
	}

	public void setNoviStaroStanje(Double noviStaroStanje) {
		NoviStaroStanje = noviStaroStanje;
	}

	public Double getNoviNovoStanje() {
		return NoviNovoStanje;
	}

	public void setNoviNovoStanje(Double noviNovoStanje) {
		NoviNovoStanje = noviNovoStanje;
	}

	public java.awt.image.BufferedImage getImgQr() {
		return imgQr;
	}

	public void setImgQr(java.awt.image.BufferedImage imgQr) {
		this.imgQr = imgQr;
	}

	public String getPozivNaBroj() {
		return pozivNaBroj;
	}

	public void setPozivNaBroj(String pozivNaBroj) {
		this.pozivNaBroj = pozivNaBroj;
	}

	public String getSlikaQrStan() {
		return slikaQrStan;
	}

	public void setSlikaQrStan(String slikaQrStan) {
		this.slikaQrStan = slikaQrStan;
	}

	public BigDecimal getCenaFixIskljucen() {
		return cenaFixIskljucen;
	}

	public void setCenaFixIskljucen(BigDecimal cenaFixIskljucen) {
		this.cenaFixIskljucen = cenaFixIskljucen;
	}

	public StanjaPodstaniceZaRacun getStanjeZaRacun() {
		return stanjeZaRacun;
	}

	public void setStanjeZaRacun(StanjaPodstaniceZaRacun stanjeZaRacun) {
		this.stanjeZaRacun = stanjeZaRacun;
	}

	public LocalDate getDatumSaldiranja() {
		return datumSaldiranja;
	}

	public void setDatumSaldiranja(LocalDate datumSaldiranja) {
		this.datumSaldiranja = datumSaldiranja;
	}

	public BigDecimal getUtrosakVarijabilniBezPdv() {
		return utrosakVarijabilniBezPdv;
	}

	public void setUtrosakVarijabilniBezPdv(BigDecimal utrosakVarijabilniBezPdv) {
		this.utrosakVarijabilniBezPdv = utrosakVarijabilniBezPdv;
	}

	public BigDecimal getUtrosakFiksniBezPdv() {
		return utrosakFiksniBezPdv;
	}

	public void setUtrosakFiksniBezPdv(BigDecimal utrosakFiksniBezPdv) {
		this.utrosakFiksniBezPdv = utrosakFiksniBezPdv;
	}

	public BigDecimal getUtrosakOdrzavanjeBezPdv() {
		return utrosakOdrzavanjeBezPdv;
	}

	public void setUtrosakOdrzavanjeBezPdv(BigDecimal utrosakOdrzavanjeBezPdv) {
		this.utrosakOdrzavanjeBezPdv = utrosakOdrzavanjeBezPdv;
	}

	public BigDecimal getUtrosakVarijabilniPdv() {
		return utrosakVarijabilniPdv;
	}

	public void setUtrosakVarijabilniPdv(BigDecimal utrosakVarijabilniPdv) {
		this.utrosakVarijabilniPdv = utrosakVarijabilniPdv;
	}

	public BigDecimal getUtrosakFiksniPdv() {
		return utrosakFiksniPdv;
	}

	public void setUtrosakFiksniPdv(BigDecimal utrosakFiksniPdv) {
		this.utrosakFiksniPdv = utrosakFiksniPdv;
	}

	public BigDecimal getUtrosakOdrzavanjePdv() {
		return utrosakOdrzavanjePdv;
	}

	public void setUtrosakOdrzavanjePdv(BigDecimal utrosakOdrzavanjePdv) {
		this.utrosakOdrzavanjePdv = utrosakOdrzavanjePdv;
	}

	public BigDecimal getUtrosakVarijabilniPopust() {
		return utrosakVarijabilniPopust;
	}

	public void setUtrosakVarijabilniPopust(BigDecimal utrosakVarijabilniPopust) {
		this.utrosakVarijabilniPopust = utrosakVarijabilniPopust;
	}

	public BigDecimal getUtrosakFiksniPopust() {
		return utrosakFiksniPopust;
	}

	public void setUtrosakFiksniPopust(BigDecimal utrosakFiksniPopust) {
		this.utrosakFiksniPopust = utrosakFiksniPopust;
	}

		public BigDecimal getUtrosakVarijabilniBezPopusta() {
			return utrosakVarijabilniBezPopusta;
		}

		public void setUtrosakVarijabilniBezPopusta(BigDecimal utrosakVarijabilniBezPopusta) {
			this.utrosakVarijabilniBezPopusta = utrosakVarijabilniBezPopusta;
		}

		public BigDecimal getUtrosakFiksniBezPopusta() {
			return utrosakFiksniBezPopusta;
		}

		public void setUtrosakFiksniBezPopusta(BigDecimal utrosakFiksniBezPopusta) {
			this.utrosakFiksniBezPopusta = utrosakFiksniBezPopusta;
		}
	
	public BigDecimal getZaPlacanje() {
		return zaPlacanje;
	}

	public void setZaPlacanje(BigDecimal zaPlacanje) {
		this.zaPlacanje = zaPlacanje;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDatumRacuna() {
		return datumRacuna;
	}

	public void setDatumRacuna(LocalDate datumRacuna) {
		this.datumRacuna = datumRacuna;
	}

	public LocalDate getValutaPlacanja() {
		return valutaPlacanja;
	}

	public void setValutaPlacanja(LocalDate valutaPlacanja) {
		this.valutaPlacanja = valutaPlacanja;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public BigDecimal getUtrosakVarijabilni() {
		return utrosakVarijabilni;
	}

	public void setUtrosakVarijabilni(BigDecimal utrosakVarijabilni) {
		this.utrosakVarijabilni = utrosakVarijabilni;
	}

	public BigDecimal getUtrosakFiksni() {
		return utrosakFiksni;
	}

	public void setUtrosakFiksni(BigDecimal utrosakFiksni) {
		this.utrosakFiksni = utrosakFiksni;
	}

	public BigDecimal getUtrosakOdrzavanje() {
		return utrosakOdrzavanje;
	}

	public void setUtrosakOdrzavanje(BigDecimal utrosakOdrzavanje) {
		this.utrosakOdrzavanje = utrosakOdrzavanje;
	}

	public BigDecimal getUtrosakUKwh() {
		return utrosakUKwh;
	}

	public void setUtrosakUKwh(BigDecimal utrosakUKwh) {
		this.utrosakUKwh = utrosakUKwh;
	}

	public BigDecimal getCenaKwh() {
		return cenaKwh;
	}

	public void setCenaKwh(BigDecimal cenaKwh) {
		this.cenaKwh = cenaKwh;
	}

	public BigDecimal getCenaFix() {
		return cenaFix;
	}

	public void setCenaFix(BigDecimal cenaFix) {
		this.cenaFix = cenaFix;
	}

	public BigDecimal getCenaOdrzavanje() {
		return cenaOdrzavanje;
	}

	public void setCenaOdrzavanje(BigDecimal cenaOdrzavanje) {
		this.cenaOdrzavanje = cenaOdrzavanje;
	}

	public BigDecimal getCenaOStalo() {
		return cenaOStalo;
	}

	public void setCenaOStalo(BigDecimal cenaOStalo) {
		this.cenaOStalo = cenaOStalo;
	}

	public BigDecimal getPdv1() {
		return pdv1;
	}

	public void setPdv1(BigDecimal pdv1) {
		this.pdv1 = pdv1;
	}

	public BigDecimal getPdv2() {
		return pdv2;
	}

	public void setPdv2(BigDecimal pdv2) {
		this.pdv2 = pdv2;
	}

	public BigDecimal getPopust() {
		return popust;
	}

	public void setPopust(BigDecimal popust) {
		this.popust = popust;
	}

	public BigDecimal getUkupnoZaduzenje() {
		return ukupnoZaduzenje;
	}

	public void setUkupnoZaduzenje(BigDecimal ukupnoZaduzenje) {
		this.ukupnoZaduzenje = ukupnoZaduzenje;
	}

	public Boolean getProknjizen() {
		return proknjizen;
	}

	public void setProknjizen(Boolean proknjizen) {
		this.proknjizen = proknjizen;
	}

	public Boolean getAzuriran() {
		return azuriran;
	}

	public void setAzuriran(Boolean azuriran) {
		this.azuriran = azuriran;
	}

	public User getAzurirao() {
		return azurirao;
	}

	public void setAzurirao(User azurirao) {
		this.azurirao = azurirao;
	}

	public User getKreirao() {
		return kreirao;
	}

	public void setKreirao(User kreirao) {
		this.kreirao = kreirao;
	}

	public Stan getStan() {
		return stan;
	}

	public void setStan(Stan stan) {
		this.stan = stan;
	}

	public NacrtRacuna getNacrtRacuna() {
		return nacrtRacuna;
	}

	public void setNacrtRacuna(NacrtRacuna nacrtRacuna) {
		this.nacrtRacuna = nacrtRacuna;
	}

	public Transakcija getTransakcija() {
		return transakcija;
	}

	public void setTransakcija(Transakcija transakcija) {
		this.transakcija = transakcija;
	}

	public TransakcijaStara getTransakcijaStara() {
		return transakcijaStara;
	}

	public void setTransakcijaStara(TransakcijaStara transakcijaStara) {
		this.transakcijaStara = transakcijaStara;
	}

	public BigDecimal getZaduzenjePoRacunu() {
		return zaduzenjePoRacunu;
	}

	public void setZaduzenjePoRacunu(BigDecimal zaduzenjePoRacunu) {
		this.zaduzenjePoRacunu = zaduzenjePoRacunu;
	}
	 
    public BigDecimal getUkupno() {
		return ukupno;
	}

	 public void setUkupno(BigDecimal ukupno) {
		 this.ukupno = ukupno;
	 }

	 
}
