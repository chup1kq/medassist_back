package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import owl.medassist_back.medAssist.dto.specialistFacility.SpecialistFacilityDto;
import owl.medassist_back.medAssist.entity.medicalFacility.SpecialistFacility;

import java.util.List;
import java.util.Optional;

public interface SpecialistFacilityRepository extends JpaRepository<SpecialistFacility, Integer> {

    Optional<SpecialistFacility> findBySpecialistIdAndFacilityId(Integer specialistId, Integer facilityId);

    @EntityGraph(attributePaths = {
            "specialist",
            "facility"
    })
    Optional<SpecialistFacility> findDetailedById(Integer id);

    @Query("""
            select new owl.medassist_back.medAssist.dto.specialistFacility.SpecialistFacilityDto(
                sf.id,
                sp.id,
                sp.fullName,
                f.id,
                f.name
            )
            from SpecialistFacility sf
            join sf.specialist sp
            join sf.facility f
            order by f.name, sp.fullName
            """)
    List<SpecialistFacilityDto> findAllCards();
}

