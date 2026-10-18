package toplana.service;

import toplana.domain.Cene;
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
import java.util.Collections;
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
    /*
    public TransakcijeZaStanZbirnoDTO findAllByStanOrderByDatum(Stan s){
    	
    	
    	LocalDate today = LocalDate.now();
    	LocalDate datumOd = today.withDayOfYear(1);
    	LocalDate datumDo = today.withMonth(12).withDayOfMonth(31);
    	
    	BigDecimal saldo = transakcijaRepository.getSaldoPreDatuma(s.getId(), datumOd);
    	
    	
    	TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
    	out.setStan(s);
    	
    	//List<Transakcija> transakcije = transakcijaRepository.findAllByStanOrderByDatum(s);
    	List<Transakcija> transakcije = transakcijaRepository.findAllByStanAndDatumBetweenOrderByDatum(s, datumOd, datumDo);
    	
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
    
    
    
    public TransakcijeZaStanZbirnoDTO findAllByStanOrderByDatum(Stan s, boolean sve) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        out.setStan(s);

        List<Transakcija> transakcije;
        BigDecimal saldo;

        if (sve) {
            // 🔥 sve godine
            transakcije = transakcijaRepository.findAllByStanOrderByDatum(s);
            saldo = BigDecimal.ZERO;
        } else {
            // 🔥 samo tekuća godina
            LocalDate today = LocalDate.now();
            LocalDate datumOd = today.withDayOfYear(1);
            LocalDate datumDo = LocalDate.of(today.getYear(), 12, 31);

            saldo = transakcijaRepository.getSaldoPreDatuma(s.getId(), datumOd);
            if (saldo == null) saldo = BigDecimal.ZERO;

            transakcije = transakcijaRepository
                    .findAllByStanAndDatumBetweenOrderByDatum(s, datumOd, datumDo);
        }

        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<>();

        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();
            duguje = duguje.add(trans.getDuguje()).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2, RoundingMode.HALF_UP);
            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    
    */
    
    public TransakcijeZaStanZbirnoDTO findAllByStanOrderByDatum(Stan s, boolean sve) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        out.setStan(s);

        List<Transakcija> transakcije;
        BigDecimal saldo;

        BigDecimal pocetnoDuguje = BigDecimal.ZERO;
        BigDecimal pocetnoPotrazuje = BigDecimal.ZERO;

        LocalDate datumOd = null;
        LocalDate datumDo = null;

        if (sve) {
            transakcije = transakcijaRepository.findAllByStanOrderByDatum(s);
            saldo = BigDecimal.ZERO;
        } else {
            LocalDate today = LocalDate.now();
            datumOd = today.withDayOfYear(1);
            datumDo = LocalDate.of(today.getYear(), 12, 31);

            pocetnoDuguje = transakcijaRepository.getDugujePreDatuma(s.getId(), datumOd);
            pocetnoPotrazuje = transakcijaRepository.getPotrazujePreDatuma(s.getId(), datumOd);
            saldo = transakcijaRepository.getSaldoPreDatuma(s.getId(), datumOd);

            if (pocetnoDuguje == null) pocetnoDuguje = BigDecimal.ZERO;
            if (pocetnoPotrazuje == null) pocetnoPotrazuje = BigDecimal.ZERO;
            if (saldo == null) saldo = BigDecimal.ZERO;

            pocetnoDuguje = pocetnoDuguje.setScale(2, RoundingMode.HALF_UP);
            pocetnoPotrazuje = pocetnoPotrazuje.setScale(2, RoundingMode.HALF_UP);
            saldo = saldo.setScale(2, RoundingMode.HALF_UP);

            transakcije = transakcijaRepository
                    .findAllByStanAndDatumBetweenOrderByDatum(s, datumOd, datumDo);
        }

        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<>();

        if (!sve && (pocetnoDuguje.compareTo(BigDecimal.ZERO) != 0 || pocetnoPotrazuje.compareTo(BigDecimal.ZERO) != 0)) {
            TransakcijaZaStanDTO pocetna = new TransakcijaZaStanDTO();
            //pocetna.setDatumKnjizenja(datumOd.minusDays(1));
            pocetna.setDatumKnjizenja(datumOd);
            pocetna.setOpis("Početno stanje iz prethodnih godina");
            pocetna.setDuguje(pocetnoDuguje);
            pocetna.setPotrazuje(pocetnoPotrazuje);
            pocetna.setSaldo(saldo);

            tr.add(pocetna);

            duguje = duguje.add(pocetnoDuguje).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(pocetnoPotrazuje).setScale(2, RoundingMode.HALF_UP);
        }

        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();
            duguje = duguje.add(trans.getDuguje()).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2, RoundingMode.HALF_UP);
            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    
    /*
    public TransakcijeZaStanZbirnoDTO findAllByStanOrderByDatumAndOpis(Stan s, Boolean sve, String opis) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        out.setStan(s);

        List<Transakcija> transakcije;
        BigDecimal saldo = BigDecimal.ZERO;
        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<TransakcijaZaStanDTO>();

        String opisClean = opis == null ? "" : opis.trim();

        if (Boolean.TRUE.equals(sve)) {
            transakcije = transakcijaRepository.findAllByStanAndOpisContainingIgnoreCaseOrderByDatum(s, opisClean);
        } else {
            LocalDate today = LocalDate.now();
            LocalDate datumOd = today.withDayOfYear(1);
            LocalDate datumDo = LocalDate.of(today.getYear(), 12, 31);

            saldo = transakcijaRepository.getSaldoPreDatumaZaStanIOpis(s.getId(), datumOd, opisClean);
            if (saldo == null) {
                saldo = BigDecimal.ZERO;
            }

            transakcije = transakcijaRepository.findAllByStanAndDatumBetweenAndOpisContainingIgnoreCaseOrderByDatum(
                    s, datumOd, datumDo, opisClean
            );
        }

        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();
            duguje = duguje.add(trans.getDuguje()).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2, RoundingMode.HALF_UP);
            tr.add(trans);
        }
   
        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    */
 /*
    public TransakcijeZaStanZbirnoDTO findAllByStanOrderByDatumAndOpis(Stan s, Boolean sve, String opis) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        out.setStan(s);

        List<Transakcija> transakcije;
        BigDecimal saldo = BigDecimal.ZERO;
        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<>();

        String opisClean = opis == null ? "" : opis.trim();

        BigDecimal pocetnoDuguje = BigDecimal.ZERO;
        BigDecimal pocetnoPotrazuje = BigDecimal.ZERO;

        LocalDate datumOd = null;
        LocalDate datumDo = null;

        if (Boolean.TRUE.equals(sve)) {

            transakcije = transakcijaRepository
                    .findAllByStanAndOpisContainingIgnoreCaseOrderByDatum(s, opisClean);

        } else {

            LocalDate today = LocalDate.now();
            datumOd = today.withDayOfYear(1);
            datumDo = LocalDate.of(today.getYear(), 12, 31);

            // 🔥 početno stanje (pre godine + filter po opisu)
            pocetnoDuguje = transakcijaRepository
                    .getDugujePreDatumaZaStanIOpis(s.getId(), datumOd, opisClean);

            pocetnoPotrazuje = transakcijaRepository
                    .getPotrazujePreDatumaZaStanIOpis(s.getId(), datumOd, opisClean);

            saldo = transakcijaRepository
                    .getSaldoPreDatumaZaStanIOpis(s.getId(), datumOd, opisClean);

            if (pocetnoDuguje == null) pocetnoDuguje = BigDecimal.ZERO;
            if (pocetnoPotrazuje == null) pocetnoPotrazuje = BigDecimal.ZERO;
            if (saldo == null) saldo = BigDecimal.ZERO;

            pocetnoDuguje = pocetnoDuguje.setScale(2, RoundingMode.HALF_UP);
            pocetnoPotrazuje = pocetnoPotrazuje.setScale(2, RoundingMode.HALF_UP);
            saldo = saldo.setScale(2, RoundingMode.HALF_UP);

            transakcije = transakcijaRepository
                    .findAllByStanAndDatumBetweenAndOpisContainingIgnoreCaseOrderByDatum(
                            s, datumOd, datumDo, opisClean
                    );
        }
/*
        // 🔥 početna transakcija
        if (!Boolean.TRUE.equals(sve)
                && (pocetnoDuguje.compareTo(BigDecimal.ZERO) != 0
                || pocetnoPotrazuje.compareTo(BigDecimal.ZERO) != 0)) {

            TransakcijaZaStanDTO pocetna = new TransakcijaZaStanDTO();

            pocetna.setDatumKnjizenja(datumOd.minusDays(1));   // 👈 TRAŽENO
            pocetna.setOpis("Početno stanje iz prethodnih godina");
            pocetna.setDuguje(pocetnoDuguje);
            pocetna.setPotrazuje(pocetnoPotrazuje);
            pocetna.setSaldo(saldo);

            tr.add(pocetna);

            duguje = duguje.add(pocetnoDuguje).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(pocetnoPotrazuje).setScale(2, RoundingMode.HALF_UP);
        }
*/
    /*
        // 🔥 regularne transakcije
        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();
            duguje = duguje.add(trans.getDuguje()).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2, RoundingMode.HALF_UP);

            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    
    */
    
    public TransakcijeZaStanZbirnoDTO findAllByStanOrderByDatumAndOpis(
            Stan s,
            Boolean sve,
            String opis,
            Boolean racuni,
            Boolean uplate) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        out.setStan(s);

        List<Transakcija> transakcije;
        BigDecimal saldo = BigDecimal.ZERO;
        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<>();

        String opisClean = opis == null ? "" : opis.trim();

        BigDecimal pocetnoDuguje = BigDecimal.ZERO;
        BigDecimal pocetnoPotrazuje = BigDecimal.ZERO;

        LocalDate datumOd = null;
        LocalDate datumDo = null;

        if (Boolean.TRUE.equals(sve)) {

            transakcije = transakcijaRepository
                    .findAllByStanAndOpisContainingIgnoreCaseOrderByDatum(
                            s, opisClean, racuni, uplate);

        } else {

            LocalDate today = LocalDate.now();
            datumOd = today.withDayOfYear(1);
            datumDo = LocalDate.of(today.getYear(), 12, 31);

            pocetnoDuguje = transakcijaRepository
                    .getDugujePreDatumaZaStanIOpis(
                            s.getId(), datumOd, opisClean, racuni, uplate);

            pocetnoPotrazuje = transakcijaRepository
                    .getPotrazujePreDatumaZaStanIOpis(
                            s.getId(), datumOd, opisClean, racuni, uplate);

            if (Boolean.TRUE.equals(racuni) || Boolean.TRUE.equals(uplate)) {
                saldo = BigDecimal.ZERO;
            } else {
                saldo = transakcijaRepository
                        .getSaldoPreDatumaZaStanIOpis(
                                s.getId(), datumOd, opisClean, racuni, uplate);
            }

            if (pocetnoDuguje == null) pocetnoDuguje = BigDecimal.ZERO;
            if (pocetnoPotrazuje == null) pocetnoPotrazuje = BigDecimal.ZERO;
            if (saldo == null) saldo = BigDecimal.ZERO;

            pocetnoDuguje = pocetnoDuguje.setScale(2, RoundingMode.HALF_UP);
            pocetnoPotrazuje = pocetnoPotrazuje.setScale(2, RoundingMode.HALF_UP);
            saldo = saldo.setScale(2, RoundingMode.HALF_UP);

            transakcije = transakcijaRepository
                    .findAllByStanAndDatumBetweenAndOpisContainingIgnoreCaseOrderByDatum(
                            s, datumOd, datumDo, opisClean, racuni, uplate);
        }

        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();

            duguje = duguje.add(trans.getDuguje()).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2, RoundingMode.HALF_UP);

            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    
    
    
    
    /*
    public TransakcijeZaStanZbirnoDTO findAllByDodatniRacunOrderByDatum(String sifra, boolean sve) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        OstaliRacuni or = ostaliRacuniRepository.findBySifra(sifra);

        if (or == null) {
            out.setTransakcije(new ArrayList<TransakcijaZaStanDTO>());
            out.setDugujeUkupno(BigDecimal.ZERO);
            out.setPotrazujeUkupno(BigDecimal.ZERO);
            out.setSaldoUkupno(BigDecimal.ZERO);
            return out;
        }

        out.setOstaliRacuni(or);

        List<Transakcija> transakcije;
        BigDecimal saldo;

        if (sve) {
            transakcije = transakcijaRepository.findAllByOstaliRacuniOrderByDatum(or);
            saldo = BigDecimal.ZERO;
        } else {
            LocalDate today = LocalDate.now();
            LocalDate datumOd = today.withDayOfYear(1);
            LocalDate datumDo = LocalDate.of(today.getYear(), 12, 31);

            saldo = transakcijaRepository.getSaldoPreDatumaZaOstaleRacune(or.getId(), datumOd);
            if (saldo == null) {
                saldo = BigDecimal.ZERO;
            }

            transakcije = transakcijaRepository
                    .findAllByOstaliRacuniAndDatumBetweenOrderByDatum(or, datumOd, datumDo);
        }

        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<TransakcijaZaStanDTO>();

        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();
            duguje = duguje.add(trans.getDuguje());
            potrazuje = potrazuje.add(trans.getPotrazuje());
            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    */
    
    public TransakcijeZaStanZbirnoDTO findAllByDodatniRacunOrderByDatum(String sifra, boolean sve) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        OstaliRacuni or = ostaliRacuniRepository.findBySifra(sifra);

        if (or == null) {
            out.setTransakcije(new ArrayList<TransakcijaZaStanDTO>());
            out.setDugujeUkupno(BigDecimal.ZERO);
            out.setPotrazujeUkupno(BigDecimal.ZERO);
            out.setSaldoUkupno(BigDecimal.ZERO);
            return out;
        }

        out.setOstaliRacuni(or);

        List<Transakcija> transakcije;
        BigDecimal saldo;

        BigDecimal pocetnoDuguje = BigDecimal.ZERO;
        BigDecimal pocetnoPotrazuje = BigDecimal.ZERO;

        LocalDate datumOd = null;
        LocalDate datumDo = null;

        if (sve) {
            transakcije = transakcijaRepository.findAllByOstaliRacuniOrderByDatum(or);
            saldo = BigDecimal.ZERO;
        } else {
            LocalDate today = LocalDate.now();
            datumOd = today.withDayOfYear(1);
            datumDo = LocalDate.of(today.getYear(), 12, 31);

            pocetnoDuguje = transakcijaRepository.getDugujePreDatumaZaOstaleRacune(or.getId(), datumOd);
            pocetnoPotrazuje = transakcijaRepository.getPotrazujePreDatumaZaOstaleRacune(or.getId(), datumOd);
            saldo = transakcijaRepository.getSaldoPreDatumaZaOstaleRacune(or.getId(), datumOd);

            if (pocetnoDuguje == null) {
                pocetnoDuguje = BigDecimal.ZERO;
            }
            if (pocetnoPotrazuje == null) {
                pocetnoPotrazuje = BigDecimal.ZERO;
            }
            if (saldo == null) {
                saldo = BigDecimal.ZERO;
            }

            pocetnoDuguje = pocetnoDuguje.setScale(2, RoundingMode.HALF_UP);
            pocetnoPotrazuje = pocetnoPotrazuje.setScale(2, RoundingMode.HALF_UP);
            saldo = saldo.setScale(2, RoundingMode.HALF_UP);

            transakcije = transakcijaRepository
                    .findAllByOstaliRacuniAndDatumBetweenOrderByDatum(or, datumOd, datumDo);
        }

        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<TransakcijaZaStanDTO>();

        if (!sve
                && (pocetnoDuguje.compareTo(BigDecimal.ZERO) != 0
                || pocetnoPotrazuje.compareTo(BigDecimal.ZERO) != 0)) {

            TransakcijaZaStanDTO pocetna = new TransakcijaZaStanDTO();
            pocetna.setDatumKnjizenja(datumOd.minusDays(1));
            pocetna.setOpis("Početno stanje iz prethodnih godina");
            pocetna.setDuguje(pocetnoDuguje);
            pocetna.setPotrazuje(pocetnoPotrazuje);
            pocetna.setSaldo(saldo);

            tr.add(pocetna);

            duguje = duguje.add(pocetnoDuguje).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(pocetnoPotrazuje).setScale(2, RoundingMode.HALF_UP);
        }

        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();

            duguje = duguje.add(trans.getDuguje()).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2, RoundingMode.HALF_UP);

            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    
    /*
    public TransakcijeZaStanZbirnoDTO findAllByDodatniRacunOrderByDatumAndOpis(String sifra, Boolean sve, String opis) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        OstaliRacuni or = ostaliRacuniRepository.findBySifra(sifra);

        if (or != null) {
            out.setOstaliRacuni(or);
        } else {
            out.setDugujeUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setPotrazujeUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setSaldoUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setTransakcije(new ArrayList<TransakcijaZaStanDTO>());
            return out;
        }

        List<Transakcija> transakcije;
        BigDecimal saldo = BigDecimal.ZERO;
        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<TransakcijaZaStanDTO>();

        String opisClean = opis == null ? "" : opis.trim();

        if (Boolean.TRUE.equals(sve)) {
            transakcije = transakcijaRepository.findAllByOstaliRacuniAndOpisContainingIgnoreCaseOrderByDatum(or, opisClean);
        } else {
            LocalDate today = LocalDate.now();
            LocalDate datumOd = today.withDayOfYear(1);
            LocalDate datumDo = LocalDate.of(today.getYear(), 12, 31);

            saldo = transakcijaRepository.getSaldoPreDatumaZaOstaleRacuneIOpis(or.getId(), datumOd, opisClean);
            if (saldo == null) {
                saldo = BigDecimal.ZERO;
            }

            transakcije = transakcijaRepository.findAllByOstaliRacuniAndDatumBetweenAndOpisContainingIgnoreCaseOrderByDatum(
                    or, datumOd, datumDo, opisClean
            );
        }

        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();
            duguje = duguje.add(trans.getDuguje()).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2, RoundingMode.HALF_UP);
            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    
    */
    /*
    public TransakcijeZaStanZbirnoDTO findAllByDodatniRacunOrderByDatumAndOpis(String sifra, Boolean sve, String opis) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        OstaliRacuni or = ostaliRacuniRepository.findBySifra(sifra);

        if (or != null) {
            out.setOstaliRacuni(or);
        } else {
            out.setDugujeUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setPotrazujeUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setSaldoUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setTransakcije(new ArrayList<TransakcijaZaStanDTO>());
            return out;
        }

        List<Transakcija> transakcije;
        BigDecimal saldo = BigDecimal.ZERO;
        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<TransakcijaZaStanDTO>();

        String opisClean = opis == null ? "" : opis.trim();

        BigDecimal pocetnoDuguje = BigDecimal.ZERO;
        BigDecimal pocetnoPotrazuje = BigDecimal.ZERO;

        LocalDate datumOd = null;
        LocalDate datumDo = null;

        if (Boolean.TRUE.equals(sve)) {

            transakcije = transakcijaRepository
                    .findAllByOstaliRacuniAndOpisContainingIgnoreCaseOrderByDatum(or, opisClean);

        } else {

            LocalDate today = LocalDate.now();
            datumOd = today.withDayOfYear(1);
            datumDo = LocalDate.of(today.getYear(), 12, 31);

            pocetnoDuguje = transakcijaRepository
                    .getDugujePreDatumaZaOstaleRacuneIOpis(or.getId(), datumOd, opisClean);

            pocetnoPotrazuje = transakcijaRepository
                    .getPotrazujePreDatumaZaOstaleRacuneIOpis(or.getId(), datumOd, opisClean);

            saldo = transakcijaRepository
                    .getSaldoPreDatumaZaOstaleRacuneIOpis(or.getId(), datumOd, opisClean);

            if (pocetnoDuguje == null) {
                pocetnoDuguje = BigDecimal.ZERO;
            }
            if (pocetnoPotrazuje == null) {
                pocetnoPotrazuje = BigDecimal.ZERO;
            }
            if (saldo == null) {
                saldo = BigDecimal.ZERO;
            }

            pocetnoDuguje = pocetnoDuguje.setScale(2, RoundingMode.HALF_UP);
            pocetnoPotrazuje = pocetnoPotrazuje.setScale(2, RoundingMode.HALF_UP);
            saldo = saldo.setScale(2, RoundingMode.HALF_UP);

            transakcije = transakcijaRepository
                    .findAllByOstaliRacuniAndDatumBetweenAndOpisContainingIgnoreCaseOrderByDatum(
                            or, datumOd, datumDo, opisClean
                    );
        }
       /*
        if (!Boolean.TRUE.equals(sve)
                && (pocetnoDuguje.compareTo(BigDecimal.ZERO) != 0
                || pocetnoPotrazuje.compareTo(BigDecimal.ZERO) != 0)) {

            TransakcijaZaStanDTO pocetna = new TransakcijaZaStanDTO();
            pocetna.setDatumKnjizenja(datumOd.minusDays(1));
            pocetna.setOpis("Početno stanje iz prethodnih godina");
            pocetna.setDuguje(pocetnoDuguje);
            pocetna.setPotrazuje(pocetnoPotrazuje);
            pocetna.setSaldo(saldo);

            tr.add(pocetna);

            duguje = duguje.add(pocetnoDuguje).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(pocetnoPotrazuje).setScale(2, RoundingMode.HALF_UP);
        }
*//*
        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();
            duguje = duguje.add(trans.getDuguje()).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(trans.getPotrazuje()).setScale(2, RoundingMode.HALF_UP);
            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    */
    public TransakcijeZaStanZbirnoDTO findAllByDodatniRacunOrderByDatumAndOpis(
            String sifra,
            Boolean sve,
            String opis,
            Boolean racuni,
            Boolean uplate) {

        TransakcijeZaStanZbirnoDTO out = new TransakcijeZaStanZbirnoDTO();
        OstaliRacuni or = ostaliRacuniRepository.findBySifra(sifra);

        if (or != null) {
            out.setOstaliRacuni(or);
        } else {
            out.setDugujeUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setPotrazujeUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setSaldoUkupno(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
            out.setTransakcije(new ArrayList<TransakcijaZaStanDTO>());
            return out;
        }

        List<Transakcija> transakcije;
        BigDecimal saldo = BigDecimal.ZERO;
        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> tr = new ArrayList<TransakcijaZaStanDTO>();

        String opisClean = opis == null ? "" : opis.trim();

        BigDecimal pocetnoDuguje = BigDecimal.ZERO;
        BigDecimal pocetnoPotrazuje = BigDecimal.ZERO;

        LocalDate datumOd = null;
        LocalDate datumDo = null;

        if (Boolean.TRUE.equals(sve)) {

            transakcije = transakcijaRepository
                    .findAllByOstaliRacuniAndOpisContainingIgnoreCaseOrderByDatum(
                            or,
                            opisClean,
                            racuni,
                            uplate
                    );

        } else {

            LocalDate today = LocalDate.now();
            datumOd = today.withDayOfYear(1);
            datumDo = LocalDate.of(today.getYear(), 12, 31);

            pocetnoDuguje = transakcijaRepository
                    .getDugujePreDatumaZaOstaleRacuneIOpis(
                            or.getId(),
                            datumOd,
                            opisClean,
                            racuni,
                            uplate
                    );

            pocetnoPotrazuje = transakcijaRepository
                    .getPotrazujePreDatumaZaOstaleRacuneIOpis(
                            or.getId(),
                            datumOd,
                            opisClean,
                            racuni,
                            uplate
                    );

            saldo = transakcijaRepository
                    .getSaldoPreDatumaZaOstaleRacuneIOpis(
                            or.getId(),
                            datumOd,
                            opisClean,
                            racuni,
                            uplate
                    );

            if (pocetnoDuguje == null) {
                pocetnoDuguje = BigDecimal.ZERO;
            }

            if (pocetnoPotrazuje == null) {
                pocetnoPotrazuje = BigDecimal.ZERO;
            }

            if (saldo == null) {
                saldo = BigDecimal.ZERO;
            }

            pocetnoDuguje = pocetnoDuguje.setScale(2, RoundingMode.HALF_UP);
            pocetnoPotrazuje = pocetnoPotrazuje.setScale(2, RoundingMode.HALF_UP);
            saldo = saldo.setScale(2, RoundingMode.HALF_UP);

            transakcije = transakcijaRepository
                    .findAllByOstaliRacuniAndDatumBetweenAndOpisContainingIgnoreCaseOrderByDatum(
                            or,
                            datumOd,
                            datumDo,
                            opisClean,
                            racuni,
                            uplate
                    );
        }
        
        
       
        /*
        if (!Boolean.TRUE.equals(sve)
                && (pocetnoDuguje.compareTo(BigDecimal.ZERO) != 0
                || pocetnoPotrazuje.compareTo(BigDecimal.ZERO) != 0)) {

            TransakcijaZaStanDTO pocetna = new TransakcijaZaStanDTO();
            pocetna.setDatumKnjizenja(datumOd.minusDays(1));
            pocetna.setOpis("Početno stanje iz prethodnih godina");
            pocetna.setDuguje(pocetnoDuguje);
            pocetna.setPotrazuje(pocetnoPotrazuje);
            pocetna.setSaldo(saldo);

            tr.add(pocetna);

            duguje = duguje.add(pocetnoDuguje).setScale(2, RoundingMode.HALF_UP);
            potrazuje = potrazuje.add(pocetnoPotrazuje).setScale(2, RoundingMode.HALF_UP);
        }
        */

        for (Transakcija t : transakcije) {
            TransakcijaZaStanDTO trans = new TransakcijaZaStanDTO(t, saldo);
            saldo = trans.getSaldo();

            duguje = duguje
                    .add(trans.getDuguje())
                    .setScale(2, RoundingMode.HALF_UP);

            potrazuje = potrazuje
                    .add(trans.getPotrazuje())
                    .setScale(2, RoundingMode.HALF_UP);

            tr.add(trans);
        }

        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        out.setTransakcije(tr);

        return out;
    }
    
    
    // Otvorene stavke: raspodela samo u memoriji, bez izmene knjizenja.
    private static final class OtvorenaPozicija {
        final Transakcija transakcija;
        BigDecimal ostatak;
        final String period;
        final List<toplana.web.rest.dto.RaspodelaUplateDTO> raspodele = new ArrayList<>();
        OtvorenaPozicija(Transakcija t, BigDecimal iznos, String period) {
            this.transakcija = t;
            this.ostatak = iznos;
            this.period = period;
        }
    }

    private static BigDecimal iznosIliNula(BigDecimal v) {
        return v == null ? BigDecimal.ZERO : v;
    }

    // Vraca yyyy-MM; nepoznate i kombinovane oznake se NE tumace.
    private static String normalizujPeriod(String tekst) {
        if (tekst == null) return null;
        String s = java.text.Normalizer.normalize(tekst.trim().toUpperCase(java.util.Locale.ROOT),
                java.text.Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        s = s.replace('А','A').replace('В','V').replace('Г','G').replace('Д','D')
             .replace('Е','E').replace('Ј','J').replace('К','K').replace('Л','L')
             .replace('М','M').replace('Н','N').replace('О','O').replace('П','P')
             .replace('Р','R').replace('С','S').replace('Т','T').replace('У','U')
             .replace('Ф','F').replace('Б','B').replace('Ц','C').replace('И','I');
        String[] nazivi = {"JANUAR", "FEBRUAR", "MART", "APRIL", "MAJ", "JUN", "JUL", "AVGUST", "SEPTEMBAR", "OKTOBAR", "NOVEMBAR", "DECEMBAR"};
        String[] kratki = {"JAN", "FEB", "MAR", "APR", "MAJ", "JUN", "JUL", "AVG", "SEP", "OKT", "NOV", "DEC"};
        for (int i = 0; i < 12; i++) {
            String p = "(?:" + nazivi[i] + "|" + kratki[i] + ")";
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("^" + p + "[ ._-]*(20[0-9]{2}|[0-9]{2})$").matcher(s);
            if (m.matches()) {
                int godina = Integer.parseInt(m.group(1));
                if (godina < 100) godina += 2000;
                return String.format(java.util.Locale.ROOT, "%04d-%02d", godina, i + 1);
            }
        }
        return null;
    }

    // Broj izvrsnog predmeta, npr. IIVK404/26 ili IVK 404/2026.
    // Ne spajamo IVK i IIVK niti razlicite brojeve predmeta.
    private static String oznakaIzvrsenja(String tekst) {
        if (tekst == null) return null;
        String s = java.text.Normalizer.normalize(tekst.toUpperCase(java.util.Locale.ROOT),
                java.text.Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                "(?<![A-Z0-9])(IIVK|IVK)\\s*[-:]?\\s*(\\d+)\\s*/\\s*(\\d{2,4})(?![0-9])")
                .matcher(s);
        if (!m.find()) return null;
        String godina = m.group(3);
        if (godina.length() == 4 && godina.startsWith("20")) godina = godina.substring(2);
        return m.group(1) + m.group(2) + "/" + godina;
    }

    private static String predmetTransakcije(Transakcija t) {
        // Na fakturi je predmet u RACUN.OPIS_RACUNA, ne u opisu transakcije.
        if (t.getRacun() != null) {
            String izRacuna = oznakaIzvrsenja(t.getRacun().getOpisRacuna());
            if (izRacuna != null) return izRacuna;
        }
        // Izvrsni predmet moze biti vezan i za stavku utuzenja.
        if (t.getStavkaUtuzenja() != null) {
            String izUtuzenja = oznakaIzvrsenja(t.getStavkaUtuzenja().getOpis());
            if (izUtuzenja != null) return izUtuzenja;
        }
        String oznaka = oznakaIzvrsenja(t.getSifraDokumenta());
        if (oznaka != null) return oznaka;
        if (t.getStavkaIzvoda() != null) {
            oznaka = oznakaIzvrsenja(t.getStavkaIzvoda().getOpis());
            if (oznaka != null) return oznaka;
        }
        return oznakaIzvrsenja(t.getOpis());
    }

    // Uplata po predmetu moze samo na isti predmet. Redovna uplata
    // moze samo na redovan racun. Iznos nikada nije kriterijum uparivanja.
    private static boolean dozvoljenaRaspodela(OtvorenaPozicija uplata, OtvorenaPozicija racun) {
        String u = predmetTransakcije(uplata.transakcija);
        String r = predmetTransakcije(racun.transakcija);
        return u == null ? r == null : u.equals(r);
    }

    private static void rasporedi(OtvorenaPozicija uplata, OtvorenaPozicija racun,
            java.util.Map<Long, toplana.web.rest.dto.PregledUplateDTO> pregledi) {
        BigDecimal dodeljeno = uplata.ostatak.min(racun.ostatak);
        if (dodeljeno.signum() > 0) {
            uplata.ostatak = uplata.ostatak.subtract(dodeljeno);
            racun.ostatak = racun.ostatak.subtract(dodeljeno);
            String opisUplate = uplata.transakcija.getStavkaIzvoda() == null ? null : uplata.transakcija.getStavkaIzvoda().getOpis();
            if (opisUplate == null || opisUplate.trim().isEmpty()) opisUplate = uplata.transakcija.getOpis();
            racun.raspodele.add(new toplana.web.rest.dto.RaspodelaUplateDTO(
                    uplata.transakcija.getId(), uplata.transakcija.getDatum(),
                    uplata.transakcija.getSifraDokumenta(), opisUplate, dodeljeno));
            // Trag po uplati: ukljucuje i racune koji su potpuno zatvoreni.
            // Ne menja redosled niti iznose raspodele.
            if (pregledi != null) {
                toplana.web.rest.dto.PregledUplateDTO pregled = pregledi.get(uplata.transakcija.getId());
                if (pregled != null) {
                    String cilj = racun.transakcija.getRacun() != null
                            ? racun.transakcija.getRacun().getPeriod()
                            : racun.transakcija.getSifraDokumenta();
                    pregled.dodajRaspodelu(racun.transakcija.getId(), cilj,
                            racun.transakcija.getDatum(), dodeljeno);
                }
            }
        }
    }

    private TransakcijeZaStanZbirnoDTO obracunajOtvoreneStavke(
            List<Transakcija> sve, Stan stan, OstaliRacuni dodatniRacun) {
        toplana.web.rest.dto.OtvoreneStavkeZbirnoDTO out = new toplana.web.rest.dto.OtvoreneStavkeZbirnoDTO();
        if (stan != null) out.setStan(stan);
        if (dodatniRacun != null) out.setOstaliRacuni(dodatniRacun);

        java.util.Map<Long, toplana.web.rest.dto.PregledUplateDTO> pregledi = new java.util.LinkedHashMap<>();
        List<OtvorenaPozicija> racuni = new ArrayList<>();
        List<OtvorenaPozicija> uplate = new ArrayList<>();
        List<OtvorenaPozicija> korekcije = new ArrayList<>();
        BigDecimal ocekivaniSaldo = BigDecimal.ZERO;
        Comparator<OtvorenaPozicija> redosled = Comparator
                .comparing((OtvorenaPozicija x) -> x.transakcija.getDatum(),
                        Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(x -> x.transakcija.getId(), Comparator.nullsLast(Comparator.naturalOrder()));
        List<Transakcija> hronoloski = new ArrayList<>(sve);
        hronoloski.sort(Comparator
                .comparing(Transakcija::getDatum, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Transakcija::getId, Comparator.nullsLast(Comparator.naturalOrder())));

        // Jedinstven hronoloski prolaz. Nijedna transakcija ne sme biti
        // rasporedjena na dug koji jos nije nastao. Ne radi se zavrsni FIFO.
        for (Transakcija t : hronoloski) {
            BigDecimal d = iznosIliNula(t.getDuguje());
            BigDecimal p = iznosIliNula(t.getPotrazuje());
            ocekivaniSaldo = ocekivaniSaldo.add(d).subtract(p);
            BigDecimal neto = d.subtract(p);
            if (neto.signum() == 0) continue;

            boolean migracioni = d.signum() > 0 && p.signum() > 0
                    && "ZR 22".equalsIgnoreCase(t.getSifraDokumenta() == null
                            ? "" : t.getSifraDokumenta().trim());
            if (d.signum() > 0 && p.signum() > 0 && !migracioni) {
                throw new IllegalStateException("Nepodrzano dvostrano knjizenje, ID " + t.getId());
            }

            if (neto.signum() > 0) {
                String period = d.signum() > 0 && p.signum() == 0 && t.getRacun() != null
                        ? normalizujPeriod(t.getRacun().getPeriod()) : null;
                OtvorenaPozicija r = new OtvorenaPozicija(t, neto, period);
                racuni.add(r);

                // Prethodne uplate su vec obradjene na datum knjizenja.
                // Nerasporedjena pretplata moze pokriti novi racun.
                // Prethodne pretplate i otpisi mogu pokriti novi dug.
                // Njihov redosled mora biti zajednicki hronoloski, ne po grupama.
                List<OtvorenaPozicija> slobodnaSredstva = new ArrayList<>();
                for (OtvorenaPozicija u : uplate)
                    if (u.ostatak.signum() > 0) slobodnaSredstva.add(u);
                for (OtvorenaPozicija k : korekcije)
                    if (k.ostatak.signum() > 0) slobodnaSredstva.add(k);
                slobodnaSredstva.sort(redosled);
                for (OtvorenaPozicija u : slobodnaSredstva) {
                    if (r.ostatak.signum() == 0) break;
                    if (dozvoljenaRaspodela(u, r)) rasporedi(u, r, pregledi);
                }
            } else {
                boolean korekcija = d.signum() < 0 && p.signum() == 0;
                String period = null;
                if (!korekcija && !migracioni) {
                    String opis = t.getStavkaIzvoda() == null ? null : t.getStavkaIzvoda().getOpis();
                    period = normalizujPeriod(opis);
                    if (period == null) period = normalizujPeriod(t.getOpis());
                }
                OtvorenaPozicija u = new OtvorenaPozicija(t, neto.negate(), period);
                if (korekcija) korekcije.add(u); else uplate.add(u);
                String opisUplate = t.getStavkaIzvoda() == null ? null : t.getStavkaIzvoda().getOpis();
                if (opisUplate == null || opisUplate.trim().isEmpty()) opisUplate = t.getOpis();
                pregledi.put(t.getId(), new toplana.web.rest.dto.PregledUplateDTO(
                        t.getId(), t.getDatum(), t.getSifraDokumenta(), opisUplate,
                        neto.negate(), korekcija ? "KOREKCIJA" : "UPLATA"));

                // Strogo razdvajanje IVK/IIVK predmeta i redovnih uplata.
                // Bez odgovarajuceg oznacenog zaduzenja uplata ostaje
                // nerasporedjena; ne sme se proizvoljno vezati za ZR 22.
                if (period != null) {
                    for (OtvorenaPozicija r : racuni) {
                        if (u.ostatak.signum() == 0) break;
                        if (period.equals(r.period) && r.ostatak.signum() > 0
                                && dozvoljenaRaspodela(u, r))
                            rasporedi(u, r, pregledi);
                    }
                }
                for (OtvorenaPozicija r : racuni) {
                    if (u.ostatak.signum() == 0) break;
                    if (r.ostatak.signum() > 0 && dozvoljenaRaspodela(u, r))
                        rasporedi(u, r, pregledi);
                }
            }
        }
        // Ne vrsi se naknadna preraspodela vec rasporedjenih uplata.
        // Nerasporedjena pretplata moze pokriti buduci racun korisnika.
        racuni.sort(redosled);
        uplate.sort(redosled);
        korekcije.sort(redosled);

        List<OtvorenaPozicija> otvorene = new ArrayList<>();
        for (OtvorenaPozicija r : racuni) if (r.ostatak.signum() > 0) otvorene.add(r);
        for (OtvorenaPozicija u : uplate) if (u.ostatak.signum() > 0) otvorene.add(u);
        for (OtvorenaPozicija k : korekcije) if (k.ostatak.signum() > 0) otvorene.add(k);
        otvorene.sort(redosled);
        BigDecimal saldo = BigDecimal.ZERO;
        BigDecimal duguje = BigDecimal.ZERO;
        BigDecimal potrazuje = BigDecimal.ZERO;
        List<TransakcijaZaStanDTO> rezultat = new ArrayList<>();
        for (OtvorenaPozicija o : otvorene) {
            TransakcijaZaStanDTO dto = new TransakcijaZaStanDTO(o.transakcija, saldo);
            // Za migracioni zapis obe strane su pozitivne: smer odredjuje neto iznos.
            BigDecimal brutoD = iznosIliNula(o.transakcija.getDuguje());
            BigDecimal brutoP = iznosIliNula(o.transakcija.getPotrazuje());
            boolean migracioni = brutoD.signum() > 0 && brutoP.signum() > 0
                    && "ZR 22".equalsIgnoreCase(o.transakcija.getSifraDokumenta() == null
                            ? "" : o.transakcija.getSifraDokumenta().trim());
            boolean zaduzivanje = migracioni ? brutoD.compareTo(brutoP) > 0
                    : (brutoD.signum() > 0 || brutoP.signum() < 0);
            BigDecimal d = zaduzivanje ? o.ostatak : BigDecimal.ZERO;
            BigDecimal p = zaduzivanje ? BigDecimal.ZERO : o.ostatak;
            saldo = saldo.add(d).subtract(p);
            duguje = duguje.add(d);
            potrazuje = potrazuje.add(p);
            dto.setDuguje(d);
            dto.setPotrazuje(p);
            dto.setSaldo(saldo);
            BigDecimal originalni = migracioni ? brutoD.subtract(brutoP).abs()
                    : (zaduzivanje ? (brutoD.signum() > 0 ? brutoD : brutoP.negate())
                            : (brutoP.signum() > 0 ? brutoP : brutoD.negate()));
            dto.setOriginalniIznos(originalni);
            dto.setRasporedjenoUkupno(originalni.subtract(o.ostatak));
            dto.setRaspodelaUplata(new ArrayList<>(o.raspodele));
            rezultat.add(dto);
        }
        if (saldo.compareTo(ocekivaniSaldo) != 0) {
            throw new IllegalStateException("Otvorene stavke: saldo " + saldo
                    + " nije jednak saldu svih transakcija " + ocekivaniSaldo);
        }
        out.setDugujeUkupno(duguje);
        out.setPotrazujeUkupno(potrazuje);
        out.setSaldoUkupno(saldo);
        // Svaka uplata i korekcija mora biti knjigovodstveno konzistentna.
        for (OtvorenaPozicija u : uplate) {
            toplana.web.rest.dto.PregledUplateDTO pr = pregledi.get(u.transakcija.getId());
            if (pr != null) pr.setNerasporedjeno(u.ostatak);
            if (u.ostatak.signum() < 0) throw new IllegalStateException("Negativan ostatak uplate ID " + u.transakcija.getId());
        }
        for (OtvorenaPozicija u : korekcije) {
            toplana.web.rest.dto.PregledUplateDTO pr = pregledi.get(u.transakcija.getId());
            if (pr != null) pr.setNerasporedjeno(u.ostatak);
            if (u.ostatak.signum() < 0) throw new IllegalStateException("Negativan ostatak korekcije ID " + u.transakcija.getId());
        }
        // Pregled se i dalje prenosi kroz prvi DTO red; ovo je ogranicenje
        // postojeceg DTO ugovora kada su sva zaduzenja zatvorena.
        out.setPregledUplata(new ArrayList<>(pregledi.values()));
        if (!rezultat.isEmpty()) rezultat.get(0).setPregledUplata(new ArrayList<>(pregledi.values()));
        out.setTransakcije(rezultat);
        return out;
    }

    public TransakcijeZaStanZbirnoDTO findOtvoreneStavkeZaStan(Stan stan) {
        return obracunajOtvoreneStavke(transakcijaRepository.findAllByStanOrderByDatum(stan), stan, null);
    }

    public TransakcijeZaStanZbirnoDTO findOtvoreneStavkeZaDodatniRacun(String sifraRacuna) {
        OstaliRacuni or = ostaliRacuniRepository.findBySifra(sifraRacuna);
        if (or == null) return obracunajOtvoreneStavke(new ArrayList<>(), null, null);
        return obracunajOtvoreneStavke(transakcijaRepository.findAllByOstaliRacuniOrderByDatum(or), null, or);
    }

 /*
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
 */
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
    	
    	
    	 LocalDate today = LocalDate.now();
    	
    	 if(search.getDatumOd() != null) {
     		datumOdNotExists = 0;
     		datumOd = search.getDatumOd();
     	}
     	
     	if(search.getDatumDo() != null) {
     		datumDoNotExists = 0;
     		datumDo = search.getDatumDo();
     	}
    	
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
    	
    	
    	
    	
    	if(search.getSifraStana() != null && !search.getSifraStana().trim().equals("")) {
    		sifraNotExists = 0;
    		sifra = "%" + search.getSifraStana() + "%";
    	}
    	
    	 String p = search.getPrezime();
    	 p = (p == null) ? "" : p.trim();
    	 search.setPrezime(p);
    	 
	   	 
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
    		
    	if (out == null) return Collections.emptyList();
    	else {
	   	 	out.removeIf(r ->
	   	 		(saldoOdFinal != null && r.getStanje() != null && r.getStanje().compareTo(saldoOdFinal) < 0) ||
	   	 		(saldoDoFinal != null && r.getStanje() != null && r.getStanje().compareTo(saldoDoFinal) > 0)
	   	 			);
    	}
    	
    	
    	return out;
    }
    
    
    /****************************************************************************************************************
     * Pravi se PDF sa transakcijama dnevnikom    
     * @param rps
     * @return
     *****************************************************************************************************************/
        public String generateReportTransakcijaDnevnik(List<TransakcijaStanUkupnoDTO> rps) {
    		 
    		try {
    			
    			ClassPathResource cl = new ClassPathResource("/jasper/TransS.jrxml");
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
    			JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPutanja + "\\TransS.pdf");
    		
    		}catch(Exception e) {
    			e.printStackTrace();
    		}           
    		
    		return pdfPutanja + "\\TransS.pdf";             
        }
        
        
        public byte[] generateReportTransakcijaDnevnikB(List<TransakcijaStanUkupnoDTO> rps) {
            try {
                ClassPathResource cl = new ClassPathResource("/jasper/TransS.jrxml");
                InputStream input = cl.getInputStream();

                JasperReport jasperReport = JasperCompileManager.compileReport(input);
                JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(rps);

                Map<String, Object> parameters = new HashMap<>();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);

                return JasperExportManager.exportReportToPdf(jasperPrint);

            } catch (Exception e) {
                throw new RuntimeException("Greška pri generisanju PDF izveštaja.", e);
            }
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
    public String generateReportAnalitickiDnevnik(List<TransakcijaZaStanDTO> rps,  List<DugujePotrazujeReoni> dpr) {
		 
		try {
			
			BigDecimal lokaliDuguje = BigDecimal.ZERO;
	        BigDecimal lokaliPotrazuje = BigDecimal.ZERO;
	        BigDecimal ostaliDuguje = BigDecimal.ZERO;
	        BigDecimal ostaliPotrazuje = BigDecimal.ZERO;
	        BigDecimal ukupnoDuguje = BigDecimal.ZERO;
	        BigDecimal ukupnoPotrazuje = BigDecimal.ZERO;

	

	        for (DugujePotrazujeReoni reon : dpr) {

	            int tip = reon.getTipPotrosacaInteger();

	            if (tip == 5) {
	                lokaliDuguje = lokaliDuguje.add(
	                    reon.getDuguje() != null ? reon.getDuguje() : BigDecimal.ZERO
	                );

	                lokaliPotrazuje = lokaliPotrazuje.add(
	                    reon.getPotrazuje() != null ? reon.getPotrazuje() : BigDecimal.ZERO
	                );
	            } else {
	                // sve ostalo ide u OSTALI
	                ostaliDuguje = ostaliDuguje.add(
	                    reon.getDuguje() != null ? reon.getDuguje() : BigDecimal.ZERO
	                );

	                ostaliPotrazuje = ostaliPotrazuje.add(
	                    reon.getPotrazuje() != null ? reon.getPotrazuje() : BigDecimal.ZERO
	                );
	            }
	        }
	        
	 			
	        Map<String, Object> params = new HashMap<>();
	        params.put("lokaliDuguje", lokaliDuguje);
	        params.put("lokaliPotrazuje", lokaliPotrazuje);
	        params.put("ostaliDuguje", ostaliDuguje);
	        params.put("ostaliPotrazuje", ostaliPotrazuje);
	        params.put("ukupnoDuguje", ukupnoDuguje);      
	        params.put("ukupnoPotrazuje", ukupnoPotrazuje);
			
			ClassPathResource cl = new ClassPathResource("/jasper/AnDnevnik.jrxml");
			InputStream input = cl.getInputStream();
			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(input);
			// Get your data source
			JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(rps); 
			// Add parameters    
			
			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);
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
        public List<RacunDTO> rekapitulacijaSifraPromeneDatum(SearchTransakcijaDTO search, Cene cene){
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
        	List<RacunDTO> out_sum = RacunUtils.groupAndSumBySifraPrefix(out, cene);
        	
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
    public byte[] generateReportAnalitickaKartica(TransakcijeZaStanZbirnoDTO trans) {

        try {
        	
       
            ClassPathResource cl = new ClassPathResource("/jasper/AnalitickaKartica.jrxml");
            InputStream input = cl.getInputStream();

            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(trans.getTransakcije());

            
           
            Map<String, Object> parameters = new HashMap<>();
           
            
            parameters.put("dugujeUkupno", trans.getDugujeUkupno());
            parameters.put("potrazujeUkupno", trans.getPotrazujeUkupno());
            parameters.put("saldoUkupno", trans.getSaldoUkupno());

                       
            Stan stan = trans.getStan();

            parameters.put("sifraStana", stan.getSifra());
            parameters.put("grad", stan.getGrad());
            parameters.put("ulica", stan.getUlica());
            parameters.put("broj", stan.getBroj());
            parameters.put("ulaz", stan.getUlaz());
            parameters.put("ukljucen", stan.getUkljucen());
            
            
            if (stan.getVlasnik() != null) {
            	parameters.put("ime", stan.getVlasnik().getIme());
            	parameters.put("prezime", stan.getVlasnik().getPrezime());   
            }
  
            
            String adresa = 
            	    (stan.getUlica() == null ? "" : stan.getUlica()) +
            	    (stan.getBroj() == null ? "" : " " + stan.getBroj()) +
            	    (stan.getUlaz() == null ? "" : "/" + stan.getUlaz()) +
            	    (stan.getGrad() == null ? "" : ", " + stan.getGrad());

            parameters.put("adresa", adresa);
             
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            throw new IllegalStateException("Greska pri generisanju PDF izvestaja", e);
        }

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
    
    @Transactional(readOnly = true)
    public List<Transakcija> findAllByStavkaUtuzenja(Long stavkaUtuzenjaId) {
        return transakcijaRepository.findAllByStavkaUtuzenjaIdOrderByDatum(stavkaUtuzenjaId);
    }
    
}
