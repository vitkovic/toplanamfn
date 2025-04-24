package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A StanStanje.
 */
@Entity
@Table(name = "stan_stanje")
public class StanStanje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "sifra", nullable = false)
    private String sifra;

    @NotNull
    @Column(name = "datum", nullable = false)
    private LocalDate datum;

    @NotNull
    @Column(name = "vrednost", nullable = false)
    private Long vrednost;

   

    @ManyToOne
    @JoinColumn(name="stan_id",referencedColumnName="id", insertable=false, updatable=false)
    @JsonIgnoreProperties(value = "stanStanjes", allowSetters = true)
    private Stan stan;

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

    public StanStanje sifra(String sifra) {
        this.sifra = sifra;
        return this;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public StanStanje datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Long getVrednost() {
        return vrednost;
    }

    public StanStanje vrednost(Long vrednost) {
        this.vrednost = vrednost;
        return this;
    }

    public void setVrednost(Long vrednost) {
        this.vrednost = vrednost;
    }

   
  

   

    public Stan getStan() {
        return stan;
    }

    public StanStanje stan(Stan stan) {
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
        if (!(o instanceof StanStanje)) {
            return false;
        }
        return id != null && id.equals(((StanStanje) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StanStanje{" +
            "id=" + getId() +
            ", sifra='" + getSifra() + "'" +
            ", datum='" + getDatum() + "'" +
            ", vrednost=" + getVrednost() +
           
            "}";
    }
}
