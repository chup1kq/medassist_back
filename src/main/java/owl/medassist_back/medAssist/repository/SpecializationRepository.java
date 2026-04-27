package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.medassist_back.medAssist.entity.specialist.Specialization;

import java.util.Optional;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

    Optional<Specialization> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}

