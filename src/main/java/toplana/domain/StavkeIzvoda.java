package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import toplana.web.rest.dto.IzvodStavkaDTO;
import toplana.web.rest.dto.StavkeIzvodaTransakcijaDTO;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A StavkeIzvoda.
 */
@Entity
@Table(name = "stavke_izvoda")
public class StavkeIzvoda implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rasporedjena")
    private Boolean rasporedjena;
    
    @Column(name = "status")
    private String status;

    @Column(name = "iznos", precision = 21, scale = 2)
    private BigDecimal iznos;

    @Column(name = "opis")
    private String opis;
    
    @Column(name = "racun_zaduzenja")
	private String racunZaduzenja;
	
    @Column(name = "naziv_zaduzenja")
	private String nazivZaduzenja;
	
    @Column(name = "mesto_zaduzenja")
	private String mestoZaduzenja;
	
    @Column(name = "izvor_informacija")
	private String izvorInformacije;
	
    @Column(name = "model_poziva_zaduzenja")
	private String modelPozivaZaduzenja;
	
    @Column(name = "poziv_zaduzenja")
	private String pozivZaduzenja;
	
    @Column(name = "sifra_placanja")
	private String sifraPlacanja;
	
    @Column(name = "racun_odobrenja")
	private String racunOdobrenja;
	
    @Column(name = "naziv_odobrenja")
	private String nazivOdobrenja;
	
    @Column(name = "mesto_odobrenja")
	private String mestoOdobrenja;
	
    @Column(name = "model_poziva_odobrenja")
	private String modelPozivaOdobrenja;
	
    @Column(name = "poziv_odobrenja")
	private String pozivOdobrenja;//sifra stana
	
    @Column(name = "svrha_doznake")
	private String svrhaDoznake;
	
    @Column(name = "podatak_za_reklamaciju")
	private String podatakZaReklamaciju;
	
    @Column(name = "datum_valute")
	private LocalDate datumValute;
	
    @Column(name = "naziv_obracuna")
	private String nacinObracuna;
	
    @Column(name = "prioritet_naloga")
	private String prioritetNaloga;
	
    @Column(name = "vreme_unosa")
	private String vremeUnosa;
	
    @Column(name = "vreme_izvrsenja")
	private String vremeIzvrsenja;
	
    @Column(name = "status_naloga")
	private String statusNaloga;
	
    @Column(name = "tip_sloga")
	private String tipSloga;
    
    @OneToOne
    @JoinColumn(unique = true)
    private Transakcija transakcija;

    @OneToOne
    @JoinColumn(unique = true)
    private TransakcijaStara transakcijaStara;

    @ManyToOne
    @JsonIgnoreProperties(value = "stavkaIzvodas", allowSetters = true)
    private Izvod izvod;

    
    
    public StavkeIzvoda() {
		super();
	}
    
   
    
    public StavkeIzvoda(IzvodStavkaDTO u) {
    	
    	this.rasporedjena = false;
    	this.status = null;
    	this.iznos = new BigDecimal(u.getIznos());
    	this.opis = null;
    	this.racunZaduzenja = u.getRacunZaduzenja();
    	this.nazivZaduzenja = u.getNazivZaduzenja();
    	this.mestoZaduzenja = u.getMestoZaduzenja();
    	this.izvorInformacije = u.getIzvorInformacije();
    	this.modelPozivaZaduzenja = u.getModelPozivaZaduzenja();
    	this.pozivZaduzenja = u.getPozivZaduzenja();
    	this.sifraPlacanja = u.getSifraPlacanja();
    	this.racunOdobrenja = u.getRacunOdobrenja();
    	this.nazivOdobrenja = u.getNazivOdobrenja();
    	this.mestoOdobrenja = u.getMestoOdobrenja();
    	this.modelPozivaOdobrenja = u.getModelPozivaOdobrenja();
    	
    	if(u.getPozivOdobrenja() != null) {
	    	this.pozivOdobrenja = u.getPozivOdobrenja().trim();//sifra stana
	    	this.pozivOdobrenja = this.pozivOdobrenja.replaceAll("\\s+", "");
	    	this.pozivOdobrenja = this.pozivOdobrenja.replaceAll("\\.", "");
    	}

    	
    	this.svrhaDoznake = u.getSvrhaDoznake();
    	this.podatakZaReklamaciju = u.getPodatakZaReklamaciju();
    		
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");   	  
    	this.datumValute = LocalDate.parse(u.getDatumValute(), formatter);
    		
    	this.nacinObracuna = u.getNacinObracuna();
    	this.prioritetNaloga = u.getPrioritetNaloga();
    	this.vremeUnosa = u.getVremeUnosa();
    	this.vremeIzvrsenja = u.getVremeIzvrsenja();
    	this.statusNaloga = u.getStatusNaloga();
    	this.tipSloga = u.getTipSloga();
    }

	// jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isRasporedjena() {
        return rasporedjena;
    }

    public StavkeIzvoda rasporedjena(Boolean rasporedjena) {
        this.rasporedjena = rasporedjena;
        return this;
    }

    public void setRasporedjena(Boolean rasporedjena) {
        this.rasporedjena = rasporedjena;
    }

  

    public String getStatus() {
        return status;
    }

    public StavkeIzvoda status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public StavkeIzvoda iznos(BigDecimal iznos) {
        this.iznos = iznos;
        return this;
    }

    public void setIznos(BigDecimal iznos) {
        this.iznos = iznos;
    }

   

    public String getOpis() {
        return opis;
    }

    public StavkeIzvoda opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Transakcija getTransakcija() {
        return transakcija;
    }

    public StavkeIzvoda transakcija(Transakcija transakcija) {
        this.transakcija = transakcija;
        return this;
    }

    public void setTransakcija(Transakcija transakcija) {
        this.transakcija = transakcija;
    }

    public TransakcijaStara getTransakcijaStara() {
        return transakcijaStara;
    }

    public StavkeIzvoda transakcijaStara(TransakcijaStara transakcijaStara) {
        this.transakcijaStara = transakcijaStara;
        return this;
    }

    public void setTransakcijaStara(TransakcijaStara transakcijaStara) {
        this.transakcijaStara = transakcijaStara;
    }

    public Izvod getIzvod() {
        return izvod;
    }

    public StavkeIzvoda izvod(Izvod izvod) {
        this.izvod = izvod;
        return this;
    }

    public void setIzvod(Izvod izvod) {
        this.izvod = izvod;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

   





	public Boolean getRasporedjena() {
		return rasporedjena;
	}

	public String getRacunZaduzenja() {
		return racunZaduzenja;
	}

	public void setRacunZaduzenja(String racunZaduzenja) {
		this.racunZaduzenja = racunZaduzenja;
	}

	public String getNazivZaduzenja() {
		return nazivZaduzenja;
	}

	public void setNazivZaduzenja(String nazivZaduzenja) {
		this.nazivZaduzenja = nazivZaduzenja;
	}

	public String getMestoZaduzenja() {
		return mestoZaduzenja;
	}

	public void setMestoZaduzenja(String mestoZaduzenja) {
		this.mestoZaduzenja = mestoZaduzenja;
	}

	public String getIzvorInformacije() {
		return izvorInformacije;
	}

	public void setIzvorInformacije(String izvorInformacije) {
		this.izvorInformacije = izvorInformacije;
	}

	public String getModelPozivaZaduzenja() {
		return modelPozivaZaduzenja;
	}

	public void setModelPozivaZaduzenja(String modelPozivaZaduzenja) {
		this.modelPozivaZaduzenja = modelPozivaZaduzenja;
	}

	public String getPozivZaduzenja() {
		return pozivZaduzenja;
	}

	public void setPozivZaduzenja(String pozivZaduzenja) {
		this.pozivZaduzenja = pozivZaduzenja;
	}

	public String getSifraPlacanja() {
		return sifraPlacanja;
	}

	public void setSifraPlacanja(String sifraPlacanja) {
		this.sifraPlacanja = sifraPlacanja;
	}

	public String getRacunOdobrenja() {
		return racunOdobrenja;
	}

	public void setRacunOdobrenja(String racunOdobrenja) {
		this.racunOdobrenja = racunOdobrenja;
	}

	public String getNazivOdobrenja() {
		return nazivOdobrenja;
	}

	public void setNazivOdobrenja(String nazivOdobrenja) {
		this.nazivOdobrenja = nazivOdobrenja;
	}

	public String getMestoOdobrenja() {
		return mestoOdobrenja;
	}

	public void setMestoOdobrenja(String mestoOdobrenja) {
		this.mestoOdobrenja = mestoOdobrenja;
	}

	public String getModelPozivaOdobrenja() {
		return modelPozivaOdobrenja;
	}

	public void setModelPozivaOdobrenja(String modelPozivaOdobrenja) {
		this.modelPozivaOdobrenja = modelPozivaOdobrenja;
	}

	public String getPozivOdobrenja() {
		return pozivOdobrenja;
	}

	public void setPozivOdobrenja(String pozivOdobrenja) {
		this.pozivOdobrenja = pozivOdobrenja;
	}

	public String getSvrhaDoznake() {
		return svrhaDoznake;
	}

	public void setSvrhaDoznake(String svrhaDoznake) {
		this.svrhaDoznake = svrhaDoznake;
	}

	public String getPodatakZaReklamaciju() {
		return podatakZaReklamaciju;
	}

	public void setPodatakZaReklamaciju(String podatakZaReklamaciju) {
		this.podatakZaReklamaciju = podatakZaReklamaciju;
	}

	public LocalDate getDatumValute() {
		return datumValute;
	}

	public void setDatumValute(LocalDate datumValute) {
		this.datumValute = datumValute;
	}

	public String getNacinObracuna() {
		return nacinObracuna;
	}

	public void setNacinObracuna(String nacinObracuna) {
		this.nacinObracuna = nacinObracuna;
	}

	public String getPrioritetNaloga() {
		return prioritetNaloga;
	}

	public void setPrioritetNaloga(String prioritetNaloga) {
		this.prioritetNaloga = prioritetNaloga;
	}

	public String getVremeUnosa() {
		return vremeUnosa;
	}

	public void setVremeUnosa(String vremeUnosa) {
		this.vremeUnosa = vremeUnosa;
	}

	public String getVremeIzvrsenja() {
		return vremeIzvrsenja;
	}

	public void setVremeIzvrsenja(String vremeIzvrsenja) {
		this.vremeIzvrsenja = vremeIzvrsenja;
	}

	public String getStatusNaloga() {
		return statusNaloga;
	}

	public void setStatusNaloga(String statusNaloga) {
		this.statusNaloga = statusNaloga;
	}

	public String getTipSloga() {
		return tipSloga;
	}

	public void setTipSloga(String tipSloga) {
		this.tipSloga = tipSloga;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StavkeIzvoda)) {
            return false;
        }
        return id != null && id.equals(((StavkeIzvoda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

   
}
