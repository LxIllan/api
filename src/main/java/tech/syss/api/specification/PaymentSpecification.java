package tech.syss.api.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tech.syss.api.model.Payment;

public class PaymentSpecification {
    public static Specification<Payment> filterPayments(Long memberId, LocalDate from, LocalDate to) {
        return (root, query, criteriaBulder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (memberId != null) {
                predicates.add(criteriaBulder.equal(root.get("member").get("id"), memberId));
            }
            if (from != null) {
                predicates.add(criteriaBulder.greaterThanOrEqualTo(root.get("date"), from));
            }
            if (to != null) {
                predicates.add(criteriaBulder.lessThanOrEqualTo(root.get("date"), to));
            }

            return criteriaBulder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
