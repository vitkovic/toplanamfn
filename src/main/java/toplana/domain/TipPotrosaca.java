package toplana.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipPotrosaca.
 */
@Entity
@Table(name = "tip_potrosaca")
public class TipPotrosaca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tip")
    private Integer tip;
    
    @Column(name = "opis")
    private String opis;

    @OneToMany(mappedBy = "tipPotrosaca")
    private Set<Stan> stans = new HashSet<>();
    
    

    public TipPotrosaca(Long id, Integer tip) {		
		this.id = id;
		this.tip = tip;		
	}
    
    

	public TipPotrosaca() {
		super();
	}



	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTip() {
        return tip;
    }

    public TipPotrosaca tip(Integer tip) {
        this.tip = tip;
        return this;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

    public Set<Stan> getStans() {
        return stans;
    }

    public TipPotrosaca stans(Set<Stan> stans) {
        this.stans = stans;
        return this;
    }

    public TipPotrosaca addStan(Stan stan) {
        this.stans.add(stan);
        stan.setTipPotrosaca(this);
        return this;
    }

    public TipPotrosaca removeStan(Stan stan) {
        this.stans.remove(stan);
        stan.setTipPotrosaca(null);
        return this;
    }

    public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public void setStans(Set<Stan> stans) {
        this.stans = stans;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipPotrosaca)) {
            return false;
        }
        return id != null && id.equals(((TipPotrosaca) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipPotrosaca{" +
            "id=" + getId() +
            ", tip=" + getTip() +
            "}";
    }
}
