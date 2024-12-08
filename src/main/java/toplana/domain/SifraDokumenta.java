package toplana.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A SifraDokumenta.
 */
@Entity
@Table(name = "sifra_dokumenta")
public class SifraDokumenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sifra")
    private String sifra;

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

    public SifraDokumenta sifra(String sifra) {
        this.sifra = sifra;
        return this;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SifraDokumenta)) {
            return false;
        }
        return id != null && id.equals(((SifraDokumenta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SifraDokumenta{" +
            "id=" + getId() +
            ", sifra='" + getSifra() + "'" +
            "}";
    }
}
