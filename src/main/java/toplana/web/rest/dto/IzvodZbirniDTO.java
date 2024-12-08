package toplana.web.rest.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class IzvodZbirniDTO {
	
	@JacksonXmlProperty(localName = "RacunIzvoda")
	private String RacunIzvoda;
	@JacksonXmlProperty(localName = "Naziv")
	private String Naziv;
	@JacksonXmlProperty(localName = "Mesto")
	private String Mesto;
	@JacksonXmlProperty(localName = "BrNalogaDuguje")
	private int BrNalogaDuguje;
	@JacksonXmlProperty(localName = "BrNalogaPotrazuje")
	private int BrNalogaPotrazuje;
	@JacksonXmlProperty(localName = "KumulativnoDuguje")
	private String KumulativnoDuguje;
	@JacksonXmlProperty(localName = "KumulativnoPotrazuje")
	private String KumulativnoPotrazuje;
	@JacksonXmlProperty(localName = "PrethodniSaldo")
	private String PrethodniSaldo;
	@JacksonXmlProperty(localName = "IznosDuguje")
	private String IznosDuguje;
	@JacksonXmlProperty(localName = "IznosPotrazuje")
	private String IznosPotrazuje;
	@JacksonXmlProperty(localName = "Saldo")
	private String Saldo;
	@JacksonXmlProperty(localName = "DatumIzvoda")
	private String DatumIzvoda;
	@JacksonXmlProperty(localName = "BrojIzvoda")
	private int BrojIzvoda;
	@JacksonXmlProperty(localName = "RbrObrada")
	private int RbrObrada;
	@JacksonXmlProperty(localName = "TipSloga")
	private String TipSloga;
	
	
	
	public String getRacunIzvoda() {
		return RacunIzvoda;
	}
	public void setRacunIzvoda(String racunIzvoda) {
		RacunIzvoda = racunIzvoda;
	}
	public String getNaziv() {
		return Naziv;
	}
	public void setNaziv(String naziv) {
		Naziv = naziv;
	}
	public String getMesto() {
		return Mesto;
	}
	public void setMesto(String mesto) {
		Mesto = mesto;
	}
	public int getBrNalogaDuguje() {
		return BrNalogaDuguje;
	}
	public void setBrNalogaDuguje(int brNalogaDuguje) {
		BrNalogaDuguje = brNalogaDuguje;
	}
	public int getBrNalogaPotrazuje() {
		return BrNalogaPotrazuje;
	}
	public void setBrNalogaPotrazuje(int brNalogaPotrazuje) {
		BrNalogaPotrazuje = brNalogaPotrazuje;
	}
	
	
	
	public String getKumulativnoDuguje() {
		return KumulativnoDuguje;
	}
	public void setKumulativnoDuguje(String kumulativnoDuguje) {
		KumulativnoDuguje = kumulativnoDuguje;
	}
	public String getKumulativnoPotrazuje() {
		return KumulativnoPotrazuje;
	}
	public void setKumulativnoPotrazuje(String kumulativnoPotrazuje) {
		KumulativnoPotrazuje = kumulativnoPotrazuje;
	}
	public String getPrethodniSaldo() {
		return PrethodniSaldo;
	}
	public void setPrethodniSaldo(String prethodniSaldo) {
		PrethodniSaldo = prethodniSaldo;
	}
	public String getIznosDuguje() {
		return IznosDuguje;
	}
	public void setIznosDuguje(String iznosDuguje) {
		IznosDuguje = iznosDuguje;
	}
	public String getIznosPotrazuje() {
		return IznosPotrazuje;
	}
	public void setIznosPotrazuje(String iznosPotrazuje) {
		IznosPotrazuje = iznosPotrazuje;
	}
	public String getSaldo() {
		return Saldo;
	}
	public void setSaldo(String saldo) {
		Saldo = saldo;
	}
	public String getDatumIzvoda() {
		return DatumIzvoda;
	}
	public void setDatumIzvoda(String datumIzvoda) {
		DatumIzvoda = datumIzvoda;
	}
	public int getBrojIzvoda() {
		return BrojIzvoda;
	}
	public void setBrojIzvoda(int brojIzvoda) {
		BrojIzvoda = brojIzvoda;
	}
	public int getRbrObrada() {
		return RbrObrada;
	}
	public void setRbrObrada(int rbrObrada) {
		RbrObrada = rbrObrada;
	}
	public String getTipSloga() {
		return TipSloga;
	}
	public void setTipSloga(String tipSloga) {
		TipSloga = tipSloga;
	}
	
	

}
