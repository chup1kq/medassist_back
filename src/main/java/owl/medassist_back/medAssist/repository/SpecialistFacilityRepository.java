package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.medassist_back.medAssist.entity.medicalFacility.SpecialistFacility;

import java.util.Optional;

public interface SpecialistFacilityRepository extends JpaRepository<SpecialistFacility, Integer> {

    Optional<SpecialistFacility> findBySpecialistIdAndFacilityId(Integer specialistId, Integer facilityId);
}

