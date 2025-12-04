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
//00000000000001700000000025352410470009                           SVETISLAV (MILIVOJE) STRAHINIC010470009           311020252711202500000000000000000009085.69941  27112025 - Nova linija
	/*
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
		
		
		// 00000000000001700000000025352410470009                           SVETISLAV (MILIVOJE) STRAHINIC010470009           311020252711202500000000000000000009085.69941  27112025

		// Leva numerička polja (interni brojevi, partija itd.)
		String partijaRacuna = line.substring(0, 40).trim();        // "0000000000000170000000002"
		//String polje2 = line.substring(25, 35).trim();       // "5352410470"  (verovatno partija ili sličan ID)
		//String polje3 = line.substring(35, 40).trim();       // "009"        (nastavak koda)

		// Ime i prezime
		String ime = line.substring(65, 95).trim()
		        .replace("Æ", "Ć")
		        .replace("È", "Č");                          // "SVETISLAV (MILIVOJE) STRAHINIC"

		// Šifra (010470009)
		String sifra = line.substring(95, 104).trim();       // "010470009"

		// Datumi
		String sValuta = line.substring(115, 123).trim();    // "31102025"
		//String datumDo = line.substring(123, 131).trim();    // "27112025"

		// Iznos (bez vodećim nulama)
		String sIznos = line.substring(131, 160).trim().replace(",", ".");
		sIznos = sIznos.replaceFirst("^0+(?!$)", "");   // uklanja sve vodeće nule

		// Još jedan datum na kraju (npr. datum dospeća)
		String datumKraja = line.substring(160).trim();      // "27112025"
		
		//this.broj = Integer.parseInt(sBroj);
		if(npl != null && !npl.isEmpty())
			this.npl = npl;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
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
