package toplana.web.rest.dto;

import java.math.BigDecimal;

public class StanSaldoDTO {

    private Long stanId;
    private String sifra;
    private String ime;
    private String prezime;
    private String partija;
    private BigDecimal saldo;

    public StanSaldoDTO(
        Long stanId,
        String sifra,
        String ime,
        String prezime,
        BigDecimal saldo
    ) {
        this.stanId = stanId;
        this.sifra = sifra;
        this.ime = ime;
        this.prezime = prezime;
        this.saldo = saldo;
    }

	public Long getStanId() {
		return stanId;
	}

	public void setStanId(Long stanId) {
		this.stanId = stanId;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getPartija() {
		return partija;
	}

	public void setPartija(String partija) {
		this.partija = partija;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

    
    
    
    // getters
}