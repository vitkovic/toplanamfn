package toplana.service.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ProveraDTO {

    private Integer status;
    private BigDecimal cena;

    private static final Map<Integer, BigDecimal> CENA_ODRZAVANJA_PO_ULAZU = new HashMap<>();

    static {

        CENA_ODRZAVANJA_PO_ULAZU.put(0, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(17, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(19, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(21, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(23, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(25, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(27, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(29, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(31, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(33, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(35, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(37, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(39, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(41, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(43, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(45, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(47, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(49, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(51, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(53, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(55, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(57, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(59, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(61, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(63, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(271, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(272, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(273, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(291, new BigDecimal("0.00"));
        CENA_ODRZAVANJA_PO_ULAZU.put(292, new BigDecimal("0.00"));
    }
    
    
    public static BigDecimal getCenaZaUlaz(Integer ulaz) {

        if (ulaz == null) {
            return BigDecimal.ZERO;
        }

        return CENA_ODRZAVANJA_PO_ULAZU.getOrDefault(
            ulaz,
            BigDecimal.ZERO
        );
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