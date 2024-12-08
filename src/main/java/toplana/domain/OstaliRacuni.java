package toplana.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ostali_racuni")
public class OstaliRacuni {
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "naziv")
    private String naziv;
    
    @Column(name = "sifra", unique = true)
    private String sifra;
    
    @Column(name = "aktivan")
    private Boolean aktivan; //false nije aktivan, true aktivan
    
    @OneToMany(mappedBy = "ostaliRacuni")
    private Set<Transakcija> transakcijas = new HashSet<>();

    @OneToMany(mappedBy = "ostaliRacuni")
    private Set<TransakcijaStara> transakcijaStaras = new HashSet<>();
    
    @OneToOne
    @JoinColumn(unique = true)
    private Stan stan;

	public Set<Transakcija> getTransakcijas() {
		return transakcijas;
	}

	public void setTransakcijas(Set<Transakcija> transakcijas) {
		this.transakcijas = transakcijas;
	}

	public Set<TransakcijaStara> getTransakcijaStaras() {
		return transakcijaStaras;
	}

	public void setTransakcijaStaras(Set<TransakcijaStara> transakcijaStaras) {
		this.transakcijaStaras = transakcijaStaras;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public Boolean getAktivan() {
		return aktivan;
	}

	public void setAktivan(Boolean aktivan) {
		this.aktivan = aktivan;
	}

	public Stan getStan() {
		return stan;
	}

	public void setStan(Stan stan) {
		this.stan = stan;
	}
    
    


}
