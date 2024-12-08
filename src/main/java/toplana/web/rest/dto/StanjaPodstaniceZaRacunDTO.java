package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import toplana.domain.Podstanica;

public class StanjaPodstaniceZaRacunDTO {

	private BigDecimal staroStanje;
	private LocalDate datumStaroStanje;
	private BigDecimal novoStanje;
	private LocalDate datumNovoStanje;
	private BigDecimal ukupnaPovrsina;
	private BigDecimal utrosakPoM2;
	private Podstanica podstanica;
	
	
	 
	public StanjaPodstaniceZaRacunDTO() {}
	
	
	public BigDecimal getStaroStanje() {
		return staroStanje;
	}
	public void setStaroStanje(BigDecimal staroStanje) {
		this.staroStanje = staroStanje;
	}
	public LocalDate getDatumStaroStanje() {
		return datumStaroStanje;
	}
	public void setDatumStaroStanje(LocalDate datumStaroStanje) {
		this.datumStaroStanje = datumStaroStanje;
	}
	public BigDecimal getNovoStanje() {
		return novoStanje;
	}
	public void setNovoStanje(BigDecimal novoStanje) {
		this.novoStanje = novoStanje;
	}
	public LocalDate getDatumNovoStanje() {
		return datumNovoStanje;
	}
	public void setDatumNovoStanje(LocalDate datumNovoStanje) {
		this.datumNovoStanje = datumNovoStanje;
	}
	public BigDecimal getUkupnaPovrsina() {
		return ukupnaPovrsina;
	}
	public void setUkupnaPovrsina(BigDecimal ukupnaPovrsina) {
		this.ukupnaPovrsina = ukupnaPovrsina;
	}
	public BigDecimal getUtrosakPoM2() {
		return utrosakPoM2;
	}
	public void setUtrosakPoM2(BigDecimal utrosakPoM2) {
		this.utrosakPoM2 = utrosakPoM2;
	}
	public Podstanica getPodstanica() {
		return podstanica;
	}
	public void setPodstanica(Podstanica podstanica) {
		this.podstanica = podstanica;
	}
	 
	 
	 
}
