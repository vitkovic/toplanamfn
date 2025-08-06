package toplana.web.rest.dto;

public class StanVlasnikDTO {

	private String ime;
	private String prezime;
	private String sifra;
	private String email;
	private String ulica;
	private int ulaz;
	
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
	public String getSifra() {
		return sifra;
	}
	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUlica() {
		return ulica;
	}
	public void setUlica(String ulica) {
		this.ulica = ulica;
	}
	public int getUlaz() {
		return ulaz;
	}
	public void setUlaz(int ulaz) {
		this.ulaz = ulaz;
	}
	
	public StanVlasnikDTO(String ime, String prezime, String email, String sifra, String ulica, int ulaz) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.sifra = sifra;
		this.email = email;
		this.ulica = ulica;
		this.ulaz = ulaz;
	}
	public StanVlasnikDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}
