package toplana;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.EncodeHintType;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class QrGeneratorFromText {

    public static void generateQr(String sifra,String senderData, BigDecimal ammount, String pozivnaBroj) throws IOException, WriterException {
        // QR payload string
    	
    	//pozivnaBroj = "97";
    	//String sifraTemp = "163220000111111111000";
    	//sifraTemp = sifra;
    	
        String qrText =
            "K:PR|V:01|C:1|R:840000000174566663|N:MFN AL. Medvedeva 14"
            + "|I:RSD" + ammount.toString().replace('.', ',')+"|P:" + senderData 
            + "|SF:189|S:UPLATA PO RAČUNU ZA Toplotnu EN."
            + "|RO:" + "00" + sifra;

        // Set encoding hints
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 4);  // quiet zone

        // Generate QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 300, 300, hints);

        // Output file
        Path outputFile = Path.of(sifra+".png");
        MatrixToImageWriter.writeToPath(matrix, "PNG", outputFile);

        System.out.println("✅ QR code generated: " + outputFile.toAbsolutePath());
    }
}
