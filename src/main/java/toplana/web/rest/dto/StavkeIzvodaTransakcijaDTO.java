package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import toplana.domain.Izvod;
import toplana.domain.SifraPromene;
import toplana.domain.StavkeIzvoda;
import toplana.domain.Transakcija;
import toplana.domain.TransakcijaStara;

/*****
 * Klasi stavke dodata i sifra promene, da bi moglo da se bira kod razduzenja
 * @author Dragan
 *
 */
public class StavkeIzvodaTransakcijaDTO {
	
	private StavkeIzvoda stavkeIzvoda;
	private SifraPromene sifraPromene;
	
	public StavkeIzvodaTransakcijaDTO(StavkeIzvoda stavkeIzvoda, SifraPromene sifraPromene) {
		super();
		this.stavkeIzvoda = stavkeIzvoda;
		this.sifraPromene = sifraPromene;
	}
	
	public StavkeIzvodaTransakcijaDTO() {
		super();
	}
	
	public StavkeIzvoda getStavkeIzvoda() {
		return stavkeIzvoda;
	}
	public void setStavkeIzvoda(StavkeIzvoda stavkeIzvoda) {
		this.stavkeIzvoda = stavkeIzvoda;
	}
	public SifraPromene getSifraPromene() {
		return sifraPromene;
	}
	public void setSifraPromene(SifraPromene sifraPromene) {
		this.sifraPromene = sifraPromene;
	}
	
	
    
    
    
}
