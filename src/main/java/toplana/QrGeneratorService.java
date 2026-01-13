package toplana;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class QrGeneratorService {

    public static BufferedImage testService(String sifra,String senderData, BigDecimal ammount, String pozivnaBroj) throws Exception {
        // JSON request payload
    	
    	BufferedImage bufferedImage = null;
    	
    	// set ammount for service
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
                + "\"R\": \"840000003280884575\","
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
            
            ByteArrayInputStream bais = new ByteArrayInputStream(response.body()); // convert to bytearray
            bufferedImage = ImageIO.read(bais); // create buffered image for jhipster
            //Files.write(outputPath, response.body());
            //System.out.println("✅ QR code saved to: " + outputPath.toAbsolutePath());
            return bufferedImage;
        } else {
            System.err.println("❌ Failed to generate QR. Status code: " + response.statusCode());
            System.err.println(new String(response.body()));
            return null;
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
