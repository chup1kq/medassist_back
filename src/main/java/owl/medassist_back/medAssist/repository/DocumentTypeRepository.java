package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.document.DocumentType;

import java.util.List;
import java.util.Optional;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

    Optional<DocumentType> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    @Query("""
            select d from DocumentType d
            where (:querry is null or lower(d.name) like lower(concat('%', :querry, '%')))
            """)
    Page<DocumentType> search(@Param("querry") String querry, Pageable pageable);

    @Query("""
            select d from DocumentType d
            where (:querry is null or lower(d.name) like lower(concat('%', :querry, '%')))
            """)
    List<DocumentType> search(@Param("querry") String querry);
}
