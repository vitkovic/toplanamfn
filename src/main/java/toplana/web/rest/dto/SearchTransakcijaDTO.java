package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import toplana.domain.Podstanica;

public class SearchTransakcijaDTO {
	private LocalDate datumOd;
	private LocalDate datumDo;
    private String sifraStana;  
    private String sifraOd;
    private String sifraDo;
    private String podstanicaOd;
    private String podstanicaDo;
    private boolean ukljucen;    
    private Podstanica podstanica; 
    private String prezime;
    private String ime;
    private String prezimeime = "";
    private BigDecimal saldoOd;
    private BigDecimal saldoDo;
    
    
    
    
    public BigDecimal getSaldoOd() {
		return saldoOd;
	}

	public void setSaldoOd(BigDecimal saldoOd) {
		this.saldoOd = saldoOd;
	}

	public BigDecimal getSaldoDo() {
		return saldoDo;
	}

	public void setSaldoDo(BigDecimal saldoDo) {
		this.saldoDo = saldoDo;
	}

	public String getPrezimeime() {
		return prezimeime;
	}

	public void setPrezimeime(String prezimeime) {
		this.prezimeime = prezimeime;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	private List<Long> reoni;
    
	public SearchTransakcijaDTO() {
		super();
	}

	public String getSifraOd() {
		return sifraOd;
	}

	public void setSifraOd(String sifraOd) {
		this.sifraOd = sifraOd;
	}

	public String getSifraDo() {
		return sifraDo;
	}

	public void setSifraDo(String sifraDo) {
		this.sifraDo = sifraDo;
	}

	public String getPodstanicaOd() {
		return podstanicaOd;
	}

	public void setPodstanicaOd(String podstanicaOd) {
		this.podstanicaOd = podstanicaOd;
	}

	public String getPodstanicaDo() {
		return podstanicaDo;
	}

	public void setPodstanicaDo(String podstanicaDo) {
		this.podstanicaDo = podstanicaDo;
	}

	public LocalDate getDatumOd() {
		return datumOd;
	}

	public void setDatumOd(LocalDate datumOd) {
		this.datumOd = datumOd;
	}

	public LocalDate getDatumDo() {
		return datumDo;
	}

	public void setDatumDo(LocalDate datumDo) {
		this.datumDo = datumDo;
	}

	public String getSifraStana() {
		return sifraStana;
	}

	public void setSifraStana(String sifraStana) {
		this.sifraStana = sifraStana;
	}

	

	public boolean isUkljucen() {
		return ukljucen;
	}

	public void setUkljucen(boolean ukljucen) {
		this.ukljucen = ukljucen;
	}

	public Podstanica getPodstanica() {
		return podstanica;
	}

	public void setPodstanica(Podstanica podstanica) {
		this.podstanica = podstanica;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public List<Long> getReoni() {
		return reoni;
	}

	public void setReoni(List<Long> reoni) {
		this.reoni = reoni;
	}

	

}
