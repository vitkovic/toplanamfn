package toplana;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;

public class FormPS {
	
	String file1,outFile;
	ByteArrayResource file2;

    // Records from the first file (received): keyed by SKS (trimmed)
    static class ReceivedRecord {
        String BUP = "";
        String BUN = "";
        String SKS = "";
        String NN  = "";
        String DSLA = "";
    }

    // Records from the second file (to send source): keyed by SKS (dots removed)
    static class SendSourceRecord {
        String SMFN = "";
        String SKS  = "";
        String PO   = "";
        BigDecimal IZ = BigDecimal.ZERO; // numeric value (VAL / 100)
    }

    // Final joined record to export
    static class ExportRecord {
        String TS  = "1";
        String BUP = "";
        String BUN = "";
        String SKS = "";
        String NN  = "";
        String PNB = "";
        String DPO = ""; // ddMMyyyy
        String RP  = ""; // ddMMyyyy
        String IZ  = ""; // left-padded to length 22 (string)
        String SVZ = "RSD";
        String DSZ = ""; // ddMMyyyy
    }

    public ByteArrayResource createFile(ByteArrayResource file2) {
       file1 = "c:\\toplana\\ps\\T23.txt";
       this.file2 = file2;
       outFile = "c:\\toplana\\ps\\PS_export_JUN_2025_v2.txt";

        Map<String, ReceivedRecord> receivedMap = new HashMap<>();
        Map<String, SendSourceRecord> sendSourceMap = new LinkedHashMap<>();
        Map<String, ExportRecord> exportMap = new LinkedHashMap<>();

        // --- Read first file: fixed-width fields -> receivedMap keyed by SKS.trim() ---
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file1), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                String s = rstrip(line);
                ReceivedRecord rr = new ReceivedRecord();
                rr.BUP  = substr(s, 0, 15);
                rr.BUN  = substr(s, 15, 30);
                rr.SKS  = substr(s, 31, 65); // note: index 30 is skipped in the Python
                rr.NN   = substr(s, 65, 95);
                rr.DSLA = substr(s, 95, 103);
                receivedMap.put(rr.SKS.trim(), rr);
            }
        } catch (IOException e) {
            System.out.println("The file " + file1 + " was not found or could not be read.");
        }

        // --- Read second file: fixed-width fields -> sendSourceMap keyed by SKS (dots removed) ---
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file2.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String s = rstrip(line);
                SendSourceRecord sr = new SendSourceRecord();
                sr.SMFN = substr(s, 0, 10).trim();
                String sksDots = substr(s, 10, 21);
                String sks = sksDots.replace(".", "");
                sr.SKS = sks.trim();
                sr.PO  = sr.SKS;

                String valRaw = substr(s, 21, 31);
                String valNoLeadingZeros = lstrip(valRaw, '0');
                if (valNoLeadingZeros.isEmpty()) valNoLeadingZeros = "0";

                // Python: float(VAL_WO_ZEROS) / 100  -> keep numeric and later format as string
                BigDecimal iz = new BigDecimal(valNoLeadingZeros).divide(new BigDecimal("100"), 2, RoundingMode.DOWN);
                sr.IZ = iz.stripTrailingZeros();

                sendSourceMap.put(sr.SKS, sr);
            }
        } catch (IOException e) {
            System.out.println("The file " + file2 + " was not found or could not be read.");
        }

        DateTimeFormatter dmy = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate dtm_dpo = LocalDate.parse("2025-01-31"); // adjust if needed
        LocalDate dtm_rp  = dtm_dpo.plusDays(30);
        String dpoStr = dtm_dpo.format(dmy);
        String rpStr  = dtm_rp.format(dmy);
        String dszStr = LocalDate.now().format(dmy);

        for (Map.Entry<String, SendSourceRecord> e : sendSourceMap.entrySet()) {
            String sksKey = e.getKey();
            SendSourceRecord src = e.getValue();
            ReceivedRecord rr = receivedMap.get(sksKey);
            if (rr != null) {
                ExportRecord ex = new ExportRecord();
                ex.BUP = rr.BUP;
                ex.BUN = rr.BUN;
                ex.SKS = rr.SKS;
                ex.NN  = rr.NN;
                ex.PNB = sksKey; // PNB = keyr (from Python)
                ex.DPO = dpoStr;
                ex.RP  = rpStr;
                String izStr = normalizeNumberString(src.IZ);
                ex.IZ = leftPad(izStr, 22, '0');
                ex.DSZ = dszStr;

                exportMap.put(sksKey, ex);
            }
        }

        // --- Build output in-memory ---
        StringBuilder out = new StringBuilder(1024 * 64);
        for (ExportRecord ex : exportMap.values()) {
            String bup = rightPad(ex.BUP, 15, ' ');
            String bun = rightPad(ex.BUN, 15, ' ');
            String sks = rightPad(ex.SKS, 35, ' ');
            String nn  = rightPad(ex.NN , 30, ' ');
            String pnb = rightPad(ex.PNB, 20, ' ');
            out.append(ex.TS)
               .append(bup)
               .append(bun)
               .append(sks)
               .append(nn)
               .append(pnb)
               .append(ex.DPO)
               .append(ex.RP)
               .append(ex.IZ)
               .append(ex.SVZ)
               .append(ex.DSZ)
               .append('\n');
        }

        byte[] bytes = out.toString().getBytes(StandardCharsets.UTF_8);
        return new ByteArrayResource(bytes);
    }

    // --- helpers ---

    private static String substr(String s, int start, int end) {
        if (s == null) return "";
        int len = s.length();
        int a = Math.max(0, Math.min(start, len));
        int b = Math.max(0, Math.min(end, len));
        if (a >= b) return "";
        return s.substring(a, b);
    }

    private static String rstrip(String s) {
        if (s == null) return "";
        int i = s.length() - 1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) i--;
        return s.substring(0, i + 1);
    }

    private static String lstrip(String s, char ch) {
        if (s == null) return "";
        int i = 0, n = s.length();
        while (i < n && s.charAt(i) == ch) i++;
        return s.substring(i);
    }

    private static String rightPad(String s, int length, char pad) {
        if (s == null) s = "";
        if (s.length() >= length) return s;
        StringBuilder sb = new StringBuilder(length);
        sb.append(s);
        for (int i = s.length(); i < length; i++) sb.append(pad);
        return sb.toString();
    }

    private static String leftPad(String s, int length, char pad) {
        if (s == null) s = "";
        if (s.length() >= length) return s;
        StringBuilder sb = new StringBuilder(length);
        for (int i = s.length(); i < length; i++) sb.append(pad);
        sb.append(s);
        return sb.toString();
    }

    // Produce a string like Python's str(float) but using BigDecimal to avoid scientific notation.
    private static String normalizeNumberString(BigDecimal bd) {
        if (bd == null) return "0";
        // Keep up to 2 decimals (VAL/100), drop trailing zeros to mimic Python's default
        BigDecimal scaled = bd.stripTrailingZeros();
        return scaled.scale() < 0 ? scaled.setScale(0).toPlainString() : scaled.toPlainString();
    }
}
