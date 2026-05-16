package owl.medassist_back.medAssist.entity.medicalFacility;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "medical_facility_schedules", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"medical_facility_id", "day_of_week"})
})
@Getter
@Setter
public class MedicalFacilitySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_facility_id", nullable = false)
    private MedicalFacility medicalFacility;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "is_closed", nullable = false)
    private Boolean isClosed = false;

    @Column(name = "is_24_hours", nullable = false)
    private Boolean is24Hours = false;
}


