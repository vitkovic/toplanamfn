package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import toplana.web.rest.dto.DugujePotrazujeDTO;
import toplana.web.rest.dto.DugujePotrazujeReoni;
import toplana.web.rest.dto.RekapitulacijaPoPdvDelimicnaDTO;
import toplana.web.rest.dto.TransakcijaStanUkupnoDTO;
import toplana.web.rest.dto.TransakcijaZaStanDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * A Racun.
 */
@Entity
@Table(name = "racun")

@SqlResultSetMappings({
	@SqlResultSetMapping(name="rpp", 
			classes={
			@ConstructorResult(
			     targetClass=RekapitulacijaPoPdvDelimicnaDTO.class,
			       columns={
			          @ColumnResult(name="varijabilniDeo", type=BigDecimal.class),
			          @ColumnResult(name="fiksniDeo", type=BigDecimal.class),
			          @ColumnResult(name="popust", type=BigDecimal.class),
			          @ColumnResult(name="odrzavanje", type=BigDecimal.class),
			          }
			   )
			}
	)
})

@NamedNativeQueries({	
	@NamedNativeQuery(name="Racun.rekapitulacijaPoPdv", 
			query="select sum(utrosak_u_kwh* cena_kwh) as varijabilniDeo, "
					+ "sum (s.povrsina*cena_fix) as fiksniDeo, "
					+ "sum(popust*(utrosak_u_kwh* cena_kwh + s.povrsina*cena_fix)/100.) as popust, "
					+ "sum(s.povrsina*cena_odrzavanje) as odrzavanje "
					+ "from racun r "
					+ "left join stan s on r.stan_id = s.id "
					+ "left join tip_potrosaca t on t.id = s.tip_potrosaca_id "
					+ "where nacrt_racuna_id = :nacrtRacunaId "
					+ "and t.tip != 5", 
			resultSetMapping="rpp")				
})

public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "datum_racuna")
    private LocalDate datumRacuna;
    
    @Column(name = "valuta_placanja")
    private LocalDate valutaPlacanja;

    //Datum kada je radjen saldo za dugovanja (obicno poslednji dan prethodnog meseca)
    @Column(name = "datum_saldiranja")
    private LocalDate datumSaldiranja;

	@Column(name = "period")
    private String period;

    @Column(name = "utrosak_varijabilni", precision = 21, scale = 2)
    private BigDecimal utrosakVarijabilni;

    @Column(name = "utrosak_fiksni", precision = 21, scale = 2)
    private BigDecimal utrosakFiksni;

    @Column(name = "utrosak_odrzavanje", precision = 21, scale = 2)
    private BigDecimal utrosakOdrzavanje;
    
    //potrosena kolicina u kilovatima, za stan (dobija se kao potrosnja po m2 x kvadratura stana)
    @Column(name = "utrosak_u_kwh", precision = 21, scale = 3)
    private BigDecimal utrosakUKwh;
    
    @Column(name = "cena_kwh", precision = 21, scale = 2, nullable = false)
    private BigDecimal cenaKwh;

    @Column(name = "cena_fix", precision = 21, scale = 2, nullable = false)
    private BigDecimal cenaFix;
    
    @Column(name = "cena_fix_iskljucen", precision = 21, scale = 2, nullable = false)
    private BigDecimal cenaFixIskljucen;

    @Column(name = "cena_odrzavanje", precision = 21, scale = 2, nullable = false)
    private BigDecimal cenaOdrzavanje;

    @Column(name = "cena_o_stalo", precision = 21, scale = 2)
    private BigDecimal cenaOStalo;
    
    @Column(name = "pdv_1", precision = 21, scale = 2, nullable = false)
    private BigDecimal pdv1;

    @Column(name = "pdv_2", precision = 21, scale = 2, nullable = false)
    private BigDecimal pdv2;
    
    @Column(name = "popust", precision = 21, scale = 2)
    private BigDecimal popust;
    
    //Ukupno zaduzenje za prethodni period (saldo u trenutku izdavanja racuna) ovo moze da se promeni ako se kasnije doda neki izvod koji nije bio upisan
    @Column(name = "ukupno_zaduzenje", precision = 21, scale = 2)
    private BigDecimal ukupnoZaduzenje;
   
    @Column(name = "proknjizen")
    private Boolean proknjizen;

    // pokazuje da li je racun manuelno azuriran
    @Column(name = "azuriran")
    private Boolean azuriran;
    
    
    
    @ManyToOne
    @JsonIgnoreProperties(value = "racuniAzurirani", allowSetters = true)
    private User azurirao;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "racuniKreirani", allowSetters = true)
    private User kreirao;
   

	@ManyToOne
    @JsonIgnoreProperties(value = "racuns", allowSetters = true)
    private Stan stan;

    @ManyToOne
    @JsonIgnoreProperties(value = "racuns", allowSetters = true)
    private NacrtRacuna nacrtRacuna;
    
    @OneToOne
    @JoinColumn(unique = true)
    private Transakcija transakcija;
    
    @OneToOne
    @JoinColumn(unique = true)
    private TransakcijaStara transakcijaStara;
    
    public Racun() {}
    
    public Racun(Stan stan, NacrtRacuna nacrtRacuna, User user, BigDecimal saldo, LocalDate poslednjiDanPrethodnogMeseca, Podstanica pn) {
    	this.datumRacuna = nacrtRacuna.getDatumRacuna();
    	this.period = nacrtRacuna.getPeriod();
    	this.stan = stan;
    	this.azuriran = false;
    	this.valutaPlacanja = nacrtRacuna.getValutaPlacanja();    	
    	this.datumSaldiranja = poslednjiDanPrethodnogMeseca;
    	
    	Podstanica p = stan.getPodstanica();
    	StanjaPodstaniceZaRacun spr = null;
    	for(StanjaPodstaniceZaRacun spz : nacrtRacuna.getStanjaPodstaniceZaRacune()) {
    		if(spz.getPodstanica().getId().longValue() == stan.getPodstanica().getId().longValue()) {
    			spr = spz;
    			break;
    		}
    	}    	
    	this.cenaFix = nacrtRacuna.getCenaFix();
    	this.cenaFixIskljucen = nacrtRacuna.getCenaFixIskljucen();
    	this.cenaKwh = nacrtRacuna.getCenaKwh();
    	this.cenaOdrzavanje = nacrtRacuna.getCenaOdrzavanje();
    	this.cenaOStalo = nacrtRacuna.getCenaOStalo();
    	this.kreirao = user;
    	this.nacrtRacuna = nacrtRacuna;
    	this.pdv1 = nacrtRacuna.getPdv1();
    	this.pdv2 = nacrtRacuna.getPdv2();    	
    	this.proknjizen = false;
    	this.ukupnoZaduzenje = saldo;    
    	
    	
    	if (p.getId() > 1105) {
    		System.out.println(stan.getId() + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
    	
    		
    	}
    	
    	if(stan.isUkljucen()) {
    		
    		if (p.getId() > 1105) { // 1105 treba definisati kao varijablu u parametrima i obrisati ove posebne id stanova
    			
    			
    			System.out.println(p.getId() + "********************************************************************************" + spr.getNovoStanje().getStanje());
    			System.out.println(p.getId() + "********************************************************************************" + spr.getStaroStanje().getStanje());
    			System.out.println(p.getId() + "********************************************************************************" + saldo);
    			System.out.println(p.getId() + "********************************************************************************" + saldo);
    			System.out.println(p.getId() + "********************************************************************************" + saldo);
    	    	
    			
    			
    			
    			
    			BigDecimal zajednickostanjepodstanice = (spr.getNovoStanje().getStanje().subtract(spr.getStaroStanje().getStanje())).multiply(BigDecimal.valueOf(1000.00)).setScale(2, RoundingMode.HALF_UP);; 
    			// Razlika prathodnog i novog stanja (I115)
    			
    			
    			 System.out.println("ZSP: " + zajednickostanjepodstanice);
    			
    			BigDecimal ukupnapotrosnja =BigDecimal.valueOf(pn.getUkupnapotrosnjapostanu()).setScale(2, RoundingMode.HALF_UP);
    			// Suma potrosnji stanova - J115 (I111)
    			
    			 System.out.println("UP: " + ukupnapotrosnja);
    			
    			BigDecimal ukupnapovrsina = BigDecimal.valueOf(pn.getUkupnapovrsina()).setScale(2, RoundingMode.HALF_UP);
    			// Povrsina svih stanova
    			
    			
    			 System.out.println("Ukupna Povrsina:" + ukupnapovrsina);
    			 
    			 System.out.println("Stan Povrsina:" + stan.getPovrsina());
    			
    			BigDecimal udeostananum = stan.getPovrsina().divide(ukupnapovrsina,5, RoundingMode.HALF_UP).setScale(5);
    			
    			BigDecimal udeostana = udeostananum.multiply(BigDecimal.valueOf(100.00)).setScale(3, RoundingMode.HALF_UP);
    			// Procentualni udeo stana
    			
    			
    			 System.out.println("Udeo Stana: " + udeostana);
    			    			
    			BigDecimal udeozajednickepotrosnje = udeostana.multiply(zajednickostanjepodstanice.subtract(ukupnapotrosnja)).setScale(2, RoundingMode.HALF_UP);
    			// Za stan deo koji se odnosi na udeo zajednicke potrosnje - J5
    			
    			
    			 System.out.println(udeozajednickepotrosnje);
    			
    			
    			 for(int i=0;i<stan.getZadnjaStanja().size();i++){
    				 System.out.println(stan.getZadnjaStanja());
    		        } 
    			 
    			 System.out.println(p.getId() + "********************************************************************************" + stan.getId());
    			BigDecimal sopstvenapotrosnja =BigDecimal.valueOf(stan.getZadnjaStanja().get(1) - stan.getZadnjaStanja().get(0)).setScale(2, RoundingMode.HALF_UP);
    			// Za stan sopstvena potrosnja ocitavanja - I5
    			
    			
    			 System.out.println(sopstvenapotrosnja );
    			
    			this.utrosakUKwh = (udeozajednickepotrosnje.add(sopstvenapotrosnja)).setScale(2, RoundingMode.HALF_UP); 
    			// Ukupna potrosnja po stanu u kW
    			this.utrosakFiksni = stan.getPovrsina().multiply(this.cenaFix).setScale(2, RoundingMode.HALF_UP);
    			
    		} else {
    		
    			this.utrosakUKwh = spr.getUtrosakPoM2().multiply(stan.getPovrsina()).setScale(2, RoundingMode.HALF_UP);
    			this.utrosakFiksni = stan.getPovrsina().multiply(this.cenaFix).setScale(2, RoundingMode.HALF_UP);
    		}
    	}else {
    		
    		this.cenaFix = this.cenaFixIskljucen;
    		
    		if (p.getId() > 1105) {
    			
    			System.out.println("ISKLJUCEN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    			
    			this.utrosakUKwh = new BigDecimal("0.");
    			this.utrosakFiksni = stan.getPovrsina().multiply(this.cenaFixIskljucen).setScale(2, RoundingMode.HALF_UP);
    			
    			
    			System.out.println("Varijabila:" + utrosakUKwh);
    			 
    			 System.out.println("Fiksni:" + this.utrosakFiksni);
    			
    			
    		} else {
    		
    			this.utrosakUKwh = new BigDecimal("0.");
    			this.utrosakFiksni = stan.getPovrsina().multiply(this.cenaFixIskljucen).setScale(2, RoundingMode.HALF_UP);
    		}
    	}
    	
    	if (p.getId() > 1105) {
    		
    		this.utrosakVarijabilni = this.utrosakUKwh.multiply(this.cenaKwh).setScale(2, RoundingMode.HALF_UP);
    		this.utrosakOdrzavanje  = new BigDecimal("0.");
		
    	} else {
    		
			this.utrosakVarijabilni = this.utrosakUKwh.multiply(this.cenaKwh).setScale(2, RoundingMode.HALF_UP);
    	
			this.utrosakOdrzavanje = stan.getPovrsina().multiply(this.cenaOdrzavanje).setScale(2, RoundingMode.HALF_UP);
    	
		}
    	//ako je saldo negativan ili jednak 0
    	if((this.ukupnoZaduzenje.compareTo(new BigDecimal("0.")) <= 0 && this.stan.getTipPotrosaca().getTip() != 5) || 
    			(this.ukupnoZaduzenje.doubleValue() <= 0.05 && this.stan.getTipPotrosaca().getTip() != 5)) {
    		
    		this.popust = nacrtRacuna.getPopust();
    		
    		 System.out.println(p.getId() + " Popust ********************************************************************************" + this.popust);
    		
    		if (p.getId() > 1105) {
    			
    			this.utrosakVarijabilni = this.utrosakVarijabilni.multiply(new BigDecimal("100.").subtract(this.popust).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
    			this.utrosakFiksni = this.utrosakFiksni.multiply(new BigDecimal("100.").subtract(this.popust).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
    	
    		
    		} else {
    			this.utrosakVarijabilni = this.utrosakVarijabilni.multiply(new BigDecimal("100.").subtract(this.popust).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
    			this.utrosakFiksni = this.utrosakFiksni.multiply(new BigDecimal("100.").subtract(this.popust).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
    	
    		}
    	
    	
    	}else {
    		this.popust = BigDecimal.ZERO;
    	}
    	
    	if (p.getId() > 1105) {
    		
    		System.out.println(stan.getId() + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
    		
    		this.utrosakVarijabilni = this.utrosakVarijabilni.multiply(new BigDecimal("100.").add(this.pdv2).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
			this.utrosakFiksni = this.utrosakFiksni.multiply(new BigDecimal("100.").add(this.pdv2).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
			this.utrosakOdrzavanje = new BigDecimal("0.");
			
			
			System.out.println(stan.getId() + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+this.utrosakFiksni);
			
			
			
			
			
	
		} else {
    	
			this.utrosakVarijabilni = this.utrosakVarijabilni.multiply(new BigDecimal("100.").add(this.pdv2).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
			this.utrosakFiksni = this.utrosakFiksni.multiply(new BigDecimal("100.").add(this.pdv2).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
			this.utrosakOdrzavanje = this.utrosakOdrzavanje.multiply(new BigDecimal("100.").add(this.pdv1).divide(new BigDecimal("100."))).setScale(2, RoundingMode.HALF_UP);
		}
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transakcija getTransakcija() {
		return transakcija;
	}

	public void setTransakcija(Transakcija transakcija) {
		this.transakcija = transakcija;
	}

	public TransakcijaStara getTransakcijaStara() {
		return transakcijaStara;
	}

	public void setTransakcijaStara(TransakcijaStara transakcijaStara) {
		this.transakcijaStara = transakcijaStara;
	}

	public LocalDate getDatumRacuna() {
        return datumRacuna;
    }

    public Racun datumRacuna(LocalDate datumRacuna) {
        this.datumRacuna = datumRacuna;
        return this;
    }

    public void setDatumRacuna(LocalDate datumRacuna) {
        this.datumRacuna = datumRacuna;
    }

    public String getPeriod() {
        return period;
    }

    public Racun period(String period) {
        this.period = period;
        return this;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getUtrosakVarijabilni() {
        return utrosakVarijabilni;
    }

    public Racun utrosakVarijabilni(BigDecimal utrosakVarijabilni) {
        this.utrosakVarijabilni = utrosakVarijabilni;
        return this;
    }

    public void setUtrosakVarijabilni(BigDecimal utrosakVarijabilni) {
        this.utrosakVarijabilni = utrosakVarijabilni;
    }

    public BigDecimal getUtrosakFiksni() {
        return utrosakFiksni;
    }

    public Racun utrosakFiksni(BigDecimal utrosakFiksni) {
        this.utrosakFiksni = utrosakFiksni;
        return this;
    }

    public void setUtrosakFiksni(BigDecimal utrosakFiksni) {
        this.utrosakFiksni = utrosakFiksni;
    }
    
    public Boolean getProknjizen() {
		return proknjizen;
	}

	public void setProknjizen(Boolean proknjizen) {
		this.proknjizen = proknjizen;
	}

    public BigDecimal getUtrosakOdrzavanje() {
        return utrosakOdrzavanje;
    }

    public Racun utrosakOdrzavanje(BigDecimal utrosakOdrzavanje) {
        this.utrosakOdrzavanje = utrosakOdrzavanje;
        return this;
    }

    public void setUtrosakOdrzavanje(BigDecimal utrosakOdrzavanje) {
        this.utrosakOdrzavanje = utrosakOdrzavanje;
    }

    public Stan getStan() {
        return stan;
    }

    public Racun stan(Stan stan) {
        this.stan = stan;
        return this;
    }

    public void setStan(Stan stan) {
        this.stan = stan;
    }

    public NacrtRacuna getNacrtRacuna() {
        return nacrtRacuna;
    }

    public Racun nacrtRacuna(NacrtRacuna nacrtRacuna) {
        this.nacrtRacuna = nacrtRacuna;
        return this;
    }

    public void setNacrtRacuna(NacrtRacuna nacrtRacuna) {
        this.nacrtRacuna = nacrtRacuna;              
        
    }
    
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Boolean getAzuriran() {
		return azuriran;
	}

	public void setAzuriran(Boolean azuriran) {
		this.azuriran = azuriran;
	}

	public User getAzurirao() {
		return azurirao;
	}

	public void setAzurirao(User azurirao) {
		this.azurirao = azurirao;
	}

	public User getKreirao() {
		return kreirao;
	}

	public void setKreirao(User kreirao) {
		this.kreirao = kreirao;
	}

	public BigDecimal getUtrosakUKwh() {
		return utrosakUKwh;
	}

	public void setUtrosakUKwh(BigDecimal utrosakUKwh) {
		this.utrosakUKwh = utrosakUKwh;
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

	public BigDecimal getPopust() {
		return popust;
	}

	public void setPopust(BigDecimal popust) {
		this.popust = popust;
	}

	public BigDecimal getUkupnoZaduzenje() {
		return ukupnoZaduzenje;
	}

	public void setUkupnoZaduzenje(BigDecimal ukupnoZaduzenje) {
		this.ukupnoZaduzenje = ukupnoZaduzenje;
	}
	
	

	public LocalDate getValutaPlacanja() {
		return valutaPlacanja;
	}

	public void setValutaPlacanja(LocalDate valutaPlacanja) {
		this.valutaPlacanja = valutaPlacanja;
	}
	
	

	public LocalDate getDatumSaldiranja() {
		return datumSaldiranja;
	}

	public void setDatumSaldiranja(LocalDate datumSaldiranja) {
		this.datumSaldiranja = datumSaldiranja;
	}
	
	

	public BigDecimal getCenaFixIskljucen() {
		return cenaFixIskljucen;
	}

	public void setCenaFixIskljucen(BigDecimal cenaFixIskljucen) {
		this.cenaFixIskljucen = cenaFixIskljucen;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Racun)) {
            return false;
        }
        return id != null && id.equals(((Racun) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Racun{" +
            "id=" + getId() +
            ", datumRacuna='" + getDatumRacuna() + "'" +
            ", period='" + getPeriod() + "'" +
            ", utrosakVarijabilni=" + getUtrosakVarijabilni() +
            ", utrosakFiksni=" + getUtrosakFiksni() +
            ", utrosakOdrzavanje=" + getUtrosakOdrzavanje() +
            "}";
    }
}
