package toplana.service.dto;

import java.math.BigDecimal;

public class ProveraDTO {

    private Integer status;
    private BigDecimal cena;

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