package toplana.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
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
import toplana.domain.NacrtRacuna;
import toplana.domain.Podstanica;
import toplana.domain.Racun;
import toplana.domain.Stan;
import toplana.domain.StanStanje;
import toplana.domain.StanjaPodstaniceZaRacun;
import toplana.repository.StanRepository;
import toplana.repository.StanStanjeRepository;
import toplana.web.rest.dto.MailWithAttachment;
import toplana.web.rest.dto.RacunDTO;
import toplana.web.rest.dto.RacunStampanje;
import toplana.web.rest.dto.RekapitulacijaPoPdvDTO;
import toplana.service.MailService;
@Service
@Transactional
public class RacunService  {
    private final Logger log = LoggerFactory.getLogger(RacunService.class);
    
    @Value("${putanja.pdf}")
    private String pdfPutanja; 
    
    private String downFileName;
    
    private boolean smail = false;
    
    

	private final MailService mailService;
    
    
    private final StanStanjeRepository stanstanjeRepository;
    private final StanRepository stanRepository;

	public String getDownFileName() {
		return downFileName;
	}

    
	public RacunService(MailService mailService, StanStanjeRepository stanstanjeRepository, StanRepository stanRepository) {
		this.mailService = mailService;
		this.stanstanjeRepository = stanstanjeRepository;
		this.stanRepository = stanRepository;
	}

	public boolean isSmail() {
		return smail;
	}


	public void setSmail(boolean smail) {
		this.smail = smail;
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
			//// // //System.out.println("PDF File rekapitulacija Generated !!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\Rekapitulacija.pdf";
    }
	// pojedinacno slanje emailova za vise korisnika
	
	public String generateReportSmail(List<RacunStampanje> racuni) {
		 
		try {
			List<MailWithAttachment> emailList = new ArrayList<>();
			
			List<String> whoSend = new ArrayList<>();
			
			// kad prodje provera ovo treba obrisati
			whoSend.add("goran.janevski@masfak.ni.ac.rs");
			whoSend.add("dejan.mitrovic@masfak.ni.ac.rs");
			whoSend.add("julijana.simonovic@masfak.ni.ac.rs");
			whoSend.add("dragan.stojanovic2312@gmail.com");
			whoSend.add("nikola.korunovic@masfak.ni.ac.rs");
			
			
			
			if (this.smail) {                             
				for(RacunStampanje r : racuni) { 
					
					if (r.getVlasnikEmail() != null && r.getVlasnikEmail().length()>0 ) {
						
						if (whoSend.stream().anyMatch(s -> s.equalsIgnoreCase(r.getVlasnikEmail()))) {
							
							System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM        Saljemo mail: " + r.getVlasnikEmail());
						
							//	emailList.add(
								//	new MailWithAttachment(r.getVlasnikEmail(), "Račun za toplotnu energiju za " + r.getPeriod(), "Račun je u prilogu elektronske pošte.", this.generateIndRacun(r))
							//	);
						}
					}
					
				}
				mailService.sendMultipleEmails(emailList);
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\Racun.pdf";
    }
	
	public String generateIndRacun(RacunStampanje r) {
		 
		try {
			ClassPathResource cl = new ClassPathResource("/jasper/Racun6.jrxml");
			//File file = ResourceUtils.getFile("classpath:jasper/Racun2.jrxml");
			InputStream input = cl.getInputStream();//new FileInputStream(file);
			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(input);
			// Get your data source
			List<RacunStampanje> lr = Collections.singletonList(r);
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(lr);
			// Add parameters
			Map<String, Object> parameters = new HashMap<>();
			//parameters.put("createdBy", "JavaHelper.org");
			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);
			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPutanja + "\\Racun.pdf");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\Racun.pdf";
    } 
    
	// dovde slanje mailova za pojedinacnog korisnika, ali svima
   
    public String generateReport(List<RacunStampanje> racuni) {
		 
		try {
			
			
				
			
			List<MailWithAttachment> emailList = new ArrayList<>();
			
			ClassPathResource cl = new ClassPathResource("/jasper/Racun6.jrxml");
			
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
			
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\Racun.pdf";
    }
    
    public String createRacuneZaStampanje(List<Racun> racuni) {
    	
    	String outputFileName =  null;
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");  
    	DateTimeFormatter formatterMesec = DateTimeFormatter.ofPattern("MMM yy");  
    	List<RacunStampanje> racuniStampanje = new ArrayList<RacunStampanje>();
    	Locale loc = new Locale("SH");
    	// ovaj
		
		
		
		
		
    	for(Racun r : racuni) {
    		
    		r.getNacrtRacuna().getStanjaPodstaniceZaRacune();
    		RacunDTO rDTO = new RacunDTO(r);
    		Podstanica pn = r.getStan().getPodstanica();
    		
    		if (pn.getId() > 1105) {   
    			
    		
    			this.NoviCalculateData(rDTO, pn, r);
			
    		}  else {
    			rDTO.setNoviStaroStanje(0.0);
	    		rDTO.setNoviNovoStanje(0.0);
	    		rDTO.setNovipotrosnjazaPeriod(0.0);
	    		rDTO.setNoviUdeoUZajednickoj(0.0);
	    		rDTO.setNoviPotrosnjaPoSvimMerilima(0.0);
	    		rDTO.setNoviZajednickaPotrosnja(0.0);
    		}
			
			// Za stan deo koji se odnosi na udeo zajednicke potrosnje - J5
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
    		
    		
    		//System.out.println("*****************************************************************************************" + r.getStan().getUkljucen());
    		//System.out.println("*****************************************************************************************" + r.getCenaFix());
    		//System.out.println("*****************************************************************************************" + r.getCenaFixIskljucen());
    		//System.out.println("*****************************************************************************************" + rDTO.isIskljucen());
    		
    		String month = rDTO.getDatumRacuna().getMonth().getDisplayName(TextStyle.FULL, loc);
    		String a = month + " " + Integer.valueOf(rDTO.getDatumRacuna().getYear()).toString().substring(2,4);   		
    		
    		RacunStampanje rs = new RacunStampanje(1L, 
    				rDTO.getStan().getVlasnik().getPrezime(), 
    				rDTO.getStan().getVlasnik().getIme(), rDTO.getStan().getVlasnik().getNaziv(), 
    				rDTO.getStan().getUlica(),rDTO.getStan().getUlaz() + "", rDTO.getStan().getBroj() + "", 
    				rDTO.getStan().getSifra(), rDTO.getStan().getStatus(), rDTO.getStan().getVlasnik().getBrojRacuna(),
    				rDTO.getStan().getVlasnik().getPartijaRacuna(),
    				rDTO.getStanjeZaRacun().getStaroStanje().getStanje().toString(),
    				rDTO.getStanjeZaRacun().getNovoStanje().getStanje().toString(),
    				rDTO.getStanjeZaRacun().getNovoStanje().getStanje().subtract(rDTO.getStanjeZaRacun().getStaroStanje().getStanje()).toString(),
    				rDTO.getStanjeZaRacun().getUkupnaPovrsina().toString(), rDTO.getStanjeZaRacun().getUtrosakPoM2().toString(),
    				rDTO.getStan().getPovrsina().toString(), 
    				rDTO.getUtrosakUKwh().toString(),
    				rDTO.getCenaKwh().toString(), 
    				rDTO.getUtrosakVarijabilniBezPopusta().toString(),
    				rDTO.getUtrosakVarijabilniPopust().toString(), 
    				rDTO.getUtrosakVarijabilniBezPdv().toString(),
    				rDTO.getPdv2().toString(), 
    				rDTO.getPdv1().toString(),
    				rDTO.getUtrosakVarijabilniPdv().toString(),
    				rDTO.getUtrosakVarijabilni().toString(),
    				rDTO.getCenaFix().toString(), 
    				rDTO.getUtrosakFiksniBezPopusta().toString(),
    				rDTO.getUtrosakFiksniPopust().toString(), 
    				rDTO.getUtrosakFiksniBezPdv().toString(), 
    				rDTO.getUtrosakFiksniPdv().toString(), 
    				rDTO.getUtrosakFiksni().toString(),
    				rDTO.getCenaOdrzavanje().toString(),
    				rDTO.getUtrosakOdrzavanjeBezPdv().toString(),
    				rDTO.getUtrosakOdrzavanjePdv().toString(), 
    				rDTO.getUtrosakOdrzavanje().toString(),
    				rDTO.getZaduzenjePoRacunu().toString(), 
    				rDTO.getUkupnoZaduzenje().toString(),
    				rDTO.getZaPlacanje().toString(), 
    				rDTO.getDatumRacuna().format(formatter),
    				rDTO.getValutaPlacanja().format(formatter), 
    				rDTO.getDatumSaldiranja().format(formatter),     
    				rDTO.getUkupnoZaduzenje().toString(), 
    				"1", 
    				a, 
    				rDTO.getPopust().toString(),
    				rDTO.isIskljucen(), 
    				rDTO.getPopust() == null ? false : true,       
    				rDTO.getCenaFixIskljucen().toString(), 
    				rDTO.getPeriod(),
    				rDTO.getSlikaQrStan(), 
    				rDTO.getImgQr(), 
    				rDTO.getStan().getVlasnik().getEmail(),
    				rDTO.getNoviStaroStanje(), 
    				rDTO.getNoviNovoStanje(), 
    				rDTO.getNovipotrosnjazaPeriod(),
    				rDTO.getNoviUdeoUZajednickoj(),     
    				rDTO.getNoviPotrosnjaPoSvimMerilima(), 
    				rDTO.getNoviZajednickaPotrosnja()
    				); 
    				
    		racuniStampanje.add(rs); 
    		
    		
    	}
    	if (this.smail) {
    	 return this.generateReportSmail(racuniStampanje);
    	} else
    	 return this.generateReport(racuniStampanje);
    	
    }
    
    public void NoviCalculateData(RacunDTO rDTO, Podstanica pn, Racun r) {
    	
    	BigDecimal zajednickostanjepodstanice = new BigDecimal(0.0);
		BigDecimal ukupnapotrosnjapoStanu  = new BigDecimal(0.0);
		BigDecimal udeozajednickepotrosnje = new BigDecimal(0.0);
		
    	
    	
    	pn.setUkupnapovrsina(stanRepository.findKvSumPodstanicaId(pn.getId()));
    	
    	
   	 
 //   	LocalDate today = LocalDate.now();
  //      LocalDate previousMonth = today.minusMonths(1); 
  //      int previousMonthNumber = previousMonth.getMonthValue();
        
        int previousMonthNumber = rDTO.getDatumRacuna().getMonth().getValue();
        
        
        // !!! Proracun ukupne potrosnje po stanu
        List <StanStanje> vrednostipotrosnje = stanRepository.findPotrosnjaPodstanicaId(pn.getId(),previousMonthNumber);
        		
        String sifra="";
        String temp = "";
        String vrednost="";
        Map<String, String> m = new HashMap<>();
        for(int i=0;i<vrednostipotrosnje.size();i++){
        	
        	
        	try {
        		
        		StanStanje ss = (StanStanje)vrednostipotrosnje.get(i);
        	//	// // //System.out.println(ss);
        		
        		 sifra = ss.getSifra().trim();
        		 if (i == 0) temp = sifra;
        		 
        		 if (sifra.equalsIgnoreCase(temp)) {
        			 vrednost+= ss.getVrednost() + ";";
        		 } else {
        			 vrednost = ss.getVrednost().toString() + ";";
        		 }
        		
        		 m.put(ss.getSifra(), vrednost);
        		
        		
            //	// // //System.out.println(vrednost);
            	temp = sifra;
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	    
        }    
        
        String map="";
        BigDecimal suma = new BigDecimal(.0);
        
        for (String key : m.keySet()) {
            map = key + "...." + m.get(key);
          //  // // //System.out.println(map);
            String value = m.get(key);
            
            String[] vrednosti = value.split(";");
            
            Long val = Math.abs(Long.valueOf(vrednosti[1]) - Long.valueOf(vrednosti[0]));
            
            suma = suma.add(BigDecimal.valueOf(val));
            
            
          //  // // //System.out.println(map + "    #####################################################################################################");
           // // // //System.out.println(suma + "    #####################################################################################################");
        }
        
       
        
  
    	pn.setUkupnapotrosnjapostanu(suma.doubleValue());
		
		rDTO.getStan().setZadnjaStanja(stanstanjeRepository.getLastStatesForStan(rDTO.getStan().getId()));
		rDTO.setNoviStaroStanje(rDTO.getStan().getZadnjaStanja().get(0));
		rDTO.setNoviNovoStanje(rDTO.getStan().getZadnjaStanja().get(1));
		rDTO.setNovipotrosnjazaPeriod(rDTO.getNoviNovoStanje() - rDTO.getNoviStaroStanje());
		NacrtRacuna nc = r.getNacrtRacuna();
		StanjaPodstaniceZaRacun spr = null;
		//Podstanica pn = r.getStan().getPodstanica();
		Stan stan = r.getStan();
		
		for(StanjaPodstaniceZaRacun spz : nc.getStanjaPodstaniceZaRacune()) {
			spr = spz;
    	}   
	 
		
		// // //System.out.println(pn + " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"  );
		
		// ovaj    
		zajednickostanjepodstanice = (spr.getNovoStanje().getStanje().subtract(spr.getStaroStanje().getStanje())).multiply(BigDecimal.valueOf(1000.00)).setScale(2, RoundingMode.HALF_UP);;
		// ovaj
		ukupnapotrosnjapoStanu =BigDecimal.valueOf(pn.getUkupnapotrosnjapostanu()).setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal ukupnapotrosnja =BigDecimal.valueOf(pn.getUkupnapotrosnjapostanu()).setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal ukupnapovrsina = BigDecimal.valueOf(pn.getUkupnapovrsina()).setScale(2, RoundingMode.HALF_UP);
		// Povrsina svih stanova
		
		
		BigDecimal udeostananum = stan.getPovrsina().divide(ukupnapovrsina,5, RoundingMode.HALF_UP).setScale(5);
		
		BigDecimal udeostana = udeostananum.multiply(BigDecimal.valueOf(100.00)).setScale(3, RoundingMode.HALF_UP);
		// Procentualni udeo stana
		
		
        // ovaj		
		udeozajednickepotrosnje = udeostananum.multiply(zajednickostanjepodstanice.subtract(ukupnapotrosnja)).setScale(2, RoundingMode.HALF_UP);
		
		
	
		rDTO.setNoviUdeoUZajednickoj(udeozajednickepotrosnje.doubleValue());
		rDTO.setNoviPotrosnjaPoSvimMerilima(ukupnapotrosnjapoStanu.doubleValue());
		rDTO.setNoviZajednickaPotrosnja(zajednickostanjepodstanice.doubleValue());
		
		
		
    	
    }
 
 


}
