package owl.medassist_back.medAssist.entity.medicalFacility;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medical_facilities")
@Getter
@Setter
public class MedicalFacility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String description;

    @OneToMany(
            mappedBy = "facility",
            fetch = FetchType.LAZY
    )
    private List<SpecialistFacility> specialistFacilities = new ArrayList<>();
}

