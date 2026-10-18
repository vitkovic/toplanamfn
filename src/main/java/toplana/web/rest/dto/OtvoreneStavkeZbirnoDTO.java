package toplana.web.rest.dto;

import java.util.ArrayList;
import java.util.List;

/** Dodatni podaci iskljucivo za odgovor otvorenih stavki. */
public class OtvoreneStavkeZbirnoDTO extends TransakcijeZaStanZbirnoDTO {

    private List<PregledUplateDTO> pregledUplata = new ArrayList<>();

    public List<PregledUplateDTO> getPregledUplata() {
        return pregledUplata;
    }

    public void setPregledUplata(List<PregledUplateDTO> pregledUplata) {
        this.pregledUplata = pregledUplata;
    }
}
