package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import owl.medassist_back.medAssist.entity.schedule.Schedule;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("""
            select sch from Schedule sch
            join fetch sch.specialistFacility sf
            join fetch sf.specialist sp
            join fetch sf.facility f
            where (:specialistId is null or sp.id = :specialistId)
              and (:facilityId is null or f.id = :facilityId)
              and (:dayOfWeek is null or sch.dayOfWeek = :dayOfWeek)
            order by f.name, sch.dayOfWeek, sch.startTime
            """)
    List<Schedule> findByFilters(
            @Param("specialistId") Integer specialistId,
            @Param("facilityId") Integer facilityId,
            @Param("dayOfWeek") Integer dayOfWeek
    );

    @Query("""
            select sch from Schedule sch
            join sch.specialistFacility sf
            join sf.specialist sp
            join sf.facility f
            where (:specialistQuery is null or lower(sp.fullName) like lower(concat('%', :specialistQuery, '%')))
              and (:facilityQuery is null or lower(f.name) like lower(concat('%', :facilityQuery, '%')))
            order by f.name, sch.dayOfWeek, sch.startTime
            """)
    Page<Schedule> search(@Param("specialistQuery") String specialistQuery, @Param("facilityQuery") String facilityQuery, Pageable pageable);
}

