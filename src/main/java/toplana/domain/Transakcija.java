package toplana.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import toplana.web.rest.dto.DugujePotrazujeDTO;
import toplana.web.rest.dto.DugujePotrazujeReoni;
import toplana.web.rest.dto.RekapitulacijaSifraPromeneDatumDTO;
import toplana.web.rest.dto.TransakcijaStanUkupnoDTO;
import toplana.web.rest.dto.TransakcijaZaStanDTO;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Transakcija.
 */
@Entity
@Table(name = "transakcija")


@SqlResultSetMappings({
	@SqlResultSetMapping(name="aa", 
			classes={
			@ConstructorResult(
			     targetClass=DugujePotrazujeDTO.class,
			       columns={
			          @ColumnResult(name="saldoDuguje", type=BigDecimal.class),
			          @ColumnResult(name="saldoPotrazuje", type=BigDecimal.class),
			          }
			   )
			}
	),
	@SqlResultSetMapping(name="ab", 
		classes={
				@ConstructorResult(
						targetClass=TransakcijaStanUkupnoDTO.class,
						columns={
								@ColumnResult(name="duguje", type=BigDecimal.class),
								@ColumnResult(name="potrazuje", type=BigDecimal.class),
								@ColumnResult(name="stanId", type=Long.class),
								@ColumnResult(name="sifra", type=String.class),
						}
				)
		}
	),
	
	@SqlResultSetMapping(name="or", 
	classes={
			@ConstructorResult(
					targetClass=TransakcijaStanUkupnoDTO.class,
					columns={
							@ColumnResult(name="datum", type=String.class),
							@ColumnResult(name="opis", type=String.class),
							@ColumnResult(name="duguje", type=BigDecimal.class),
							@ColumnResult(name="potrazuje", type=BigDecimal.class),
							@ColumnResult(name="sifra", type=String.class),
					}
			)
	}
),
	
	
	@SqlResultSetMapping(name="ac", 
	classes={
			@ConstructorResult(
					targetClass=TransakcijaStanUkupnoDTO.class,
					columns={
							@ColumnResult(name="dugovanje", type=BigDecimal.class),
							@ColumnResult(name="potrazivanje", type=BigDecimal.class),
							@ColumnResult(name="stanje", type=BigDecimal.class),
							@ColumnResult(name="krajnjaSifra", type=String.class),
							@ColumnResult(name="krajnjiNaziv", type=String.class),
							@ColumnResult(name="krajnjiNazivIme", type=String.class),
					}
			)
		}
	),
	@SqlResultSetMapping(name="ad", 
	classes={
			@ConstructorResult(
					targetClass=TransakcijaZaStanDTO.class,
					columns={
							@ColumnResult(name="datum", type=LocalDate.class),
							@ColumnResult(name="sifra_dokumenta", type=String.class),
							@ColumnResult(name="sifraPromene", type=String.class),
							@ColumnResult(name="dugovanje", type=BigDecimal.class),
							@ColumnResult(name="potrazivanje", type=BigDecimal.class),
							@ColumnResult(name="krajnjaSifra", type=String.class),
							@ColumnResult(name="krajnjiNaziv", type=String.class),
							@ColumnResult(name="grad", type=String.class),
							@ColumnResult(name="ulica", type=String.class),
							@ColumnResult(name="ulaz", type=Integer.class),
							@ColumnResult(name="broj", type=Integer.class),
							@ColumnResult(name="ukljucen", type=Boolean.class),
							@ColumnResult(name="tipPotrosaca", type=Integer.class),
							@ColumnResult(name="podstanicaBroj", type=Integer.class),
							@ColumnResult(name="opis", type=String.class),
					}
			)
		}
	),
	@SqlResultSetMapping(name="ae", 
	classes={
			@ConstructorResult(
					targetClass=DugujePotrazujeReoni.class,
					columns={
							@ColumnResult(name="tipPotrosaca", type=Integer.class),							
							@ColumnResult(name="dugovanje", type=BigDecimal.class),
							@ColumnResult(name="potrazivanje", type=BigDecimal.class),							
					}
			)
		}
	),
	
	@SqlResultSetMapping(name="af", 
	classes={
			@ConstructorResult(
					targetClass=RekapitulacijaSifraPromeneDatumDTO.class,
					columns={
							@ColumnResult(name="datum", type=LocalDate.class),
							@ColumnResult(name="sifra", type=String.class),
							@ColumnResult(name="dugovanje", type=BigDecimal.class),
							@ColumnResult(name="potrazivanje", type=BigDecimal.class),
							
					}
			)
		}
	),
	
	@SqlResultSetMapping(
		    name = "ar",
		    entities = @EntityResult(entityClass = Racun.class)
	),
	
	
	
	@SqlResultSetMapping(name="ag", 
	classes={
			@ConstructorResult(
					targetClass=RekapitulacijaSifraPromeneDatumDTO.class,
					columns={
							@ColumnResult(name="datum", type=LocalDate.class),							
							@ColumnResult(name="dugovanje", type=BigDecimal.class),
							@ColumnResult(name="potrazivanje", type=BigDecimal.class),
							
					}
			)
		}
	)
})

@NamedNativeQueries({	
	@NamedNativeQuery(name="Transakcija.getDugujePotrazujeZaStan", 
			query="select coalesce(sum(duguje),0) as saldoDuguje, coalesce(sum(potrazuje),0) as saldoPotrazuje "
					+ " from transakcija t "
					+ "	where stan_id = :stanId ", 
			resultSetMapping="aa"),
	
	@NamedNativeQuery(name="Transakcija.getDugujePotrazujeZaDodatniRacun", 
	query="select coalesce(sum(duguje),0) as saldoDuguje, coalesce(sum(potrazuje),0) as saldoPotrazuje "
			+ " from transakcija t "
			+ "	where ostali_racuni_id = :ostaliRacuniId ", 
	resultSetMapping="aa"),
	
	@NamedNativeQuery(name="Transakcija.pronadjiZaStan", 
	query="SELECT sum(t.duguje) as duguje, sum(t.potrazuje) as potrazuje, t.stan_id as stanId, s.sifra as sifra "
				+ "	from transakcija t "
				+ "	left join stan s on s.id = t.stan_id "
				+ "	group by t.stan_id, s.sifra", 
	resultSetMapping="ab"),
	
	
	@NamedNativeQuery(name="Transakcija.searchOR", 
	query="SELECT t.datum, t.opis,  t.duguje as duguje, t.potrazuje as potrazuje, t.ostali_racuni_id, ort.sifra "
				+ "	from transakcija t, ostali_racuni ort "
				+ "	where t.ostali_racuni_id = ort.id and ort.sifra = :sifra"
				+ "	", 	resultSetMapping="or"),
	
	@NamedNativeQuery(name="Transakcija.search", 
	  query="SELECT coalesce(sum(duguje),0) as dugovanje, coalesce(sum(potrazuje),0) as potrazivanje, "
	  		+ "coalesce(sum(duguje),0)- coalesce(sum(potrazuje), 0) as stanje, "
	  		+ "case "
	  		+ "	when t.stan_id is not null then s.sifra "
	  		+ "	when t.stan_id is null then o.sifra "
	  		+ "end as krajnjaSifra, "
	  		+ "case "
	  		+ "		when t.stan_id is not null then v.prezime "
	  		+ "		when t.stan_id is null and o.stan_id is not null then o.naziv "
	  		+ "		when t.stan_id is null and o.stan_id is null then '' "
	  		+ "end as krajnjiNaziv, "
	  		+ "case "
	  		+ "		when t.stan_id is not null then v.ime "
	  		+ "		when t.stan_id is null and o.stan_id is not null then o.naziv "
	  		+ "		when t.stan_id is null and o.stan_id is null then '' "
	  		+ "end as krajnjiNazivIme "
	  		+ "from transakcija t "
	  		+ "left join stan s on s.id = t.stan_id "
	  		+ "left join vlasnik v on v.id = s.vlasnik_id "
	  		+ "left join ostali_racuni o on t.ostali_racuni_id = o.id "
	  		+ "left join stan s1 on o.stan_id = s1.id "
	  		+ "where (1 = :datumOdNotExists or t.datum >= :datumOd) "  
	  		+ "and (1 = :datumDoNotExists or t.datum <= :datumDo ) "	  		
	  		+ "and (1 = :sifraNotExists or "
	  		+ "	 CASE "
	  		+ "        WHEN t.stan_id is not null then s.sifra like :sifra "
	  		+ "	 	   when t.stan_id is null then o.sifra like :sifra "
	  		+ "     END) "
	  		+ "and (1 = :prezimeNotExists or "
	  		+ "	 CASE "
	  		+ "        WHEN t.stan_id IS NOT NULL THEN unaccent(lower(v.prezime)) LIKE unaccent(lower(:prezime))"
	  		+ "	 	   WHEN t.stan_id IS NULL THEN unaccent(lower(o.naziv)) LIKE unaccent(lower(:prezime))"
	  		+ "  END)	"
	  		
	  		+ "and (1 = :imeNotExists or "
	  		+ "	 CASE "
	  		+ "        WHEN t.stan_id IS NOT NULL THEN unaccent(lower(v.ime)) LIKE unaccent(lower(:ime))"
	  		+ "	 	   WHEN t.stan_id IS NULL THEN unaccent(lower(o.naziv)) LIKE unaccent(lower(:ime))"
	  		+ "  END)	"

	  		+ "and (1 = :podstanicaNotExists or "
	  		+ "	 CASE "
	  		+ "        WHEN t.stan_id is not null then s.podstanica_id = :podstanicaId "
	  		+ "	 	   when t.stan_id is null then s1.podstanica_id = :podstanicaId "
	  		+ "  END)"
	  		+ "and (1 = :tipPotrosacaNotExists or "
	  		+ "	 CASE "
	  		+ "       WHEN t.stan_id is not null then s.tip_potrosaca_id in ( :tipPotrosacaIds )"
	  		+ "	 	  when t.stan_id is null then s1.tip_potrosaca_id in ( :tipPotrosacaIds) "
	  		+ "  END) "
	  		+ "and ( "
	  		+ "	 CASE "
	  		//+ "       WHEN t.stan_id is not null then s.ukljucen = :stanUkljucen "
	  		//+ "	 	  when t.stan_id is null then s1.ukljucen = :stanUkljucen "
	  		
			+ "	 	  WHEN t.stan_id is not null then s.ukljucen = :stanUkljucen " 	
			+ "       when t.stan_id is null and t.ostali_racuni_id is not null and s1.id is not null then s1.ukljucen = :stanUkljucen"   
			+ "       when t.stan_id is null and t.ostali_racuni_id is not null and s1.id is null then true"   
	  		
	  		
	  		+ "  END)	"	  		
	  		+ "group by krajnjaSifra, krajnjiNaziv, krajnjiNazivIme", resultSetMapping="ac"),
	
	@NamedNativeQuery(name="Transakcija.searchForDnevnik", 
	  query="SELECT coalesce(sum(duguje),0) as dugovanje, coalesce(sum(potrazuje),0) as potrazivanje, "
	  		+ "coalesce(sum(duguje),0)- coalesce(sum(potrazuje), 0) as stanje, "
	  		+ "case "
	  		+ "	when t.stan_id is not null then s.sifra "
	  		+ "	when t.stan_id is null then o.sifra "
	  		+ "end as krajnjaSifra, "
	  		+ "case "
	  		+ "		when t.stan_id is not null then v.prezime "
	  		+ "		when t.stan_id is null then o.naziv "
	  		+ "end as krajnjiNaziv "
	  		+ "from transakcija t "
	  		+ "left join stan s on s.id = t.stan_id "
	  		+ "left join vlasnik v on v.id = s.vlasnik_id "
	  		+ "left join ostali_racuni o on t.ostali_racuni_id = o.id "
	  		+ "left join stan s1 on o.stan_id = s1.id "
	  		+ "where (1 = :datumOdNotExists or t.datum >= :datumOd) "  
	  		+ "and (1 = :datumDoNotExists or t.datum <= :datumDo ) "	  		
	  		+ "and (1 = :sifraNotExists or "
	  		+ "	 CASE "
	  		+ "        WHEN t.stan_id is not null then s.sifra like :sifra "
	  		+ "	 	   when t.stan_id is null then o.sifra like :sifra "
	  		+ "     END) "
	  		+ "and (1 = :prezimeNotExists or "
	  		+ "	 CASE "
	  		+ "        WHEN t.stan_id IS NOT NULL THEN unaccent(lower(v.prezime)) LIKE unaccent(lower(:prezime))"
	  		+ "	 	   WHEN t.stan_id IS NULL THEN unaccent(lower(o.naziv)) LIKE unaccent(lower(:prezime))"
	  		+ "  END)	"
	  		
	  		+ "and (1 = :imeNotExists or "
	  		+ "	 CASE "
	  		+ "        WHEN t.stan_id IS NOT NULL THEN unaccent(lower(v.ime)) LIKE unaccent(lower(:ime))"
	  		+ "	 	   WHEN t.stan_id IS NULL THEN unaccent(lower(o.naziv)) LIKE unaccent(lower(:ime))"
	  		+ "  END)	"
	  		
	  		+ "and (1 = :podstanicaNotExists or "
	  		+ "	 CASE "
	  		+ "        WHEN t.stan_id is not null then s.podstanica_id = :podstanicaId "
	  		+ "	 	   when t.stan_id is null then s1.podstanica_id = :podstanicaId "
	  		+ "  END)"
	  		+ "and (1 = :tipPotrosacaNotExists or "
	  		+ "	 CASE "
	  		+ "       WHEN t.stan_id is not null then s.tip_potrosaca_id in ( :tipPotrosacaIds )"
	  		+ "	 	  when t.stan_id is null then s1.tip_potrosaca_id in ( :tipPotrosacaIds) "
	  		+ "  END) "
	  		+ "and ( "
	  		+ "	 CASE "
	  		+ "       WHEN t.stan_id is not null then s.ukljucen = :stanUkljucen "
	  		+ "	 	  when t.stan_id is null then s1.ukljucen = :stanUkljucen "
	  		+ "  END)	"	  		
	  		+ "group by krajnjaSifra, krajnjiNaziv", resultSetMapping="ac"),
	
	@NamedNativeQuery(name="Transakcija.searchForAnalitickiDnevnik", 
	  query="select t.datum, t.sifra_dokumenta,sp.sifra as sifraPromene, "
	  		+ "coalesce(duguje,0) as dugovanje, coalesce(potrazuje,0) as potrazivanje, "
	  		+ "case   "
	  		+ "	 when t.stan_id is not null then s.sifra "
	  		+ "	 when t.stan_id is null and o.stan_id is not null then o.sifra "
	  		+ "  else '' "
	  		+ "end as krajnjaSifra, "
	  		+ "case "
	  		+ "	when t.stan_id is not null then v.prezime || ' ' || v.ime "
	  		+ "		when t.stan_id is null and o.stan_id is not null then o.naziv "
	  		+ "		else '' "
	  		+ "end as krajnjiNaziv, "
	  		+ "case "
	  		+ "	when t.stan_id is not null then s.grad "
	  		+ "	when t.stan_id is null and o.stan_id is not null then s1.grad "
	  		+ "		else '' "
	  		+ "end as grad, "
	  		+ "case "
	  		+ "	when t.stan_id is not null then s.ulica "
	  		+ "	when t.stan_id is null and o.stan_id is not null then s1.ulica "
	  		+ "		else '' "
	  		+ "end as ulica, "
	  		+ "case "
	  		+ "	when t.stan_id is not null then s.ulaz "
	  		+ "	when t.stan_id is null and o.stan_id is not null then s1.ulaz "
	  		+ "		else 0 "
	  		+ "end as ulaz, "
	  		+ "case  "
	  		+ "	when t.stan_id is not null then s.broj "
	  		+ "	when t.stan_id is null and o.stan_id is not null then s1.broj "
	  		+ " else 0 "
	  		+ "end as broj, "
	  		+ "case  "
	  		+ "	when t.stan_id is not null then s.ukljucen "
	  		+ "	when t.stan_id is null and o.stan_id is not null then s1.ukljucen "
	  		+ " else true "
	  		+ "end as ukljucen, "
	  		+ "case "
	  		+ "	when t.stan_id is not null then tp.tip "
	  		+ "	when t.stan_id is null and o.stan_id is not null then tp1.tip "
	  		+ " else 0  "
	  		+ "end as tipPotrosaca, "
	  		
			+ "case "
			+ "	when t.stan_id is not null then p.broj "
			+ "	when t.stan_id is null and o.stan_id is not null then p1.broj "
			+ " else 0 "
			+ "end as podstanicaBroj, "
	  		
	  		+ "t.opis "
	  		+ "from transakcija t "
	  		+ "left join stan s on s.id = t.stan_id "
	  		+ "left join vlasnik v on v.id = s.vlasnik_id "
	  		+ "left join ostali_racuni o on t.ostali_racuni_id = o.id "
	  		+ "left join stan s1 on o.stan_id = s1.id "
	  		+ "left join tip_potrosaca tp on s.tip_potrosaca_id = tp.id "
	  		+ "left join tip_potrosaca tp1 on s1.tip_potrosaca_id = tp1.id "
	  		+ "left join sifra_promene sp on sp.id = t.sifra_promene_id "
	  		+ "left join podstanica p on p.id = s.podstanica_id "
	  		+ "left join podstanica p1 on s1.podstanica_id = p1.id "
	  		+ "where (1 = :datumOdNotExists or t.datum >= :datumOd)   "
	  		+ "and (1 = :datumDoNotExists or t.datum <= :datumDo )	"
	  		+ "and (1 = :sifraNotExists or "
	  		+ "	 CASE "
	  		+ "	  	WHEN t.stan_id is not null then s.sifra like :sifra "
	  		+ "	  	when t.stan_id is null then o.sifra like :sifra "
	  		+ "	 END) "
	  		+ "and (1 = :prezimeNotExists or "
	  		+ "	 CASE "
	  		+ "	  	WHEN t.stan_id is not null then lower(v.prezime) like lower( :prezime ) "
	  		+ "	  	when t.stan_id is null then lower(o.naziv) like lower( :prezime ) "
	  		+ "	 END)	"
	  		+ "and (1 = :podstanicaNotExists or "
	  		+ "	 CASE "
	  		+ "	  	WHEN t.stan_id is not null then s.podstanica_id = :podstanicaId "
	  		+ "	  	when t.stan_id is null then s1.podstanica_id = :podstanicaId "
	  		+ "	 END) "
	  		+ "and (1 = :tipPotrosacaNotExists or "
	  		+ "	 CASE "
	  		+ "	  	WHEN t.stan_id is not null then s.tip_potrosaca_id in ( :tipPotrosacaIds )"
	  		+ "	  	when t.stan_id is null then s1.tip_potrosaca_id in ( :tipPotrosacaIds ) "
	  		+ "	 END) "
	  //		+ "and ( "
	  	//	+ "	CASE "
	  		//+ "	  	WHEN t.stan_id is not null then s.ukljucen = :stanUkljucen "
	  		//+ "  	when t.stan_id is null then s1.ukljucen = :stanUkljucen "
	  		
//+ "	 	  WHEN t.stan_id is not null then s.ukljucen = :stanUkljucen " 	
//+ "       when t.stan_id is null and t.ostali_racuni_id is not null and s1.id is not null then s1.ukljucen = :stanUkljucen"   
//+ "       when t.stan_id is null and t.ostali_racuni_id is not null and s1.id is null then true"   
	
	  		
//	  		+ "	END)"
	  		+ "", resultSetMapping="ad"),
	@NamedNativeQuery(name="Transakcija.findSumForDnevnik", 
	  query="select  "
	  		+ "case "
	  		+ "	  	when t.stan_id is not null then tp.tip "
	  		+ "	  	when t.stan_id is null and o.stan_id is not null then tp1.tip "
	  		+ "		else 0 "
	  		+ "end as tipPotrosaca, "
	  		+ "coalesce(sum(duguje),0) as dugovanje, coalesce(sum(potrazuje),0) as potrazivanje "	  		
	  		+ "from transakcija t "
	  		+ "left join stan s on s.id = t.stan_id "
	  		+ "left join vlasnik v on v.id = s.vlasnik_id "
	  		+ "left join ostali_racuni o on t.ostali_racuni_id = o.id "
	  		+ "left join stan s1 on o.stan_id = s1.id "
	  		+ "left join tip_potrosaca tp on s.tip_potrosaca_id = tp.id "
	  		+ "left join tip_potrosaca tp1 on s1.tip_potrosaca_id = tp1.id "
	  		+ "left join sifra_promene sp on sp.id = t.sifra_promene_id "
	  		+ "where (1 = :datumOdNotExists or t.datum >= :datumOd)   "
	  		+ "and (1 = :datumDoNotExists or t.datum <= :datumDo )	"
	  		+ "and (1 = :sifraNotExists or "
	  		+ "	 CASE "
	  		+ "	  	WHEN t.stan_id is not null then s.sifra like :sifra "
	  		+ "	  	when t.stan_id is null then o.sifra like :sifra "
	  		+ "	 END) "
	  		+ "and (1 = :prezimeNotExists or "
	  		+ "	 CASE "
	  		+ "	  	WHEN t.stan_id is not null then lower(v.prezime) like lower( :prezime ) "
	  		+ "	  	when t.stan_id is null then lower(o.naziv) like lower( :prezime ) "
	  		+ "	 END)	"
	  		+ "and (1 = :podstanicaNotExists or "
	  		+ "	 CASE "
	  		+ "	  	WHEN t.stan_id is not null then s.podstanica_id = :podstanicaId "
	  		+ "	  	when t.stan_id is null then s1.podstanica_id = :podstanicaId "
	  		+ "	 END) "
	  		+ "and (1 = :tipPotrosacaNotExists or "
	  		+ "	 CASE "
	  		+ "	  	WHEN t.stan_id is not null then s.tip_potrosaca_id in ( :tipPotrosacaIds )"
	  		+ "	  	when t.stan_id is null then s1.tip_potrosaca_id in ( :tipPotrosacaIds ) "
	  		+ "	 END) "
//	  		+ "and ( "
//	  		+ "	CASE "
	//  		+ "	  	WHEN t.stan_id is not null then s.ukljucen = :stanUkljucen "
	//  		+ "  	when t.stan_id is null then s1.ukljucen = :stanUkljucen "
	  		
//+ "	 	  WHEN t.stan_id is not null then s.ukljucen = :stanUkljucen " 	
//+ "       when t.stan_id is null and t.ostali_racuni_id is not null and s1.id is not null then s1.ukljucen = :stanUkljucen"   
//+ "       when t.stan_id is null and t.ostali_racuni_id is not null and s1.id is null then true"   
	
	  		
//	  		+ "	END)"
	  		+ " group by tipPotrosaca "
	  		+ "", resultSetMapping="ae"),
	
	@NamedNativeQuery(name="Transakcija.rekapitulacijaSifraPromeneDatum", 
	  query="select t.datum, sp.sifra, coalesce(sum(duguje),0.00) as dugovanje, coalesce(sum(potrazuje),0.00) as potrazivanje "
	  		+ " from transakcija t "
	  		+ "left join sifra_promene sp on sp.id = t.sifra_promene_id "
	  		+ "where (1 = :datumOdNotExists or t.datum >= :datumOd) "  
	  		+ "and (1 = :datumDoNotExists or t.datum <= :datumDo ) "  		
	  		+ "group by t.datum, sp.sifra "
	  		+ "order by t.datum, sp.sifra ", resultSetMapping="af"),
	
	/*
	@NamedNativeQuery(
		    name = "Transakcija.rekapitulacijaSifraPromeneDatumRacun",
		    query =
		        "select r.*, " +
		        "       (coalesce(r.utrosak_varijabilni, 0) + " +
		        "        coalesce(r.utrosak_fiksni, 0) - " +
		        "        coalesce(r.popust, 0)) as ukupno " +
		        "from racun r " +
		        "inner join stan s on r.stan_id = s.id " +
		        "inner join podstanica p on s.podstanica_id = p.id " +
		        "where (1 = :datumOdNotExists or r.datum_racuna >= :datumOd) " +
		        "  and (1 = :datumDoNotExists or r.datum_racuna <= :datumDo) " +
		        "  and (p.broj >= :podstanicaOd and p.broj <= :podstanicaDo) " +
		        "  and (s.sifra >= :sifraOd and s.sifra <= :sifraDo) " +
		        "order by r.datum_racuna DESC",
		    resultSetMapping = "ar"
		),
	*/
	
	
	@NamedNativeQuery(
		    name = "Transakcija.rekapitulacijaSifraPromeneDatumRacun",
		    query =
		        "select r.*, " +
		        "       (coalesce(r.utrosak_varijabilni, 0) + " +
		        "        coalesce(r.utrosak_fiksni, 0) - " +
		        "        coalesce(r.popust, 0)) as ukupno " +
		        "from racun r " +
		        "inner join stan s on r.stan_id = s.id " +
		        "inner join podstanica p on s.podstanica_id = p.id " +
		        "where  r.datum_racuna = :datumDo " +
		        "  and (p.broj >= :podstanicaOd and p.broj <= :podstanicaDo) " +
		        "  and (s.sifra >= :sifraOd and s.sifra <= :sifraDo) " +
		        "order by r.datum_racuna DESC",
		    resultSetMapping = "ar"
		),
	
	
	@NamedNativeQuery(name="Transakcija.sintetickiDnevnik", 
	  query="select t.datum, coalesce(sum(duguje),0.00) as dugovanje, coalesce(sum(potrazuje),0.00) as potrazivanje "
	  		+ " from transakcija t "	  		
	  		+ "where (1 = :datumOdNotExists or t.datum >= :datumOd) "  
	  		+ "and (1 = :datumDoNotExists or t.datum <= :datumDo ) "  		
	  		+ "group by t.datum "
	  		+ "order by t.datum ", resultSetMapping="ag"),
			
})


public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;
    
    @Column(name = "datum_dokumenta")
    private LocalDate datumDokumenta;

    @Column(name = "valuta")
    private LocalDate valuta;

    @Column(name = "status")
    private String status;

    @Column(name = "opis")
    private String opis;
    
    @Column(name = "sifra_dokumenta")
    private String sifraDokumenta;

    //zaduzenje (racuni)
    @Column(name = "duguje", precision = 21, scale = 2)
    private BigDecimal duguje;

    //razduzenje (placanje / izvodi, blagajna ..)
    @Column(name = "potrazuje", precision = 21, scale = 2)
    private BigDecimal potrazuje;

    @ManyToOne
    @JsonIgnoreProperties(value = "transakcijas", allowSetters = true)
    private VrstaTransakcije vrstaTransakcije;

    @ManyToOne
    @JsonIgnoreProperties(value = "transakcijas", allowSetters = true)
    private SifraPromene sifraPromene;

    

    @OneToOne(mappedBy = "transakcija")
    @JsonIgnore
    private StavkeIzvoda stavkaIzvoda;
    
    @OneToOne(mappedBy = "transakcija")
    @JsonIgnore
    private StavkeIzvodaPostanska stavkaIzvodaPostanska;
    

    @OneToOne(mappedBy = "transakcija")
    @JsonIgnore
    private Racun racun;

    @ManyToOne
    @JsonIgnoreProperties(value = "transakcijas", allowSetters = true)
    private Stan stan;
    
    @ManyToOne
    @JsonIgnoreProperties(value = "transakcijas", allowSetters = true)
    private OstaliRacuni ostaliRacuni;
    
    public Transakcija() {}
    
    public Transakcija(StavkeIzvoda s, Stan stan, SifraPromene sp) {
    	this.datum = s.getDatumValute();
    	this.potrazuje = s.getIznos();
    	this.opis = "Извод";
    	this.sifraDokumenta = s.getIzvod().getBrojIzvoda().toString();
    	this.setSifraPromene(sp);
    	this.stan = stan;
    	this.setStavkaIzvoda(s);
    	this.status = stan.getStatus();
    }
    
    public Transakcija(StavkeIzvodaPostanska s, Stan stan) {
    	this.datum = s.getValuta();
    	this.potrazuje = s.getIznos();
    	this.opis = "Поштанска";
    	//this.sifraDokumenta = s.getIzvod().getBrojIzvoda().toString();
    	this.setSifraPromene(new SifraPromene(25701L));
    	this.stan = stan;
    	this.setStavkaIzvodaPostanska(s);
    	this.status = stan.getStatus();
    }
    
    public Transakcija(StavkeIzvoda s, OstaliRacuni ostaliRacuni, SifraPromene sp) {
    	this.datum = s.getDatumValute();
    	this.potrazuje = s.getIznos();
    	this.opis = "Извод";
    	this.sifraDokumenta = s.getIzvod().getBrojIzvoda().toString();
    	this.setSifraPromene(sp);
    	this.ostaliRacuni = ostaliRacuni;
    	this.setStavkaIzvoda(s);
    	//this.status = stan.getStatus();
    }
    
    public Transakcija(BigDecimal duguje, BigDecimal potrazuje, Stan s) {
    	this.potrazuje = potrazuje;
    	this.duguje = duguje;
    	this.stan = s;    	
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public StavkeIzvodaPostanska getStavkaIzvodaPostanska() {
		return stavkaIzvodaPostanska;
	}

	public void setStavkaIzvodaPostanska(StavkeIzvodaPostanska stavkaIzvodaPostanska) {
		this.stavkaIzvodaPostanska = stavkaIzvodaPostanska;
	}

	public String getSifraDokumenta() {
		return sifraDokumenta;
	}

	public void setSifraDokumenta(String sifraDokumenta) {
		this.sifraDokumenta = sifraDokumenta;
	}

	public LocalDate getDatumDokumenta() {
		return datumDokumenta;
	}

	public void setDatumDokumenta(LocalDate datumDokumenta) {
		this.datumDokumenta = datumDokumenta;
	}

	
	public LocalDate getDatum() {
        return datum;
    }

    public Transakcija datum(LocalDate datum) {
        this.datum = datum;
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalDate getValuta() {
        return valuta;
    }

    public Transakcija valuta(LocalDate valuta) {
        this.valuta = valuta;
        return this;
    }

    public void setValuta(LocalDate valuta) {
        this.valuta = valuta;
    }

    public String getStatus() {
        return status;
    }

    public Transakcija status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpis() {
        return opis;
    }

    public Transakcija opis(String opis) {
        this.opis = opis;
        return this;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public BigDecimal getDuguje() {
        return duguje;
    }

    public Transakcija duguje(BigDecimal duguje) {
        this.duguje = duguje;
        return this;
    }

    public void setDuguje(BigDecimal duguje) {
        this.duguje = duguje;
    }

    public BigDecimal getPotrazuje() {
        return potrazuje;
    }

    public Transakcija potrazuje(BigDecimal potrazuje) {
        this.potrazuje = potrazuje;
        return this;
    }

    public void setPotrazuje(BigDecimal potrazuje) {
        this.potrazuje = potrazuje;
    }

    public VrstaTransakcije getVrstaTransakcije() {
        return vrstaTransakcije;
    }

    public Transakcija vrstaTransakcije(VrstaTransakcije vrstaTransakcije) {
        this.vrstaTransakcije = vrstaTransakcije;
        return this;
    }

    public void setVrstaTransakcije(VrstaTransakcije vrstaTransakcije) {
        this.vrstaTransakcije = vrstaTransakcije;
    }   

    public SifraPromene getSifraPromene() {
        return sifraPromene;
    }

    public Transakcija sifraPromene(SifraPromene sifraPromene) {
        this.sifraPromene = sifraPromene;
        return this;
    }

    public void setSifraPromene(SifraPromene sifraPromene) {
        this.sifraPromene = sifraPromene;
    }

   

    public Racun getRacun() {
		return racun;
	}

	public void setRacun(Racun racun) {
		this.racun = racun;
	}

	public StavkeIzvoda getStavkaIzvoda() {
        return stavkaIzvoda;
    }

    public Transakcija stavkaIzvoda(StavkeIzvoda stavkeIzvoda) {
        this.stavkaIzvoda = stavkeIzvoda;
        return this;
    }

    public void setStavkaIzvoda(StavkeIzvoda stavkeIzvoda) {
        this.stavkaIzvoda = stavkeIzvoda;
    }

    public Stan getStan() {
        return stan;
    }

    public Transakcija stan(Stan stan) {
        this.stan = stan;
        return this;
    }

    public void setStan(Stan stan) {
        this.stan = stan;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public OstaliRacuni getOstaliRacuni() {
		return ostaliRacuni;
	}

	public void setOstaliRacuni(OstaliRacuni ostaliRacuni) {
		this.ostaliRacuni = ostaliRacuni;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transakcija)) {
            return false;
        }
        return id != null && id.equals(((Transakcija) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transakcija{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", valuta='" + getValuta() + "'" +
            ", status='" + getStatus() + "'" +
            ", opis='" + getOpis() + "'" +
            ", duguje=" + getDuguje() +
            ", potrazuje=" + getPotrazuje() +
            "}";
    }
}
