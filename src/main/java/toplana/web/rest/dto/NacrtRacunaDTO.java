package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import toplana.domain.Cene;
import toplana.domain.NacrtRacuna;
import toplana.domain.StanjaPodstaniceZaRacun;

public class NacrtRacunaDTO {
	private Long id;
	private LocalDate datumRacuna;
	private String period;	
	private LocalDate valutaPlacanja;
	private BigDecimal cenaKwh;
	private BigDecimal cenaFix;
	private BigDecimal cenaFixIskljucen;
	private BigDecimal cenaOdrzavanje;
	private BigDecimal cenaOStalo;
	private BigDecimal popust;
	private BigDecimal pdv1;
	private BigDecimal pdv2;
	private boolean proknjizen;
	private List<StanjaPodstaniceZaRacun> stanjaPodstaniceZaRacune = null;
	private BigDecimal ukupnoStanjeStaro;
	private BigDecimal ukupnoStanjeNovo;
	private BigDecimal potrosnja;
	
	
	public NacrtRacunaDTO(LocalDate datumRacuna, String period, LocalDate valutaPlacanja, BigDecimal cenaKwh,
			BigDecimal cenaFix, BigDecimal cenaFixIskljucen, BigDecimal cenaOdrzavanje, BigDecimal cenaOStalo, BigDecimal popust, BigDecimal pdv1,
			BigDecimal pdv2, boolean proknjizen, List<StanjaPodstaniceZaRacun> stanjaPodstaniceZaRacune) {
		this.datumRacuna = datumRacuna;
		this.period = period;
		this.valutaPlacanja = valutaPlacanja;
		this.cenaKwh = cenaKwh;
		this.cenaFix = cenaFix;
		this.cenaFixIskljucen = cenaFixIskljucen;
		this.cenaOdrzavanje = cenaOdrzavanje;
		this.cenaOStalo = cenaOStalo;
		this.popust = popust;
		this.pdv1 = pdv1;
		this.pdv2 = pdv2;
		this.proknjizen = proknjizen;
		this.stanjaPodstaniceZaRacune = stanjaPodstaniceZaRacune;
		this.setUkupnaPotrosnja();
	}
	
	public NacrtRacunaDTO(NacrtRacuna nr) {
		this.id = nr.getId();
		this.datumRacuna = nr.getDatumRacuna();
		this.period = nr.getPeriod();
		this.valutaPlacanja = nr.getValutaPlacanja();
		this.cenaKwh = nr.getCenaKwh();
		this.cenaFix = nr.getCenaFix();
		this.cenaFixIskljucen = nr.getCenaFixIskljucen();
		this.cenaOdrzavanje = nr.getCenaOdrzavanje();
		this.cenaOStalo = nr.getCenaOStalo();
		this.popust = nr.getPopust();
		this.pdv1 = nr.getPdv1();
		this.pdv2 = nr.getPdv2();
		this.proknjizen = nr.getProknjizen();
		this.stanjaPodstaniceZaRacune = new ArrayList(nr.getStanjaPodstaniceZaRacune());
		this.setUkupnaPotrosnja();
	}
	
	
	
	public NacrtRacunaDTO() {
		super();
	}
	
	public void setCene(Cene cene) {
		this.cenaKwh = cene.getKwh();
		this.cenaFix = cene.getFix();
		this.cenaOdrzavanje = cene.getOdrzavanje();
		this.cenaOStalo = cene.getOstalo();
		this.pdv1 = cene.getPdv1();
		this.pdv2 = cene.getPdv2();
		this.popust = cene.getPopust1();
		this.cenaFixIskljucen = cene.getFixIskljuceno();
	}
	
	public BigDecimal getCenaFixIskljucen() {
		return cenaFixIskljucen;
	}

	public void setCenaFixIskljucen(BigDecimal cenaFixIskljucen) {
		this.cenaFixIskljucen = cenaFixIskljucen;
	}

	public void setUkupnaPotrosnja() {
		if(this.stanjaPodstaniceZaRacune != null) {
			this.ukupnoStanjeStaro = new BigDecimal("0.");
			this.ukupnoStanjeNovo = new BigDecimal("0.");
			this.potrosnja = new BigDecimal("0.");
			for(StanjaPodstaniceZaRacun s : this.stanjaPodstaniceZaRacune) {
				this.ukupnoStanjeStaro = this.ukupnoStanjeStaro.add(s.getStaroStanje().getStanje());
				this.ukupnoStanjeNovo = this.ukupnoStanjeNovo.add(s.getNovoStanje().getStanje());				
			}
			this.potrosnja = this.getUkupnoStanjeNovo().subtract(this.getUkupnoStanjeStaro());
		}
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDatumRacuna() {
		return datumRacuna;
	}
	public void setDatumRacuna(LocalDate datumRacuna) {
		this.datumRacuna = datumRacuna;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public LocalDate getValutaPlacanja() {
		return valutaPlacanja;
	}
	public void setValutaPlacanja(LocalDate valutaPlacanja) {
		this.valutaPlacanja = valutaPlacanja;
	}
	public BigDecimal getCenaKwh() {
		return cenaKwh;
	}
	public void setCenaKwh(BigDecimal cenaKwh) {
		this.cenaKwh = cenaKwh;
	}
	public BigDecimal getCenaFix() {
		return cenaFix;
	}
	public void setCenaFix(BigDecimal cenaFix) {
		this.cenaFix = cenaFix;
	}
	public BigDecimal getCenaOdrzavanje() {
		return cenaOdrzavanje;
	}
	public void setCenaOdrzavanje(BigDecimal cenaOdrzavanje) {
		this.cenaOdrzavanje = cenaOdrzavanje;
	}
	public BigDecimal getCenaOStalo() {
		return cenaOStalo;
	}
	public void setCenaOStalo(BigDecimal cenaOStalo) {
		this.cenaOStalo = cenaOStalo;
	}
	public BigDecimal getPopust() {
		return popust;
	}
	public void setPopust(BigDecimal popust) {
		this.popust = popust;
	}
	public BigDecimal getPdv1() {
		return pdv1;
	}
	public void setPdv1(BigDecimal pdv1) {
		this.pdv1 = pdv1;
	}
	public BigDecimal getPdv2() {
		return pdv2;
	}
	public void setPdv2(BigDecimal pdv2) {
		this.pdv2 = pdv2;
	}
	
	public List<StanjaPodstaniceZaRacun> getStanjaPodstaniceZaRacune() {
		return stanjaPodstaniceZaRacune;
	}

	public void setStanjaPodstaniceZaRacune(List<StanjaPodstaniceZaRacun> stanjaPodstaniceZaRacune) {
		this.stanjaPodstaniceZaRacune = stanjaPodstaniceZaRacune;
	}

	public boolean isProknjizen() {
		return proknjizen;
	}
	public void setProknjizen(boolean proknjizen) {
		this.proknjizen = proknjizen;
	}

	public BigDecimal getUkupnoStanjeStaro() {
		return ukupnoStanjeStaro;
	}

	public void setUkupnoStanjeStaro(BigDecimal ukupnoStanjeStaro) {
		this.ukupnoStanjeStaro = ukupnoStanjeStaro;
	}

	public BigDecimal getUkupnoStanjeNovo() {
		return ukupnoStanjeNovo;
	}

	public void setUkupnoStanjeNovo(BigDecimal ukupnoStanjeNovo) {
		this.ukupnoStanjeNovo = ukupnoStanjeNovo;
	}

	public BigDecimal getPotrosnja() {
		return potrosnja;
	}

	public void setPotrosnja(BigDecimal potrosnja) {
		this.potrosnja = potrosnja;
	}
	
	
	

}
