package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A NacrtRacuna.
 */
@Entity
@Table(name = "nacrt_racuna")
public class NacrtRacuna implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datum_racuna", nullable = false)
    private LocalDate datumRacuna;

    @NotNull
    @Column(name = "period", nullable = false)
    private String period;

    @NotNull
    @Column(name = "valuta_placanja", nullable = false)
    private LocalDate valutaPlacanja;

    @NotNull
    @Column(name = "cena_kwh", precision = 21, scale = 2, nullable = false)
    private BigDecimal cenaKwh;

    @NotNull
    @Column(name = "cena_fix", precision = 21, scale = 2, nullable = false)
    private BigDecimal cenaFix;
    
    @NotNull
    @Column(name = "cena_fix_iskljucen", precision = 21, scale = 2, nullable = false)
    private BigDecimal cenaFixIskljucen;

    @NotNull
    @Column(name = "cena_odrzavanje", precision = 21, scale = 2, nullable = false)
    private BigDecimal cenaOdrzavanje;

    @Column(name = "cena_o_stalo", precision = 21, scale = 2)
    private BigDecimal cenaOStalo;

    @Column(name = "popust", precision = 21, scale = 2)
    private BigDecimal popust;
   

    @NotNull
    @Column(name = "pdv_1", precision = 21, scale = 2, nullable = false)
    private BigDecimal pdv1;

    @NotNull
    @Column(name = "pdv_2", precision = 21, scale = 2, nullable = false)
    private BigDecimal pdv2;
    
    
    @Column(name = "proknjizen")
    private Boolean proknjizen;

    @OneToMany(mappedBy = "nacrtRacuna")
    private Set<Racun> racuns = new HashSet<>();

    @OneToMany(mappedBy = "nacrtRacuna")
    private Set<StanjaPodstaniceZaRacun> stanjaPodstaniceZaRacune = new HashSet<>();
    

    public Set<StanjaPodstaniceZaRacun> getStanjaPodstaniceZaRacune() {
		return stanjaPodstaniceZaRacune;
	}

	public void setStanjaPodstaniceZaRacune(Set<StanjaPodstaniceZaRacun> stanjaPodstaniceZaRacune) {
		this.stanjaPodstaniceZaRacune = stanjaPodstaniceZaRacune;
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

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumRacuna() {
        return datumRacuna;
    }

    public NacrtRacuna datumRacuna(LocalDate datumRacuna) {
        this.datumRacuna = datumRacuna;
        return this;
    }

    public void setDatumRacuna(LocalDate datumRacuna) {
        this.datumRacuna = datumRacuna;
    }

    public String getPeriod() {
        return period;
    }

    public NacrtRacuna period(String period) {
        this.period = period;
        return this;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LocalDate getValutaPlacanja() {
        return valutaPlacanja;
    }

    public NacrtRacuna valutaPlacanja(LocalDate valutaPlacanja) {
        this.valutaPlacanja = valutaPlacanja;
        return this;
    }

    public void setValutaPlacanja(LocalDate valutaPlacanja) {
        this.valutaPlacanja = valutaPlacanja;
    }

    public BigDecimal getCenaKwh() {
        return cenaKwh;
    }

    public NacrtRacuna cenaKwh(BigDecimal cenaKwh) {
        this.cenaKwh = cenaKwh;
        return this;
    }

    public void setCenaKwh(BigDecimal cenaKwh) {
        this.cenaKwh = cenaKwh;
    }

    public BigDecimal getCenaFix() {
        return cenaFix;
    }

    public NacrtRacuna cenaFix(BigDecimal cenaFix) {
        this.cenaFix = cenaFix;
        return this;
    }

    public void setCenaFix(BigDecimal cenaFix) {
        this.cenaFix = cenaFix;
    }

    public BigDecimal getCenaOdrzavanje() {
        return cenaOdrzavanje;
    }

    public NacrtRacuna cenaOdrzavanje(BigDecimal cenaOdrzavanje) {
        this.cenaOdrzavanje = cenaOdrzavanje;
        return this;
    }

    public void setCenaOdrzavanje(BigDecimal cenaOdrzavanje) {
        this.cenaOdrzavanje = cenaOdrzavanje;
    }

    public BigDecimal getCenaOStalo() {
        return cenaOStalo;
    }

    public NacrtRacuna cenaOStalo(BigDecimal cenaOStalo) {
        this.cenaOStalo = cenaOStalo;
        return this;
    }

    public void setCenaOStalo(BigDecimal cenaOStalo) {
        this.cenaOStalo = cenaOStalo;
    }

    public BigDecimal getPopust() {
        return popust;
    }

    public NacrtRacuna popust(BigDecimal popust) {
        this.popust = popust;
        return this;
    }

    public void setPopust(BigDecimal popust) {
        this.popust = popust;
    }

   

  

  

    public BigDecimal getCenaFixIskljucen() {
		return cenaFixIskljucen;
	}

	public void setCenaFixIskljucen(BigDecimal cenaFixIskljucen) {
		this.cenaFixIskljucen = cenaFixIskljucen;
	}

	public BigDecimal getPdv1() {
        return pdv1;
    }

    public NacrtRacuna pdv1(BigDecimal pdv1) {
        this.pdv1 = pdv1;
        return this;
    }

    public void setPdv1(BigDecimal pdv1) {
        this.pdv1 = pdv1;
    }

    public BigDecimal getPdv2() {
        return pdv2;
    }

    public NacrtRacuna pdv2(BigDecimal pdv2) {
        this.pdv2 = pdv2;
        return this;
    }

    public void setPdv2(BigDecimal pdv2) {
        this.pdv2 = pdv2;
    }

    public Set<Racun> getRacuns() {
        return racuns;
    }

    public NacrtRacuna racuns(Set<Racun> racuns) {
        this.racuns = racuns;
        return this;
    }

    public NacrtRacuna addRacun(Racun racun) {
        this.racuns.add(racun);
        racun.setNacrtRacuna(this);
        return this;
    }

    public NacrtRacuna removeRacun(Racun racun) {
        this.racuns.remove(racun);
        racun.setNacrtRacuna(null);
        return this;
    }

    public void setRacuns(Set<Racun> racuns) {
        this.racuns = racuns;
    }

   
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Boolean getProknjizen() {
		return proknjizen;
	}

	public void setProknjizen(Boolean proknjizen) {
		this.proknjizen = proknjizen;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NacrtRacuna)) {
            return false;
        }
        return id != null && id.equals(((NacrtRacuna) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NacrtRacuna{" +
            "id=" + getId() +
            ", datumRacuna='" + getDatumRacuna() + "'" +
            ", period='" + getPeriod() + "'" +
            ", valutaPlacanja='" + getValutaPlacanja() + "'" +
            ", cenaKwh=" + getCenaKwh() +
            ", cenaFix=" + getCenaFix() +
            ", cenaOdrzavanje=" + getCenaOdrzavanje() +
            ", cenaOStalo=" + getCenaOStalo() +
            ", popust=" + getPopust() +          
            ", pdv1=" + getPdv1() +
            ", pdv2=" + getPdv2() +
            "}";
    }
}
