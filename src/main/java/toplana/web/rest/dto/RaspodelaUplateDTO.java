package toplana.web.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Jedan trag raspodele uplate na racun (izracunat, nije knjizenje). */
public class RaspodelaUplateDTO {
    private Long transakcijaId;
    private LocalDate datum;
    private String sifraDokumenta;
    private String opis;
    private BigDecimal iznos;
    public RaspodelaUplateDTO(Long transakcijaId, LocalDate datum, String sifraDokumenta, String opis, BigDecimal iznos) {
        this.transakcijaId = transakcijaId;
        this.datum = datum;
        this.sifraDokumenta = sifraDokumenta;
        this.opis = opis;
        this.iznos = iznos;
    }
    public Long getTransakcijaId() { return transakcijaId; }
    public LocalDate getDatum() { return datum; }
    public String getSifraDokumenta() { return sifraDokumenta; }
    public String getOpis() { return opis; }
    public BigDecimal getIznos() { return iznos; }
}
