package toplana.specifications;

import org.springframework.data.jpa.domain.Specification;
import toplana.domain.Stan;
import toplana.domain.Vlasnik;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class VlasnikSpecification implements Specification<Vlasnik> {

    private final List<SearchCriteria> criteriaList = new ArrayList<>();

    private final boolean sortByStanSifra;
    private final boolean sortAsc;

    public VlasnikSpecification(boolean sortByStanSifra, boolean sortAsc) {
        this.sortByStanSifra = sortByStanSifra;
        this.sortAsc = sortAsc;
    }

    public void add(SearchCriteria criteria) {
        criteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Vlasnik> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        // join za filter/sort po stan.sifra (NE FETCH!)
        Join<Vlasnik, Stan> stanJoin = root.join("stans", JoinType.LEFT);

        // FILTERI (tvoj SearchCriteria: entityName/key/value/operation)
        for (SearchCriteria c : criteriaList) {

            String value = c.getValue() == null ? null : c.getValue().toString().trim();
            if (value == null || value.isEmpty()) continue;

            if ("Vlasnik".equalsIgnoreCase(c.getEntityName())) {
                Path<String> field = root.get(c.getKey());

                if (c.getOperation() == SearchOperation.MATCH) {
                    predicates.add(likeUnaccent(cb, field, value));
                } else if (c.getOperation() == SearchOperation.EQUAL) {
                    predicates.add(cb.equal(field, value));
                }
            }

            if ("Stan".equalsIgnoreCase(c.getEntityName())) {
                Path<String> field = stanJoin.get(c.getKey());

                if (c.getOperation() == SearchOperation.MATCH) {
                    predicates.add(likeUnaccent(cb, field, value));
                } else if (c.getOperation() == SearchOperation.EQUAL) {
                    predicates.add(cb.equal(field, value));
                }
            }
        }

        Predicate where = cb.and(predicates.toArray(new Predicate[0]));

        // COUNT query: nikad ne diraj ORDER BY / GROUP BY
        boolean isCountQuery =
            Long.class.equals(query.getResultType()) || long.class.equals(query.getResultType());

        if (!isCountQuery && sortByStanSifra) {
            // GROUP BY da bi MIN/ORDER BY bio legalan
            query.groupBy(root.get("id"));

            // MIN nad String -> koristi funkciju "min" (Postgres radi za text)
            Expression<String> firstStanSifra = cb.function("min", String.class, stanJoin.get("sifra"));

            // Primarni sort: stanSifra, Sekundarni: id (stabilno)
            query.orderBy(
                sortAsc ? cb.asc(firstStanSifra) : cb.desc(firstStanSifra),
                cb.asc(root.get("id"))
            );
        } else if (!isCountQuery) {
            // za normalne slučajeve bez stanSifra sortiranja – distinct da ne vrati duplikate zbog join-a
            query.distinct(true);
        }

        return where;
    }



    // -------- helper --------
    private Predicate likeUnaccent(CriteriaBuilder cb, Path<String> field, String value) {
        Expression<String> f = cb.function("unaccent", String.class, cb.upper(field));
        Expression<String> p = cb.function(
            "unaccent", String.class,
            cb.literal("%" + value.toUpperCase() + "%")
        );
        return cb.like(f, p);
    }
}
