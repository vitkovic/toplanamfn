package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A TransakcijaStara.
 */
@Entity
@Table(name = "transakcija_stara")
public class TransakcijaStara implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "valuta")
    private LocalDate valuta;
    
    @Column(name = "datum_dokumenta")
    private LocalDate datumDokumenta;

    @Column(name = "status")
    private String status;

    @Column(name = "opis")
    private String opis;
    
    @Column(name = "sifra_dokumenta")
    private String sifraDokumenta;

    @Column(name = "duguje", precision = 21, scale = 2)
    private BigDecimal duguje;

    @Column(name = "potrazuje", precision = 21, scale = 2)
    private BigDecimal potrazuje;

    @ManyToOne
    @JsonIgnoreProperties(value = "transakcijaStaras", allowSetters = true)
    private VrstaTransakcije vrstaTransakcije;

   


    @ManyToOne
    @JsonIgnoreProperties(value = "transakcijaStaras", allowSetters = true)
    private SifraPromene sifraPromene;   

    @OneToOne(mappedBy = "transakcijaStara")
    @JsonIgnore
    private StavkeIzvoda stavkaIzvoda;
    
    @OneToOne(mappedBy = "transakcijaStara")
    @JsonIgnore
    private StavkeIzvodaPostanska stavkaIzvodaPostanska;
    
    @OneToOne(mappedBy = "transakcijaStara")
    @JsonIgnore
    private Racun racun;

    @ManyToOne
    @JsonIgnoreProperties(value = "transakcijaStaras", allowSetters = true)
    private Stan stan;
    
    @ManyToOne
    @JsonIgnore
    private OstaliRacuni ostaliRacuni;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public TransakcijaStara datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalDate getValuta() {
        return valuta;
    }

    public TransakcijaStara valuta(LocalDate valuta) {
        this.valuta = valuta;
        return this;
    }

    public void setValuta(LocalDate valuta) {
        this.valuta = valuta;
    }

    public String getStatus() {
        return status;
    }

    public TransakcijaStara status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpis() {
        return opis;
    }

    public TransakcijaStara opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getDuguje() {
        return duguje;
    }

    public TransakcijaStara duguje(BigDecimal duguje) {
        this.duguje = duguje;
        return this;
    }

    public void setDuguje(BigDecimal duguje) {
        this.duguje = duguje;
    }

    public BigDecimal getPotrazuje() {
        return potrazuje;
    }

    public TransakcijaStara potrazuje(BigDecimal potrazuje) {
        this.potrazuje = potrazuje;
        return this;
    }

    public void setPotrazuje(BigDecimal potrazuje) {
        this.potrazuje = potrazuje;
    }

    public VrstaTransakcije getVrstaTransakcije() {
        return vrstaTransakcije;
    }

    public TransakcijaStara vrstaTransakcije(VrstaTransakcije vrstaTransakcije) {
        this.vrstaTransakcije = vrstaTransakcije;
        return this;
    }

    public void setVrstaTransakcije(VrstaTransakcije vrstaTransakcije) {
        this.vrstaTransakcije = vrstaTransakcije;
    }

   

    public LocalDate getDatumDokumenta() {
		return datumDokumenta;
	}

	public void setDatumDokumenta(LocalDate datumDokumenta) {
		this.datumDokumenta = datumDokumenta;
	}

	public String getSifraDokumenta() {
		return sifraDokumenta;
	}

	public void setSifraDokumenta(String sifraDokumenta) {
		this.sifraDokumenta = sifraDokumenta;
	}

	public SifraPromene getSifraPromene() {
        return sifraPromene;
    }

    public TransakcijaStara sifraPromene(SifraPromene sifraPromene) {
        this.sifraPromene = sifraPromene;
        return this;
    }

    public void setSifraPromene(SifraPromene sifraPromene) {
        this.sifraPromene = sifraPromene;
    }
  
    public StavkeIzvoda getStavkaIzvoda() {
        return stavkaIzvoda;
    }

    public TransakcijaStara stavkaIzvoda(StavkeIzvoda stavkeIzvoda) {
        this.stavkaIzvoda = stavkeIzvoda;
        return this;
    }

    public void setStavkaIzvoda(StavkeIzvoda stavkeIzvoda) {
        this.stavkaIzvoda = stavkeIzvoda;
    }

    public Stan getStan() {
        return stan;
    }

    public TransakcijaStara stan(Stan stan) {
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
        if (!(o instanceof TransakcijaStara)) {
            return false;
        }
        return id != null && id.equals(((TransakcijaStara) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransakcijaStara{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", status='" + getStatus() + "'" +
            ", opis='" + getOpis() + "'" +
            ", duguje=" + getDuguje() +
            ", potrazuje=" + getPotrazuje() +
            "}";
    }
}
