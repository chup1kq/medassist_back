package owl.medassist_back.medAssist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceCardDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceNameDto;
import owl.medassist_back.medAssist.dto.medicalService.MedicalServiceNameUrlDto;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;

import java.util.List;
import java.util.Optional;

public interface MedicalServiceRepository extends JpaRepository<MedicalService, Integer>, JpaSpecificationExecutor<MedicalService> {

    boolean existsByUrlIgnoreCase(String url);

    boolean existsByUrlIgnoreCaseAndIdNot(String url, Integer id);

    @EntityGraph(attributePaths = {
            "prices",
            "prices.service",
            "indications",
            "contraindications",
            "reviews"
    })
    @Query("select s from MedicalService s where lower(trim(s.url)) = lower(trim(:url))")
    Optional<MedicalService> findDetailedByUrl(@Param("url") String url);

    @EntityGraph(attributePaths = {
            "prices",
            "prices.service",
            "indications",
            "contraindications",
            "reviews"
    })
    Optional<MedicalService> findDetailedById(Integer id);

    @EntityGraph(attributePaths = {
            "prices",
            "prices.service",
            "indications",
            "contraindications",
            "specialists",
            "reviews"
    })
    @Query("""
            select s from MedicalService s
            where (:query is null
                or lower(s.name) like lower(concat('%', :query, '%'))
                or lower(coalesce(s.description, '')) like lower(concat('%', :query, '%'))
                or lower(s.url) like lower(concat('%', :query, '%')))
            """)
    Page<MedicalService> searchDetailed(@Param("query") String query, Pageable pageable);

    @Query("""
                select new owl.medassist_back.medAssist.dto.medicalService.MedicalServiceNameUrlDto(
                    s.id,
                    s.name,
                    s.url
                )
                from MedicalService s
                where (:query is null
                    or lower(s.name) like lower(concat('%', :query, '%'))
                    or lower(s.url) like lower(concat('%', :query, '%')))
            """)
    List<MedicalServiceNameUrlDto> findAllNameUrl(@Param("query") String query);

    @Query("""
                select new owl.medassist_back.medAssist.dto.medicalService.MedicalServiceNameDto(
                    s.id,
                    s.name
                )
                from MedicalService s
                order by s.name
            """)
    List<MedicalServiceNameDto> findAllName();

    @Query("""
                select new owl.medassist_back.medAssist.dto.medicalService.MedicalServiceCardDto(
                    s.id,
                    s.misId,
                    s.name,
                    s.description,
                    s.url,
                    s.photoUrl
                )
                from MedicalService s
                where (:query is null
                    or lower(s.name) like lower(concat('%', :query, '%'))
                    or lower(coalesce(s.description, '')) like lower(concat('%', :query, '%'))
                    or lower(s.url) like lower(concat('%', :query, '%')))
            """)
    Page<MedicalServiceCardDto> findAllCards(@Param("query") String query, Pageable pageable);
}

