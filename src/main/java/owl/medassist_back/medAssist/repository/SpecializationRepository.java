package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.specialist.Specialization;

import java.util.Optional;

import java.util.List;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    Optional<Specialization> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    @Query("""
            select s from Specialization s
            where (:query is null or lower(s.name) like lower(concat('%', :query, '%')))
            """)
    Page<Specialization> search(@Param("query") String query, Pageable pageable);

    @Query("""
            select s from Specialization s
            where (:query is null or lower(s.name) like lower(concat('%', :query, '%')))
            order by s.name asc
            """)
    List<Specialization> search(@Param("query") String query);
}

