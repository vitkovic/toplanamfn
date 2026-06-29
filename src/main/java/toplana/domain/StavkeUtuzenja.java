package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A StavkeUtuzenja.
 */
@Entity
@Table(name = "stavke_utuzenja")
public class StavkeUtuzenja implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "obracunski_period", nullable = false)
    private String obracunskiPeriod;

    @NotNull
    @Column(name = "datum_izdavanja_racuna", nullable = false)
    private LocalDate datumIzdavanjaRacuna;

    @NotNull
    @Column(name = "datum_dospeca_racuna", nullable = false)
    private LocalDate datumDospecaRacuna;

    @NotNull
    @Column(name = "zaduzenje", precision = 21, scale = 2, nullable = false)
    private BigDecimal zaduzenje;

    @NotNull
    @Column(name = "ukupno_za_uplatu", precision = 21, scale = 2, nullable = false)
    private BigDecimal ukupnoZaUplatu;

    @ManyToOne
    @JsonIgnoreProperties(value = "stavkeUtuzenjas", allowSetters = true)
    private Utuzenje utuzenje;
    
    
    @OneToMany(mappedBy = "stavkaUtuzenja")
    private Set<Transakcija> transakcijas = new HashSet<>();
    
    @Column(name = "opis")
    private String opis;
    
    public String getOpis() {
        return opis;
    }

    public StavkeUtuzenja opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    public Set<Transakcija> getTransakcijas() {
        return transakcijas;
    }

    public void setTransakcijas(Set<Transakcija> transakcijas) {
        this.transakcijas = transakcijas;
    }

    public StavkeUtuzenja transakcijas(Set<Transakcija> transakcijas) {
        this.transakcijas = transakcijas;
        return this;
    }

    public StavkeUtuzenja addTransakcija(Transakcija transakcija) {
        this.transakcijas.add(transakcija);
        transakcija.setStavkaUtuzenja(this);
        return this;
    }

    public StavkeUtuzenja removeTransakcija(Transakcija transakcija) {
        this.transakcijas.remove(transakcija);
        transakcija.setStavkaUtuzenja(null);
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObracunskiPeriod() {
        return obracunskiPeriod;
    }

    public StavkeUtuzenja obracunskiPeriod(String obracunskiPeriod) {
        this.obracunskiPeriod = obracunskiPeriod;
        return this;
    }

    public void setObracunskiPeriod(String obracunskiPeriod) {
        this.obracunskiPeriod = obracunskiPeriod;
    }

    public LocalDate getDatumIzdavanjaRacuna() {
        return datumIzdavanjaRacuna;
    }

    public StavkeUtuzenja datumIzdavanjaRacuna(LocalDate datumIzdavanjaRacuna) {
        this.datumIzdavanjaRacuna = datumIzdavanjaRacuna;
        return this;
    }

    public void setDatumIzdavanjaRacuna(LocalDate datumIzdavanjaRacuna) {
        this.datumIzdavanjaRacuna = datumIzdavanjaRacuna;
    }

    public LocalDate getDatumDospecaRacuna() {
        return datumDospecaRacuna;
    }

    public StavkeUtuzenja datumDospecaRacuna(LocalDate datumDospecaRacuna) {
        this.datumDospecaRacuna = datumDospecaRacuna;
        return this;
    }

    public void setDatumDospecaRacuna(LocalDate datumDospecaRacuna) {
        this.datumDospecaRacuna = datumDospecaRacuna;
    }

    public BigDecimal getZaduzenje() {
        return zaduzenje;
    }

    public StavkeUtuzenja zaduzenje(BigDecimal zaduzenje) {
        this.zaduzenje = zaduzenje;
        return this;
    }

    public void setZaduzenje(BigDecimal zaduzenje) {
        this.zaduzenje = zaduzenje;
    }

    public BigDecimal getUkupnoZaUplatu() {
        return ukupnoZaUplatu;
    }

    public StavkeUtuzenja ukupnoZaUplatu(BigDecimal ukupnoZaUplatu) {
        this.ukupnoZaUplatu = ukupnoZaUplatu;
        return this;
    }

    public void setUkupnoZaUplatu(BigDecimal ukupnoZaUplatu) {
        this.ukupnoZaUplatu = ukupnoZaUplatu;
    }

    public Utuzenje getUtuzenje() {
        return utuzenje;
    }

    public StavkeUtuzenja utuzenje(Utuzenje utuzenje) {
        this.utuzenje = utuzenje;
        return this;
    }

    public void setUtuzenje(Utuzenje utuzenje) {
        this.utuzenje = utuzenje;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StavkeUtuzenja)) {
            return false;
        }
        return id != null && id.equals(((StavkeUtuzenja) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StavkeUtuzenja{" +
            "id=" + getId() +
            ", obracunskiPeriod='" + getObracunskiPeriod() + "'" +
            ", datumIzdavanjaRacuna='" + getDatumIzdavanjaRacuna() + "'" +
            ", datumDospecaRacuna='" + getDatumDospecaRacuna() + "'" +
            ", zaduzenje=" + getZaduzenje() +
            ", ukupnoZaUplatu=" + getUkupnoZaUplatu() +
            "}";
    }
}
