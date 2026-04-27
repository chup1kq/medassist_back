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
                select d from Document d
                where (:query is null
                    or lower(d.name) like lower(concat('%', :query, '%'))
                    or lower(coalesce(d.description, '')) like lower(concat('%', :query, '%')))
                  and (:documentTypeId is null or d.documentType.id = :documentTypeId)
            """)
    List<Document> search(
            @Param("query") String query,
            @Param("documentTypeId") Integer documentTypeId
    );

    @Query("""
                select d from Document d
                where (:query is null
                    or lower(d.name) like lower(concat('%', :query, '%'))
                    or lower(coalesce(d.description, '')) like lower(concat('%', :query, '%')))
                  and (:documentTypeId is null or d.documentType.id = :documentTypeId)
            """)
    Page<Document> search(
            @Param("query") String query,
            @Param("documentTypeId") Integer documentTypeId,
            Pageable pageable
    );
}

