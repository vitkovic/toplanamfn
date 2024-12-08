package toplana.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import toplana.domain.SifraPromene;
import toplana.domain.Stan;
import toplana.domain.Transakcija;
import toplana.domain.Vlasnik;
import toplana.specifications.TransakcijaSpecification;
import toplana.web.rest.dto.DugujePotrazujeReoni;
import toplana.web.rest.dto.TransakcijaStanUkupnoDTO;
import toplana.web.rest.dto.TransakcijaZaStanDTO;

@Repository
public class TransakcijaCustomRepository {
	 @PersistenceContext
	  private EntityManager entityManager;
	 
	 public List<Transakcija> findAll() {
		 TypedQuery<Transakcija> query = entityManager.createQuery("SELECT t FROM Transakcija t", Transakcija.class);
		 return query.getResultList();
	 }
	 
	 public List<TransakcijaStanUkupnoDTO> findAllSumed(TransakcijaSpecification spec){
		 
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder(); 
		 CriteriaQuery<TransakcijaStanUkupnoDTO> query = cb.createQuery(TransakcijaStanUkupnoDTO.class);
		 Root<Transakcija> root = query.from(Transakcija.class);
		 Join<Stan, Transakcija> stanTransakcija = root.join("stan");
		 Join<Vlasnik,Stan> stanVlasnik = stanTransakcija.join("vlasnik");
		 
		 query.multiselect(cb.sum(root.get("duguje")),cb.sum(root.get("potrazuje")), 
				 stanVlasnik.get("prezime"),
				 stanTransakcija.get("sifra")).groupBy(stanTransakcija.get("sifra"),stanVlasnik.get("prezime"));
		 
		 query.where(spec.toPredicate(root, query, cb));
		 
		 TypedQuery<TransakcijaStanUkupnoDTO> queryOut = entityManager.createQuery(query);
		 
		 return queryOut.getResultList();
		 
	 }
	 
	 public List<TransakcijaZaStanDTO> findAllForDnevnik(TransakcijaSpecification spec){
		 
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder(); 
		 CriteriaQuery<TransakcijaZaStanDTO> query = cb.createQuery(TransakcijaZaStanDTO.class);
		 Root<Transakcija> root = query.from(Transakcija.class);
		 Join<Stan, Transakcija> stanTransakcija = root.join("stan");
		 Join<Vlasnik,Stan> stanVlasnik = stanTransakcija.join("vlasnik");
		 Join<Transakcija, SifraPromene> transakcijaSifraPromene = root.join("sifraPromene");
		 
		 
		 List<Order> orderList = new ArrayList();
		 orderList.add(cb.asc(stanTransakcija.get("sifra")));
		 
		 query.multiselect(root.get("datum"),root.get("sifraDokumenta"),transakcijaSifraPromene.get("sifra"),
				 root.get("duguje"),root.get("potrazuje"), 
				 stanVlasnik.get("prezime"),
				 stanTransakcija.get("sifra"), root.get("opis"),
				 stanVlasnik.get("ime"),stanTransakcija.get("grad"),stanTransakcija.get("ulica"),
				 stanTransakcija.get("ulaz"),stanTransakcija.get("broj"),
				 stanTransakcija.get("podstanica"),stanTransakcija.get("tipPotrosaca"), stanTransakcija.get("ukljucen"));				 
				 
		 query.where(spec.toPredicate(root, query, cb));
		 query.orderBy(orderList);
		 
		 TypedQuery<TransakcijaZaStanDTO> queryOut = entityManager.createQuery(query);
		 
		 return queryOut.getResultList();
		 
	 }
	 
	 public List<DugujePotrazujeReoni> findSumForDnevnik(TransakcijaSpecification spec){
		 
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder(); 
		 CriteriaQuery<DugujePotrazujeReoni> query = cb.createQuery(DugujePotrazujeReoni.class);
		 Root<Transakcija> root = query.from(Transakcija.class);
		 Join<Stan, Transakcija> stanTransakcija = root.join("stan");
		 Join<Vlasnik,Stan> stanVlasnik = stanTransakcija.join("vlasnik");
		 Join<Transakcija, SifraPromene> transakcijaSifraPromene = root.join("sifraPromene");
		 
		 
		 //List<Order> orderList = new ArrayList();
		 //orderList.add(cb.asc(stanTransakcija.get("sifra")));
		 
		 query.multiselect(stanTransakcija.get("tipPotrosaca"),cb.sum(root.get("duguje")),cb.sum(root.get("potrazuje"))).
		 							groupBy(stanTransakcija.get("tipPotrosaca"));

		 query.where(spec.toPredicate(root, query, cb));
		 //query.orderBy(orderList);
		 
		 TypedQuery<DugujePotrazujeReoni> queryOut = entityManager.createQuery(query);
		 
		 return queryOut.getResultList();
		 
	 }

}
