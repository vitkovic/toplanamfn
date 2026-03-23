package toplana.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	            	
	            	
	            	Long podstanicaId = r.getStan().getPodstanica().getId();
	            	boolean imaOdrzavanje = podstanicaId != null && podstanicaId <= 1105;
	            	
	            	
	            	
	            	
	                utrosakVarijabilni = utrosakVarijabilni.add(nz(r.getUtrosakVarijabilniBezPopusta()));
	                utrosakFiksni      = utrosakFiksni.add(nz(r.getUtrosakFiksniBezPopusta()));
	                
	                
	                
	            	
	           //     utrosakVarijabilni = utrosakVarijabilni.add(nz(r.getUtrosakVarijabilni()));
	           //     utrosakFiksni      = utrosakFiksni.add(nz(r.getUtrosakFiksni()));
	                
	                
	                
	                
	                if (imaOdrzavanje) {
	                    utrosakOdrzavanje = utrosakOdrzavanje.add(nz(r.getUtrosakOdrzavanjeBezPdv()));
	                    cenaOdrzavanje    = cenaOdrzavanje.add(nz(r.getCenaOdrzavanje()));
	                    pdv1              = pdv1.add(nz(r.getUtrosakOdrzavanjePdv()));
	                }
	                
	                utrosakUKwh        = utrosakUKwh.add(nz(r.getUtrosakUKwh()));
	                cenaKwh           = cenaKwh.add(nz(r.getCenaKwh()));
	                cenaFix           = cenaFix.add(nz(r.getCenaFix()));
	                cenaFixIskljucen  = cenaFixIskljucen.add(nz(r.getCenaFixIskljucen()));
	                cenaOStalo        = cenaOStalo.add(nz(r.getCenaOStalo()));
	                pdv2              = pdv2.add(nz(r.getUtrosakFiksniPdv())).add(nz(r.getUtrosakVarijabilniPdv())); // proveriti
	                popust            = popust.add(nz(r.getUtrosakFiksniPopust())).add(nz(r.getUtrosakVarijabilniPopust()));
	                ukupno 			  = ukupno.add(nz(r.getUkupno()));
	                
	               
	                // ISPRAVNO RACUNJANJE - SVE OSTALO IMA PROBLEM SA ZAOKRUZIVANJEM
	              
	                ukupnoZaduzenje = ukupnoZaduzenje
	                        .add(nz(r.getUtrosakFiksni()))
	                        .add(nz(r.getUtrosakVarijabilni()));
	                      
	                
	                // OVO JE RACUNJANJE koje ima problem sa zaokruzivanjem 
	                //ukupnoZaduzenje   = ukupnoZaduzenje.add(nz(r.getUkupno())).add(nz(r.getUtrosakFiksniPdv())).add(nz(r.getUtrosakVarijabilniPdv()));
	                
	                if (imaOdrzavanje) {
	                    ukupnoZaduzenje = ukupnoZaduzenje
	                    		  .add(nz(r.getUtrosakOdrzavanjeBezPdv()))
	                    	       .add(nz(r.getUtrosakOdrzavanjePdv()));
	                }
	                
	               
	                
	            }
	            
	            ukupnoZaduzenje = ukupnoZaduzenje.setScale(2, RoundingMode.HALF_UP);
	            

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

	        // 🔹 skaliranje NA KRAJU (ispravno)
	        RoundingMode RM = RoundingMode.HALF_UP;

	        total.setUtrosakVarijabilni(utrosakVarijabilni.setScale(2, RM));
	        total.setUtrosakFiksni(utrosakFiksni.setScale(2, RM));
	        
	        total.setUtrosakOdrzavanje(utrosakOdrzavanje.setScale(2, RM));
	        total.setUtrosakUKwh(utrosakUKwh.setScale(2, RM));

	        total.setCenaKwh(cenaKwh.setScale(2, RM));
	        total.setCenaFix(cenaFix.setScale(2, RM));
	        total.setCenaFixIskljucen(cenaFixIskljucen.setScale(2, RM));
	        total.setCenaOdrzavanje(cenaOdrzavanje.setScale(2, RM));
	        total.setCenaOStalo(cenaOStalo.setScale(2, RM));

	        total.setPdv1(pdv1.setScale(2, RM));
	        total.setPdv2(pdv2.setScale(2, RM));
	        total.setPopust(popust.setScale(2, RM));
	        total.setUkupno(ukupno.setScale(2, RM));
	        total.setUkupnoZaduzenje(ukupnoZaduzenje.setScale(2, RM));

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