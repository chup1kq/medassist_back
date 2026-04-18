package owl.medassist_back.medAssist.entity.medicalFacility;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import owl.medassist_back.medAssist.entity.schedule.Schedule;
import owl.medassist_back.medAssist.entity.specialist.Specialist;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "specialist_facilities",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_specialist_facility", columnNames = {"specialist_id", "facility_id"})
        }
)
@Getter
@Setter
public class SpecialistFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialist_id", nullable = false)
    private Specialist specialist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private MedicalFacility facility;

    @OneToMany(
            mappedBy = "specialistFacility",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Schedule> schedules = new ArrayList<>();
}

