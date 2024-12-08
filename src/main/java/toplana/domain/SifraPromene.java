package toplana.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A SifraPromene.
 */
@Entity
@Table(name = "sifra_promene")
public class SifraPromene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sifra")
    private String sifra;
    
    @Column(name = "broj")
    private Integer broj;
    
    //tip = zaduzenje (racun)
    //tip = razduzenje 
    @Column(name = "tip_promene")
    private String tipPromene;
    
    

    public SifraPromene(Long id) {
		super();
		this.id = id;
	}
    
    

	public SifraPromene() {
		super();
	}



	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSifra() {
        return sifra;
    }

    public SifraPromene sifra(String sifra) {
        this.sifra = sifra;
        return this;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Integer getBroj() {
		return broj;
	}

	public void setBroj(Integer broj) {
		this.broj = broj;
	}

	public String getTipPromene() {
		return tipPromene;
	}

	public void setTipPromene(String tipPromene) {
		this.tipPromene = tipPromene;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SifraPromene)) {
            return false;
        }
        return id != null && id.equals(((SifraPromene) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SifraPromene{" +
            "id=" + getId() +
            ", sifra='" + getSifra() + "'" +
            "}";
    }
}
