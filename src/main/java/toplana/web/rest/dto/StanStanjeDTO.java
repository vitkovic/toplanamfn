package toplana.web.rest.dto;

import java.time.LocalDate;

import toplana.domain.Stan;

public class StanStanjeDTO {
    private String sifra;
    private LocalDate datum;
    private Long vrednost;
    private Stan stan;

    public StanStanjeDTO(String sifra, LocalDate datum, Long vrednost) {
        this.sifra = sifra;
        this.datum = datum;
        this.vrednost = vrednost;
    }
    
    public StanStanjeDTO(String sifra, LocalDate datum, Long vrednost, Stan stan) {
        this.sifra = sifra;
        this.datum = datum;
        this.vrednost = vrednost;
        this.stan = stan;
    }

    // Getters
    public String getSifra() { return sifra; }
    public Stan getStan() {
		return stan;
	}

	public void setStan(Stan stan) {
		this.stan = stan;
	}

	public LocalDate getDatum() { return datum; }
    public Long getVrednost() { return vrednost; }

    @Override
    public String toString() {
        return sifra + "=" + vrednost + " @ " + datum;
    }
}
