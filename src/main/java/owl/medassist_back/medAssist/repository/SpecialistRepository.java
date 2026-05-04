package owl.medassist_back.medAssist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.specialist.Specialist;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Integer>, JpaSpecificationExecutor<Specialist> {

    @EntityGraph(attributePaths = {
            "specializations",
            "specialistFacilities",
            "specialistFacilities.facility",
            "specialistFacilities.schedules",
            "reviews"
    })
    Optional<Specialist> findDetailedById(Integer id);

    @EntityGraph(attributePaths = {
            "specializations"
    })
    Page<Specialist> findAll(Specification<Specialist> spec, Pageable pageable);

    @EntityGraph(attributePaths = {
            "specializations"
    })
    @org.springframework.data.jpa.repository.Query("""
            select s from Specialist s
            where (:querry is null or lower(s.fullName) like lower(concat('%', :querry, '%')))
            """)
    Page<Specialist> searchByFullName(@Param("querry") String querry, Pageable pageable);
}

