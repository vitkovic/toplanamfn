package toplana.domain;


import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A StanjaPodstaniceZaRacun.
 */
@Entity
@Table(name = "stanja_podstanice_za_racun")
public class StanjaPodstaniceZaRacun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
   
    @OneToOne
    @JoinColumn(unique = true)
    private StanjaPodstanice staroStanje;
    
    @OneToOne
    @JoinColumn(unique = true)
    private StanjaPodstanice novoStanje;

    @Column(name = "ukupna_povrsina", precision = 21, scale = 2)
    private BigDecimal ukupnaPovrsina;

    @Column(name = "utrosak_po_m_2", precision = 21, scale = 2)
    private BigDecimal utrosakPoM2;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "stanjaPodstaniceZaRacune", allowSetters = true)
    private Podstanica podstanica;
    
  
    
    
    public Podstanica getPodstanica() {
		return podstanica;
	}

	public void setPodstanica(Podstanica podstanica) {
		this.podstanica = podstanica;
	}

	public NacrtRacuna getNacrtRacuna() {
		return nacrtRacuna;
	}

	public void setNacrtRacuna(NacrtRacuna nacrtRacuna) {
		this.nacrtRacuna = nacrtRacuna;
	}

	@ManyToOne
    @JsonIgnoreProperties(value = "stanjaPodstaniceZaRacune", allowSetters = true)
    private NacrtRacuna nacrtRacuna;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public BigDecimal getUkupnaPovrsina() {
        return ukupnaPovrsina;
    }

    public StanjaPodstaniceZaRacun ukupnaPovrsina(BigDecimal ukupnaPovrsina) {
        this.ukupnaPovrsina = ukupnaPovrsina;
        return this;
    }

    public void setUkupnaPovrsina(BigDecimal ukupnaPovrsina) {
        this.ukupnaPovrsina = ukupnaPovrsina;
    }

    public BigDecimal getUtrosakPoM2() {
        return utrosakPoM2;
    }

    public StanjaPodstaniceZaRacun utrosakPoM2(BigDecimal utrosakPoM2) {
        this.utrosakPoM2 = utrosakPoM2;
        return this;
    }

    public void setUtrosakPoM2(BigDecimal utrosakPoM2) {
        this.utrosakPoM2 = utrosakPoM2;
    }

   

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public StanjaPodstanice getStaroStanje() {
		return staroStanje;
	}

	public void setStaroStanje(StanjaPodstanice staroStanje) {
		this.staroStanje = staroStanje;
	}

	public StanjaPodstanice getNovoStanje() {
		return novoStanje;
	}

	public void setNovoStanje(StanjaPodstanice novoStanje) {
		this.novoStanje = novoStanje;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StanjaPodstaniceZaRacun)) {
            return false;
        }
        return id != null && id.equals(((StanjaPodstaniceZaRacun) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StanjaPodstaniceZaRacun{" +
            "id=" + getId() +           
            ", ukupnaPovrsina=" + getUkupnaPovrsina() +
            ", utrosakPoM2=" + getUtrosakPoM2() +
            "}";
    }
}
