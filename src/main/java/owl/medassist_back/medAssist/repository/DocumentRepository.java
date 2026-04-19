package owl.medassist_back.medAssist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.document.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query("""
        SELECT d FROM Document d
        WHERE (:query IS NULL
                OR lower(d.name) LIKE lower(concat('%', :query, '%'))
                OR lower(coalesce(d.description, '')) LIKE lower(concat('%', :query, '%')))
          AND (:documentType IS NULL OR lower(d.documentType.name) = lower(:documentType))
        ORDER BY d.name
        """)
    List<Document> search(
            @Param("query") String query,
            @Param("documentType") String documentType
    );

    @Query("""
        SELECT d FROM Document d
        WHERE (:query IS NULL
                OR lower(d.name) LIKE lower(concat('%', :query, '%'))
                OR lower(coalesce(d.description, '')) LIKE lower(concat('%', :query, '%')))
          AND (:documentType IS NULL OR lower(d.documentType.name) = lower(:documentType))
        """)
    Page<Document> search(
            @Param("query") String query,
            @Param("documentType") String documentType,
            Pageable pageable
    );
}

