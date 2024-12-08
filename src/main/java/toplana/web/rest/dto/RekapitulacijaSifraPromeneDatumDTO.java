package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RekapitulacijaSifraPromeneDatumDTO {
	LocalDate datum;
	BigDecimal duguje;
	BigDecimal potrazuje;
	String sifra;
	
	public RekapitulacijaSifraPromeneDatumDTO(LocalDate datum, String sifra, BigDecimal duguje, BigDecimal potrazuje) {
		super();
		this.datum = datum;
		this.duguje = duguje;
		this.potrazuje = potrazuje;
		this.sifra = sifra;
	}
	
	public RekapitulacijaSifraPromeneDatumDTO(LocalDate datum, BigDecimal duguje, BigDecimal potrazuje) {
		super();
		this.datum = datum;
		this.duguje = duguje;
		this.potrazuje = potrazuje;	
	}

	public RekapitulacijaSifraPromeneDatumDTO() {
		super();
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
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

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	
	
	
	

}
