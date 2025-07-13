package toplana.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import toplana.QrGeneratorFromText;
import toplana.domain.Racun;
import toplana.web.rest.dto.MailWithAttachment;
import toplana.web.rest.dto.RacunDTO;
import toplana.web.rest.dto.RacunStampanje;
import toplana.web.rest.dto.RekapitulacijaPoPdvDTO;
import toplana.service.MailService;
@Service
@Transactional
public class RacunService {
    private final Logger log = LoggerFactory.getLogger(RacunService.class);
    
    @Value("${putanja.pdf}")
    private String pdfPutanja; 
    
    private String downFileName;
    
    private final MailService mailService;
    
    public String getDownFileName() {
		return downFileName;
	}

    
	public RacunService(MailService mailService) {
		this.mailService = mailService;
	}




	public void setDownFileName(String downFileName) {
		this.downFileName = downFileName;
	}

	public String generateRekapitulacijaReport(List<RekapitulacijaPoPdvDTO> rps) {
		 
		try {
			
			ClassPathResource cl = new ClassPathResource("/jasper/Rekapitulacija.jrxml");
			InputStream input = cl.getInputStream();
			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(input);
			// Get your data source
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(rps);
			// Add parameters
			Map<String, Object> parameters = new HashMap<>();
			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPutanja + "\\Rekapitulacija.pdf");
			//System.out.println("PDF File rekapitulacija Generated !!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\Rekapitulacija.pdf";
    }
   
    public String generateReport(List<RacunStampanje> racuni) {
		 
		try {
			List<MailWithAttachment> emailList = new ArrayList<>();
			ClassPathResource cl = new ClassPathResource("/jasper/Racun4.jrxml");
			
			//File file = ResourceUtils.getFile("classpath:jasper/Racun2.jrxml");
			InputStream input = cl.getInputStream();//new FileInputStream(file);
			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(input);
			// Get your data source
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(racuni);
			// Add parameters
			Map<String, Object> parameters = new HashMap<>();
			//parameters.put("createdBy", "JavaHelper.org");
			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPutanja + "\\Racun.pdf");
			System.out.println("PDF File Generated !!");
			
			for(RacunStampanje r : racuni) { 
				
				emailList.add(
	    			    new MailWithAttachment("nvitko@gmail.com", "Račun za toplotnu energiju za " + r.getPeriod(), "Račun je u prilogu elektronske pošte.", pdfPutanja + "\\Racun.pdf")
	    			);

				
			}
			mailService.sendMultipleEmails(emailList);
			
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\Racuni.pdf";
    }
    
    public String createRacuneZaStampanje(List<Racun> racuni) {
    	
    	String outputFileName =  null;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");  
    	DateTimeFormatter formatterMesec = DateTimeFormatter.ofPattern("MMM yy");  
    	List<RacunStampanje> racuniStampanje = new ArrayList<RacunStampanje>();
    	Locale loc = new Locale("SH");
    	
    	
    	for(Racun r : racuni) {
    		
    		r.getNacrtRacuna().getStanjaPodstaniceZaRacune();
    		RacunDTO rDTO = new RacunDTO(r);
    		
    		try {
    			if (QrGeneratorFromText.generateQr(rDTO.getStan().getSifra(),rDTO.getStan().getVlasnik().getIme() + rDTO.getStan().getVlasnik().getPrezime(), 
    			rDTO.getZaPlacanje(), rDTO.getPozivNaBroj())) {
    				rDTO.setSlikaQrStan(rDTO.getStan().getSifra() + ".png");
    				rDTO.setImgQr(QrGeneratorFromText.awtImage); // set byte image to transfer - no saving on disk
    				
    			} else {
    				rDTO.setSlikaQrStan("c://toplana//temp//empty.png");
    			}
    			
    		} catch (Exception ex) {
    			
    			ex.printStackTrace();
    			
    		}
    		
    		
    		
    		
    		String month = rDTO.getDatumRacuna().getMonth().getDisplayName(TextStyle.FULL, loc);
    		String a = month + " " + Integer.valueOf(rDTO.getDatumRacuna().getYear()).toString().substring(2,4);   		
    		
    		RacunStampanje rs = new RacunStampanje(1L, rDTO.getStan().getVlasnik().getPrezime(), 
    				rDTO.getStan().getVlasnik().getIme(), rDTO.getStan().getVlasnik().getNaziv(), 
    				rDTO.getStan().getUlica(),rDTO.getStan().getUlaz() + "", rDTO.getStan().getBroj() + "", 
    				rDTO.getStan().getSifra(), rDTO.getStan().getStatus(), rDTO.getStan().getVlasnik().getBrojRacuna(),
    				rDTO.getStan().getVlasnik().getPartijaRacuna(),
    				rDTO.getStanjeZaRacun().getStaroStanje().getStanje().toString(),
    				rDTO.getStanjeZaRacun().getNovoStanje().getStanje().toString(),
    				rDTO.getStanjeZaRacun().getNovoStanje().getStanje().subtract(rDTO.getStanjeZaRacun().getStaroStanje().getStanje()).toString(),
    				rDTO.getStanjeZaRacun().getUkupnaPovrsina().toString(), rDTO.getStanjeZaRacun().getUtrosakPoM2().toString(),
    				rDTO.getStan().getPovrsina().toString(), rDTO.getUtrosakUKwh().toString(),
    				rDTO.getCenaKwh().toString(), rDTO.getUtrosakVarijabilniBezPopusta().toString(),
    				rDTO.getUtrosakVarijabilniPopust().toString(), rDTO.getUtrosakVarijabilniBezPdv().toString(),
    				rDTO.getPdv2().toString(), rDTO.getPdv1().toString(),
    				rDTO.getUtrosakVarijabilniPdv().toString(),rDTO.getUtrosakVarijabilni().toString(),
    				rDTO.getCenaFix().toString(), rDTO.getUtrosakFiksniBezPopusta().toString(),
    				rDTO.getUtrosakFiksniPopust().toString(), rDTO.getUtrosakFiksniBezPdv().toString(), 
    				rDTO.getUtrosakFiksniPdv().toString(), rDTO.getUtrosakFiksni().toString(),
    				rDTO.getCenaOdrzavanje().toString(), rDTO.getUtrosakOdrzavanjeBezPdv().toString(),
    				rDTO.getUtrosakOdrzavanjePdv().toString(), rDTO.getUtrosakOdrzavanje().toString(),
    				rDTO.getZaduzenjePoRacunu().toString(), rDTO.getUkupnoZaduzenje().toString(),
    				rDTO.getZaPlacanje().toString(), rDTO.getDatumRacuna().format(formatter),
    				rDTO.getValutaPlacanja().format(formatter), rDTO.getDatumSaldiranja().format(formatter),
    				rDTO.getUkupnoZaduzenje().toString(), "1", a, rDTO.getPopust().toString(),
    				rDTO.getStan().isUkljucen(), rDTO.getPopust() == null ? false : true, rDTO.getCenaFixIskljucen().toString(), rDTO.getPeriod(),
    				rDTO.getSlikaQrStan(), rDTO.getImgQr(), rDTO.getStan().getVlasnik().getEmail()
    				);    				
    		racuniStampanje.add(rs); 
    		
    	
    		
    		
    	}
    	
    	return this.generateReport(racuniStampanje);
    	
    }
    
    


}
