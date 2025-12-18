package toplana.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vlasnik.
 */
@Entity
@Table(name = "vlasnik")
public class Vlasnik implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "broj_racuna")
    private String brojRacuna;

    @Column(name = "partija_racuna")
    private String partijaRacuna;

    @Column(name = "naziv")
    private String naziv;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Column(name = "email")
    private String email;

    @Column(name = "psr")
    private String psr;

    @OneToMany(mappedBy = "vlasnik")
    private Set<Stan> stans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public Vlasnik ime(String ime) {
        this.ime = ime;
        return this;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public Vlasnik prezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }

    public Vlasnik brojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
        return this;
    }

    public void setBrojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public String getPartijaRacuna() {
        return partijaRacuna;
    }

    public Vlasnik partijaRacuna(String partijaRacuna) {
        this.partijaRacuna = partijaRacuna;
        return this;
    }

    public void setPartijaRacuna(String partijaRacuna) {
        this.partijaRacuna = partijaRacuna;
    }

    public String getNaziv() {
        return naziv;
    }

    public Vlasnik naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getEmail() {
        return email;
    }

    public Vlasnik email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsr() {
        return psr;
    }

    public Vlasnik psr(String psr) {
        this.psr = psr;
        return this;
    }

    public void setPsr(String psr) {
        this.psr = psr;
    }

    public Set<Stan> getStans() {
        return stans;
    }

    public Vlasnik stans(Set<Stan> stans) {
        this.stans = stans;
        return this;
    }

    public Vlasnik addStan(Stan stan) {
        this.stans.add(stan);
        stan.setVlasnik(this);
        return this;
    }

    public Vlasnik removeStan(Stan stan) {
        this.stans.remove(stan);
        stan.setVlasnik(null);
        return this;
    }

    public void setStans(Set<Stan> stans) {
        this.stans = stans;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vlasnik)) {
            return false;
        }
        return id != null && id.equals(((Vlasnik) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vlasnik{" +
            "id=" + getId() +
            ", ime='" + getIme() + "'" +
            ", prezime='" + getPrezime() + "'" +
            ", brojRacuna='" + getBrojRacuna() + "'" +
            ", partijaRacuna='" + getPartijaRacuna() + "'" +
            ", naziv='" + getNaziv() + "'" +
            ", email='" + getEmail() + "'" +
            ", psr='" + getPsr() + "'" +
            "}";
    }
}
