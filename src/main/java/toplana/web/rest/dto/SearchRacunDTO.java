package toplana.web.rest.dto;

import java.time.LocalDate;

import toplana.domain.Podstanica;

public class SearchRacunDTO {
	private LocalDate datumOd;
	private LocalDate datumDo;
    private String sifraStana;
    private String prezime;
    private boolean azuriran;
    private boolean proknjizen;
    private Podstanica podstanica;
	
    public SearchRacunDTO() {
		super();
	}

	public LocalDate getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(LocalDate datumOd) {
		this.datumOd = datumOd;
	}

	public LocalDate getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(LocalDate datumDo) {
		this.datumDo = datumDo;
	}

	public String getSifraStana() {
		return sifraStana;
	}

	public void setSifraStana(String sifraStana) {
		this.sifraStana = sifraStana;
	}

	public boolean isAzuriran() {
		return azuriran;
	}

	public void setAzuriran(boolean azuriran) {
		this.azuriran = azuriran;
	}

	public boolean isProknjizen() {
		return proknjizen;
	}

	public void setProknjizen(boolean proknjizen) {
		this.proknjizen = proknjizen;
	}

	public Podstanica getPodstanica() {
		return podstanica;
	}

	public void setPodstanica(Podstanica podstanica) {
		this.podstanica = podstanica;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	
    
    
    

}
