package toplana.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A VrstaTransakcije.
 */
@Entity
@Table(name = "vrsta_transakcije")
public class VrstaTransakcije implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "vrsta")
    private String vrsta;

    @Column(name = "dodaje_vrednost")
    private Integer dodajeVrednost;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVrsta() {
        return vrsta;
    }

    public VrstaTransakcije vrsta(String vrsta) {
        this.vrsta = vrsta;
        return this;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public Integer getDodajeVrednost() {
        return dodajeVrednost;
    }

    public VrstaTransakcije dodajeVrednost(Integer dodajeVrednost) {
        this.dodajeVrednost = dodajeVrednost;
        return this;
    }

    public void setDodajeVrednost(Integer dodajeVrednost) {
        this.dodajeVrednost = dodajeVrednost;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VrstaTransakcije)) {
            return false;
        }
        return id != null && id.equals(((VrstaTransakcije) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VrstaTransakcije{" +
            "id=" + getId() +
            ", vrsta='" + getVrsta() + "'" +
            ", dodajeVrednost=" + getDodajeVrednost() +
            "}";
    }
}
