package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A ParametriIstorija.
 */
@Entity
@Table(name = "parametri_istorija")
public class ParametriIstorija implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "vrednost", precision = 21, scale = 2)
    private BigDecimal vrednost;

    @Column(name = "vazi_od")
    private LocalDate vaziOd;

    @Column(name = "vazi_do")
    private LocalDate vaziDo;

    @ManyToOne
    @JsonIgnoreProperties(value = "istorijas", allowSetters = true)
    private Parametri parametri;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getVrednost() {
        return vrednost;
    }

    public ParametriIstorija vrednost(BigDecimal vrednost) {
        this.vrednost = vrednost;
        return this;
    }

    public void setVrednost(BigDecimal vrednost) {
        this.vrednost = vrednost;
    }

    public LocalDate getVaziOd() {
        return vaziOd;
    }

    public ParametriIstorija vaziOd(LocalDate vaziOd) {
        this.vaziOd = vaziOd;
        return this;
    }

    public void setVaziOd(LocalDate vaziOd) {
        this.vaziOd = vaziOd;
    }

    public LocalDate getVaziDo() {
        return vaziDo;
    }

    public ParametriIstorija vaziDo(LocalDate vaziDo) {
        this.vaziDo = vaziDo;
        return this;
    }

    public void setVaziDo(LocalDate vaziDo) {
        this.vaziDo = vaziDo;
    }

    public Parametri getParametri() {
        return parametri;
    }

    public ParametriIstorija parametri(Parametri parametri) {
        this.parametri = parametri;
        return this;
    }

    public void setParametri(Parametri parametri) {
        this.parametri = parametri;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParametriIstorija)) {
            return false;
        }
        return id != null && id.equals(((ParametriIstorija) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParametriIstorija{" +
            "id=" + getId() +
            ", vrednost=" + getVrednost() +
            ", vaziOd='" + getVaziOd() + "'" +
            ", vaziDo='" + getVaziDo() + "'" +
            "}";
    }
}
