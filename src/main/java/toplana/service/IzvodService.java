package toplana.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.universalchardet.UniversalDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import toplana.domain.Izvod;
import toplana.domain.StavkeIzvodaPostanska;
import toplana.repository.IzvodRepository;
import toplana.repository.StavkeIzvodaPostanskaRepository;
import toplana.web.rest.IzvodResource;

@Service
@Transactional
public class IzvodService {
	
	private final Logger log = LoggerFactory.getLogger(IzvodService.class);
	
	private final IzvodRepository izvodRepository;
	private final StavkeIzvodaPostanskaRepository stavkeIzvodaPostanskaRepository;
			
	public IzvodService(IzvodRepository izvodRepository,
			StavkeIzvodaPostanskaRepository stavkeIzvodaPostanskaRepository) {
		this.izvodRepository = izvodRepository;
		this.stavkeIzvodaPostanskaRepository = stavkeIzvodaPostanskaRepository;
	}

	public Izvod readFilePostanska(@RequestParam("file") MultipartFile file ) throws Exception {		
				
		String encoding = UniversalDetector.detectCharset(file.getInputStream());
		
		String fname = file.getOriginalFilename();
		if (fname != null) {
	        fname = Paths.get(fname).getFileName().toString();  // samo ime bez putanje
	    }
		String number = null;
		try {
			String name = "T25_07174713___20251025_20251127"; // new name of izvod file

			int startc = name.indexOf("T25_");
			
			int start = 0;
			int end = 0;
			
			String charFN = name.substring(4); 
			if (charFN.equalsIgnoreCase("0")) {
			
				start = name.indexOf("T25_0");
				end = name.indexOf("_", start+4);
			} else {
				start = name.indexOf("T25_");
			    end = name.indexOf("_", start+3);
			}
	
			number = name.substring(start+4, end);
		} catch (Exception ex) {
			
		}
		
		
		String myEncoding = null;
		if(encoding.equalsIgnoreCase("WINDOWS-1252"))
			myEncoding = "WINDOWS-1252";
		else
			myEncoding = "UTF-8";
		Izvod izvod = new Izvod();
		izvod.setTip("POSTANSKA");
		List<StavkeIzvodaPostanska> stavke = new ArrayList<StavkeIzvodaPostanska>(); 
		InputStream inputStream =  new BufferedInputStream(file.getInputStream());
		StringBuilder builder = new StringBuilder();  
		String line;  
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, myEncoding));
		String datum = null;
		
		while ((line = reader.readLine()) != null) {  
			if (datum == null) datum = line.substring(115, 123).trim(); 
			
			/*
			if(line.startsWith("*"))//preskace linije koje pocinju *
				continue;
			
			if(line.startsWith(" ")) {
				if(!line.contains("UKUPNO")	&& !line.contains("TOTAL")) {
					//	stavka
					StavkeIzvodaPostanska s = new StavkeIzvodaPostanska(line );
					stavke.add(s);
				}else {
					this.upisiSume(line, izvod);
					
				}
			}else if(line.startsWith("MESEC")) {
				this.upisiSume(line, izvod);
				//tekst zaglavlja
				
			} else {
			*/
				StavkeIzvodaPostanska s = new StavkeIzvodaPostanska(line );
				stavke.add(s);
				//tekst zaglavlja
			
			builder.append(line);  
		}  
		reader.close();
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
			izvod.setDatumIzvodaZaglavlje(LocalDate.parse(datum, formatter));
			izvod.setBrojIzvoda(Integer.valueOf(number));
		} catch (Exception ex){
			izvod.setDatumIzvodaZaglavlje(LocalDate.now());
			Long l = LocalDate.now().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
			izvod.setBrojIzvoda(l.intValue());
		}
		Izvod resIzvod = izvodRepository.save(izvod);
		for(StavkeIzvodaPostanska s : stavke) {
			s.setIzvod(resIzvod);
		}
		
		stavkeIzvodaPostanskaRepository.saveAll(stavke);
		
		
		return izvod;
		
	}
	
	public void upisiSume(String line, Izvod izvod) {
		/*
		if(line.contains("UKUPNO ZA USLUGU")) {
			String ukupnoZaUslugu = line.substring(118).trim().replace(",",".");
			izvod.setUkupnoZaUslugu(new BigDecimal(ukupnoZaUslugu));
			System.out.println(izvod.getUkupnoZaUslugu());
		}else if(line.contains("UKUPNO NEPLA")) {
			String ukupnoNeplacenih = line.substring(118).trim().replace(",",".");
			izvod.setUkupnoNeplacenih(new BigDecimal(ukupnoNeplacenih));
			System.out.println(izvod.getUkupnoNeplacenih());
		}else if(line.contains("TOTAL PLA")) {
			if(izvod.getUkupnoPlacenih() == null) {
				String ukupnoPlacenih = line.substring(118).trim().replace(",",".");
				izvod.setUkupnoPlacenih(new BigDecimal(ukupnoPlacenih));
				System.out.println(izvod.getUkupnoPlacenih());
			}
		}else if(line.contains("DATUM RADA")) {
			if(izvod.getDatumIzvodaZaglavlje() == null) {
				String sDdatumIzvoda = line.substring(123,132).trim();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy.");
				  //convert String to LocalDate		
				izvod.setDatumIzvodaZaglavlje(LocalDate.parse(sDdatumIzvoda, formatter));								
				System.out.println(izvod.getDatumIzvodaZaglavlje());
			}
		}
		*/
		
		String sIznos = line.substring(131, 160).trim().replace(",", ".");
		sIznos = sIznos.replaceFirst("^0+(?!$)", "");   // uklanja sve vodeće nule
		izvod.setUkupnoZaUslugu(new BigDecimal(sIznos));
		
		System.out.println(izvod.getUkupnoZaUslugu());
		
		
		
		
		
	}

}
