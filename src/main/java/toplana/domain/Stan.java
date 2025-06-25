package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;											   
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Stan.
 */
@Entity
@Table(name = "stan")
public class Stan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "povrsina", precision = 21, scale = 2, nullable = false)
    private BigDecimal povrsina;

	
    @Column(name = "ulica")
    private String ulica;

    @Column(name = "ulaz")
    private Integer ulaz;

    @Column(name = "broj")
    private Integer broj;

    @Column(name = "ukljucen")
    private Boolean ukljucen;

    
    @Column(name = "sifra", unique = true)
    private String sifra;

    @Column(name = "grad")
    private String grad;

    @Column(name = "postanski_broj")
    private String postanskiBroj;

    @Column(name = "broj_merila")
    private String brojMerila;

    @OneToMany(mappedBy = "stan")
    private Set<Opomena> opomenas = new HashSet<>();

    @OneToMany(mappedBy = "stan")
    private Set<Utuzenje> utuzenjes = new HashSet<>();

    @OneToMany(mappedBy = "stan")
    private Set<UgovorRate> ugovorRates = new HashSet<>();

    @OneToMany(mappedBy = "stan")
    private Set<Transakcija> transakcijas = new HashSet<>();

    @OneToMany(mappedBy = "stan")
    private Set<TransakcijaStara> transakcijaStaras = new HashSet<>();

    @OneToMany(mappedBy = "stan")
    private Set<Racun> racuns = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "stans", allowSetters = true)
    private TipPotrosaca tipPotrosaca;

    @ManyToOne
    @JsonIgnoreProperties(value = "stans", allowSetters = true)
    private Podstanica podstanica;

    @ManyToOne
    @JsonIgnoreProperties(value = "stans", allowSetters = true)
    private Vlasnik vlasnik;
	
								
	@OneToOne(mappedBy = "stan")
    @JsonIgnore
    private OstaliRacuni ostaliRacuni;		   
																	   

	private List<Double> zadnjaStanja;
	
	
	
    public List<Double> getZadnjaStanja() {
		return zadnjaStanja;
	}

	public void setZadnjaStanja(List<Double> zadnjaStanja) {
		this.zadnjaStanja = zadnjaStanja;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPovrsina() {
        return povrsina;
    }

    public Stan povrsina(BigDecimal povrsina) {
        this.povrsina = povrsina;
        return this;
    }

    public void setPovrsina(BigDecimal povrsina) {
        this.povrsina = povrsina;
    }

    public String getUlica() {
        return ulica;
    }

    public Stan ulica(String ulica) {
        this.ulica = ulica;
        return this;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public Integer getUlaz() {
        return ulaz;
    }

    public Stan ulaz(Integer ulaz) {
        this.ulaz = ulaz;
        return this;
    }

    public void setUlaz(Integer ulaz) {
        this.ulaz = ulaz;
    }

    public Integer getBroj() {
        return broj;
    }

    public Stan broj(Integer broj) {
        this.broj = broj;
        return this;
    }

    public void setBroj(Integer broj) {
        this.broj = broj;
    }

    public Boolean isUkljucen() {
        return ukljucen;
    }

    public Stan ukljucen(Boolean ukljucen) {
        this.ukljucen = ukljucen;
        return this;
    }

    public void setUkljucen(Boolean ukljucen) {
        this.ukljucen = ukljucen;
    }

    public String getSifra() {
        return sifra;
    }

    public Stan sifra(String sifra) {
        this.sifra = sifra;
        return this;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getGrad() {
        return grad;
    }

    public Stan grad(String grad) {
        this.grad = grad;
        return this;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public Stan postanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
        return this;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public String getBrojMerila() {
        return brojMerila;
    }

    public Stan brojMerila(String brojMerila) {
        this.brojMerila = brojMerila;
        return this;
    }

    public void setBrojMerila(String brojMerila) {
        this.brojMerila = brojMerila;
    }

    public Set<Opomena> getOpomenas() {
        return opomenas;
    }

    public Stan opomenas(Set<Opomena> opomenas) {
        this.opomenas = opomenas;
        return this;
    }

    public Stan addOpomena(Opomena opomena) {
        this.opomenas.add(opomena);
        opomena.setStan(this);
        return this;
    }

    public Stan removeOpomena(Opomena opomena) {
        this.opomenas.remove(opomena);
        opomena.setStan(null);
        return this;
    }

    public void setOpomenas(Set<Opomena> opomenas) {
        this.opomenas = opomenas;
    }

    public Set<Utuzenje> getUtuzenjes() {
        return utuzenjes;
    }

    public Stan utuzenjes(Set<Utuzenje> utuzenjes) {
        this.utuzenjes = utuzenjes;
        return this;
    }

    public Stan addUtuzenje(Utuzenje utuzenje) {
        this.utuzenjes.add(utuzenje);
        utuzenje.setStan(this);
        return this;
    }

    public Stan removeUtuzenje(Utuzenje utuzenje) {
        this.utuzenjes.remove(utuzenje);
        utuzenje.setStan(null);
        return this;
    }

    public void setUtuzenjes(Set<Utuzenje> utuzenjes) {
        this.utuzenjes = utuzenjes;
    }

    public Set<UgovorRate> getUgovorRates() {
        return ugovorRates;
    }

    public Stan ugovorRates(Set<UgovorRate> ugovorRates) {
        this.ugovorRates = ugovorRates;
        return this;
    }

    public Stan addUgovorRate(UgovorRate ugovorRate) {
        this.ugovorRates.add(ugovorRate);
        ugovorRate.setStan(this);
        return this;
    }

    public Stan removeUgovorRate(UgovorRate ugovorRate) {
        this.ugovorRates.remove(ugovorRate);
        ugovorRate.setStan(null);
        return this;
    }

    public void setUgovorRates(Set<UgovorRate> ugovorRates) {
        this.ugovorRates = ugovorRates;
    }

    public Set<Transakcija> getTransakcijas() {
        return transakcijas;
    }

    public Stan transakcijas(Set<Transakcija> transakcijas) {
        this.transakcijas = transakcijas;
        return this;
    }

    public Stan addTransakcija(Transakcija transakcija) {
        this.transakcijas.add(transakcija);
        transakcija.setStan(this);
        return this;
    }

    public Stan removeTransakcija(Transakcija transakcija) {
        this.transakcijas.remove(transakcija);
        transakcija.setStan(null);
        return this;
    }

    public void setTransakcijas(Set<Transakcija> transakcijas) {
        this.transakcijas = transakcijas;
    }

    public Set<TransakcijaStara> getTransakcijaStaras() {
        return transakcijaStaras;
    }

    public Stan transakcijaStaras(Set<TransakcijaStara> transakcijaStaras) {
        this.transakcijaStaras = transakcijaStaras;
        return this;
    }

    public Stan addTransakcijaStara(TransakcijaStara transakcijaStara) {
        this.transakcijaStaras.add(transakcijaStara);
        transakcijaStara.setStan(this);
        return this;
    }

    public Stan removeTransakcijaStara(TransakcijaStara transakcijaStara) {
        this.transakcijaStaras.remove(transakcijaStara);
        transakcijaStara.setStan(null);
        return this;
    }

    public void setTransakcijaStaras(Set<TransakcijaStara> transakcijaStaras) {
        this.transakcijaStaras = transakcijaStaras;
    }

    public Set<Racun> getRacuns() {
        return racuns;
    }

    public Stan racuns(Set<Racun> racuns) {
        this.racuns = racuns;
        return this;
    }

    public Stan addRacun(Racun racun) {
        this.racuns.add(racun);
        racun.setStan(this);
        return this;
    }

    public Stan removeRacun(Racun racun) {
        this.racuns.remove(racun);
        racun.setStan(null);
        return this;
    }

    public void setRacuns(Set<Racun> racuns) {
        this.racuns = racuns;
    }

    public TipPotrosaca getTipPotrosaca() {
        return tipPotrosaca;
    }

    public Stan tipPotrosaca(TipPotrosaca tipPotrosaca) {
        this.tipPotrosaca = tipPotrosaca;
        return this;
    }

    public void setTipPotrosaca(TipPotrosaca tipPotrosaca) {
        this.tipPotrosaca = tipPotrosaca;
    }

    public Podstanica getPodstanica() {
        return podstanica;
    }

    public Stan podstanica(Podstanica podstanica) {
        this.podstanica = podstanica;
        return this;
    }

    public void setPodstanica(Podstanica podstanica) {
        this.podstanica = podstanica;
    }

    public Vlasnik getVlasnik() {
        return vlasnik;
    }

    public Stan vlasnik(Vlasnik vlasnik) {
        this.vlasnik = vlasnik;
        return this;
    }

    public void setVlasnik(Vlasnik vlasnik) {
        this.vlasnik = vlasnik;
    }
					  
	public String getUkljucenKaoString() {
    	if(this.ukljucen)
    		return "0";
    	else 
    		return "1";
    }
    
    
    
    public OstaliRacuni getOstaliRacuni() {
		return ostaliRacuni;
	}

	public void setOstaliRacuni(OstaliRacuni ostaliRacuni) {
		this.ostaliRacuni = ostaliRacuni;
	}

	public String getStatus() {
    	String out = "" + this.podstanica.getBroj() + this.getUkljucenKaoString();
    	return out;
    }									  
			 
									 
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stan)) {
            return false;
        }
        return id != null && id.equals(((Stan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stan{" +
            "id=" + getId() +
            ", povrsina=" + getPovrsina() +
            ", ulica='" + getUlica() + "'" +
            ", ulaz=" + getUlaz() +
            ", broj=" + getBroj() +
            ", ukljucen='" + isUkljucen() + "'" +
            ", sifra='" + getSifra() + "'" +
            ", grad='" + getGrad() + "'" +
            ", postanskiBroj='" + getPostanskiBroj() + "'" +
            ", brojMerila='" + getBrojMerila() + "'" +
            "}";
    }
}
