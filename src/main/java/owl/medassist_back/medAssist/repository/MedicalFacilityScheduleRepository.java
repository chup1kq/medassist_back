package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.medicalFacility.MedicalFacilitySchedule;

import java.util.List;
import java.util.Optional;

public interface MedicalFacilityScheduleRepository extends JpaRepository<MedicalFacilitySchedule, Integer> {

    @Query("""
            select mfs from MedicalFacilitySchedule mfs
            join fetch mfs.medicalFacility mf
            where mf.id = :facilityId
            order by mfs.dayOfWeek, mfs.startTime
            """)
    List<MedicalFacilitySchedule> findByFacilityId(@Param("facilityId") Integer facilityId);

    @Query("""
            select mfs from MedicalFacilitySchedule mfs
            join fetch mfs.medicalFacility
            where mfs.medicalFacility.id = :facilityId
              and mfs.dayOfWeek = :dayOfWeek
            """)
    Optional<MedicalFacilitySchedule> findByFacilityIdAndDayOfWeek(
            @Param("facilityId") Integer facilityId,
            @Param("dayOfWeek") Integer dayOfWeek
    );
}

