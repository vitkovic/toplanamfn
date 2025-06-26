package toplana.web.rest;

import toplana.domain.Cene;
import toplana.domain.NacrtRacuna;
import toplana.domain.Podstanica;
import toplana.domain.Racun;
import toplana.domain.SifraPromene;
import toplana.domain.Stan;
import toplana.domain.StanStanje;
import toplana.domain.StanjaPodstanice;
import toplana.domain.StanjaPodstaniceZaRacun;
import toplana.domain.Transakcija;
import toplana.domain.User;
import toplana.repository.CeneRepository;
import toplana.repository.NacrtRacunaRepository;
import toplana.repository.PodstanicaRepository;
import toplana.repository.RacunRepository;
import toplana.repository.SifraPromeneRepository;
import toplana.repository.StanRepository;
import toplana.repository.StanStanjeRepository;
import toplana.repository.StanjaPodstaniceRepository;
import toplana.repository.StanjaPodstaniceZaRacunRepository;
import toplana.repository.TransakcijaRepository;
import toplana.service.RacunService;
import toplana.service.UserService;
import toplana.util.Utilities;
import toplana.web.rest.dto.NacrtRacunaDTO;
import toplana.web.rest.dto.RacunStampanje;
import toplana.web.rest.dto.RekapitulacijaPoPdvDTO;
import toplana.web.rest.dto.RekapitulacijaPoPdvDelimicnaDTO;
import toplana.web.rest.dto.StanjaPodstaniceZaRacunDTO;
import toplana.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.time.YearMonth;
/**
 * REST controller for managing {@link toplana.domain.NacrtRacuna}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class NacrtRacunaResource {

    private final Logger log = LoggerFactory.getLogger(NacrtRacunaResource.class);

    private static final String ENTITY_NAME = "nacrtRacuna";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    
    private final UserService userService;

    private final NacrtRacunaRepository nacrtRacunaRepository;
    private final CeneRepository ceneRepository;
    private final StanjaPodstaniceRepository stanjaPodstaniceRepository;
    private final PodstanicaRepository podstanicaRepository;
    private final StanjaPodstaniceZaRacunRepository stanjaPodstaniceZaRacunRepository;
    private final StanRepository stanRepository;
    private final TransakcijaRepository transakcijaRepository;
    private final RacunRepository racunRepository;
    private final RacunService racunService;
    private final SifraPromeneRepository sifraPromeneRepository;
    private final StanStanjeRepository stanstanjeRepository;
    
  

	public NacrtRacunaResource(UserService userService, NacrtRacunaRepository nacrtRacunaRepository,
			CeneRepository ceneRepository, StanjaPodstaniceRepository stanjaPodstaniceRepository,
			PodstanicaRepository podstanicaRepository,
			StanjaPodstaniceZaRacunRepository stanjaPodstaniceZaRacunRepository, StanRepository stanRepository,
			TransakcijaRepository transakcijaRepository, RacunRepository racunRepository, RacunService racunService,
			SifraPromeneRepository sifraPromeneRepository, StanStanjeRepository stanstanjeRepository) {
		super();
		this.userService = userService;
		this.nacrtRacunaRepository = nacrtRacunaRepository;
		this.ceneRepository = ceneRepository;
		this.stanjaPodstaniceRepository = stanjaPodstaniceRepository;
		this.podstanicaRepository = podstanicaRepository;
		this.stanjaPodstaniceZaRacunRepository = stanjaPodstaniceZaRacunRepository;
		this.stanRepository = stanRepository;
		this.transakcijaRepository = transakcijaRepository;
		this.racunRepository = racunRepository;
		this.racunService = racunService;
		this.sifraPromeneRepository = sifraPromeneRepository;
		this.stanstanjeRepository = stanstanjeRepository;
	}

	/**
     * {@code POST  /nacrt-racunas} : Create a new nacrtRacuna.
     *
     * @param nacrtRacuna the nacrtRacuna to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nacrtRacuna, or with status {@code 400 (Bad Request)} if the nacrtRacuna has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nacrt-racunas")
    public ResponseEntity<NacrtRacunaDTO> createNacrtRacuna(@Valid @RequestBody NacrtRacuna nacrtRacuna) throws URISyntaxException {
        log.debug("REST request to save NacrtRacuna : {}", nacrtRacuna);
        if (nacrtRacuna.getId() != null) {
            throw new BadRequestAlertException("A new nacrtRacuna cannot already have an ID", ENTITY_NAME, "idexists");
        }
        //provera da li je vec napravljen racun za tekuci mesec
      /*  List<Long> racuniZaTekuciMesec = nacrtRacunaRepository.getAllOfCurrentMonth();
        if(racuniZaTekuciMesec != null && racuniZaTekuciMesec.size() > 0) {
        	NacrtRacunaDTO nrDt = new NacrtRacunaDTO(nacrtRacuna);
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.nacrtRacuna.postojiRacunZaMesec", "")).body(nrDt);
        }
        */
      //provera da li je vec napravljen racun za zadati mesec
        
        List<Long> racuniZaZadatiMesec = nacrtRacunaRepository.getAllForGivenMonthAndYear(nacrtRacuna.getDatumRacuna().getMonthValue(), 
        		nacrtRacuna.getDatumRacuna().getYear());
        if(racuniZaZadatiMesec != null && racuniZaZadatiMesec.size() > 0) {
        	NacrtRacunaDTO nrDt = new NacrtRacunaDTO(nacrtRacuna);
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.nacrtRacuna.postojiRacunZaMesec", "")).body(nrDt);
        }
        
        Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();        
        
        NacrtRacuna result = nacrtRacunaRepository.save(nacrtRacuna);
        NacrtRacunaDTO nrDto = this.upamtiNacrtRacuna(result, nacrtRacuna,  user);
               
        
        return ResponseEntity.created(new URI("/api/nacrt-racunas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(nrDto);
    }
    
    /**
     * POmocni metod koji treba da prvo obrise stare racune i stanja, a da onda napravi nove
     * @param result
     * @param nacrtRacuna
     * @param user
     * @return
     */    
    private NacrtRacunaDTO upamtiNacrtRacuna(NacrtRacuna result, NacrtRacuna nacrtRacuna, User user) {
    	
    	NacrtRacunaDTO nrDto = new NacrtRacunaDTO(result);
    	
    	for(StanjaPodstaniceZaRacun sp : nacrtRacuna.getStanjaPodstaniceZaRacune()) {
        	sp.setNacrtRacuna(result);
        	//sp.setId(null);
        }        
        
        List<StanjaPodstaniceZaRacun> s =  stanjaPodstaniceZaRacunRepository.saveAll(nacrtRacuna.getStanjaPodstaniceZaRacune());
        
        
        nrDto.setStanjaPodstaniceZaRacune(s);
        
        List<Podstanica> podstanice = podstanicaRepository.findAllByOrderByBroj();
        LocalDate poslednjiDanPrethodnogMeseca = Utilities.getPoslednjiDanPrethodnogMeseca();
        StanStanje sstan = new StanStanje();
       
        for(Podstanica p : podstanice) {
        	List<Racun> racuniZaPodstanicu = new ArrayList<Racun>();
        	List<Stan> stanoviZaPodstanicu = stanRepository.findAllByPodstanicaId(p.getId());
        	
        	p.setUkupnapovrsina(stanRepository.findKvSumPodstanicaId(p.getId()));
        	
        	
        	
        	LocalDate today = LocalDate.now();
            LocalDate previousMonth = today.minusMonths(1); 
            int previousMonthNumber = previousMonth.getMonthValue(); 
        	p.setUkupnapotrosnjapostanu(stanRepository.findPotrosnjaPodstanicaId(p.getId(),1));
        	p.setUkupnapotrosnjapostanu(600.00);
        //	p.setUkupnapotrosnjapostanu();
        			
        	for(Stan stan : stanoviZaPodstanicu) {
        		//BigDecimal saldo = transakcijaRepository.getSaldoDoKrajaPrethodnogMesecaZaStan(stan.getId());
        		
        		
        		if (p.getId() > 1105) { // za nove podstanice
        			
        			 stan.setZadnjaStanja(stanstanjeRepository.getLastStatesForStan(stan.getId()));
        			 
        			
        		}
        		
        		BigDecimal saldo = transakcijaRepository.getSaldoDoKrajaPrethodnogMesecaZaStanAndValuta(nacrtRacuna.getValutaPlacanja(), stan.getId());
        		//ovde dodati sta da se radi kad nema popusta (za neku podstanicu ili vrstu korisnika (reon)
        		Racun racun = new Racun(stan, result, user, saldo, poslednjiDanPrethodnogMeseca,p);
        		racuniZaPodstanicu.add(racun);
        	}
        	racunRepository.saveAll(racuniZaPodstanicu);
        }
        return nrDto;
    }

    /**
     * {@code PUT  /nacrt-racunas} : Updates an existing nacrtRacuna.
     *
     * @param nacrtRacuna the nacrtRacuna to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nacrtRacuna,
     * or with status {@code 400 (Bad Request)} if the nacrtRacuna is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nacrtRacuna couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nacrt-racunas")
    public ResponseEntity<NacrtRacunaDTO> updateNacrtRacuna(@Valid @RequestBody NacrtRacuna nacrtRacuna) throws URISyntaxException {
        log.debug("REST request to update NacrtRacuna : {}", nacrtRacuna);
        if (nacrtRacuna.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        
        List<Racun> azuriraniRacuni = racunRepository.findByAzuriranAndNacrtRacunaId(true,nacrtRacuna.getId());
        if(azuriraniRacuni != null && azuriraniRacuni.size() > 0) {
        	NacrtRacunaDTO nrDt = new NacrtRacunaDTO(nacrtRacuna);
        	return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.nacrtRacuna.racuniSuAzurirani", "")).body(nrDt);
        }
        
        Optional<User> currentUser = userService.getUserWithAuthorities();
        User user = currentUser.get();  
        
      //brisanje stanjaPodstaniceZaRacun
       // stanjaPodstaniceZaRacunRepository.deleteAllByNacrtRacunaId(nacrtRacuna.getId());
        racunRepository.deleteByNacrtRacunaId(nacrtRacuna.getId());
        
        NacrtRacuna result = nacrtRacunaRepository.save(nacrtRacuna);
        NacrtRacunaDTO nrDto = this.upamtiNacrtRacuna(result, nacrtRacuna, user);
        
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nacrtRacuna.getId().toString()))
            .body(nrDto);
    }

    /**
     * {@code GET  /nacrt-racunas} : get all the nacrtRacunas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nacrtRacunas in body.
     */
    @GetMapping("/nacrt-racunas")
    public ResponseEntity<List<NacrtRacuna>> getAllNacrtRacunas(Pageable pageable) {
        log.debug("REST request to get a page of NacrtRacunas");
     
       
        Page<NacrtRacuna> page = nacrtRacunaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nacrt-racunas/:id} : get the "id" nacrtRacuna.
     *
     * @param id the id of the nacrtRacuna to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nacrtRacuna, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nacrt-racunas/{id}")
    public NacrtRacunaDTO getNacrtRacuna(@PathVariable Long id) {
        log.debug("REST request to get NacrtRacuna : {}", id);
       // Optional<NacrtRacuna> nacrtRacuna = nacrtRacunaRepository.findById(id);
        Optional<NacrtRacuna> nr = nacrtRacunaRepository.findById(id);
        if(nr.isPresent()) {
        	NacrtRacunaDTO nrDto = new NacrtRacunaDTO(nr.get());
        	List<StanjaPodstaniceZaRacun> spzl = stanjaPodstaniceZaRacunRepository.findAllByNacrtRacunaId(id);
        	nrDto.setStanjaPodstaniceZaRacune(spzl);
        	return nrDto;
        }
        return null;        
    }    
    

    /**
     * {@code DELETE  /nacrt-racunas/:id} : delete the "id" nacrtRacuna.
     * Brisanje je moguce samo ako nacrtRacuna nije proknjizen. 
     *
     * @param id the id of the nacrtRacuna to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nacrt-racunas/{id}")
    public ResponseEntity<Void> deleteNacrtRacuna(@PathVariable Long id) {
        log.debug("REST request to delete NacrtRacuna : {}", id);
        NacrtRacuna nr = nacrtRacunaRepository.getOne(id);
        if(nr.getProknjizen()) {
        	//nije moguce brisanje
        	return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.nacrtRacuna.racuniIzNacrtaSuProknjizeni", "")).build();        	
        }
        
        //brisanje stanjaPodstaniceZaRacun
        stanjaPodstaniceZaRacunRepository.deleteAll(nr.getStanjaPodstaniceZaRacune());
        racunRepository.deleteAll(nr.getRacuns());
        
        nacrtRacunaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * Priprema za kreiranje zbirnih racuna za mesec. Citaju se poslednje cene i stanja podstanica
     * @return
     */
    @GetMapping("/racuniPriprema")
    public ResponseEntity<NacrtRacunaDTO> pripremaNacrtRacuna() {
        NacrtRacunaDTO nr = new NacrtRacunaDTO();
        Cene cene = ceneRepository.findFirstByOrderByKwhAsc();
        nr.setCene(cene);
        List<Podstanica> podstanice = podstanicaRepository.findAllByOrderByBroj();
        
        List<StanjaPodstaniceZaRacun> svePodstanice = new ArrayList<StanjaPodstaniceZaRacun>();
        
        for(Podstanica p : podstanice) {
        	StanjaPodstaniceZaRacun spz = new StanjaPodstaniceZaRacun();
        	spz.setPodstanica(p);

        	BigDecimal povrsinaZaPodstanicu = podstanicaRepository.findUkupnaPovrsina(p.getId());
        	spz.setUkupnaPovrsina(povrsinaZaPodstanicu);        	
        	
        	List<StanjaPodstanice> lsp = stanjaPodstaniceRepository.findFirst2ByPodstanicaIdOrderByDatumOcitavanjaDesc(p.getId());
        	if(lsp != null && !lsp.isEmpty()) {
        		spz.setNovoStanje(lsp.get(0));
        		spz.setStaroStanje(lsp.get(1));
        		spz.setUtrosakPoM2(spz.getNovoStanje().getStanje().subtract(spz.getStaroStanje().getStanje()).divide(povrsinaZaPodstanicu, 3, RoundingMode.HALF_UP));
        	}
        	
        	
        	svePodstanice.add(spz);
        }
        
        nr.setStanjaPodstaniceZaRacune(svePodstanice); 
        for(int i=0;i<svePodstanice.size();i++){
            System.out.println(svePodstanice.get(i));
        } 
        nr.setUkupnaPotrosnja();
        //provera da li za sve podstanice postoji uneto stanje za tekuci mesec
        boolean citanjeProblem = true;
        List<Integer> brojeviPodstanica = stanjaPodstaniceRepository.checkCurrentMonthRecords();
        if(brojeviPodstanica != null) {
        	if(brojeviPodstanica.size() ==6)
        		citanjeProblem = false;
        }
        
        if(citanjeProblem) {
        	return ResponseEntity.ok().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.nacrtRacuna.stanja.nisu.ubacena", "")).body(nr);
        }else        
        	return ResponseEntity.ok().body(nr);        
    }
/******************************************************************************************************************     
 * 													KNJIZENJE
 * @param nacrtRacuna
 * @return
 * @throws URISyntaxException
 /*****************************************************************************************************************/    
    @PutMapping("/nacrtRacunaKnjizenje")
    public ResponseEntity<Void> knjizenjeNacrtRacuna(@Valid @RequestBody NacrtRacuna nacrtRacuna) throws URISyntaxException {

    	SifraPromene sifra = sifraPromeneRepository.findByTipPromeneAndBroj("razduzenje", 4);
    	NacrtRacuna nr = nacrtRacunaRepository.getOne(nacrtRacuna.getId());
    	
    	for(Racun r: nr.getRacuns()) {
    		Transakcija t = new Transakcija();
        	//t.setDatum(LocalDate.now());
    		t.setDatum(nr.getDatumRacuna());
        	t.setDuguje(r.getUtrosakVarijabilni().add(r.getUtrosakFiksni()).add(r.getUtrosakOdrzavanje()));
        	t.setOpis("Рачун");
        	t.setStan(r.getStan());
        	t.setValuta(r.getValutaPlacanja());
        	t.setRacun(r);     
        	t.setSifraPromene(sifra);// sifra promene - racun
        	t.setStatus(r.getStan().getStatus());
        	t.setSifraDokumenta(r.getPeriod());
        	Transakcija result = transakcijaRepository.save(t);
        	r.setTransakcija(result);
        	r.setProknjizen(true);
        	racunRepository.save(r);
    	}
    	
    	nr.setProknjizen(true);
    	nacrtRacunaRepository.save(nr);
    	
    	return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "toplanaApp.nacrtRacuna.stanja.nisu.ubacena", "")).build();
    }
    
    
    @RequestMapping(value = "/stampanje/{nacrtRacunaId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long nacrtRacunaId)
            throws IOException {
    	
    	
    	List<Racun> racuni = racunRepository.findAllByNacrtRacunaId(nacrtRacunaId);
    	String filename = racunService.createRacuneZaStampanje(racuni);
    	File file = new File(filename);   	       

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition","attachment; filename=\"" + filename +"\"");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }  
    
/**********************************************************************************************************
* Kreiranje suma po racunu, za prikaz rekapitulaciju razreza po PDF tarifama     
* Ulaz je tekuci mesec
* @param mesec
* @return
* @throws IOException
 ***********************************************************************************************************/
    @RequestMapping(value = "/racuni-rekapitulacija-po-pdv/{nacrtRacunaId}",
            method = RequestMethod.GET)
    public ResponseEntity<RekapitulacijaPoPdvDTO> rekapitulacijaRazrezaPoPdfTarifama(@PathVariable Long nacrtRacunaId)
            throws IOException {    	    	
    	
    	Optional<NacrtRacuna> nrOptional = nacrtRacunaRepository.findById(nacrtRacunaId);
    	NacrtRacuna nr = nrOptional.get();
    	
    	RekapitulacijaPoPdvDelimicnaDTO resultD = racunRepository.rekapitulacijaPoPdv(nacrtRacunaId);
    			
    	RekapitulacijaPoPdvDTO out = new RekapitulacijaPoPdvDTO(resultD, nr.getDatumRacuna(), nr.getId(), nr.getPdv1(), nr.getPdv2());
    			
        return ResponseEntity.ok().body(out);

    }    
/***************************************************************************************************************
 * Stampanje rekapitulacije razreza po pdv tarifama
 * @param nacrtRacunaId
 * @return
 * @throws IOException
 ****************************************************************************************************************/
    @RequestMapping(value = "/racuni-rekapitulacija-po-pdv-stampanje/{nacrtRacunaId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> downloadRekapitulacijaFile(@PathVariable Long nacrtRacunaId)
            throws IOException {
    	
    	Optional<NacrtRacuna> nrOptional = nacrtRacunaRepository.findById(nacrtRacunaId);
    	NacrtRacuna nr = nrOptional.get();
    	
    	RekapitulacijaPoPdvDelimicnaDTO resultD = racunRepository.rekapitulacijaPoPdv(nacrtRacunaId);
    			
    	RekapitulacijaPoPdvDTO out = new RekapitulacijaPoPdvDTO(resultD, nr.getDatumRacuna(), nr.getId(), nr.getPdv1(), nr.getPdv2());
    			
    	List<RekapitulacijaPoPdvDTO> reks = new ArrayList<RekapitulacijaPoPdvDTO>();
    	reks.add(out);
    	String filename = racunService.generateRekapitulacijaReport(reks);
    	File file = new File(filename);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition","attachment; filename=\"" + filename +"\"");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(new FileInputStream(file)));
    }      
    
    
    
    
       
}
