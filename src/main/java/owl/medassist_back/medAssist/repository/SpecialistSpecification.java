package owl.medassist_back.medAssist.repository;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import owl.medassist_back.medAssist.entity.specialist.Specialist;
import owl.medassist_back.medAssist.entity.specialist.Specialization;

public class SpecialistSpecification {

    public static Specification<Specialist> searchActive(String query, String specialization) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new java.util.ArrayList<Predicate>();

            // s.active = true
            predicates.add(criteriaBuilder.isTrue(root.get("active")));

            // query filter: lower(s.fullName) like lower(concat('%', query, '%'))
            if (query != null) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("fullName")),
                                "%" + query + "%"
                        )
                );
            }

            // specialization filter: join specializations and compare
            if (specialization != null) {
                Join<Specialist, Specialization> join = root.join("specializations", JoinType.LEFT);
                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(join.get("name")),
                                specialization
                        )
                );
                if (criteriaQuery != null) {
                    criteriaQuery.distinct(true);
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}


