package toplana.specifications;

import org.springframework.data.jpa.domain.Specification;
import toplana.domain.Stan;
import toplana.domain.Vlasnik;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class VlasnikSpecification implements Specification<Vlasnik> {

    private final List<SearchCriteria> list = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Vlasnik> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> andPredicates = new ArrayList<>();

        // join za sifru stana
        Join<Vlasnik, Stan> stanJoin = root.join("stans", JoinType.LEFT);
        query.distinct(true);

        for (SearchCriteria c : list) {

            // očekujemo jedan MATCH kriterijum (npr. "q") – globalna pretraga
            if (c.getOperation() == SearchOperation.MATCH) {
                String q = String.valueOf(c.getValue()).trim();
                if (q.isEmpty()) continue;

                Predicate pPrezime = likeUnaccent(cb, root.get("prezime"), q);
                Predicate pIme     = likeUnaccent(cb, root.get("ime"), q);
                Predicate pSifra   = likeUnaccent(cb, stanJoin.get("sifra"), q);

                // OR po ova tri polja
                andPredicates.add(cb.or(pPrezime, pIme, pSifra));
                continue;
            }

            // ako imaš još filtera (npr. EQUAL na ukljucen itd), dodaj ih ovde kao AND
            // andPredicates.add(...)
        }

        return cb.and(andPredicates.toArray(new Predicate[0]));
    }

    private Predicate likeUnaccent(CriteriaBuilder cb, Path<String> field, String value) {
        Expression<String> fieldExpr = cb.function("unaccent", String.class, cb.upper(field));
        Expression<String> paramExpr = cb.function(
            "unaccent", String.class,
            cb.literal("%" + value.toUpperCase() + "%")
        );
        return cb.like(fieldExpr, paramExpr);
    }
}