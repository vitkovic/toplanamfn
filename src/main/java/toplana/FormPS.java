package toplana;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;

import toplana.domain.Racun;
import toplana.web.rest.dto.RacunDTO;
import toplana.web.rest.dto.StanSaldoDTO;

public class FormPS {

    public ByteArrayResource createFile(List<Racun> racuni, LocalDate end) {

        StringBuilder out = new StringBuilder(1024 * 64);

        // PS FORMAT datumi
        DateTimeFormatter dmy = DateTimeFormatter.ofPattern("ddMMyyyy");

        // Uzimamo mesec iz prvog računa (svi su isti mesec u upitu)
        LocalDate racunDate = racuni.get(0).getDatumRacuna();
        YearMonth ym = YearMonth.from(racunDate);

        LocalDate dpo = ym.atEndOfMonth();      // zadnji dan meseca
        LocalDate rp  = dpo.plusDays(30);       // +30 dana
        LocalDate dsz = LocalDate.now();        // datum slanja

        String dpoStr = dpo.format(dmy);
        String rpStr  = rp.format(dmy);
        String dszStr = dsz.format(dmy);

        for (Racun r : racuni) {
        	RacunDTO rdto = new RacunDTO(r);
            // -----------------------------
            // POLJA IZ OBJEKATA
            // -----------------------------

            // 1) BUP — uvek fiksno
            String bup = "0000000000000170";

            // 2) BUN — partija vlasnika, padded left
            String bun = leftPad(r.getStan().getVlasnik().getPsr(), 23, '0');

            // 3) SKS — sifra stana, padded right to 35
            String sks = rightPad("", 35, ' ');

            // 4) IME I PREZIME — padded right to 30
            String imePrezime =
                    r.getStan().getVlasnik().getIme() + " " +
                    r.getStan().getVlasnik().getPrezime();
            imePrezime = rightPad(imePrezime, 30, ' ');

            // 5) PNB — sifra stana padded right 20
            String pnb = rightPad(r.getStan().getSifra(), 20, ' ');

            // 6) IZNOS
            BigDecimal iz = rdto.getZaPlacanje();
          
            String izStr = normalizeNumberString(iz);
            String izPad = leftPad(izStr, 22, '0');
           
            // -----------------------------
            // FORMIRANJE PS REDA
            // Ako je racun za mesec za koji se radi negativan (u pretplati) ne ulazi u izvestaj            
            // -----------------------------
            if (iz.compareTo(new BigDecimal(0)) == 1) {
            	// System.out.println(iz);
	            out.append("1")           // TS
	               .append(bup)
	               .append(bun)
	               .append(sks)
	               .append(imePrezime)
	               .append(pnb)
	               .append(dpoStr)
	               .append(rpStr)
	               .append(izPad)
	               .append("941")       // fiksno polje
	               .append(dszStr)
	               .append("\n");
            }  
        }

    //    System.out.println(out);
        return new ByteArrayResource(out.toString().getBytes());
    }

    // -------------------------------------------------
    // HELPERS
    // -------------------------------------------------

    private static String leftPad(String s, int len, char pad) {
        if (s == null) s = "";
        if (s.length() >= len) return s;
        StringBuilder sb = new StringBuilder();
        for (int i = s.length(); i < len; i++) sb.append(pad);
        sb.append(s);
        return sb.toString();
    }

    private static String rightPad(String s, int len, char pad) {
        if (s == null) s = "";
        if (s.length() >= len) return s;
        StringBuilder sb = new StringBuilder(s);
        for (int i = s.length(); i < len; i++) sb.append(pad);
        return sb.toString();
    }

    private static String normalizeNumberString(BigDecimal bd) {
        // uklanja trailing zeros, ali cuva decimals
        BigDecimal x = bd.stripTrailingZeros();
        if (x.scale() < 0) x = x.setScale(0);
        return x.toPlainString();
    }
}
