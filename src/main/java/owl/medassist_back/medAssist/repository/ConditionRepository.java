package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.indication.Condition;

import java.util.Optional;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {

    Optional<Condition> findByTextIgnoreCase(String text);

    boolean existsByTextIgnoreCase(String text);

    @Query("""
            select c from Condition c
            where (:query is null or lower(c.text) like lower(concat('%', :query, '%')))
            """)
    Page<Condition> search(@Param("query") String query, Pageable pageable);
}

