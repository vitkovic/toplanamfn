package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import toplana.domain.OstaliRacuni;
import toplana.domain.Stan;

public class TransakcijeZaStanZbirnoDTO {
	
	private Stan stan;
	private OstaliRacuni ostaliRacuni;
	private List<TransakcijaZaStanDTO> transakcije;
	private BigDecimal dugujeUkupno;
	private BigDecimal potrazujeUkupno;
	private BigDecimal saldoUkupno;
	
	public TransakcijeZaStanZbirnoDTO() {
		super();
	}

	public Stan getStan() {
		return stan;
	}

	public void setStan(Stan stan) {
		this.stan = stan;
	}

	public List<TransakcijaZaStanDTO> getTransakcije() {
		return transakcije;
	}

	public void setTransakcije(List<TransakcijaZaStanDTO> transakcije) {
		this.transakcije = transakcije;
	}

	public BigDecimal getDugujeUkupno() {
		return dugujeUkupno;
	}

	public void setDugujeUkupno(BigDecimal dugujeUkupno) {
		this.dugujeUkupno = dugujeUkupno;
	}

	public BigDecimal getPotrazujeUkupno() {
		return potrazujeUkupno;
	}

	public void setPotrazujeUkupno(BigDecimal potrazujeUkupno) {
		this.potrazujeUkupno = potrazujeUkupno;
	}

	public BigDecimal getSaldoUkupno() {
		return saldoUkupno;
	}

	public void setSaldoUkupno(BigDecimal saldoUkupno) {
		this.saldoUkupno = saldoUkupno;
	}

	public OstaliRacuni getOstaliRacuni() {
		return ostaliRacuni;
	}

	public void setOstaliRacuni(OstaliRacuni ostaliRacuni) {
		this.ostaliRacuni = ostaliRacuni;
	}
	
	

}
