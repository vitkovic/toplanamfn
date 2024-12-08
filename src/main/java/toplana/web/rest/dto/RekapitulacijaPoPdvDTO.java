package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class RekapitulacijaPoPdvDTO {
	BigDecimal varijabilniDeo;
	BigDecimal fiksniDeo;
	BigDecimal popust;
	BigDecimal ukupno;
	BigDecimal pdv1; // 20%
	BigDecimal pdv2; // 10%
	BigDecimal odrzavanje;
	BigDecimal ukupnoZaduzenje;
	LocalDate datum;
	
	Long nacrtRacunaId;
	
	public RekapitulacijaPoPdvDTO() {
		super();
	}

	public RekapitulacijaPoPdvDTO(BigDecimal varijabilniDeo, BigDecimal fiksniDeo, BigDecimal popust,
			BigDecimal odrzavanje, LocalDate datum, BigDecimal pdv1, BigDecimal pdv2) {
		super();
		this.varijabilniDeo = varijabilniDeo;
		this.fiksniDeo = fiksniDeo;
		this.popust = popust;
		this.odrzavanje = odrzavanje;
		this.datum = datum;
		
		this.ukupno = this.varijabilniDeo.add(this.fiksniDeo).subtract(this.popust).setScale(2, RoundingMode.HALF_UP);
		this.pdv2 = this.ukupno.multiply(pdv2).divide(new BigDecimal("100.")).setScale(2, RoundingMode.HALF_UP);
		this.pdv1 = this.odrzavanje.multiply(pdv1).divide(new BigDecimal("100.")).setScale(2, RoundingMode.HALF_UP);
		this.ukupnoZaduzenje = this.ukupno.add(this.pdv1).add(this.odrzavanje).add(this.pdv2);
	}
	
	public RekapitulacijaPoPdvDTO(RekapitulacijaPoPdvDelimicnaDTO in, LocalDate datum, Long nacrtRacunaId, 
			BigDecimal pdv1, BigDecimal pdv2) {
		this.varijabilniDeo = in.getVarijabilniDeo();
		this.fiksniDeo = in.getFiksniDeo();
		this.popust = in.getPopust();
		this.odrzavanje = in.getOdrzavanje();
		this.datum = datum;
		
		this.ukupno = this.varijabilniDeo.add(this.fiksniDeo).subtract(this.popust).setScale(2, RoundingMode.HALF_UP);
		this.pdv2 = this.ukupno.multiply(pdv2).divide(new BigDecimal("100.")).setScale(2, RoundingMode.HALF_UP);
		this.pdv1 = this.odrzavanje.multiply(pdv1).divide(new BigDecimal("100.")).setScale(2, RoundingMode.HALF_UP);
		this.ukupnoZaduzenje = this.ukupno.add(this.pdv1).add(this.odrzavanje).add(this.pdv2);
		this.nacrtRacunaId = nacrtRacunaId;
	}

	public BigDecimal getVarijabilniDeo() {
		return varijabilniDeo;
	}

	public void setVarijabilniDeo(BigDecimal varijabilniDeo) {
		this.varijabilniDeo = varijabilniDeo;
	}

	public BigDecimal getFiksniDeo() {
		return fiksniDeo;
	}

	public void setFiksniDeo(BigDecimal fiksniDeo) {
		this.fiksniDeo = fiksniDeo;
	}

	public BigDecimal getPopust() {
		return popust;
	}

	public void setPopust(BigDecimal popust) {
		this.popust = popust;
	}

	public BigDecimal getUkupno() {
		return ukupno;
	}

	public void setUkupno(BigDecimal ukupno) {
		this.ukupno = ukupno;
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

	public BigDecimal getOdrzavanje() {
		return odrzavanje;
	}

	public void setOdrzavanje(BigDecimal odrzavanje) {
		this.odrzavanje = odrzavanje;
	}

	public BigDecimal getUkupnoZaduzenje() {
		return ukupnoZaduzenje;
	}

	public void setUkupnoZaduzenje(BigDecimal ukupnoZaduzenje) {
		this.ukupnoZaduzenje = ukupnoZaduzenje;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public Long getNacrtRacunaId() {
		return nacrtRacunaId;
	}

	public void setNacrtRacunaId(Long nacrtRacunaId) {
		this.nacrtRacunaId = nacrtRacunaId;
	}
	
	
	
	

}
