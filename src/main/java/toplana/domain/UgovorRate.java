package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A UgovorRate.
 */
@Entity
@Table(name = "ugovor_rate")
public class UgovorRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "broj_rata")
    private Integer brojRata;

    @Column(name = "datum_sklapanja")
    private LocalDate datumSklapanja;

    @Column(name = "datum_dospeca")
    private LocalDate datumDospeca;

    @ManyToOne
    @JsonIgnoreProperties(value = "ugovorRates", allowSetters = true)
    private Stan stan;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBrojRata() {
        return brojRata;
    }

    public UgovorRate brojRata(Integer brojRata) {
        this.brojRata = brojRata;
        return this;
    }

    public void setBrojRata(Integer brojRata) {
        this.brojRata = brojRata;
    }

    public LocalDate getDatumSklapanja() {
        return datumSklapanja;
    }

    public UgovorRate datumSklapanja(LocalDate datumSklapanja) {
        this.datumSklapanja = datumSklapanja;
        return this;
    }

    public void setDatumSklapanja(LocalDate datumSklapanja) {
        this.datumSklapanja = datumSklapanja;
    }

    public LocalDate getDatumDospeca() {
        return datumDospeca;
    }

    public UgovorRate datumDospeca(LocalDate datumDospeca) {
        this.datumDospeca = datumDospeca;
        return this;
    }

    public void setDatumDospeca(LocalDate datumDospeca) {
        this.datumDospeca = datumDospeca;
    }

    public Stan getStan() {
        return stan;
    }

    public UgovorRate stan(Stan stan) {
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
        if (!(o instanceof UgovorRate)) {
            return false;
        }
        return id != null && id.equals(((UgovorRate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UgovorRate{" +
            "id=" + getId() +
            ", brojRata=" + getBrojRata() +
            ", datumSklapanja='" + getDatumSklapanja() + "'" +
            ", datumDospeca='" + getDatumDospeca() + "'" +
            "}";
    }
}
