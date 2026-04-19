package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
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
    @Query("select s from Specialist s where s.id = :id")
    Optional<Specialist> findDetailedById(@Param("id") Integer id);
}

