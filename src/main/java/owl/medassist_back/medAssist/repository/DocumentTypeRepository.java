package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.medassist_back.medAssist.entity.document.DocumentType;

import java.util.Optional;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

    Optional<DocumentType> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
