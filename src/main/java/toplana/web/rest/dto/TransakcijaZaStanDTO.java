package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.ColumnResult;

import toplana.domain.Podstanica;
import toplana.domain.SifraDokumenta;
import toplana.domain.SifraPromene;
import toplana.domain.Stan;
import toplana.domain.TipPotrosaca;
import toplana.domain.Transakcija;

public class TransakcijaZaStanDTO {
	
	private Long id;
	private LocalDate datumKnjizenja;
	private LocalDate datumDokumenta;
	private LocalDate valuta;
	private String sifraDokumenta;
	private String sifraPromene;
	private String opis;
	private BigDecimal duguje;
	private BigDecimal potrazuje;
	private BigDecimal saldo;
	private Long racunId;
	private Long stavkaIzvodaId;
	private Long stavkaIzvodaPostanskaId;
	private String prezime;
	private String sifraStana;
	private String ime;
	private String grad;
	private String ulica;
	private int ulaz;
	private int broj;
	private Podstanica podstanica;
	private TipPotrosaca tipPotrosaca;
	private String punoIme;
	private String adresa;
	private String status;
	private boolean ukljucen;
	private int tipPotrosacaInteger;
	private int podstanicaBrojInteger;
	
	public TransakcijaZaStanDTO() {
		super();
	}
	
	public TransakcijaZaStanDTO(Transakcija t, BigDecimal saldo) {
		
		this.id = t.getId();
		this.datumKnjizenja = t.getDatum();
		if(t.getDuguje() == null)
			t.setDuguje(BigDecimal.ZERO);
		if(t.getPotrazuje() == null)
			t.setPotrazuje(BigDecimal.ZERO);
		
		this.setDatumKnjizenja(t.getDatum());
		this.setSifraDokumenta(t.getSifraDokumenta());
		if(t.getSifraPromene() != null)
			this.setSifraPromene(t.getSifraPromene().getSifra());
		this.setOpis(t.getOpis());
		this.setDuguje(t.getDuguje());
		this.setPotrazuje(t.getPotrazuje());
		saldo = saldo.add(t.getDuguje()).subtract(t.getPotrazuje()).setScale(2);
		this.saldo = saldo;
		if(t.getRacun() != null)
			this.racunId = t.getRacun().getId();
		
		if(t.getStavkaIzvoda() != null)
			this.stavkaIzvodaId = t.getStavkaIzvoda().getId();
		
		if(t.getStavkaIzvodaPostanska() != null)
			this.stavkaIzvodaPostanskaId = t.getStavkaIzvodaPostanska().getId();
		
		if(t.getStan() != null) {
			this.setUlica(t.getStan().getUlica());
			this.setSifraStana(t.getStan().getSifra());
			this.setUlaz(t.getStan().getUlaz());
			this.setBroj(t.getStan().getBroj());
			this.setGrad(t.getStan().getGrad());
			this.setStatus(t.getStatus());
			this.setPrezime(t.getStan().getVlasnik().getPrezime());
			this.setIme(t.getStan().getVlasnik().getIme());
			this.setStatus(t.getStan().getStatus());
		}else if(t.getOstaliRacuni() != null && t.getOstaliRacuni().getStan() != null) {
			this.setUlica(t.getOstaliRacuni().getStan().getUlica());
			this.setSifraStana(t.getOstaliRacuni().getSifra());
			this.setUlaz(t.getOstaliRacuni().getStan().getUlaz());
			this.setBroj(t.getOstaliRacuni().getStan().getBroj());
			this.setGrad(t.getOstaliRacuni().getStan().getGrad());
			//this.setStatus(t.getStatus());
			this.setPrezime(t.getOstaliRacuni().getNaziv());
			this.setIme("");
			this.setStatus(t.getOstaliRacuni().getStan().getStatus());
		}else {
			//this.setUlica("");
			this.setSifraStana(t.getOstaliRacuni().getSifra());
			//this.setUlaz(t.getOstaliRacuni().getStan().getUlaz());
			//this.setBroj(t.getOstaliRacuni().getStan().getBroj());
			//this.setGrad(t.getOstaliRacuni().getStan().getGrad());
			//this.setStatus(t.getStatus());
			this.setPrezime(t.getOstaliRacuni().getNaziv());
			//this.setIme("");
			//this.setStatus(t.getOstaliRacuni().getStan().getStatus());
		}
		
	}	
	
	
	public TransakcijaZaStanDTO (LocalDate datumKnjizenja, String sifraDokumenta, String sifraPromene, BigDecimal duguje, 
			BigDecimal potrazuje, String maticniBroj, String prezime,  String grad, String ulica, Integer ulaz, Integer broj,   
			boolean ukljucen,  Integer tipPotrosaca, Integer podstanicaBroj, String opis) {
		
		this.datumKnjizenja = datumKnjizenja;
		this.sifraDokumenta = sifraDokumenta;
		this.sifraPromene = sifraPromene;
		this.potrazuje = potrazuje;
		this.duguje = duguje;
		this.prezime = prezime;
		this.sifraStana = maticniBroj;
		this.opis = opis;		
		this.ime = ime;
		this.grad = grad;
		this.ulica = ulica;
		this.ulaz = ulaz;
		this.broj = broj;		
		this.tipPotrosacaInteger = tipPotrosaca;
		this.podstanicaBrojInteger = podstanicaBroj;
		this.ukljucen = ukljucen;
		this.status = "" + this.podstanicaBrojInteger + this.getUkljucen(); 
		
	}
	
	public int getTipPotrosacaInteger() {
		return tipPotrosacaInteger;
	}

	public void setTipPotrosacaInteger(int tipPotrosacaInteger) {
		this.tipPotrosacaInteger = tipPotrosacaInteger;
	}

	public int getUkljucen() {
		if(ukljucen)
			return 0;
		else
			return 1;
	}

	public void setUkljucen(boolean ukljucen) {
		this.ukljucen = ukljucen;
	}

	public String getStatus() {
		if(tipPotrosaca != null)
			return "" + tipPotrosaca.getTip() + getUkljucen();
		else if(status != null)
			return status;
		else	
			return "";
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	

	public Long getStavkaIzvodaPostanskaId() {
		return stavkaIzvodaPostanskaId;
	}

	public void setStavkaIzvodaPostanskaId(Long stavkaIzvodaPostanskaId) {
		this.stavkaIzvodaPostanskaId = stavkaIzvodaPostanskaId;
	}

	public int getUlaz() {
		return ulaz;
	}

	public void setUlaz(int ulaz) {
		this.ulaz = ulaz;
	}

	public int getBroj() {
		return broj;
	}

	public void setBroj(int broj) {
		this.broj = broj;
	}

	public Podstanica getPodstanica() {
		return podstanica;
	}

	public void setPodstanica(Podstanica podstanica) {
		this.podstanica = podstanica;
	}

	public TipPotrosaca getTipPotrosaca() {
		return tipPotrosaca;
	}

	public void setTipPotrosaca(TipPotrosaca tipPotrosaca) {
		this.tipPotrosaca = tipPotrosaca;
	}

	public String getPunoIme() {
		if(prezime != null && ime != null)
			return prezime + ", " + ime;
		else if(prezime != null && ime == null)
			return prezime;
		else
			return "";
	}

	public void setPunoIme(String punoIme) {
		this.punoIme = punoIme;
	}

	public String getAdresa() {
		if(grad != null && ulica != null)
			return grad + ", " + ulica + ", " + ulaz + "/" + broj;
		else
			return "";
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	
	
	
	
	
	
	public int getPodstanicaBrojInteger() {
		return podstanicaBrojInteger;
	}

	public void setPodstanicaBrojInteger(int podstanicaBrojInteger) {
		this.podstanicaBrojInteger = podstanicaBrojInteger;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getSifraStana() {
		return sifraStana;
	}

	public void setSifraStana(String sifraStana) {
		this.sifraStana = sifraStana;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDatumKnjizenja() {
		return datumKnjizenja;
	}

	public void setDatumKnjizenja(LocalDate datumKnjizenja) {
		this.datumKnjizenja = datumKnjizenja;
	}

	

	public String getSifraDokumenta() {
		return sifraDokumenta;
	}

	public void setSifraDokumenta(String sifraDokumenta) {
		this.sifraDokumenta = sifraDokumenta;
	}

	

	public String getSifraPromene() {
		return sifraPromene;
	}

	public void setSifraPromene(String sifraPromene) {
		this.sifraPromene = sifraPromene;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public BigDecimal getDuguje() {
		return duguje;
	}

	public void setDuguje(BigDecimal duguje) {
		this.duguje = duguje;
	}

	public BigDecimal getPotrazuje() {
		return potrazuje;
	}

	public void setPotrazuje(BigDecimal potrazuje) {
		this.potrazuje = potrazuje;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Long getRacunId() {
		return racunId;
	}

	public void setRacunId(Long racunId) {
		this.racunId = racunId;
	}

	public Long getStavkaIzvodaId() {
		return stavkaIzvodaId;
	}

	public void setStavkaIzvodaId(Long stavkaIzvodaId) {
		this.stavkaIzvodaId = stavkaIzvodaId;
	}

	public LocalDate getDatumDokumenta() {
		return datumDokumenta;
	}

	public void setDatumDokumenta(LocalDate datumDokumenta) {
		this.datumDokumenta = datumDokumenta;
	}

	public LocalDate getValuta() {
		return valuta;
	}

	public void setValuta(LocalDate valuta) {
		this.valuta = valuta;
	}
	
	
	
	
	

}
