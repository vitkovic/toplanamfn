package toplana.domain;


import javax.persistence.*;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import toplana.web.rest.dto.IzvodDTO;
import toplana.web.rest.dto.IzvodStavkaDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A Izvod.
 */
@Entity
@Table(name = "izvod")
public class Izvod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    @Column(name = "sediste")
	private String sediste;
	
    @Column(name = "naziv_sedista")
	private String nazivSedista;
	
    @Column(name = "daatum_izvoda_zaglavlje")
	private LocalDate datumIzvodaZaglavlje;
	
    @Column(name = "tip_sloga_zaglavlje")
	private String tipSlogaZaglavlje;
	
	//zbirni
    @Column(name = "racun_izvoda")
	private String racunIzvoda;
	
    @Column(name = "naziv")
	private String naziv;
	
    @Column(name = "mesto")
	private String mesto;
	
    @Column(name = "br_naloga_duguje")
	private Integer brNalogaDuguje;
	
    @Column(name = "br_naloga_potrazuje")
	private Integer brNalogaPotrazuje;
	
	@Column(name = "kumulativno_duguje", precision = 21, scale = 2)
	private BigDecimal kumulativnoDuguje;
	
	@Column(name = "kumulativno_potrazuje", precision = 21, scale = 2)
	private BigDecimal kumulativnoPotrazuje;
	
	@Column(name = "prethodni_saldo", precision = 21, scale = 2)
	private BigDecimal prethodniSaldo;
	
	@Column(name = "iznos_duguje", precision = 21, scale = 2)
	private BigDecimal iznosDuguje;
	
	@Column(name = "iznos_potrazuje", precision = 21, scale = 2)
	private BigDecimal iznosPotrazuje;
	
	@Column(name = "saldo", precision = 21, scale = 2)
	private BigDecimal saldo;
	
	@Column(name = "datum_izvoda_zbirni")
	private LocalDate datumIzvodaZbirni;
	
	@Column(name = "broj_izvoda")
	private Integer brojIzvoda;
	
	@Column(name = "rbr_obrada")
	private Integer rbrObrada;
	
	@Column(name = "tip_sloga_zbirni")
	private String tipSlogaZbirni;
	
	@Column(name = "tip")//ako je klasican onda nista, moze da bude postanska ili u buducnosti jos nesto
	private String tip;
        
	@Column(name = "ukupno_za_uslugu", precision = 21, scale = 2)// kod postanske stedionie
	private BigDecimal ukupnoZaUslugu;
	
	@Column(name = "ukupno_neplacenih", precision = 21, scale = 2)
	private BigDecimal ukupnoNeplacenih;
	
	@Column(name = "ukupno_placenih", precision = 21, scale = 2)
	private BigDecimal ukupnoPlacenih;

    @OneToMany(mappedBy = "izvod")
    private List<StavkeIzvoda> stavkaIzvodas = new ArrayList<>();
    
    @OneToMany(mappedBy = "izvod")
    private List<StavkeIzvodaPostanska> stavkeIzvodaPostanska = new ArrayList<>();
    
    
    

    public Izvod() {
		super();
	}
    
    public Izvod(IzvodDTO u) {
    	 
    	this.sediste = u.getZaglavlje().getSediste();
    	this.nazivSedista = u.getZaglavlje().getNazivSedista();
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");   	  
    	this.datumIzvodaZaglavlje = LocalDate.parse(u.getZaglavlje().getDatumIzvoda(), formatter);
    		
    	this.tipSlogaZaglavlje = u.getZaglavlje().getTipSloga();
    		
   		//zbirni

    	this.racunIzvoda = u.getZbirni().getRacunIzvoda();
    	this.naziv = u.getZbirni().getNaziv();
    	this.mesto = u.getZbirni().getMesto();
    	this.brNalogaDuguje = u.getZbirni().getBrNalogaDuguje();    		
    	this.brNalogaPotrazuje = u.getZbirni().getBrNalogaPotrazuje();
    		
    	this.kumulativnoDuguje = new BigDecimal(u.getZbirni().getKumulativnoDuguje());
    	this.kumulativnoPotrazuje= new BigDecimal(u.getZbirni().getKumulativnoPotrazuje());
    	this.prethodniSaldo = new BigDecimal(u.getZbirni().getPrethodniSaldo());
    	this.iznosDuguje = new BigDecimal(u.getZbirni().getIznosDuguje());    		
    	this.iznosPotrazuje = new BigDecimal(u.getZbirni().getIznosPotrazuje());
    	this.saldo = new BigDecimal(u.getZbirni().getSaldo());
    	this.datumIzvodaZbirni = LocalDate.parse(u.getZbirni().getDatumIzvoda(), formatter);
    		
    	this.brojIzvoda = u.getZbirni().getBrojIzvoda();
    	this.rbrObrada = u.getZbirni().getRbrObrada();
    	this.tipSlogaZbirni = u.getZbirni().getTipSloga();    	
    	        
    	for(IzvodStavkaDTO s : u.getStavka()) {
    		StavkeIzvoda si = new StavkeIzvoda(s);
    		stavkaIzvodas.add(si);
    	}
    	
    }

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

   
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public BigDecimal getUkupnoZaUslugu() {
		return ukupnoZaUslugu;
	}

	public void setUkupnoZaUslugu(BigDecimal ukupnoZaUslugu) {
		this.ukupnoZaUslugu = ukupnoZaUslugu;
	}

	public BigDecimal getUkupnoNeplacenih() {
		return ukupnoNeplacenih;
	}

	public void setUkupnoNeplacenih(BigDecimal ukupnoNeplacenih) {
		this.ukupnoNeplacenih = ukupnoNeplacenih;
	}

	public BigDecimal getUkupnoPlacenih() {
		return ukupnoPlacenih;
	}

	public void setUkupnoPlacenih(BigDecimal ukupnoPlacenih) {
		this.ukupnoPlacenih = ukupnoPlacenih;
	}

	public List<StavkeIzvodaPostanska> getStavkeIzvodaPostanska() {
		return stavkeIzvodaPostanska;
	}

	public void setStavkeIzvodaPostanska(List<StavkeIzvodaPostanska> stavkeIzvodaPostanska) {
		this.stavkeIzvodaPostanska = stavkeIzvodaPostanska;
	}

	public String getSediste() {
		return sediste;
	}

	public void setSediste(String sediste) {
		this.sediste = sediste;
	}

	public String getNazivSedista() {
		return nazivSedista;
	}

	public void setNazivSedista(String nazivSedista) {
		this.nazivSedista = nazivSedista;
	}

	public LocalDate getDatumIzvodaZaglavlje() {
		return datumIzvodaZaglavlje;
	}

	public void setDatumIzvodaZaglavlje(LocalDate datumIzvodaZaglavlje) {
		this.datumIzvodaZaglavlje = datumIzvodaZaglavlje;
	}

	public String getTipSlogaZaglavlje() {
		return tipSlogaZaglavlje;
	}

	public void setTipSlogaZaglavlje(String tipSlogaZaglavlje) {
		this.tipSlogaZaglavlje = tipSlogaZaglavlje;
	}

	public String getRacunIzvoda() {
		return racunIzvoda;
	}

	public void setRacunIzvoda(String racunIzvoda) {
		this.racunIzvoda = racunIzvoda;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}

	public Integer getBrNalogaDuguje() {
		return brNalogaDuguje;
	}

	public void setBrNalogaDuguje(Integer brNalogaDuguje) {
		this.brNalogaDuguje = brNalogaDuguje;
	}

	public Integer getBrNalogaPotrazuje() {
		return brNalogaPotrazuje;
	}

	public void setBrNalogaPotrazuje(Integer brNalogaPotrazuje) {
		this.brNalogaPotrazuje = brNalogaPotrazuje;
	}

	public BigDecimal getKumulativnoDuguje() {
		return kumulativnoDuguje;
	}

	public void setKumulativnoDuguje(BigDecimal kumulativnoDuguje) {
		this.kumulativnoDuguje = kumulativnoDuguje;
	}

	public BigDecimal getKumulativnoPotrazuje() {
		return kumulativnoPotrazuje;
	}

	public void setKumulativnoPotrazuje(BigDecimal kumulativnoPotrazuje) {
		this.kumulativnoPotrazuje = kumulativnoPotrazuje;
	}

	public BigDecimal getPrethodniSaldo() {
		return prethodniSaldo;
	}

	public void setPrethodniSaldo(BigDecimal prethodniSaldo) {
		this.prethodniSaldo = prethodniSaldo;
	}

	public BigDecimal getIznosDuguje() {
		return iznosDuguje;
	}

	public void setIznosDuguje(BigDecimal iznosDuguje) {
		this.iznosDuguje = iznosDuguje;
	}

	public BigDecimal getIznosPotrazuje() {
		return iznosPotrazuje;
	}

	public void setIznosPotrazuje(BigDecimal iznosPotrazuje) {
		this.iznosPotrazuje = iznosPotrazuje;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public LocalDate getDatumIzvodaZbirni() {
		return datumIzvodaZbirni;
	}

	public void setDatumIzvodaZbirni(LocalDate datumIzvodaZbirni) {
		this.datumIzvodaZbirni = datumIzvodaZbirni;
	}

	public Integer getBrojIzvoda() {
		return brojIzvoda;
	}

	public void setBrojIzvoda(Integer brojIzvoda) {
		this.brojIzvoda = brojIzvoda;
	}

	public Integer getRbrObrada() {
		return rbrObrada;
	}

	public void setRbrObrada(Integer rbrObrada) {
		this.rbrObrada = rbrObrada;
	}

	public String getTipSlogaZbirni() {
		return tipSlogaZbirni;
	}

	public void setTipSlogaZbirni(String tipSlogaZbirni) {
		this.tipSlogaZbirni = tipSlogaZbirni;
	}

	

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public List<StavkeIzvoda> getStavkaIzvodas() {
		return stavkaIzvodas;
	}

	public void setStavkaIzvodas(List<StavkeIzvoda> stavkaIzvodas) {
		this.stavkaIzvodas = stavkaIzvodas;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Izvod)) {
            return false;
        }
        return id != null && id.equals(((Izvod) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	@Override
	public String toString() {
		return "Izvod [id=" + id + ", sediste=" + sediste + ", nazivSedista=" + nazivSedista + ", datumIzvodaZaglavlje="
				+ datumIzvodaZaglavlje + ", tipSlogaZaglavlje=" + tipSlogaZaglavlje + ", racunIzvoda=" + racunIzvoda
				+ ", naziv=" + naziv + ", mesto=" + mesto + ", brNalogaDuguje=" + brNalogaDuguje
				+ ", brNalogaPotrazuje=" + brNalogaPotrazuje + ", kumulativnoDuguje=" + kumulativnoDuguje
				+ ", kumulativnoPotrazuje=" + kumulativnoPotrazuje + ", prethodniSaldo=" + prethodniSaldo
				+ ", iznosDuguje=" + iznosDuguje + ", iznosPotrazuje=" + iznosPotrazuje + ", saldo=" + saldo
				+ ", datumIzvodaZbirni=" + datumIzvodaZbirni + ", brojIzvoda=" + brojIzvoda + ", rbrObrada=" + rbrObrada
				+ ", tipSlogaZbirni=" + tipSlogaZbirni + ", stavkaIzvodas=" + stavkaIzvodas + "]";
	}

    // prettier-ignore
  
}
