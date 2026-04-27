package owl.medassist_back.medAssist.entity.schedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import owl.medassist_back.medAssist.entity.medicalFacility.SpecialistFacility;

import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Getter
@Setter
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_facility_id", nullable = false)
    private SpecialistFacility specialistFacility;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;
}

