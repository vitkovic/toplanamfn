package toplana.service.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProveraDTO {

    private Integer status;
    private BigDecimal cena; 
    // Mapa je static da bi bila dostupna svim instancama klase Racun
    private static final Map<Integer, BigDecimal> CENA_ODRZAVANJA_PO_ULAZU = new HashMap<>();

    /**
     * Konstruktor koji prima cenu i mapira je na sve definisane ulaze.
     * @param cenaOdrzavanja Cena koja se dodeljuje ulazima
     */
    public ProveraDTO(BigDecimal cenaOdrzavanja) {
        this.cena = cenaOdrzavanja;
    
    }

    public static void setInitialCeneOdrzavanja(BigDecimal cenaOdrzavanjaOut) {
    	
    	CENA_ODRZAVANJA_PO_ULAZU.put(0, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(17, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(19, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(21, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(23, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(25, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(27, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(29, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(31, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(33, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(35, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(37, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(39, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(41, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(43, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(45, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(47, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(49, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(51, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(53, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(55, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(57, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(59, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(61, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(63, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(271, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(272, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(273, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(291, cenaOdrzavanjaOut);
        CENA_ODRZAVANJA_PO_ULAZU.put(292, cenaOdrzavanjaOut); 	
    	
    }
    public static BigDecimal getCenaZaUlaz(Integer ulaz) {
        if (ulaz == null) {
            return BigDecimal.ZERO;
        }

        // Vraća cenu iz mape, a ako ulaz nije u mapi, vraća nulu
        return CENA_ODRZAVANJA_PO_ULAZU.getOrDefault(ulaz, BigDecimal.ZERO);
    }
    public static void setPojedinacnaCena(Integer ulaz, BigDecimal posebnaCena) {
        if (ulaz != null) {
            CENA_ODRZAVANJA_PO_ULAZU.put(ulaz, posebnaCena);
        }
    }

    public ProveraDTO() {
    }

    public ProveraDTO(Integer status, BigDecimal cena) {
        this.status = status;
        this.cena = cena;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }
}