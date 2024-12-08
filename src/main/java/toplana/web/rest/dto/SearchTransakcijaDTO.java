package toplana.web.rest.dto;

import java.time.LocalDate;
import java.util.List;

import toplana.domain.Podstanica;

public class SearchTransakcijaDTO {
	private LocalDate datumOd;
	private LocalDate datumDo;
    private String sifraStana;    
    private boolean ukljucen;    
    private Podstanica podstanica; 
    private String prezime;
    private List<Long> reoni;
    
	public SearchTransakcijaDTO() {
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

	

	public boolean isUkljucen() {
		return ukljucen;
	}

	public void setUkljucen(boolean ukljucen) {
		this.ukljucen = ukljucen;
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

	public List<Long> getReoni() {
		return reoni;
	}

	public void setReoni(List<Long> reoni) {
		this.reoni = reoni;
	}

	

}
