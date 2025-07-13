package toplana.web.rest.dto;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class RacunStampanje {
	 private Long id;
	 private String vlasnikPrezime;
	 private String vlasnikIme;
	 private String vlasnikNaziv;
	 private String vlasnikImePrezimeNaziv;
	 private String vlasnikEmail;
	 private String stanUlica;
	 private String stanUlaz;
	 public String getVlasnikEmail() {
		return vlasnikEmail;
	}

	public void setVlasnikEmail(String vlasnikEmail) {
		this.vlasnikEmail = vlasnikEmail;
	}
	private String stanBroj;
	 private String stanSifra;
	 private String stanStatus;
	 private String vlasnikRacun;
	 private String vlasnikPartijaRacun;
	 private String podstanicaStaroStanje;
	 private String podstanicaNovoStanje;
	 private String podstanicaRazlikaStanja;
	 private String podstanicaUkupnaPovrsina;
	 private String utrosakPoM2;
	 private String stanPovrsina;
	 private String racunUtrosakKwh;
	 private String cenaKwh;
	 private String utrosakVarijabilniBezPopusta;
	 private String utrosakVarijabilniPopust;
	 private String utrosakVarijabilniBezPdv;
	 private String pdv2;
	 private String pdv1;
	 private String utrosakVarijabilniPdv;
	 private String utrosakVarijabilni;
	 private String cenaFix;
	 private String cenaFixIskljucen;
	 private String utrosakFiksniBezPopusta;
	 private String utrosakFiksniPopust;
	 private String utrosakFiksniBezPdv;
	 private String utrosakFiksniPdv;
	 private String utrosakFiksni;
	 private String cenaOdrzavanje;
	 private String utrosakOdrzavanjeBezPdv;
	 private String utrosakOdrzavanjePdv;
	 private String utrosakOdrzavanje;
	 private String zaduzenjePoRacunu;
	 private String ukupnoZaduzenje;
	 private String zaPlacanje;
	 private String datumRacuna;
	 private String valutaPlacanja;
	 private String datumSaldiranja;
	 private String dugIzPrethodnogPerioda;
	 private String brojRacuna;
	 private String zaMesec;
	 private String popust;
	 private String period;
	 private boolean iskljucen;
	 private boolean dajeSePopust;
	 private String slikaQrStan;
	 private Image imgQr;
	 
	 
	public RacunStampanje() {}
	
	public RacunStampanje(Long id, String vlasnikPrezime, String vlasnikIme, String vlasnikNaziv, String stanUlica,
			String stanUlaz, String stanBroj, String stanSifra, String stanStatus, String vlasnikRacun,
			String vlasnikPartijaRacun, String podstanicaStaroStanje, String podstanicaNovoStanje,
			String podstanicaRazlikaStanja, String podstanicaUkupnaPovrsina, String utrosakPoM2, String stanPovrsina,
			String racunUtrosakKwh, String cenaKwh, String utrosakVarijabilniBezPopusta,
			String utrosakVarijabilniPopust, String utrosakVarijabilniBezPdv, String pdv2, String pdv1,
			String utrosakVarijabilniPdv, String utrosakVarijabilni, String cenaFix, String utrosakFiksniBezPopusta,
			String utrosakFiksniPopust, String utrosakFiksniBezPdv, String utrosakFiksniPdv, String utrosakFiksni,
			String cenaOdrzavanje, String utrosakOdrzavanjeBezPdv, String utrosakOdrzavanjePdv,
			String utrosakOdrzavanje, String zaduzenjePoRacunu, String ukupnoZaduzenje, String zaPlacanje,
			String datumRacuna, String valutaPlacanja, String datumSaldiranja, String dugIzPrethodnogPerioda,
			String brojRacuna, String zaMesec, String popust,  boolean iskljucen, boolean dajeSePopust, String cenaFixIskljucen, String period, String qr, BufferedImage bufImg, String email) {

		this.id = id;
		this.vlasnikPrezime = vlasnikPrezime;
		this.vlasnikIme = vlasnikIme;
		this.vlasnikNaziv = vlasnikNaziv;
		this.stanUlica = stanUlica;
		this.stanUlaz = stanUlaz;
		this.stanBroj = stanBroj;
		this.stanSifra = stanSifra;
		this.stanStatus = stanStatus;
		this.vlasnikRacun = vlasnikRacun;
		this.vlasnikPartijaRacun = vlasnikPartijaRacun;
		this.podstanicaStaroStanje = podstanicaStaroStanje;
		this.podstanicaNovoStanje = podstanicaNovoStanje;
		this.podstanicaRazlikaStanja = podstanicaRazlikaStanja;
		this.podstanicaUkupnaPovrsina = podstanicaUkupnaPovrsina;
		this.utrosakPoM2 = utrosakPoM2;
		this.stanPovrsina = stanPovrsina;
		this.racunUtrosakKwh = racunUtrosakKwh;
		this.cenaKwh = cenaKwh;
		this.utrosakVarijabilniBezPopusta = utrosakVarijabilniBezPopusta;
		this.utrosakVarijabilniPopust = utrosakVarijabilniPopust;
		this.utrosakVarijabilniBezPdv = utrosakVarijabilniBezPdv;
		this.pdv2 = pdv2;
		this.pdv1 = pdv1;
		this.utrosakVarijabilniPdv = utrosakVarijabilniPdv;
		this.utrosakVarijabilni = utrosakVarijabilni;
		this.cenaFix = cenaFix;
		this.utrosakFiksniBezPopusta = utrosakFiksniBezPopusta;
		this.utrosakFiksniPopust = utrosakFiksniPopust;
		this.utrosakFiksniBezPdv = utrosakFiksniBezPdv;
		this.utrosakFiksniPdv = utrosakFiksniPdv;
		this.utrosakFiksni = utrosakFiksni;
		this.cenaOdrzavanje = cenaOdrzavanje;
		this.utrosakOdrzavanjeBezPdv = utrosakOdrzavanjeBezPdv;
		this.utrosakOdrzavanjePdv = utrosakOdrzavanjePdv;
		this.utrosakOdrzavanje = utrosakOdrzavanje;
		this.zaduzenjePoRacunu = zaduzenjePoRacunu;
		this.ukupnoZaduzenje = ukupnoZaduzenje;
		this.zaPlacanje = zaPlacanje;
		this.datumRacuna = datumRacuna;
		this.valutaPlacanja = valutaPlacanja;
		this.datumSaldiranja = datumSaldiranja;
		this.dugIzPrethodnogPerioda = dugIzPrethodnogPerioda;
		this.brojRacuna = brojRacuna;
		this.zaMesec = zaMesec;
		this.popust = popust;
		this.dajeSePopust = dajeSePopust;
		this.iskljucen = iskljucen;
		this.cenaFixIskljucen = cenaFixIskljucen;
		this.period = period;
		this.slikaQrStan = qr;
		this.imgQr = bufImg;
		this.vlasnikEmail = email;
		
		String vPrezime = "";
		if(this.vlasnikPrezime != null)
			vPrezime = this.vlasnikPrezime;
		
		String vIme = "";
		if(this.vlasnikIme != null)
			vIme = this.vlasnikIme;
		
		String vNaziv = "";
		if(this.vlasnikNaziv != null)
			vNaziv = this.vlasnikNaziv;
			
		this.vlasnikImePrezimeNaziv = vPrezime + " " + " " +  vIme + " " + vNaziv;

	}
	
	
	
	public Image getImgQr() {
		return imgQr;
	}

	public void setImgQr(Image imgQr) {
		this.imgQr = imgQr;
	}

	public String getSlikaQrStan() {
		return slikaQrStan;
	}

	public void setSlikaQrStan(String slikaQrStan) {
		this.slikaQrStan = slikaQrStan;
	}

	public String getCenaFixIskljucen() {
		return cenaFixIskljucen;
	}

	public void setCenaFixIskljucen(String cenaFixIskljucen) {
		this.cenaFixIskljucen = cenaFixIskljucen;
	}

	public boolean isIskljucen() {
		return iskljucen;
	}

	public void setIskljucen(boolean iskljucen) {
		this.iskljucen = iskljucen;
	}

	public boolean isDajeSePopust() {
		return dajeSePopust;
	}

	public void setDajeSePopust(boolean dajeSePopust) {
		this.dajeSePopust = dajeSePopust;
	}

	public String getVlasnikImePrezimeNaziv() {
		return vlasnikImePrezimeNaziv;
	}

	public void setVlasnikImePrezimeNaziv(String vlasnikImePrezimeNaziv) {
		this.vlasnikImePrezimeNaziv = vlasnikImePrezimeNaziv;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}

	public void setBrojRacuna(String brojRacuna) {
		this.brojRacuna = brojRacuna;
	}

	public String getZaMesec() {
		return zaMesec;
	}

	public void setZaMesec(String zaMesec) {
		this.zaMesec = zaMesec;
	}

	public String getPopust() {
		return popust;
	}

	public void setPopust(String popust) {
		this.popust = popust;
	}

	public String getDugIzPrethodnogPerioda() {
		return dugIzPrethodnogPerioda;
	}

	public void setDugIzPrethodnogPerioda(String dugIzPrethodnogPerioda) {
		this.dugIzPrethodnogPerioda = dugIzPrethodnogPerioda;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVlasnikPrezime() {
		return vlasnikPrezime;
	}
	public void setVlasnikPrezime(String vlasnikPrezime) {
		this.vlasnikPrezime = vlasnikPrezime;
	}
	public String getVlasnikIme() {
		return vlasnikIme;
	}
	public void setVlasnikIme(String vlasnikIme) {
		this.vlasnikIme = vlasnikIme;
	}
	public String getVlasnikNaziv() {
		return vlasnikNaziv;
	}
	public void setVlasnikNaziv(String vlasnikNaziv) {
		this.vlasnikNaziv = vlasnikNaziv;
	}
	public String getStanUlica() {
		return stanUlica;
	}
	public void setStanUlica(String stanUlica) {
		this.stanUlica = stanUlica;
	}
	public String getStanUlaz() {
		return stanUlaz;
	}
	public void setStanUlaz(String stanUlaz) {
		this.stanUlaz = stanUlaz;
	}
	public String getStanBroj() {
		return stanBroj;
	}
	public void setStanBroj(String stanBroj) {
		this.stanBroj = stanBroj;
	}
	public String getStanSifra() {
		return stanSifra;
	}
	public void setStanSifra(String stanSifra) {
		this.stanSifra = stanSifra;
	}
	public String getStanStatus() {
		return stanStatus;
	}
	public void setStanStatus(String stanStatus) {
		this.stanStatus = stanStatus;
	}
	public String getVlasnikRacun() {
		return vlasnikRacun;
	}
	public void setVlasnikRacun(String vlasnikRacun) {
		this.vlasnikRacun = vlasnikRacun;
	}
	public String getVlasnikPartijaRacun() {
		return vlasnikPartijaRacun;
	}
	public void setVlasnikPartijaRacun(String vlasnikPartijaRacun) {
		this.vlasnikPartijaRacun = vlasnikPartijaRacun;
	}
	public String getPodstanicaStaroStanje() {
		return podstanicaStaroStanje;
	}
	public void setPodstanicaStaroStanje(String podstanicaStaroStanje) {
		this.podstanicaStaroStanje = podstanicaStaroStanje;
	}
	public String getPodstanicaNovoStanje() {
		return podstanicaNovoStanje;
	}
	public void setPodstanicaNovoStanje(String podstanicaNovoStanje) {
		this.podstanicaNovoStanje = podstanicaNovoStanje;
	}
	public String getPodstanicaRazlikaStanja() {
		return podstanicaRazlikaStanja;
	}
	public void setPodstanicaRazlikaStanja(String podstanicaRazlikaStanja) {
		this.podstanicaRazlikaStanja = podstanicaRazlikaStanja;
	}
	public String getPodstanicaUkupnaPovrsina() {
		return podstanicaUkupnaPovrsina;
	}
	public void setPodstanicaUkupnaPovrsina(String podstanicaUkupnaPovrsina) {
		this.podstanicaUkupnaPovrsina = podstanicaUkupnaPovrsina;
	}
	public String getUtrosakPoM2() {
		return utrosakPoM2;
	}
	public void setUtrosakPoM2(String utrosakPoM2) {
		this.utrosakPoM2 = utrosakPoM2;
	}
	public String getStanPovrsina() {
		return stanPovrsina;
	}
	public void setStanPovrsina(String stanPovrsina) {
		this.stanPovrsina = stanPovrsina;
	}
	public String getRacunUtrosakKwh() {
		return racunUtrosakKwh;
	}
	public void setRacunUtrosakKwh(String racunUtrosakKwh) {
		this.racunUtrosakKwh = racunUtrosakKwh;
	}
	public String getCenaKwh() {
		return cenaKwh;
	}
	public void setCenaKwh(String cenaKwh) {
		this.cenaKwh = cenaKwh;
	}
	public String getUtrosakVarijabilniBezPopusta() {
		return utrosakVarijabilniBezPopusta;
	}
	public void setUtrosakVarijabilniBezPopusta(String utrosakVarijabilniBezPopusta) {
		this.utrosakVarijabilniBezPopusta = utrosakVarijabilniBezPopusta;
	}
	public String getUtrosakVarijabilniPopust() {
		return utrosakVarijabilniPopust;
	}
	public void setUtrosakVarijabilniPopust(String utrosakVarijabilniPopust) {
		this.utrosakVarijabilniPopust = utrosakVarijabilniPopust;
	}
	public String getUtrosakVarijabilniBezPdv() {
		return utrosakVarijabilniBezPdv;
	}
	public void setUtrosakVarijabilniBezPdv(String utrosakVarijabilniBezPdv) {
		this.utrosakVarijabilniBezPdv = utrosakVarijabilniBezPdv;
	}
	public String getPdv2() {
		return pdv2;
	}
	public void setPdv2(String pdv2) {
		this.pdv2 = pdv2;
	}
	public String getPdv1() {
		return pdv1;
	}
	public void setPdv1(String pdv1) {
		this.pdv1 = pdv1;
	}
	public String getUtrosakVarijabilniPdv() {
		return utrosakVarijabilniPdv;
	}
	public void setUtrosakVarijabilniPdv(String utrosakVarijabilniPdv) {
		this.utrosakVarijabilniPdv = utrosakVarijabilniPdv;
	}
	public String getUtrosakVarijabilni() {
		return utrosakVarijabilni;
	}
	public void setUtrosakVarijabilni(String utrosakVarijabilni) {
		this.utrosakVarijabilni = utrosakVarijabilni;
	}
	public String getCenaFix() {
		return cenaFix;
	}
	public void setCenaFix(String cenaFix) {
		this.cenaFix = cenaFix;
	}
	public String getUtrosakFiksniBezPopusta() {
		return utrosakFiksniBezPopusta;
	}
	public void setUtrosakFiksniBezPopusta(String utrosakFiksniBezPopusta) {
		this.utrosakFiksniBezPopusta = utrosakFiksniBezPopusta;
	}
	public String getUtrosakFiksniPopust() {
		return utrosakFiksniPopust;
	}
	public void setUtrosakFiksniPopust(String utrosakFiksniPopust) {
		this.utrosakFiksniPopust = utrosakFiksniPopust;
	}
	public String getUtrosakFiksniBezPdv() {
		return utrosakFiksniBezPdv;
	}
	public void setUtrosakFiksniBezPdv(String utrosakFiksniBezPdv) {
		this.utrosakFiksniBezPdv = utrosakFiksniBezPdv;
	}
	public String getUtrosakFiksniPdv() {
		return utrosakFiksniPdv;
	}
	public void setUtrosakFiksniPdv(String utrosakFiksniPdv) {
		this.utrosakFiksniPdv = utrosakFiksniPdv;
	}
	public String getUtrosakFiksni() {
		return utrosakFiksni;
	}
	public void setUtrosakFiksni(String utrosakFiksni) {
		this.utrosakFiksni = utrosakFiksni;
	}
	public String getCenaOdrzavanje() {
		return cenaOdrzavanje;
	}
	public void setCenaOdrzavanje(String cenaOdrzavanje) {
		this.cenaOdrzavanje = cenaOdrzavanje;
	}
	public String getUtrosakOdrzavanjeBezPdv() {
		return utrosakOdrzavanjeBezPdv;
	}
	public void setUtrosakOdrzavanjeBezPdv(String utrosakOdrzavanjeBezPdv) {
		this.utrosakOdrzavanjeBezPdv = utrosakOdrzavanjeBezPdv;
	}
	public String getUtrosakOdrzavanjePdv() {
		return utrosakOdrzavanjePdv;
	}
	public void setUtrosakOdrzavanjePdv(String utrosakOdrzavanjePdv) {
		this.utrosakOdrzavanjePdv = utrosakOdrzavanjePdv;
	}
	public String getUtrosakOdrzavanje() {
		return utrosakOdrzavanje;
	}
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setUtrosakOdrzavanje(String utrosakOdrzavanje) {
		this.utrosakOdrzavanje = utrosakOdrzavanje;
	}
	public String getZaduzenjePoRacunu() {
		return zaduzenjePoRacunu;
	}
	public void setZaduzenjePoRacunu(String zaduzenjePoRacunu) {
		this.zaduzenjePoRacunu = zaduzenjePoRacunu;
	}
	public String getUkupnoZaduzenje() {
		return ukupnoZaduzenje;
	}
	public void setUkupnoZaduzenje(String ukupnoZaduzenje) {
		this.ukupnoZaduzenje = ukupnoZaduzenje;
	}
	public String getZaPlacanje() {
		return zaPlacanje;
	}
	public void setZaPlacanje(String zaPlacanje) {
		this.zaPlacanje = zaPlacanje;
	}
	public String getDatumRacuna() {
		return datumRacuna;
	}
	public void setDatumRacuna(String datumRacuna) {
		this.datumRacuna = datumRacuna;
	}
	public String getValutaPlacanja() {
		return valutaPlacanja;
	}
	public void setValutaPlacanja(String valutaPlacanja) {
		this.valutaPlacanja = valutaPlacanja;
	}
	public String getDatumSaldiranja() {
		return datumSaldiranja;
	}
	public void setDatumSaldiranja(String datumSaldiranja) {
		this.datumSaldiranja = datumSaldiranja;
	}
	 
	 
	 
	 
	 
	 
	 
}
