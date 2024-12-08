package toplana.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A CeneStare.
 */
@Entity
@Table(name = "cene_stare")
public class CeneStare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "kwh", precision = 21, scale = 2)
    private BigDecimal kwh;

    @Column(name = "fix", precision = 21, scale = 2)
    private BigDecimal fix;
    
    @Column(name = "fix_iskljuceno", precision = 21, scale = 2)
    private BigDecimal fixIskljuceno;

    @Column(name = "odrzavanje", precision = 21, scale = 2)
    private BigDecimal odrzavanje;

    @Column(name = "ostalo", precision = 21, scale = 2)
    private BigDecimal ostalo;
    
    @Column(name = "pdv1", precision = 21, scale = 2)
    private BigDecimal pdv1;
    
    @Column(name = "pdv2", precision = 21, scale =7)
    private BigDecimal pdv2;
    
    @Column(name = "popust1", precision = 21, scale = 2)
    private BigDecimal popust1;
    
    @Column(name = "popust2", precision = 21, scale = 2)
    private BigDecimal popust2;
    
    @Column(name = "datum_prestanka")
    private LocalDate datumPrestanka;
    

    public LocalDate getDatumPrestanka() {
		return datumPrestanka;
	}

	public void setDatumPrestanka(LocalDate datumPrestanka) {
		this.datumPrestanka = datumPrestanka;
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

	public BigDecimal getPopust1() {
		return popust1;
	}

	public void setPopust1(BigDecimal popust1) {
		this.popust1 = popust1;
	}

	public BigDecimal getPopust2() {
		return popust2;
	}

	public void setPopust2(BigDecimal popust2) {
		this.popust2 = popust2;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getKwh() {
        return kwh;
    }

    public CeneStare kwh(BigDecimal kwh) {
        this.kwh = kwh;
        return this;
    }

    public void setKwh(BigDecimal kwh) {
        this.kwh = kwh;
    }

    public BigDecimal getFix() {
        return fix;
    }

    public CeneStare fix(BigDecimal fix) {
        this.fix = fix;
        return this;
    }

    public void setFix(BigDecimal fix) {
        this.fix = fix;
    }
    
    

    public BigDecimal getFixIskljuceno() {
		return fixIskljuceno;
	}

	public void setFixIskljuceno(BigDecimal fixIskljuceno) {
		this.fixIskljuceno = fixIskljuceno;
	}

	public BigDecimal getOdrzavanje() {
        return odrzavanje;
    }

    public CeneStare odrzavanje(BigDecimal odrzavanje) {
        this.odrzavanje = odrzavanje;
        return this;
    }

    public void setOdrzavanje(BigDecimal odrzavanje) {
        this.odrzavanje = odrzavanje;
    }

    public BigDecimal getOstalo() {
        return ostalo;
    }

    public CeneStare ostalo(BigDecimal ostalo) {
        this.ostalo = ostalo;
        return this;
    }

    public void setOstalo(BigDecimal ostalo) {
        this.ostalo = ostalo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CeneStare)) {
            return false;
        }
        return id != null && id.equals(((CeneStare) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CeneStare{" +
            "id=" + getId() +
            ", kwh=" + getKwh() +
            ", fix=" + getFix() +
            ", odrzavanje=" + getOdrzavanje() +
            ", ostalo=" + getOstalo() +
            "}";
    }
}
