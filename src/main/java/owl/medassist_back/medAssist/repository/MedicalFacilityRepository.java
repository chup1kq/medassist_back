package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityNameDto;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacility;

import java.util.List;

public interface MedicalFacilityRepository extends JpaRepository<MedicalFacility, Integer> {

	@Query("""
			select mf from MedicalFacility mf
			where (:query is null or lower(mf.name) like lower(concat('%', :query, '%')))
			""")
	@EntityGraph(attributePaths = "schedules")
	Page<MedicalFacility> search(@Param("query") String query, Pageable pageable);

	@Query("""
			select new owl.medassist_back.medAssist.dto.medicalFacility.MedicalFacilityNameDto(
				mf.id,
				mf.name
			)
			from MedicalFacility mf
			order by mf.name
			""")
	List<MedicalFacilityNameDto> findAllName();

	@EntityGraph(attributePaths = "schedules")
	List<MedicalFacility> findAllByOrderByNameAsc();

}

