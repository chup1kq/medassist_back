package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;

import java.util.Optional;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Integer>, JpaSpecificationExecutor<MedicalService> {

    boolean existsByUrlIgnoreCase(String url);

    boolean existsByUrlIgnoreCaseAndIdNot(String url, Integer id);


    @EntityGraph(attributePaths = {
            "prices",
            "indications",
            "contraindications",
            "reviews"
    })
    @Query("select s from MedicalService s where lower(trim(s.url)) = lower(trim(:url))")
    Optional<MedicalService> findDetailedByUrl(@Param("url") String url);
}

