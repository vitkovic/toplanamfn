package toplana;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.EncodeHintType;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QrGeneratorFromText {

    public static void generateQr(String sifra,String senderData, BigInteger ammount) throws IOException, WriterException {
        // QR payload string
        String qrText =
            "K:PR|V:01|C:1|R:845000000040484987|N:JP EPS BEOGRAD BALKANSKA 13"
            + "|I:RSD3596,13|P:MRĐO MAČKATOVIĆ ŽUPSKA 13 BEOGRAD 6"
            + "|SF:189|S:UPLATA PO RAČUNU ZA EL. ENERGIJU"
            + "|RO:97163220000111111111000";

        // Set encoding hints
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 4);  // quiet zone

        // Generate QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 300, 300, hints);

        // Output file
        Path outputFile = Path.of("qr_eps_payment.png");
        MatrixToImageWriter.writeToPath(matrix, "PNG", outputFile);

        System.out.println("✅ QR code generated: " + outputFile.toAbsolutePath());
    }
}
