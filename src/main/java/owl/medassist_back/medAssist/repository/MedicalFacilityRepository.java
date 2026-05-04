package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacility;

public interface MedicalFacilityRepository extends JpaRepository<MedicalFacility, Integer> {

	@Query("""
			select mf from MedicalFacility mf
			where (:query is null or lower(mf.name) like lower(concat('%', :query, '%')))
			""")
	Page<MedicalFacility> search(@Param("query") String query, Pageable pageable);
}

