package toplana.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "stavke_izvoda_postanska")
public class StavkeIzvodaPostanska implements Serializable {
	 
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;
	
	@Column(name = "rasporedjena")
	private Boolean rasporedjena;
	
	@Column(name = "broj")
	private Integer broj;
	
	@Column(name = "valuta")
	private LocalDate valuta;
	
	@Column(name = "npl")// da li je placeno ili ne - ako je null onda je placeno
	private String npl;
	
	@Column(name = "broj_tekuceg_racuna")
	private String brojTekucegRacuna;
	
	@Column(name = "broj_partije_poverioc")// maticni broj stana - sifra
	private String brojPartijePoverioc;
	
	@Column(name = "ime")
	private String ime;
	
	@Column(name = "adresa")
	private String adresa;
	
	@Column(name = "posta")
	private String posta;
	
	@Column(name = "iznos", precision = 21, scale = 2)
    private BigDecimal iznos;
	
	@OneToOne
    @JoinColumn(unique = true)
    private Transakcija transakcija;

    @OneToOne
    @JoinColumn(unique = true)
    private TransakcijaStara transakcijaStara;

    @ManyToOne
    @JsonIgnoreProperties(value = "stavkeIzvodaPostanska", allowSetters = true)
    private Izvod izvod;

	public StavkeIzvodaPostanska() {
		super();
	}
	
	public StavkeIzvodaPostanska(String line) {
//1      27.04.23     1016970 0010470009            STRAHINIÆ SVETISLAV         BULEVAR NIKOLE TESLE 47/9   18104          9393,30
	
		String sBroj = line.substring(0,5).trim();
		String npl = line.substring(5,11).trim();
		String sValuta = line.substring(11,19).trim();
		String partijaRacuna = line.substring(19,31).trim();
		String sifra = line.substring(31,42).trim();
		String ime = line.substring(42,82).trim().replace("Æ", "Ć");
		ime = ime.replace("È", "Č");
		String adresa = line.substring(82,108).trim();
		String posta = line.substring(108,116).trim();
		String sIznos = line.substring(116).trim().replace(",", ".");
	/*	
		System.out.println("ЋЧШЂЖ");
		System.out.println(sBroj);
		System.out.println(npl);
		System.out.println(sValuta);
		System.out.println(partijaRacuna);
		System.out.println(sifra);
		System.out.println(ime);
		System.out.println(adresa);
		System.out.println(posta);
		System.out.println(sIznos);
		System.out.println("****************************************************************************");
		*/
		this.broj = Integer.parseInt(sBroj);
		if(npl != null && !npl.isEmpty())
			this.npl = npl;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
		  //convert String to LocalDate		
		this.valuta = LocalDate.parse(sValuta, formatter);
		this.brojTekucegRacuna = partijaRacuna;
		this.brojPartijePoverioc = sifra;
		this.ime = ime;
		this.adresa = adresa;
		this.posta = posta;		
		this.iznos = new BigDecimal(sIznos);		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public String getNpl() {
		return npl;
	}

	public void setNpl(String npl) {
		this.npl = npl;
	}

	public Boolean getRasporedjena() {
		return rasporedjena;
	}

	public void setRasporedjena(Boolean rasporedjena) {
		this.rasporedjena = rasporedjena;
	}

	public Integer getBroj() {
		return broj;
	}

	public void setBroj(Integer broj) {
		this.broj = broj;
	}

	public LocalDate getValuta() {
		return valuta;
	}

	public void setValuta(LocalDate valuta) {
		this.valuta = valuta;
	}

	public String getBrojTekucegRacuna() {
		return brojTekucegRacuna;
	}

	public void setBrojTekucegRacuna(String brojTekucegRacuna) {
		this.brojTekucegRacuna = brojTekucegRacuna;
	}

	public String getBrojPartijePoverioc() {
		return brojPartijePoverioc;
	}

	public void setBrojPartijePoverioc(String brojPartijePoverioc) {
		this.brojPartijePoverioc = brojPartijePoverioc;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getPosta() {
		return posta;
	}

	public void setPosta(String posta) {
		this.posta = posta;
	}

	public BigDecimal getIznos() {
		return iznos;
	}

	public void setIznos(BigDecimal iznos) {
		this.iznos = iznos;
	}

	public Transakcija getTransakcija() {
		return transakcija;
	}

	public void setTransakcija(Transakcija transakcija) {
		this.transakcija = transakcija;
	}

	public TransakcijaStara getTransakcijaStara() {
		return transakcijaStara;
	}

	public void setTransakcijaStara(TransakcijaStara transakcijaStara) {
		this.transakcijaStara = transakcijaStara;
	}

	public Izvod getIzvod() {
		return izvod;
	}

	public void setIzvod(Izvod izvod) {
		this.izvod = izvod;
	}
	
	
	
	
}
