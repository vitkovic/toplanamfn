package toplana.web.rest.dto;

import java.time.LocalDate;

import toplana.domain.Podstanica;

public class SearchVlasnikDTO {
	private String sifraStana;
    private String prezime;
    private String ime;
    
    public SearchVlasnikDTO() {
		super();
	}

	
	public String getSifraStana() {
		return sifraStana;
	}

	public void setSifraStana(String sifraStana) {
		this.sifraStana = sifraStana;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}
    
    
    

}
