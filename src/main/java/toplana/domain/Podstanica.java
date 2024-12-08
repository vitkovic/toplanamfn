package toplana.domain;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "podstanica")
public class Podstanica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "naziv")
    private String naziv;
    
    public Set<StanjaPodstaniceZaRacun> getStanjaPodstaniceZaRacune() {
		return stanjaPodstaniceZaRacune;
	}

	public void setStanjaPodstaniceZaRacune(Set<StanjaPodstaniceZaRacun> stanjaPodstaniceZaRacune) {
		this.stanjaPodstaniceZaRacune = stanjaPodstaniceZaRacune;
	}

	@Column(name = "broj")
    private Integer broj;

   

    @OneToMany(mappedBy = "podstanica")
    private Set<Stan> stans = new HashSet<>();

    @OneToMany(mappedBy = "podstanica")
    private Set<StanjaPodstanice> stanjePodstanices = new HashSet<>();
    
    @OneToMany(mappedBy = "podstanica")
    private Set<StanjaPodstaniceZaRacun> stanjaPodstaniceZaRacune = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public Podstanica naziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

   
    public Set<Stan> getStans() {
        return stans;
    }

    public Podstanica stans(Set<Stan> stans) {
        this.stans = stans;
        return this;
    }

    public Podstanica addStan(Stan stan) {
        this.stans.add(stan);
        stan.setPodstanica(this);
        return this;
    }

    public Podstanica removeStan(Stan stan) {
        this.stans.remove(stan);
        stan.setPodstanica(null);
        return this;
    }

    public void setStans(Set<Stan> stans) {
        this.stans = stans;
    }

    public Set<StanjaPodstanice> getStanjePodstanices() {
        return stanjePodstanices;
    }

    public Podstanica stanjePodstanices(Set<StanjaPodstanice> stanjaPodstanices) {
        this.stanjePodstanices = stanjaPodstanices;
        return this;
    }

    public Podstanica addStanjePodstanice(StanjaPodstanice stanjaPodstanice) {
        this.stanjePodstanices.add(stanjaPodstanice);
        stanjaPodstanice.setPodstanica(this);
        return this;
    }

    public Podstanica removeStanjePodstanice(StanjaPodstanice stanjaPodstanice) {
        this.stanjePodstanices.remove(stanjaPodstanice);
        stanjaPodstanice.setPodstanica(null);
        return this;
    }

    public void setStanjePodstanices(Set<StanjaPodstanice> stanjaPodstanices) {
        this.stanjePodstanices = stanjaPodstanices;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Integer getBroj() {
		return broj;
	}

	public void setBroj(Integer broj) {
		this.broj = broj;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Podstanica)) {
            return false;
        }
        return id != null && id.equals(((Podstanica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Podstanica{" +
            "id=" + getId() +
            ", naziv='" + getNaziv() + "'" +
            "}";
    }
}
