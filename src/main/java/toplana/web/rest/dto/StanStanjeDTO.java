package toplana.web.rest.dto;

import java.time.LocalDate;

public class StanStanjeDTO {
    private String sifra;
    private LocalDate datum;
    private Long vrednost;

    public StanStanjeDTO(String sifra, LocalDate datum, Long vrednost) {
        this.sifra = sifra;
        this.datum = datum;
        this.vrednost = vrednost;
    }

    // Getters
    public String getSifra() { return sifra; }
    public LocalDate getDatum() { return datum; }
    public Long getVrednost() { return vrednost; }

    @Override
    public String toString() {
        return sifra + "=" + vrednost + " @ " + datum;
    }
}
