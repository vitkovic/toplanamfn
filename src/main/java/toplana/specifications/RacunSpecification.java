package toplana.specifications;

import org.springframework.data.jpa.domain.Specification;

import toplana.domain.Podstanica;
import toplana.domain.Racun;
import toplana.domain.Stan;
import toplana.domain.Vlasnik;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RacunSpecification implements Specification<Racun> {

    private List<SearchCriteria> list;

    public RacunSpecification() {
        this.list = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Racun> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    	
    	Join<Stan, Racun> stanRacun = root.join("stan");
    	Join<Vlasnik,Stan> vlasnikStan = stanRacun.join("vlasnik");
    	Join<Podstanica,Stan> podstanicaStan = stanRacun.join("podstanica");

        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();
        
        Root<Object> proba;

        //add add criteria to predicates
        for (SearchCriteria criteria : list) {
        	if(criteria.getEntityName().equals("Racun")) {// ako je polje iz klase racun
        		// datum racuna
        		if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                    predicates.add(builder.greaterThanOrEqualTo(
                    		root.get(criteria.getKey()), (LocalDate)criteria.getValue()));
                } 
        		if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                    predicates.add(builder.lessThanOrEqualTo(
                    		root.get(criteria.getKey()), (LocalDate)criteria.getValue()));
                }
        		//azuriran ili proknjizen
        		if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    predicates.add(builder.equal(
                    		root.get(criteria.getKey()), criteria.getValue()));
                }
        		
        	}else if(criteria.getEntityName().equals("Stan")) {// ako je polje iz klase stan
/**********************************************************************************************/
        		if(criteria.getKey().equals("sifra")) {
        			if (criteria.getOperation().equals(SearchOperation.MATCH)) {
        				predicates.add(builder.like(
        						builder.upper(stanRacun.get(criteria.getKey())),
        						"%" + criteria.getValue().toString().toUpperCase() + "%"));
        			}
        		}else if(criteria.getKey().equals("podstanica")) {
        			 predicates.add(builder.equal(
 	                		stanRacun.get(criteria.getKey()), criteria.getValue())); 
        		}
/**********************************************************************************************/        		
        	}else if(criteria.getEntityName().equals("Vlasnik")) {// ako je polje iz klase Vlasnik
/**********************************************************************************************/
        		if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    predicates.add(builder.like(
                            builder.upper(vlasnikStan.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toUpperCase() + "%"));
                }        		
/**********************************************************************************************/        		
        	}else if(criteria.getEntityName().equals("Podstanica")) {// ako je polje iz klase Podstanica
/**********************************************************************************************/
        		if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    predicates.add(builder.like(
                            builder.upper(vlasnikStan.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toUpperCase() + "%"));
                }        		
/**********************************************************************************************/        		
        	}else {
	            if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
	                predicates.add(builder.greaterThan(
	                		vlasnikStan.get(criteria.getKey()), criteria.getValue().toString()));
	            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
	                predicates.add(builder.lessThan(
	                		vlasnikStan.get(criteria.getKey()), criteria.getValue().toString()));
	            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
	                predicates.add(builder.greaterThanOrEqualTo(
	                		vlasnikStan.get(criteria.getKey()), criteria.getValue().toString()));
	            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
	                predicates.add(builder.lessThanOrEqualTo(
	                		vlasnikStan.get(criteria.getKey()), criteria.getValue().toString()));
	            } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
	                predicates.add(builder.notEqual(
	                		vlasnikStan.get(criteria.getKey()), criteria.getValue()));
	            } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
	                predicates.add(builder.equal(
	                		vlasnikStan.get(criteria.getKey()), criteria.getValue()));                                
	            } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
	                predicates.add(builder.like(
	                        builder.upper(vlasnikStan.get(criteria.getKey())),
	                        "%" + criteria.getValue().toString().toUpperCase() + "%"));
	            } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
	                predicates.add(builder.like(
	                        builder.lower(stanRacun.get(criteria.getKey())),
	                        criteria.getValue().toString().toLowerCase() + "%"));
	            } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
	                predicates.add(builder.like(
	                        builder.lower(stanRacun.get(criteria.getKey())),
	                        "%" + criteria.getValue().toString().toLowerCase()));
	            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
	                predicates.add(builder.in(stanRacun.get(criteria.getKey())).value(criteria.getValue()));
	            } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
	                predicates.add(builder.not(stanRacun.get(criteria.getKey())).in(criteria.getValue()));
	            }
        	}
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}