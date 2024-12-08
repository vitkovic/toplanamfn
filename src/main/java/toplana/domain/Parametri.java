package toplana.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Parametri.
 */
@Entity
@Table(name = "parametri")
public class Parametri implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "vrednost", precision = 21, scale = 2)
    private BigDecimal vrednost;

    @OneToMany(mappedBy = "parametri")
    private Set<ParametriIstorija> istorijas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public Parametri naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public BigDecimal getVrednost() {
        return vrednost;
    }

    public Parametri vrednost(BigDecimal vrednost) {
        this.vrednost = vrednost;
        return this;
    }

    public void setVrednost(BigDecimal vrednost) {
        this.vrednost = vrednost;
    }

    public Set<ParametriIstorija> getIstorijas() {
        return istorijas;
    }

    public Parametri istorijas(Set<ParametriIstorija> parametriIstorijas) {
        this.istorijas = parametriIstorijas;
        return this;
    }

    public Parametri addIstorija(ParametriIstorija parametriIstorija) {
        this.istorijas.add(parametriIstorija);
        parametriIstorija.setParametri(this);
        return this;
    }

    public Parametri removeIstorija(ParametriIstorija parametriIstorija) {
        this.istorijas.remove(parametriIstorija);
        parametriIstorija.setParametri(null);
        return this;
    }

    public void setIstorijas(Set<ParametriIstorija> parametriIstorijas) {
        this.istorijas = parametriIstorijas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parametri)) {
            return false;
        }
        return id != null && id.equals(((Parametri) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parametri{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            ", vrednost=" + getVrednost() +
            "}";
    }
}
