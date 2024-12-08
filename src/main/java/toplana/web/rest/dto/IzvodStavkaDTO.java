package toplana.web.rest.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class IzvodStavkaDTO {

	@JacksonXmlProperty(localName = "RacunZaduzenja")
	private String RacunZaduzenja;
	@JacksonXmlProperty(localName = "NazivZaduzenja")
	private String NazivZaduzenja;
	@JacksonXmlProperty(localName = "MestoZaduzenja")
	private String MestoZaduzenja;
	@JacksonXmlProperty(localName = "IzvorInformacije")
	private String IzvorInformacije;
	@JacksonXmlProperty(localName = "ModelPozivaZaduzenja")
	private String ModelPozivaZaduzenja;
	@JacksonXmlProperty(localName = "PozivZaduzenja")
	private String PozivZaduzenja;
	@JacksonXmlProperty(localName = "SifraPlacanja")
	private String SifraPlacanja;
	@JacksonXmlProperty(localName = "Iznos")
	private String Iznos;
	@JacksonXmlProperty(localName = "RacunOdobrenja")
	private String RacunOdobrenja;
	@JacksonXmlProperty(localName = "NazivOdobrenja")
	private String NazivOdobrenja;
	@JacksonXmlProperty(localName = "MestoOdobrenja")
	private String MestoOdobrenja;
	@JacksonXmlProperty(localName = "ModelPozivaOdobrenja")
	private String ModelPozivaOdobrenja;
	@JacksonXmlProperty(localName = "PozivOdobrenja")
	private String PozivOdobrenja;
	@JacksonXmlProperty(localName = "SvrhaDoznake")
	private String SvrhaDoznake;
	@JacksonXmlProperty(localName = "PodatakZaReklamaciju")
	private String PodatakZaReklamaciju;
	@JacksonXmlProperty(localName = "DatumValute")
	private String DatumValute;
	@JacksonXmlProperty(localName = "NacinObracuna")
	private String NacinObracuna;
	@JacksonXmlProperty(localName = "PrioritetNaloga")
	private String PrioritetNaloga;
	@JacksonXmlProperty(localName = "VremeUnosa")
	private String VremeUnosa;
	@JacksonXmlProperty(localName = "VremeIzvrsenja")
	private String VremeIzvrsenja;
	@JacksonXmlProperty(localName = "StatusNaloga")
	private String StatusNaloga;
	@JacksonXmlProperty(localName = "TipSloga")
	private String TipSloga;
	
	
	public String getRacunZaduzenja() {
		return RacunZaduzenja;
	}
	public void setRacunZaduzenja(String racunZaduzenja) {
		RacunZaduzenja = racunZaduzenja;
	}
	public String getNazivZaduzenja() {
		return NazivZaduzenja;
	}
	public void setNazivZaduzenja(String nazivZaduzenja) {
		NazivZaduzenja = nazivZaduzenja;
	}
	public String getMestoZaduzenja() {
		return MestoZaduzenja;
	}
	public void setMestoZaduzenja(String mestoZaduzenja) {
		MestoZaduzenja = mestoZaduzenja;
	}
	public String getIzvorInformacije() {
		return IzvorInformacije;
	}
	public void setIzvorInformacije(String izvorInformacije) {
		IzvorInformacije = izvorInformacije;
	}
	public String getModelPozivaZaduzenja() {
		return ModelPozivaZaduzenja;
	}
	public void setModelPozivaZaduzenja(String modelPozivaZaduzenja) {
		ModelPozivaZaduzenja = modelPozivaZaduzenja;
	}
	public String getPozivZaduzenja() {
		return PozivZaduzenja;
	}
	public void setPozivZaduzenja(String pozivZaduzenja) {
		PozivZaduzenja = pozivZaduzenja;
	}
	public String getSifraPlacanja() {
		return SifraPlacanja;
	}
	public void setSifraPlacanja(String sifraPlacanja) {
		SifraPlacanja = sifraPlacanja;
	}
	
	public String getIznos() {
		return Iznos;
	}
	public void setIznos(String iznos) {
		Iznos = iznos;
	}
	public String getRacunOdobrenja() {
		return RacunOdobrenja;
	}
	public void setRacunOdobrenja(String racunOdobrenja) {
		RacunOdobrenja = racunOdobrenja;
	}
	public String getNazivOdobrenja() {
		return NazivOdobrenja;
	}
	public void setNazivOdobrenja(String nazivOdobrenja) {
		NazivOdobrenja = nazivOdobrenja;
	}
	public String getMestoOdobrenja() {
		return MestoOdobrenja;
	}
	public void setMestoOdobrenja(String mestoOdobrenja) {
		MestoOdobrenja = mestoOdobrenja;
	}
	public String getPozivOdobrenja() {
		return PozivOdobrenja;
	}
	public void setPozivOdobrenja(String pozivOdobrenja) {
		PozivOdobrenja = pozivOdobrenja;
	}
	public String getSvrhaDoznake() {
		return SvrhaDoznake;
	}
	public void setSvrhaDoznake(String svrhaDoznake) {
		SvrhaDoznake = svrhaDoznake;
	}
	public String getPodatakZaReklamaciju() {
		return PodatakZaReklamaciju;
	}
	public void setPodatakZaReklamaciju(String podatakZaReklamaciju) {
		PodatakZaReklamaciju = podatakZaReklamaciju;
	}
	
	public String getDatumValute() {
		return DatumValute;
	}
	public void setDatumValute(String datumValute) {
		DatumValute = datumValute;
	}
	public String getNacinObracuna() {
		return NacinObracuna;
	}
	public void setNacinObracuna(String nacinObracuna) {
		NacinObracuna = nacinObracuna;
	}
	public String getPrioritetNaloga() {
		return PrioritetNaloga;
	}
	public void setPrioritetNaloga(String prioritetNaloga) {
		PrioritetNaloga = prioritetNaloga;
	}
	public String getVremeUnosa() {
		return VremeUnosa;
	}
	public void setVremeUnosa(String vremeUnosa) {
		VremeUnosa = vremeUnosa;
	}
	public String getVremeIzvrsenja() {
		return VremeIzvrsenja;
	}
	public void setVremeIzvrsenja(String vremeIzvrsenja) {
		VremeIzvrsenja = vremeIzvrsenja;
	}
	public String getStatusNaloga() {
		return StatusNaloga;
	}
	public void setStatusNaloga(String statusNaloga) {
		StatusNaloga = statusNaloga;
	}
	public String getTipSloga() {
		return TipSloga;
	}
	public void setTipSloga(String tipSloga) {
		TipSloga = tipSloga;
	}
	public String getModelPozivaOdobrenja() {
		return ModelPozivaOdobrenja;
	}
	public void setModelPozivaOdobrenja(String modelPozivaOdobrenja) {
		ModelPozivaOdobrenja = modelPozivaOdobrenja;
	}
	
	
	
}
