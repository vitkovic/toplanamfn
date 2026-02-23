package toplana.service;

import toplana.domain.OstaliRacuni;
import toplana.domain.Stan;
import toplana.domain.Transakcija;
import toplana.repository.OstaliRacuniRepository;
import toplana.repository.TransakcijaRepository;
import toplana.specifications.RacunSpecification;
import toplana.specifications.SearchCriteria;
import toplana.specifications.SearchOperation;
import toplana.specifications.TransakcijaSpecification;
import toplana.util.RacunUtils;
import toplana.web.rest.dto.DugujePotrazujeReoni;
import toplana.web.rest.dto.RekapitulacijaPoPdvDTO;
import toplana.web.rest.dto.RekapitulacijaSifraPromeneDatumDTO;
import toplana.web.rest.dto.SearchRacunDTO;
import toplana.web.rest.dto.SearchTransakcijaDTO;
import toplana.web.rest.dto.TransakcijaStanUkupnoDTO;
import toplana.web.rest.dto.TransakcijaZaStanDTO;
import toplana.web.rest.dto.TransakcijeZaStanZbirnoDTO;
import toplana.web.rest.dto.RacunDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Transakcija}.
 */
@Service
@Transactional
public class TransakcijaService {

    private final Logger log = LoggerFactory.getLogger(TransakcijaService.class);

    private final TransakcijaRepository transakcijaRepository;
    private final OstaliRacuniRepository ostaliRacuniRepository;

    @Value("${putanja.pdf}")
    private String pdfPutanja;   

    public TransakcijaService(TransakcijaRepository transakcijaRepository,
			OstaliRacuniRepository ostaliRacuniRepository) {
		super();
		this.transakcijaRepository = transakcijaRepository;
		this.ostaliRacuniRepository = ostaliRacuniRepository;
	}

	/**
     * Save a transakcija.
     *
     * @param transakcija the entity to save.
     * @return the persisted entity.
     */
    public Transakcija save(Transakcija transakcija) {
        log.debug("Request to save Transakcija : {}", transakcija);
        if(transakcija.getOstaliRacuni() != null) {
        	transakcija.setStan(null);
        }
        return transakcijaRepository.save(transakcija);
    }

    /**
     * Get all the transakcijas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Transakcija> findAll(Pageable pageable) {
        log.debug("Request to get all Transakcijas");
        //return transakcijaRepository.findAll(pageable);
        return transakcijaRepository.findAllWithStan(pageable);
    }



    /**
     *  Get all the transakcijas where StavkaIzvoda is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Transakcija> findAllWhereStavkaIzvodaIsNull() {
        log.debug("Request to get all transakcijas where StavkaIzvoda is null");
        return StreamSupport
            .stream(transakcijaRepository.findAll().spliterator(), false)
            .filter(transakcija -> transakcija.getStavkaIzvoda() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one transakcija by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Transakcija> findOne(Long id) {
        log.debug("Request to get Transakcija : {}", id);
        return transakcijaRepository.findById(id);
    }

    /**
     * Delete the transakcija by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Transakcija : {}", id);
        transakcijaRepository.deleteById(id);
    }
    
    /**
     * Kreira specification na osnovu kriterijuma od klijenta
     * @param search
     * @return
     */
    public TransakcijaSpecification createSpecification(SearchTransakcijaDTO search) {
    	TransakcijaSpecification transakcijaSpec = new TransakcijaSpecification();
	    if(search.getDatumOd() != null) {
	    	transakcijaSpec.add(new SearchCriteria("Transakcija", "datum", search.getDatumOd(), SearchOperation.GREATER_THAN_EQUAL));
	    }
	    
	    transakcijaSpec.add(new SearchCriteria("Stan", "ukljucen", search.isUkljucen(), SearchOperation.EQUAL));
	    
	    if(search.getDatumDo() != null) {
	    	transakcijaSpec.add(new SearchCriteria("Transakcija", "datum", search.getDatumDo(), SearchOperation.LESS_THAN_EQUAL));
	    }
	    if(search.getSifraStana() != null && !search.getSifraStana().trim().equals("")) {
	    	transakcijaSpec.add(new SearchCriteria("Stan", "sifra", search.getSifraStana(), SearchOperation.MATCH));
	    }
	    if(search.getPodstanica() != null) {
	    	transakcijaSpec.add(new SearchCriteria("Stan", "podstanica", search.getPodstanica().getId(), SearchOperation.EQUAL));
	    }
	    if(search.getPrezime() != null && !search.getPrezime().trim().equals("")) {
	    	transakcijaSpec.add(new SearchCriteria("Vlasnik", "prezime", search.getPrezime(), SearchOperation.MATCH));
	    }
	    if(search.getReoni() != null && !search.getReoni().isEmpty()) {
	    	transakcijaSpec.add(new SearchCriteria("TipPotrosaca", "id", search.getReoni(), SearchOperation.IN));
	    }
	    
	    return transakcijaSpec;
    }
    
    public TransakcijaSpecification createSpecificationDnevnik(SearchTransakcijaDTO search) {
    	TransakcijaSpecification transakcijaSpec = new TransakcijaSpecification();
	    if(search.getDatumOd() != null) {
	    	transakcijaSpec.add(new SearchCriteria("Transakcija", "datum", search.getDatumOd(), SearchOperation.GREATER_THAN_EQUAL));
	    }
	    
	    //transakcijaSpec.add(new SearchCriteria("Stan", "ukljucen", search.isUkljucen(), SearchOperation.EQUAL));
	    
	    if(search.getDatumDo() != null) {
	    	transakcijaSpec.add(new SearchCriteria("Transakcija", "datum", search.getDatumDo(), SearchOperation.LESS_THAN_EQUAL));
	    }
	    if(search.getSifraStana() != null && !search.getSifraStana().trim().equals("")) {
	    	transakcijaSpec.add(new SearchCriteria("Stan", "sifra", search.getSifraStana(), SearchOperation.MATCH));
	    }
	    if(search.getPodstanica() != null) {
	    	transakcijaSpec.add(new SearchCriteria("Stan", "podstanica", search.getPodstanica().getId(), SearchOperation.EQUAL));
	    }
	    if(search.getPrezime() != null && !search.getPrezime().trim().equals("")) {
	    	transakcijaSpec.add(new SearchCriteria("Vlasnik", "prezime", search.getPrezime(), SearchOperation.MATCH));
	    }
	    if(search.getReoni() != null && !search.getReoni().isEmpty()) {
	    	transakcijaSpec.add(new SearchCriteria("TipPotrosaca", "id", search.getReoni(), SearchOperation.IN));
	    }
	    
	    return transakcijaSpec;
    }
    
    public TransakcijeZaStanZbirnoDTO findAllByStanOrderByDatum(Stan s){
    	
    	TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
    	out.setStan(s);
    	
    	List<Transakcija> transakcije = transakcijaRepository.findAllByStanOrderByDatum(s);
    	BigDecimal saldo = BigDecimal.ZERO;
    	BigDecimal duguje = BigDecimal.ZERO;
    	BigDecimal potrazuje = BigDecimal.ZERO;
    	List<TransakcijaZaStanDTO> tr = new ArrayList<TransakcijaZaStanDTO>();
    	for(Transakcija t : transakcije) {    		
    		TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t,saldo);    		
    		saldo = trans.getSaldo();
    		duguje = duguje.add(trans.getDuguje()).setScale(2);
    		potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2);
    		tr.add(trans);
    	}
    	out.setDugujeUkupno(duguje);
    	out.setPotrazujeUkupno(potrazuje);
    	out.setSaldoUkupno(saldo);
    	out.setTransakcije(tr);
    	
    	return out;
    }
    
 public TransakcijeZaStanZbirnoDTO findAllByDodatniRacunOrderByDatum(String sifra){
    	
    	TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();    	
    	OstaliRacuni or = ostaliRacuniRepository.findBySifra(sifra);
    	if(or != null) {
    		//out.setStan(or.getStan());
    		out.setOstaliRacuni(or);
    	}
    	
    	List<Transakcija> transakcije = transakcijaRepository.findAllByOstaliRacuniOrderByDatum(or);
    	BigDecimal saldo = BigDecimal.ZERO;
    	BigDecimal duguje = BigDecimal.ZERO;
    	BigDecimal potrazuje = BigDecimal.ZERO;
    	List<TransakcijaZaStanDTO> tr = new ArrayList<TransakcijaZaStanDTO>();
    	for(Transakcija t : transakcije) {    		
    		TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t,saldo);    		
    		saldo = trans.getSaldo();
    		duguje = duguje.add(trans.getDuguje()).setScale(2);
    		potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2);
    		tr.add(trans);
    	}
    	out.setDugujeUkupno(duguje);
    	out.setPotrazujeUkupno(potrazuje);
    	out.setSaldoUkupno(saldo);
    	out.setTransakcije(tr);
    	
    	return out;
    }
 
 	public List<TransakcijaStanUkupnoDTO> findAllOR(SearchTransakcijaDTO search){
 		String sifra = "";
 		if(search.getSifraStana() != null) {
    		sifra =  search.getSifraStana() ;
    	}
 	//	//System.out.println("********************************************************************" + sifra);
 		List<TransakcijaStanUkupnoDTO> out = transakcijaRepository.searchOR(sifra);
 		
 		////System.out.println("********************************************************************" + out);
    	return out;
 	}
    
    /**
     * Trazi sve transakcije za zadate kriterijume, ali sumirano
     * @param search
     * @return
     */
    public List<TransakcijaStanUkupnoDTO> findAllSumed(SearchTransakcijaDTO search){
    	int datumOdNotExists = 1;
    	LocalDate datumOd = LocalDate.now();
    	
    	int datumDoNotExists = 1;
    	LocalDate datumDo = LocalDate.now();
    	
    	int sifraNotExists = 1;
    	String sifra = "";
    	
    	int prezimeNotExists = 1;
    	String prezime = "";
    	
    	int imeNotExists = 1;
    	String ime = "";
    	
    	int prezimeImeNotExists = 1;
    	String prezimeime = "";
    	
    	int podstanicaNotExists = 1;
    	Long podstanicaId = 0L;
    	
    	int tipPotrosacaNotExists = 1;
    	List<Long> tipPotrosacaIds = new ArrayList<Long>();
    	
    	
    	
    	// dodatno pretrazivanje
    	
    	String sifraOd = null;
    	String sifraDo = null;
    
    	
    	
    	List<TransakcijaStanUkupnoDTO> out = null;
    	
    	
    	if(search.getDatumOd() != null) {
    		datumOdNotExists = 0;
    		datumOd = search.getDatumOd();
    	}
    	
    	if(search.getDatumDo() != null) {
    		datumDoNotExists = 0;
    		datumDo = search.getDatumDo();
    	}
    	
    	if(search.getSifraStana() != null && !search.getSifraStana().trim().equals("")) {
    		sifraNotExists = 0;
    		sifra = "%" + search.getSifraStana() + "%";
    	}
    	
    	
    	 search.setPrezime(search.getPrezime().trim());
	   	 
    	 String[] prezimeComplex = search.getPrezime().split(String.valueOf(" "));
		 
		 //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ "+ search.getPrezime());
		 
		 if (prezimeComplex != null && prezimeComplex.length > 1) {
			 prezimeImeNotExists = 0;
			 search.setPrezime(prezimeComplex[0]);
			 search.setIme(prezimeComplex[1]);
			 
			 prezimeime = "%" + search.getPrezime() + " " + search.getIme() + "%";
			 
			
			 
		 } 
    	
    	if(search.getPrezime() != null && !search.getPrezime().trim().equals("")) {
    		prezimeNotExists = 0;
    		prezime = "%" + search.getPrezime() + "%";
    	}
    	
    	if(search.getIme() != null && !search.getIme().trim().equals("")) {
    		imeNotExists = 0;
    		ime = "%" + search.getIme() + "%";
    	}
    	
    	if(search.getPodstanica() != null) {
    		podstanicaNotExists = 0;
    		podstanicaId = search.getPodstanica().getId();
    	}
    	
    	if(search.getReoni() != null && !search.getReoni().isEmpty()) {
    		tipPotrosacaNotExists = 0;
    		tipPotrosacaIds.addAll(search.getReoni());
    	}    	
    	
    	
    
    	
    // Nova pretraga	
    	
    	if (search.getSifraOd() != null && !search.getSifraOd().isBlank()) {
    	    sifraOd = search.getSifraOd();
    	}

    	if (search.getSifraDo() != null && !search.getSifraDo().isBlank()) {
    	    sifraDo = search.getSifraDo();
    	}
    	
    	final BigDecimal saldoOdFinal =
    		    search.getSaldoOd() == null ? null : search.getSaldoOd().setScale(2);

    	final BigDecimal saldoDoFinal =
    		    search.getSaldoDo() == null ? null : search.getSaldoDo().setScale(2);

    	
    	
    	if(sifraOd != null) {
    		
    	    	 out = transakcijaRepository.searchSpec(datumOdNotExists,datumOd, datumDoNotExists, 
		    			datumDo, prezimeNotExists, prezime, imeNotExists, ime,  podstanicaNotExists, podstanicaId,
		    			tipPotrosacaNotExists,tipPotrosacaIds, search.isUkljucen(), sifraOd, sifraDo);
    	    	 
    	    
		    	
    	
    	} else {
    		
    			out = transakcijaRepository.search(datumOdNotExists,datumOd, datumDoNotExists, 
        			datumDo,sifraNotExists, sifra, prezimeNotExists, prezime, imeNotExists, ime,  podstanicaNotExists, podstanicaId,
        			tipPotrosacaNotExists,tipPotrosacaIds, search.isUkljucen());
    		
    	}
    		
    		
	   	 out.removeIf(r ->
		    (saldoOdFinal != null && r.getStanje() != null && r.getStanje().compareTo(saldoOdFinal) < 0) ||
		    (saldoDoFinal != null && r.getStanje() != null && r.getStanje().compareTo(saldoDoFinal) > 0)
	   	 );
    	
    	
    	
    	
    	
    	return out;
    }
    
    /**
     * Pronalazi transakcije za analiticki dnevnik
     * @param search
     * @return
     */
    public List<TransakcijaZaStanDTO> findAllForAnalitickiDnevnik(SearchTransakcijaDTO search){
    	int datumOdNotExists = 1;
    	LocalDate datumOd = LocalDate.now();
    	
    	int datumDoNotExists = 1;
    	LocalDate datumDo = LocalDate.now();
    	
    	int sifraNotExists = 1;
    	String sifra = "";
    	
    	int prezimeNotExists = 1;
    	String prezime = "";
    	
    	int imeNotExists = 1;
    	String ime = "";
    	
    	int prezimeImeNotExists = 1;
    	String prezimeime = "";
    	
    	int podstanicaNotExists = 1;
    	Long podstanicaId = 0L;
    	
    	int tipPotrosacaNotExists = 1;
    	List<Long> tipPotrosacaIds = new ArrayList<Long>();
    	
    	if(search.getDatumOd() != null) {
    		datumOdNotExists = 0;
    		datumOd = search.getDatumOd();
    	}
    	
    	if(search.getDatumDo() != null) {
    		datumDoNotExists = 0;
    		datumDo = search.getDatumDo();
    	}
    	
    	if(search.getSifraStana() != null && !search.getSifraStana().trim().equals("")) {
    		sifraNotExists = 0;
    		sifra = "%" + search.getSifraStana() + "%";
    	}
    	
    	
    	 String[] prezimeComplex = search.getPrezime().split(String.valueOf(" "));
    	 
    //	 //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ "+ search.getPrezime());
    	 
    	 if (prezimeComplex != null && prezimeComplex.length > 1) {
    		 prezimeImeNotExists = 0;
    		 search.setPrezime(prezimeComplex[0]);
    		 search.setIme(prezimeComplex[1]);
    		 
    		 prezimeime = "%" + search.getPrezime() + " " + search.getIme() + "%";
    		 
    	 } 
    	
	 	if(search.getPrezime() != null && !search.getPrezime().trim().equals("")) {
 			prezimeNotExists = 0;
 			prezime = "%" + search.getPrezime() + "%";
    	
    	 }
	 	
	 	if(search.getIme() != null && !search.getIme().trim().equals("")) {
 			imeNotExists = 0;
 			ime = "%" + search.getIme() + "%";
    	
    	 }
	 	
	 	
    	
    	if(search.getPodstanica() != null) {
    		podstanicaNotExists = 0;
    		podstanicaId = search.getPodstanica().getId();
    	}
    	
    	if(search.getReoni() != null && !search.getReoni().isEmpty()) {
    		tipPotrosacaNotExists = 0;
    		tipPotrosacaIds.addAll(search.getReoni());
    	}    	
    	
    	List<TransakcijaZaStanDTO> out = transakcijaRepository.searchForAnalitickiDnevnik(datumOdNotExists,datumOd, datumDoNotExists, 
    			datumDo,sifraNotExists, sifra, prezimeNotExists, prezime,  podstanicaNotExists, podstanicaId,
    			tipPotrosacaNotExists,tipPotrosacaIds);
    	out.sort(
    		    Comparator.comparing(
    		        TransakcijaZaStanDTO::getSifraStana,
    		        Comparator.nullsLast(String::compareTo)
    		    )
    		);
    	return out;
    }
    
    public List<DugujePotrazujeReoni> findSumForDnevnik(SearchTransakcijaDTO search){
    	int datumOdNotExists = 1;
    	LocalDate datumOd = LocalDate.now();
    	
    	int datumDoNotExists = 1;
    	LocalDate datumDo = LocalDate.now();
    	
    	int sifraNotExists = 1;
    	String sifra = "";
    	
    	int prezimeNotExists = 1;
    	String prezime = "";
    	
    	int imeNotExists = 1;
    	String ime = "";
    	
    	int prezimeImeNotExists = 1;
    	String prezimeime = "";
    	
    	int podstanicaNotExists = 1;
    	Long podstanicaId = 0L;
    	
    	int tipPotrosacaNotExists = 1;
    	List<Long> tipPotrosacaIds = new ArrayList<Long>();
    	
    	if(search.getDatumOd() != null) {
    		datumOdNotExists = 0;
    		datumOd = search.getDatumOd();
    	}
    	
    	if(search.getDatumDo() != null) {
    		datumDoNotExists = 0;
    		datumDo = search.getDatumDo();
    	}
    	
    	if(search.getSifraStana() != null && !search.getSifraStana().trim().equals("")) {
    		sifraNotExists = 0;
    		sifra = "%" + search.getSifraStana() + "%";
    	}
    	
   	 String[] prezimeComplex = search.getPrezime().split(String.valueOf(" "));
	 
     //	 //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ "+ search.getPrezime());
     	 
     	 if (prezimeComplex != null && prezimeComplex.length > 1) {
     		 prezimeImeNotExists = 0;
     		 search.setPrezime(prezimeComplex[0]);
     		 search.setIme(prezimeComplex[1]);
     		 
     		 prezimeime = "%" + search.getPrezime() + " " + search.getIme() + "%";
     		 
     	 } 
     	
 	 	if(search.getPrezime() != null && !search.getPrezime().trim().equals("")) {
  			prezimeNotExists = 0;
  			prezime = "%" + search.getPrezime() + "%";
     	
     	 }
 	 	
 	 	if(search.getIme() != null && !search.getIme().trim().equals("")) {
  			imeNotExists = 0;
  			ime = "%" + search.getIme() + "%";
     	
     	 }
    	
    	if(search.getPrezime() != null && !search.getPrezime().trim().equals("")) {
    		prezimeNotExists = 0;
    		prezime = "%" + search.getPrezime() + "%";
    	}
    	
    	if(search.getPodstanica() != null) {
    		podstanicaNotExists = 0;
    		podstanicaId = search.getPodstanica().getId();
    	}
    	
    	if(search.getReoni() != null && !search.getReoni().isEmpty()) {
    		tipPotrosacaNotExists = 0;
    		tipPotrosacaIds.addAll(search.getReoni());
    	}    	
    	
   	 
   //	 //System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ "+ search.getPrezime());
    	
    	List<DugujePotrazujeReoni> out = transakcijaRepository.findSumForDnevnik(datumOdNotExists,datumOd, datumDoNotExists, 
    			datumDo,sifraNotExists, sifra, prezimeNotExists, prezime,   podstanicaNotExists, podstanicaId,
    			tipPotrosacaNotExists,tipPotrosacaIds);
    	return out;
    }
/****************************************************************************************************************
 * Pravi se PDF sa analitickim dnevnikom    
 * @param rps
 * @return
 *****************************************************************************************************************/
    public String generateReportAnalitickiDnevnik(List<TransakcijaZaStanDTO> rps) {
		 
		try {
			
			ClassPathResource cl = new ClassPathResource("/jasper/AnDnevnik.jrxml");
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
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPutanja + "\\AnDnevnik.pdf");
			////System.out.println("PDF File rekapitulacija Generated !!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\AnDnevnik.pdf";   
    }
    
/***************************************************************************************************************
 * Rekapitulacija po danima i sifri promene (uplata, blagajna ...    
 * @param search
 * @return
 ****************************************************************************************************************/
    public List<RekapitulacijaSifraPromeneDatumDTO> rekapitulacijaSifraPromeneDatumOld(SearchTransakcijaDTO search){
    	int datumOdNotExists = 1;
    	LocalDate datumOd = LocalDate.now();
    	
    	int datumDoNotExists = 1;
    	LocalDate datumDo = LocalDate.now();    	    	
    	
    	if(search.getDatumOd() != null) {
    		datumOdNotExists = 0;
    		datumOd = search.getDatumOd();
    	}
    	
    	if(search.getDatumDo() != null) {
    		datumDoNotExists = 0;
    		datumDo = search.getDatumDo();
    	}
    	
    	
    	List<RekapitulacijaSifraPromeneDatumDTO> out = transakcijaRepository.rekapitulacijaSifraPromeneDatum(datumOdNotExists,datumOd, datumDoNotExists, 
    	    		datumDo);
    	
    	
    	
    //	List<RekapitulacijaSifraPromeneDatumDTO> out = transakcijaRepository.rekapitulacijaSifraPromeneDatum(datumOdNotExists,datumOd, datumDoNotExists, 
    	//		datumDo);
    	return out;
    }  
    
    
    /***************************************************************************************************************
     * Rekapitulacija po danima i sifri promene (uplata, blagajna ...    
     * @param search
     * @return
     ****************************************************************************************************************/
        public List<RacunDTO> rekapitulacijaSifraPromeneDatum(SearchTransakcijaDTO search){
        	int datumOdNotExists = 1;
        	LocalDate datumOd = LocalDate.now();
        	
        	int datumDoNotExists = 1;
        	LocalDate datumDo = LocalDate.now();    	    	
        	
        	if(search.getDatumOd() != null) {
        		datumOdNotExists = 0;
        		datumOd = search.getDatumOd();
        	}
        	
        	if(search.getDatumDo() != null) {
        		datumDoNotExists = 0;
        		datumDo = search.getDatumDo();
        	}
        	
        	
        	
        	List<RacunDTO> out = transakcijaRepository.rekapitulacijaSifraPromeneDatumRacun(datumDo, Integer.valueOf(search.getPodstanicaOd()), Integer.valueOf(search.getPodstanicaDo()),search.getSifraOd(), search.getSifraDo());
        	
        	//Sumarize by code
        	List<RacunDTO> out_sum = RacunUtils.groupAndSumBySifraPrefix(out);
        	
        //	List<RekapitulacijaSifraPromeneDatumDTO> out = transakcijaRepository.rekapitulacijaSifraPromeneDatum(datumOdNotExists,datumOd, datumDoNotExists, 
        	//		datumDo);
        	return out_sum;    
        }  
    
    
        
    
      
        
        
    
/****************************************************************************************************************
 * Pravi se PDF sa rekapitulacijom po siframa promene i datumima    
 * @param rps
 * @return
 *****************************************************************************************************************/
    public String generateReportRekapitulacijaSifraPromeneDatum(List<RacunDTO> rps) {
		 
		try {
			
			ClassPathResource cl = new ClassPathResource("/jasper/Rekapitulacija1.jrxml");
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
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPutanja + "\\RekapitulacijaPoSiframaPromeneIDatumu.pdf");
			////System.out.println("PDF File rekapitulacija Generated !!");
		}catch(Exception e) {
			e.printStackTrace();
		}  
		
		return pdfPutanja + "\\RekapitulacijaPoSiframaPromeneIDatumu.pdf";
    }  
/****************************************************************************************************************
 * Pravi se PDF za analiticku karticu    
 * @param rps
 * @return
 *****************************************************************************************************************/
    public String generateReportAnalitickaKartica(List<TransakcijaZaStanDTO> rps) {
		 
		try {
			
			ClassPathResource cl = new ClassPathResource("/jasper/AnalitickaKartica.jrxml");
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
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPutanja + "\\AnKartica.pdf");
			////System.out.println("PDF File rekapitulacija Generated !!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\AnKartica.pdf";
    } 
    
/***************************************************************************************************************
 * Sinteticki dnevnik    
 * @param search
 * @return
 ****************************************************************************************************************/
    public List<RekapitulacijaSifraPromeneDatumDTO> sintetickiDnevnik(SearchTransakcijaDTO search){
    	int datumOdNotExists = 1;
    	LocalDate datumOd = LocalDate.now();
    	
    	int datumDoNotExists = 1;
    	LocalDate datumDo = LocalDate.now();    	    	
    	
    	if(search.getDatumOd() != null) {
    		datumOdNotExists = 0;
    		datumOd = search.getDatumOd();
    	}
    	
    	if(search.getDatumDo() != null) {
    		datumDoNotExists = 0;
    		datumDo = search.getDatumDo();
    	}
    	
    	String sifraOd = null;
    	String sifraDo = null;
    	
    	
    	
    
    	if (search.getSifraOd() != null && !search.getSifraOd().isBlank()) {
    	    sifraOd = search.getSifraOd();
    	}

    	if (search.getSifraDo() != null && !search.getSifraDo().isBlank()) {
    	    sifraDo = search.getSifraDo();
    	}
    	
    	
    	String sifraOdParam = (sifraOd == null || sifraOd.isBlank()) ? null : sifraOd;
    	String sifraDoParam = (sifraDo == null || sifraDo.isBlank()) ? null : sifraDo;
    	
    	
    	List<RekapitulacijaSifraPromeneDatumDTO> out = null;
    	
    	
    	final BigDecimal dugujeOdFinal = search.getDugujeOd() == null ? null : search.getDugujeOd().setScale(2, RoundingMode.HALF_UP);
    	final BigDecimal dugujeDoFinal = search.getDugujeDo() == null ? null : search.getDugujeDo().setScale(2, RoundingMode.HALF_UP);
    	final BigDecimal potrazujeOdFinal = search.getPotrazujeOd() == null ? null : search.getPotrazujeOd().setScale(2, RoundingMode.HALF_UP);
    	final BigDecimal potrazujeDoFinal = search.getPotrazujeDo() == null ? null : search.getPotrazujeDo().setScale(2, RoundingMode.HALF_UP);

    
    	String sifraOdP = (sifraOd == null || sifraOd.isBlank()) ? "" : sifraOd;
    	String sifraDoP = (sifraDo == null || sifraDo.isBlank()) ? "" : sifraDo;

    	int sifraOdNotExists = (sifraOd == null || sifraOd.isBlank()) ? 1 : 0;
    	int sifraDoNotExists = (sifraDo == null || sifraDo.isBlank()) ? 1 : 0;

    	BigDecimal dOd = (dugujeOdFinal == null) ? BigDecimal.ZERO : dugujeOdFinal;
    	BigDecimal dDo = (dugujeDoFinal == null) ? BigDecimal.ZERO : dugujeDoFinal;
    	int dOdNE = (dugujeOdFinal == null) ? 1 : 0;
    	int dDoNE = (dugujeDoFinal == null) ? 1 : 0;

    	BigDecimal pOd = (potrazujeOdFinal == null) ? BigDecimal.ZERO : potrazujeOdFinal;
    	BigDecimal pDo = (potrazujeDoFinal == null) ? BigDecimal.ZERO : potrazujeDoFinal;
    	int pOdNE = (potrazujeOdFinal == null) ? 1 : 0;
    	int pDoNE = (potrazujeDoFinal == null) ? 1 : 0;

    	System.out.println("IN: dugujeOd=" + search.getDugujeOd() + " dugujeDo=" + search.getDugujeDo()
    	 + " potrazujeOd=" + search.getPotrazujeOd() + " potrazujeDo=" + search.getPotrazujeDo());

    	System.out.println("FLAGS: dOdNE=" + dOdNE + " dDoNE=" + dDoNE + " pOdNE=" + pOdNE + " pDoNE=" + pDoNE
    	 + " dOd=" + dOd + " dDo=" + dDo + " pOd=" + pOd + " pDo=" + pDo);
    
    			out = transakcijaRepository.sintetickiDnevnikSearch(
    		    	    datumOdNotExists, datumOd,
    		    	    datumDoNotExists, datumDo,
    		    	    sifraOdNotExists, sifraOdP,
    		    	    sifraDoNotExists, sifraDoP,
    		    	    dOdNE, dOd,
    		    	    dDoNE, dDo,
    		    	    pOdNE, pOd,
    		    	    pDoNE, pDo
    		    	);
    		    	
    	
    	return out;
    }  
/****************************************************************************************************************
 * Pravi se PDF za sinteticki dnevnik  
 * @param rps
 * @return
 *****************************************************************************************************************/
    public String generateReportSintetickiDnevnik(List<RekapitulacijaSifraPromeneDatumDTO> rps) {
		 
		try {
			
			ClassPathResource cl = new ClassPathResource("/jasper/SintetickiDnevnik.jrxml");
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
			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPutanja + "\\SintetickiDnevnik.pdf");
			////System.out.println("PDF File rekapitulacija Generated !!");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pdfPutanja + "\\SintetickiDnevnik.pdf";
    }      
    
}
