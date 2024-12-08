package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Opomena.
 */
@Entity
@Table(name = "opomena")
public class Opomena implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datum_opomene", nullable = false)
    private LocalDate datumOpomene;

    @NotNull
    @Column(name = "zakljucni_datum", nullable = false)
    private LocalDate zakljucniDatum;

    @NotNull
    @Column(name = "iznos", precision = 21, scale = 2, nullable = false)
    private BigDecimal iznos;

    @NotNull
    @Column(name = "datum_izmirenja", nullable = false)
    private LocalDate datumIzmirenja;

    @ManyToOne
    @JsonIgnoreProperties(value = "opomenas", allowSetters = true)
    private Stan stan;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumOpomene() {
        return datumOpomene;
    }

    public Opomena datumOpomene(LocalDate datumOpomene) {
        this.datumOpomene = datumOpomene;
        return this;
    }

    public void setDatumOpomene(LocalDate datumOpomene) {
        this.datumOpomene = datumOpomene;
    }

    public LocalDate getZakljucniDatum() {
        return zakljucniDatum;
    }

    public Opomena zakljucniDatum(LocalDate zakljucniDatum) {
        this.zakljucniDatum = zakljucniDatum;
        return this;
    }

    public void setZakljucniDatum(LocalDate zakljucniDatum) {
        this.zakljucniDatum = zakljucniDatum;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public Opomena iznos(BigDecimal iznos) {
        this.iznos = iznos;
        return this;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

    public LocalDate getDatumIzmirenja() {
        return datumIzmirenja;
    }

    public Opomena datumIzmirenja(LocalDate datumIzmirenja) {
        this.datumIzmirenja = datumIzmirenja;
        return this;
    }

    public void setDatumIzmirenja(LocalDate datumIzmirenja) {
        this.datumIzmirenja = datumIzmirenja;
    }

    public Stan getStan() {
        return stan;
    }

    public Opomena stan(Stan stan) {
        this.stan = stan;
        return this;
    }

    public void setStan(Stan stan) {
        this.stan = stan;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opomena)) {
            return false;
        }
        return id != null && id.equals(((Opomena) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opomena{" +
            "id=" + getId() +
            ", datumOpomene='" + getDatumOpomene() + "'" +
            ", zakljucniDatum='" + getZakljucniDatum() + "'" +
            ", iznos=" + getIznos() +
            ", datumIzmirenja='" + getDatumIzmirenja() + "'" +
            "}";
    }
}
