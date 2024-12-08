package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import toplana.domain.TipPotrosaca;

public class TransakcijeReoniDTO {
	List<TransakcijaZaStanDTO> transakcije ;
    List<DugujePotrazujeReoni> reoniDugovi;
    
    
    
    
	public TransakcijeReoniDTO(List<TransakcijaZaStanDTO> transakcije, List<DugujePotrazujeReoni> reoniDugovi) {
		super();
		this.transakcije = transakcije;
		DugujePotrazujeReoni lokali = new DugujePotrazujeReoni( new TipPotrosaca(0L,5), new BigDecimal("0."), new BigDecimal("0."));
		DugujePotrazujeReoni ostali = new DugujePotrazujeReoni( new TipPotrosaca(0L,0), new BigDecimal("0."), new BigDecimal("0."));
		DugujePotrazujeReoni ukupno = new DugujePotrazujeReoni( new TipPotrosaca(1000L,1000), new BigDecimal("0."), new BigDecimal("0."));
		for(DugujePotrazujeReoni dpr : reoniDugovi) {
			if(dpr.getTipPotrosacaInteger() == 5) {
				if(dpr.getDuguje() != null) {
					lokali.setDuguje(dpr.getDuguje());
					ukupno.setDuguje(ukupno.getDuguje().add(dpr.getDuguje()).setScale(2, RoundingMode.HALF_UP));
				}
				if(dpr.getPotrazuje() != null) {
					lokali.setPotrazuje(dpr.getPotrazuje());
					ukupno.setPotrazuje(ukupno.getPotrazuje().add(dpr.getPotrazuje()).setScale(2, RoundingMode.HALF_UP));
				}
			}else {
				if(dpr.getDuguje() != null) {
					ostali.setDuguje(ostali.getDuguje().add(dpr.getDuguje()).setScale(2, RoundingMode.HALF_UP));
					ukupno.setDuguje(ukupno.getDuguje().add(dpr.getDuguje()).setScale(2, RoundingMode.HALF_UP));
				}
				if(dpr.getPotrazuje() != null) {
					ostali.setPotrazuje(ostali.getPotrazuje().add(dpr.getPotrazuje()).setScale(2, RoundingMode.HALF_UP));
					ukupno.setPotrazuje(ukupno.getPotrazuje().add(dpr.getPotrazuje()).setScale(2, RoundingMode.HALF_UP));
				}
			}
		}
		this.reoniDugovi = new ArrayList<DugujePotrazujeReoni>();				
		this.reoniDugovi.add(lokali);
		this.reoniDugovi.add(ostali);
		this.reoniDugovi.add(ukupno);
		
	}
	public TransakcijeReoniDTO() {
		super();
	}
	public List<TransakcijaZaStanDTO> getTransakcije() {
		return transakcije;
	}
	public void setTransakcije(List<TransakcijaZaStanDTO> transakcije) {
		this.transakcije = transakcije;
	}
	public List<DugujePotrazujeReoni> getReoniDugovi() {
		return reoniDugovi;
	}
	public void setReoniDugovi(List<DugujePotrazujeReoni> reoniDugovi) {
		this.reoniDugovi = reoniDugovi;
	}
    
    
}
