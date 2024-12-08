package toplana.web.rest.dto;

import java.math.BigDecimal;

public class DugujePotrazujeDTO {
	private BigDecimal saldoDuguje;
	private BigDecimal saldoPotrazuje;
	private BigDecimal stanjeDuguje;
	private BigDecimal stanjePotrazuje;
	public DugujePotrazujeDTO() {
		super();
	}
	
	
	public DugujePotrazujeDTO(BigDecimal saldoDuguje, BigDecimal saldoPotrazuje) {
		super();
		this.saldoDuguje = saldoDuguje;
		this.saldoPotrazuje = saldoPotrazuje;
		this.stanjeDuguje = new BigDecimal("0.");
		this.stanjePotrazuje = new BigDecimal("0.");
		if(this.saldoDuguje.compareTo(this.saldoPotrazuje) == 1) {
			this.stanjeDuguje = this.saldoDuguje.subtract(this.saldoPotrazuje);
		}else if(this.saldoPotrazuje.compareTo(this.saldoDuguje) == 1) {
			this.stanjePotrazuje = this.saldoPotrazuje.subtract(this.saldoDuguje);
		}
	}


	public BigDecimal getSaldoDuguje() {
		return saldoDuguje;
	}
	public void setSaldoDuguje(BigDecimal saldoDuguje) {
		this.saldoDuguje = saldoDuguje;
	}
	public BigDecimal getSaldoPotrazuje() {
		return saldoPotrazuje;
	}
	public void setSaldoPotrazuje(BigDecimal saldoPotrazuje) {
		this.saldoPotrazuje = saldoPotrazuje;
	}
	public BigDecimal getStanjeDuguje() {
		return stanjeDuguje;
	}
	public void setStanjeDuguje(BigDecimal stanjeDuguje) {
		this.stanjeDuguje = stanjeDuguje;
	}
	public BigDecimal getStanjePotrazuje() {
		return stanjePotrazuje;
	}
	public void setStanjePotrazuje(BigDecimal stanjePotrazuje) {
		this.stanjePotrazuje = stanjePotrazuje;
	}
	
	

}
