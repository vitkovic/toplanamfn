package toplana;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class QrGeneratorService {

    public static boolean testService(String sifra,String senderData, BigDecimal ammount, String pozivnaBroj) throws Exception {
        // JSON request payload
        String json =
            "{"
                + "\"K\": \"PR\","
                + "\"V\": \"01\","
                + "\"C\": \"1\","
                + "\"R\": \"840000000174566663\","
                + "\"N\": \"MFN AL. Medvedeva 14\","
                + "\"I\": \"RSD" + ammount.toString().replace('.', ',')+"\","
                + "\"P\": \"" + senderData + "\","
                + "\"SF\": \"289\","
                + "\"S\": \"UPLATA PO RAČUNU ZA TOPLOTNU EN\","
                + "\"RO\": \"+ \"00\""+ sifra + "\""
            + "}";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://nbs.rs/QRcode/api/qr/v1/gen/500"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 200) {
            Path outputPath = Path.of("nbs_qr_code.png");
            Files.write(outputPath, response.body());
            System.out.println("✅ QR code saved to: " + outputPath.toAbsolutePath());
            return false;
        } else {
            System.err.println("❌ Failed to generate QR. Status code: " + response.statusCode());
            System.err.println(new String(response.body()));
        }
        
        return true;
    }
}
