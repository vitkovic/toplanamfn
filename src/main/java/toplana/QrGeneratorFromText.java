package toplana;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import jdk.internal.net.http.common.Log;

import com.google.zxing.EncodeHintType;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class QrGeneratorFromText {
	
	   public static BufferedImage awtImage;
	
	   private static final Logger logger = LoggerFactory.getLogger( QrGeneratorFromText.class);

    public static boolean generateQr(String sifra,String senderData, BigDecimal ammount, String pozivnaBroj) throws IOException, WriterException {
      
    	
    	boolean serviceCreated = false;
    	boolean serviceNeeded = false;
    	
    	ammount = ammount.setScale(2, RoundingMode.HALF_UP);
    	String value = ammount.toString();
    	
    	if (value.lastIndexOf(".") == -1) {
    		value += ",00";
    	} else {
    		value = value.replace('.', ',');
    	}
    	  
        String qrText =
            "K:PR|V:01|C:1|R:840000003280884575|N:MFN AL. Medvedeva 14"
            + "|I:RSD" + value +"|P:" + senderData 
            + "|SF:189|S:UPLATA PO RAČUNU ZA TOPLOTNU EN"
            + "|RO:" + "00" + sifra;

        
        
    	// Test qr validity
        
        if (serviceNeeded) {
	        try {
	        	
	        	BufferedImage buffimg = QrGeneratorService.testService(sifra, senderData, ammount, pozivnaBroj); 
	        	
	        	if (buffimg != null) {
	        		//System.out.println("✅ QR code not generated");
	        		logger.error("Wrong data sent to qr service!");
	        		serviceCreated = false;
	        		return false;
	        	
	        	} else {
	        		awtImage = buffimg;
	        		serviceCreated = true;
	        	}
	        
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        	logger.error("QR Service Exception!");
	        	serviceCreated = false;
	        	return false;
	        }
        
        }
        // Set encoding hints
        if (serviceNeeded && serviceCreated) {
        	return true;
        }
        
        
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 4);  // quiet zone

        // Generate QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 300, 300, hints);

        // Output file
        //Path outputFile = Path.of(sifra+".png");
        //MatrixToImageWriter.writeToPath(matrix, "PNG", outputFile);
       
        
        
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", os);
        
        
        // java.awt.image creation
        
        BufferedImage img = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(img, "png", os);

        // Now convert back to BufferedImage
        BufferedImage resultImage = byteArrayOutputStreamToBufferedImage(os);
        
       // File imgfile = new File("screenshot.png");
       // ImageIO.write(resultImage, "png", imgfile);
        
        awtImage = resultImage;
        
        ////System.out.println("Image width: " + resultImage.getWidth());
        
        ////System.out.print(os);

        ////System.out.println("✅ QR code generated: " + outputFile.toAbsolutePath());
        
        return true;
    }
    
    public static void main(String[] args) {
    	try {
			generateQr("3455678","Djoka", new BigDecimal(1000.45), "00");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 
    public static BufferedImage byteArrayOutputStreamToBufferedImage(ByteArrayOutputStream baos) throws IOException {
        byte[] imageBytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bais); // returns a BufferedImage
    }
    
    
}
