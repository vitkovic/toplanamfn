package toplana.web.rest.dto;

import java.math.BigDecimal;

public class RekapitulacijaPoPdvDelimicnaDTO {
	BigDecimal varijabilniDeo;
	BigDecimal fiksniDeo;
	BigDecimal popust;
	BigDecimal odrzavanje;
	
	public RekapitulacijaPoPdvDelimicnaDTO(BigDecimal varijabilniDeo, BigDecimal fiksniDeo, BigDecimal popust,
			BigDecimal odrzavanje) {
		super();
		this.varijabilniDeo = varijabilniDeo;
		this.fiksniDeo = fiksniDeo;
		this.popust = popust;
		this.odrzavanje = odrzavanje;
	}
	public RekapitulacijaPoPdvDelimicnaDTO() {
		super();
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
	public BigDecimal getOdrzavanje() {
		return odrzavanje;
	}
	public void setOdrzavanje(BigDecimal odrzavanje) {
		this.odrzavanje = odrzavanje;
	}
	
}
