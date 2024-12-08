package toplana.web.rest.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class IzvodZaglavljeDTO {
	@JacksonXmlProperty(localName = "Sediste")
	private String Sediste;
	@JacksonXmlProperty(localName = "NazivSedista")
	private String NazivSedista;
	@JacksonXmlProperty(localName = "DatumIzvoda")
	private String DatumIzvoda;
	@JacksonXmlProperty(localName = "TipSloga")
	private String TipSloga;
	
	
	public String getSediste() {
		return Sediste;
	}
	public void setSediste(String sediste) {
		Sediste = sediste;
	}
	public String getNazivSedista() {
		return NazivSedista;
	}
	public void setNazivSedista(String nazivSedista) {
		NazivSedista = nazivSedista;
	}
	
	public String getDatumIzvoda() {
		return DatumIzvoda;
	}
	public void setDatumIzvoda(String datumIzvoda) {
		DatumIzvoda = datumIzvoda;
	}
	public String getTipSloga() {
		return TipSloga;
	}
	public void setTipSloga(String tipSloga) {
		TipSloga = tipSloga;
	}
	
	

}
