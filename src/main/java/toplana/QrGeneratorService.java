package toplana;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

public class QrGeneratorService {

    public static void main(String[] args) throws Exception {
        // JSON request payload
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
        } else {
            System.err.println("❌ Failed to generate QR. Status code: " + response.statusCode());
            System.err.println(new String(response.body()));
        }
    }
}
