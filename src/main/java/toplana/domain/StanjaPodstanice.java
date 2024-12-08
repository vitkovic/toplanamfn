package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A StanjaPodstanice.
 */
@Entity
@Table(name = "stanja_podstanice")
public class StanjaPodstanice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "stanje", precision = 21, scale = 2, nullable = false)
    private BigDecimal stanje;

    @NotNull
    @Column(name = "datum_ocitavanja", nullable = false)
    private LocalDate datumOcitavanja;

    @ManyToOne
    @JsonIgnoreProperties(value = "stanjePodstanices", allowSetters = true)
    private Podstanica podstanica;
    
    @OneToOne(mappedBy = "staroStanje")
    @JsonIgnore
    private StanjaPodstaniceZaRacun staroStanjeYaRacun;
    
    @OneToOne(mappedBy = "novoStanje")
    @JsonIgnore
    private StanjaPodstaniceZaRacun novoStanjeZaRacun;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getStanje() {
        return stanje;
    }

    public StanjaPodstanice stanje(BigDecimal stanje) {
        this.stanje = stanje;
        return this;
    }

    public void setStanje(BigDecimal stanje) {
        this.stanje = stanje;
    }

    public LocalDate getDatumOcitavanja() {
        return datumOcitavanja;
    }

    public StanjaPodstanice datumOcitavanja(LocalDate datumOcitavanja) {
        this.datumOcitavanja = datumOcitavanja;
        return this;
    }

    public void setDatumOcitavanja(LocalDate datumOcitavanja) {
        this.datumOcitavanja = datumOcitavanja;
    }

    public Podstanica getPodstanica() {
        return podstanica;
    }

    public StanjaPodstanice podstanica(Podstanica podstanica) {
        this.podstanica = podstanica;
        return this;
    }
    
    

    public void setPodstanica(Podstanica podstanica) {
        this.podstanica = podstanica;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StanjaPodstanice)) {
            return false;
        }
        return id != null && id.equals(((StanjaPodstanice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StanjaPodstanice{" +
            "id=" + getId() +
            ", stanje=" + getStanje() +
            ", datumOcitavanja='" + getDatumOcitavanja() + "'" +
            "}";
    }
}
