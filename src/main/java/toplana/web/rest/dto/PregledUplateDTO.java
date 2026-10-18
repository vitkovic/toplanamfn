package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Samo prikaz: pregled gde je rasporedjena uplata ili korekcija. */
public class PregledUplateDTO {
    private final Long transakcijaId;
    private final LocalDate datum;
    private final String sifraDokumenta;
    private final String opis;
    private final BigDecimal originalniIznos;
    private final String vrsta;
    private BigDecimal nerasporedjeno = BigDecimal.ZERO;
    private final List<CiljRaspodeleDTO> raspodele = new ArrayList<>();

    public PregledUplateDTO(Long id, LocalDate datum, String dokument, String opis,
                            BigDecimal iznos, String vrsta) {
        this.transakcijaId = id;
        this.datum = datum;
        this.sifraDokumenta = dokument;
        this.opis = opis;
        this.originalniIznos = iznos;
        this.vrsta = vrsta;
    }
    public void dodajRaspodelu(Long racunId, String racun, LocalDate datum, BigDecimal iznos) {
        raspodele.add(new CiljRaspodeleDTO(racunId, racun, datum, iznos));
    }
    public Long getTransakcijaId() { return transakcijaId; }
    public LocalDate getDatum() { return datum; }
    public String getSifraDokumenta() { return sifraDokumenta; }
    public String getOpis() { return opis; }
    public BigDecimal getOriginalniIznos() { return originalniIznos; }
    public String getVrsta() { return vrsta; }
    public BigDecimal getNerasporedjeno() { return nerasporedjeno; }
    public void setNerasporedjeno(BigDecimal v) { nerasporedjeno = v; }
    public BigDecimal getRasporedjeno() { return originalniIznos.subtract(nerasporedjeno); }
    public List<CiljRaspodeleDTO> getRaspodele() { return raspodele; }

    public static class CiljRaspodeleDTO {
        private final Long racunId;
        private final String racun;
        private final LocalDate datumRacuna;
        private final BigDecimal iznos;
        public CiljRaspodeleDTO(Long id, String racun, LocalDate datum, BigDecimal iznos) {
            this.racunId = id; this.racun = racun; this.datumRacuna = datum; this.iznos = iznos;
        }
        public Long getRacunId() { return racunId; }
        public String getRacun() { return racun; }
        public LocalDate getDatumRacuna() { return datumRacuna; }
        public BigDecimal getIznos() { return iznos; }
    }
}
