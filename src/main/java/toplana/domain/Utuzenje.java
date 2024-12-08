package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Utuzenje.
 */
@Entity
@Table(name = "utuzenje")
public class Utuzenje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @OneToMany(mappedBy = "utuzenje")
    private Set<StavkeUtuzenja> stavkeUtuzenjas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "utuzenjes", allowSetters = true)
    private Stan stan;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public Utuzenje datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Set<StavkeUtuzenja> getStavkeUtuzenjas() {
        return stavkeUtuzenjas;
    }

    public Utuzenje stavkeUtuzenjas(Set<StavkeUtuzenja> stavkeUtuzenjas) {
        this.stavkeUtuzenjas = stavkeUtuzenjas;
        return this;
    }

    public Utuzenje addStavkeUtuzenja(StavkeUtuzenja stavkeUtuzenja) {
        this.stavkeUtuzenjas.add(stavkeUtuzenja);
        stavkeUtuzenja.setUtuzenje(this);
        return this;
    }

    public Utuzenje removeStavkeUtuzenja(StavkeUtuzenja stavkeUtuzenja) {
        this.stavkeUtuzenjas.remove(stavkeUtuzenja);
        stavkeUtuzenja.setUtuzenje(null);
        return this;
    }

    public void setStavkeUtuzenjas(Set<StavkeUtuzenja> stavkeUtuzenjas) {
        this.stavkeUtuzenjas = stavkeUtuzenjas;
    }

    public Stan getStan() {
        return stan;
    }

    public Utuzenje stan(Stan stan) {
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
        if (!(o instanceof Utuzenje)) {
            return false;
        }
        return id != null && id.equals(((Utuzenje) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utuzenje{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
