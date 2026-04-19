package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.domain.Specification;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;

public final class MedicalServiceSpecification {

    private MedicalServiceSpecification() {
    }

    public static Specification<MedicalService> search(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (query == null) {
                return criteriaBuilder.conjunction();
            }

            String pattern = "%" + query.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(criteriaBuilder.coalesce(root.get("description"), "")), pattern)
            );
        };
    }
}

