package toplana.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import toplana.domain.Stan;
import toplana.web.rest.dto.RacunDTO;

public class RacunUtils {

	  private static final int PREFIX_LEN = 5;

	    public static List<RacunDTO> groupAndSumBySifraPrefix(List<RacunDTO> racuni) {

	        // 1) Group by first 5 chars of stan.sifra
	        Map<String, List<RacunDTO>> byPrefix = racuni.stream()
	            .filter(r -> r.getStan() != null
	                      && r.getStan().getSifra() != null
	                      && r.getStan().getSifra().length() >= PREFIX_LEN)
	            .collect(Collectors.groupingBy(
	                r -> r.getStan().getSifra().substring(0, PREFIX_LEN),
	                LinkedHashMap::new,
	                Collectors.toList()
	            ));

	        List<RacunDTO> result = new ArrayList<>();

	        for (Map.Entry<String, List<RacunDTO>> e : byPrefix.entrySet()) {
	            
	        	
	        	String prefix = e.getKey();
	        	
	        	List<RacunDTO> group = e.getValue();
	            
	            
	            
	            RacunDTO first = group.get(0);

	            RacunDTO sum = new RacunDTO();

	            // We do NOT set ID – this is an aggregated row
	            sum.setId(null);

	            // Optional: copy some non-numeric fields from first (but this is safe)
	            sum.setDatumRacuna(first.getDatumRacuna());
	            sum.setValutaPlacanja(first.getValutaPlacanja());
	            sum.setDatumSaldiranja(first.getDatumSaldiranja());
	            sum.setPeriod(first.getPeriod());
	            sum.setProknjizen(first.getProknjizen());
	            sum.setAzuriran(first.getAzuriran());

	            // CRUCIAL: reuse Stan AS-IS, do NOT touch it
	            sum.setStan(first.getStan());
	            
	            sum.setStrippedsifra(prefix);

	            // 2) Sum ALL BigDecimal fields
	            BigDecimal utrosakVarijabilni = BigDecimal.ZERO;
	            BigDecimal utrosakFiksni      = BigDecimal.ZERO;
	            BigDecimal utrosakOdrzavanje  = BigDecimal.ZERO;
	            BigDecimal utrosakUKwh        = BigDecimal.ZERO;

	            BigDecimal cenaKwh            = BigDecimal.ZERO;
	            BigDecimal cenaFix            = BigDecimal.ZERO;
	            BigDecimal cenaFixIskljucen   = BigDecimal.ZERO;
	            BigDecimal cenaOdrzavanje     = BigDecimal.ZERO;
	            BigDecimal cenaOStalo         = BigDecimal.ZERO;
	            BigDecimal ukupno         	  = BigDecimal.ZERO;

	            BigDecimal pdv1               = BigDecimal.ZERO;
	            BigDecimal pdv2               = BigDecimal.ZERO;
	            BigDecimal popust             = BigDecimal.ZERO;
	            BigDecimal ukupnoZaduzenje    = BigDecimal.ZERO;
	            BigDecimal ukupnoPlacanje    = BigDecimal.ZERO;

	            for (RacunDTO r : group) {
	                utrosakVarijabilni = utrosakVarijabilni.add(nz(r.getUtrosakVarijabilniBezPopusta()));
	                utrosakFiksni      = utrosakFiksni.add(nz(r.getUtrosakFiksniBezPopusta()));
	                utrosakOdrzavanje  = utrosakOdrzavanje.add(nz(r.getUtrosakOdrzavanjeBezPdv()));
	                utrosakUKwh        = utrosakUKwh.add(nz(r.getUtrosakUKwh()));

	                cenaKwh           = cenaKwh.add(nz(r.getCenaKwh()));
	                cenaFix           = cenaFix.add(nz(r.getCenaFix()));
	                cenaFixIskljucen  = cenaFixIskljucen.add(nz(r.getCenaFixIskljucen()));
	                cenaOdrzavanje    = cenaOdrzavanje.add(nz(r.getCenaOdrzavanje()));
	                cenaOStalo        = cenaOStalo.add(nz(r.getCenaOStalo()));

	                pdv1              = pdv1.add(nz(r.getUtrosakOdrzavanjePdv()));
	                pdv2              = pdv2.add(nz(r.getUtrosakFiksniPdv())).add(nz(r.getUtrosakVarijabilniPdv())); // proveriti
	                popust            = popust.add(nz(r.getUtrosakFiksniPopust())).add(nz(r.getUtrosakVarijabilniPopust()));
	                ukupno 			  = ukupno.add(nz(r.getUkupno()));
	                ukupnoZaduzenje   = ukupnoZaduzenje.add(nz(r.getUkupno())).add(nz(r.getUtrosakOdrzavanjePdv())).add(nz(r.getUtrosakFiksniPdv())).add(nz(r.getUtrosakVarijabilniPdv())).add(nz(r.getUtrosakOdrzavanjeBezPdv()));
	                //ukupnoPlacanje    = ukupnoPlacanje.add(nz(r.getZaPlacanje()));
	            }

	            // 3) Store sums into the new DTO
	            sum.setUtrosakVarijabilni(utrosakVarijabilni);
	            sum.setUtrosakFiksni(utrosakFiksni);
	            sum.setUtrosakOdrzavanje(utrosakOdrzavanje);
	            sum.setUtrosakUKwh(utrosakUKwh);

	            sum.setCenaKwh(cenaKwh);
	            sum.setCenaFix(cenaFix);
	            sum.setCenaFixIskljucen(cenaFixIskljucen);
	            sum.setCenaOdrzavanje(cenaOdrzavanje);
	            sum.setCenaOStalo(cenaOStalo);

	            sum.setPdv1(pdv1);
	            sum.setPdv2(pdv2);
	            sum.setPopust(popust);
	            sum.setUkupnoZaduzenje(ukupnoZaduzenje);
	           // sum.setZaPlacanje(ukupnoPlacanje);
	            sum.setUkupno(ukupno);
	            result.add(sum);
	        }
	        
	        
	        RacunDTO total = new RacunDTO();

	        BigDecimal utrosakVarijabilni = z();
	        BigDecimal utrosakFiksni      = z();
	        BigDecimal utrosakOdrzavanje  = z();
	        BigDecimal utrosakUKwh        = z();

	        BigDecimal cenaKwh            = z();
	        BigDecimal cenaFix            = z();
	        BigDecimal cenaFixIskljucen   = z();
	        BigDecimal cenaOdrzavanje     = z();
	        BigDecimal cenaOStalo         = z();

	        BigDecimal pdv1               = z();
	        BigDecimal pdv2               = z();
	        BigDecimal popust             = z();
	        BigDecimal ukupno             = z();
	        BigDecimal ukupnoZaduzenje    = z();
	        
	        for (RacunDTO r : result) {

	            utrosakVarijabilni = utrosakVarijabilni.add(nz(r.getUtrosakVarijabilni()));
	            utrosakFiksni      = utrosakFiksni.add(nz(r.getUtrosakFiksni()));
	            utrosakOdrzavanje  = utrosakOdrzavanje.add(nz(r.getUtrosakOdrzavanje()));
	            utrosakUKwh        = utrosakUKwh.add(nz(r.getUtrosakUKwh()));

	            cenaKwh            = cenaKwh.add(nz(r.getCenaKwh()));
	            cenaFix            = cenaFix.add(nz(r.getCenaFix()));
	            cenaFixIskljucen   = cenaFixIskljucen.add(nz(r.getCenaFixIskljucen()));
	            cenaOdrzavanje     = cenaOdrzavanje.add(nz(r.getCenaOdrzavanje()));
	            cenaOStalo         = cenaOStalo.add(nz(r.getCenaOStalo()));

	            pdv1               = pdv1.add(nz(r.getPdv1()));
	            pdv2               = pdv2.add(nz(r.getPdv2()));
	            popust             = popust.add(nz(r.getPopust()));

	            ukupno             = ukupno.add(nz(r.getUkupno()));
	            ukupnoZaduzenje    = ukupnoZaduzenje.add(nz(r.getUkupnoZaduzenje()));
	        }
	        total.setUtrosakVarijabilni(utrosakVarijabilni);
	        total.setUtrosakFiksni(utrosakFiksni);
	        total.setUtrosakOdrzavanje(utrosakOdrzavanje);
	        total.setUtrosakUKwh(utrosakUKwh);

	        total.setCenaKwh(cenaKwh);
	        total.setCenaFix(cenaFix);
	        total.setCenaFixIskljucen(cenaFixIskljucen);
	        total.setCenaOdrzavanje(cenaOdrzavanje);
	        total.setCenaOStalo(cenaOStalo);

	        total.setPdv1(pdv1);
	        total.setPdv2(pdv2);
	        total.setPopust(popust);
	        total.setUkupno(ukupno);
	        total.setUkupnoZaduzenje(ukupnoZaduzenje);

	    
	        result.add(total);
	           
	        return result;
	    }              

  
    private static BigDecimal nz(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
    private static BigDecimal z() {
        return BigDecimal.ZERO;
    }
}