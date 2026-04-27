package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacility;

public interface MedicalFacilityRepository extends JpaRepository<MedicalFacility, Integer> {
}

