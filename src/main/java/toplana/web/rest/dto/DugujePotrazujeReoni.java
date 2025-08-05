package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import toplana.domain.TipPotrosaca;

public class DugujePotrazujeReoni {
	
	private BigDecimal duguje;
	private BigDecimal potrazuje;
	private TipPotrosaca tipPotrosaca;
	private int tipPotrosacaInteger;
	private String ime;
	private String prezimeime = "";
	
	public String getIme() {
		return ime;
	}



	public void setIme(String ime) {
		this.ime = ime;
	}



	public String getPrezimeime() {
		return prezimeime;
	}



	public void setPrezimeime(String prezimeime) {
		this.prezimeime = prezimeime;
	}



	public DugujePotrazujeReoni() {
		super();
	}



	public DugujePotrazujeReoni(TipPotrosaca tipPotrosaca, BigDecimal duguje, BigDecimal potrazuje) {
		if(duguje != null)
			this.duguje = duguje.setScale(2, RoundingMode.HALF_UP);
		if(potrazuje != null)
			this.potrazuje = potrazuje.setScale(2, RoundingMode.HALF_UP);
		this.tipPotrosaca = tipPotrosaca;
	}
	
	public DugujePotrazujeReoni(int tipPotrosaca, BigDecimal duguje, BigDecimal potrazuje) {
		if(duguje != null)
			this.duguje = duguje.setScale(2, RoundingMode.HALF_UP);
		if(potrazuje != null)
			this.potrazuje = potrazuje.setScale(2, RoundingMode.HALF_UP);
		
		this.tipPotrosacaInteger = tipPotrosaca;
	}



	public int getTipPotrosacaInteger() {
		return tipPotrosacaInteger;
	}



	public void setTipPotrosacaInteger(int tipPotrosacaInteger) {
		this.tipPotrosacaInteger = tipPotrosacaInteger;
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



	public TipPotrosaca getTipPotrosaca() {
		return tipPotrosaca;
	}



	public void setTipPotrosaca(TipPotrosaca tipPotrosaca) {
		this.tipPotrosaca = tipPotrosaca;
	}
	
	
	

}
