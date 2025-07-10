package toplana;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class QrGeneratorService {

    public static boolean testService(String sifra,String senderData, BigDecimal ammount, String pozivnaBroj) throws Exception {
        // JSON request payload
        
    	ammount = ammount.setScale(2, RoundingMode.HALF_UP);
    	String value = ammount.toString();
    	
    	if (value.lastIndexOf(".") == -1) {
    		value += ",00";
    	} else {
    		value = value.replace('.', ',');
    	}
    	
    	
    
    	String json =
            "{"
                + "\"K\": \"PR\","
                + "\"V\": \"01\","
                + "\"C\": \"1\","
                + "\"R\": \"840000000174566663\","
                + "\"N\": \"MFN AL. Medvedeva 14\","
                + "\"I\": \"RSD"+value +"\","
                + "\"P\": \"" + senderData + "\","
                + "\"SF\": \"289\","
                + "\"S\": \"UPLATA PO RAČUNU ZA TOPLOTNU EN\","
                + "\"RO\": \"00"+ sifra + "\""
            + "}";

        
        /*
        String json =
                "{"
                    + "\"K\": \"PR\","
                    + "\"V\": \"01\","
                    + "\"C\": \"1\","
                    + "\"R\": \"845000000040484987\","
                    + "\"N\": \"JP EPS BEOGRAD\\r\\nBALKANSKA 13\","
                    + "\"I\": \"RSD3596,13\","
                    + "\"P\": \"MRĐO MAČKATOVIĆ\\r\\nŽUPSKA 13\\r\\nBEOGRAD 6\","
                    + "\"SF\": \"189\","
                    + "\"S\": \"UPLATA PO RAČUNU ZA EL. ENERGIJU\","
                    + "\"RO\": \"97163220000111111111000\""
                + "}";
        */
        
        
        
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://nbs.rs/QRcode/api/qr/v1/gen/500"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 200) {
            Path outputPath = Path.of(sifra+".png");
            Files.write(outputPath, response.body());
            System.out.println("✅ QR code saved to: " + outputPath.toAbsolutePath());
            return true;
        } else {
            System.err.println("❌ Failed to generate QR. Status code: " + response.statusCode());
            System.err.println(new String(response.body()));
            return false;
        }
        
    }
    public static void main(String[] args) {
    	try {
			testService("3455678","Djoka", new BigDecimal(1000.45), "00");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
