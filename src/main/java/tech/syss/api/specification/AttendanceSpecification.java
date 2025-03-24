package tech.syss.api.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNullElse;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import tech.syss.api.model.Attendance;

public class AttendanceSpecification {
    public static Specification<Attendance> filterAttendances(Long memberId, LocalDate from, LocalDate to) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            LocalDate today = LocalDate.now();
            LocalDate finalFrom = requireNonNullElse(from, today.withDayOfMonth(1));
            LocalDate finalTo = requireNonNullElse(to, today.withDayOfMonth(today.lengthOfMonth()));

            if (memberId != null) {
                predicates.add(criteriaBuilder.equal(root.get("member").get("id"), memberId));
            }

            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), finalFrom));
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), finalTo));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
