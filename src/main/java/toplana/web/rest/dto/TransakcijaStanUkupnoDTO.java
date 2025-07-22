package toplana.web.rest.dto;

import java.math.BigDecimal;

import toplana.domain.Stan;

public class TransakcijaStanUkupnoDTO {
	private BigDecimal duguje;
	private BigDecimal potrazuje;
	private BigDecimal stanje;
	private String sifra;
	private Long stanId;
	private String prezime;
	private String datum;
		
	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public TransakcijaStanUkupnoDTO(BigDecimal duguje, BigDecimal potrazuje, Long stanId, String sifra) {
		this.duguje = duguje;
		this.potrazuje = potrazuje;		
		this.sifra = sifra;		
	}
	
	public TransakcijaStanUkupnoDTO(BigDecimal duguje, BigDecimal potrazuje,  String prezime, String sifra) {
		this.duguje = duguje;
		this.potrazuje = potrazuje;		
		this.sifra = sifra;		
		this.prezime = prezime;
		if(this.duguje == null) 
			this.duguje = BigDecimal.ZERO;
		
		if(this.potrazuje == null) 
			this.potrazuje = BigDecimal.ZERO;
		
		this.stanje = this.duguje.subtract(this.potrazuje).setScale(2);
		
	}
	
	public TransakcijaStanUkupnoDTO(BigDecimal duguje, BigDecimal potrazuje, String sifra) {
		this.duguje = duguje;
		this.potrazuje = potrazuje;		
		this.sifra = sifra;		
	}
	
	public TransakcijaStanUkupnoDTO(String datum, BigDecimal duguje, BigDecimal potrazuje, String sifra) {
		this.datum = datum;
		this.duguje = duguje;
		this.potrazuje = potrazuje;		
		this.sifra = sifra;		
	}
	
	public TransakcijaStanUkupnoDTO(BigDecimal duguje, BigDecimal potrazuje, BigDecimal stanje,  String sifra, String prezime) {
		this.duguje = duguje;
		this.potrazuje = potrazuje;		
		this.sifra = sifra;		
		this.prezime = prezime;
		if(this.duguje == null) 
			this.duguje = BigDecimal.ZERO;
		
		if(this.potrazuje == null) 
			this.potrazuje = BigDecimal.ZERO;
		
		this.stanje = stanje;
		
	}
	
	
	public TransakcijaStanUkupnoDTO() {
		super();
	}
	
	
	
	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
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
	public BigDecimal getStanje() {
		return stanje;
	}
	public void setStanje(BigDecimal stanje) {
		this.stanje = stanje;
	}
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public Long getStanId() {
		return stanId;
	}
	public void setStanId(Long stanId) {
		this.stanId = stanId;
	}
	
	
	
		

}
