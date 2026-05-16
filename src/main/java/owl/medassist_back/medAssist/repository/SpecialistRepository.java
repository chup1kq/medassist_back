package owl.medassist_back.medAssist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.dto.specialist.SpecialistNameDto;
import owl.medassist_back.medAssist.entity.specialist.Specialist;

import java.util.List;
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
            where (:query is null or lower(s.fullName) like lower(concat('%', :query, '%')))
            """)
    Page<Specialist> searchByFullName(@Param("query") String query, Pageable pageable);

    @Query("""
            select new owl.medassist_back.medAssist.dto.specialist.SpecialistNameDto(
                s.id,
                s.fullName
            )
            from Specialist s
            order by s.fullName
            """)
    List<SpecialistNameDto> findAllName();
}

