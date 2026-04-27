package owl.medassist_back.medAssist.entity.specialist;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import owl.medassist_back.medAssist.entity.medicalFacility.SpecialistFacility;
import owl.medassist_back.medAssist.entity.review.Review;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "specialists")
@Getter
@Setter
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    private String description;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "experience_years")
    private Integer experienceYears;

    private Boolean active;

    @ManyToMany
    @JoinTable(
            name = "specialist_specializations",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializations = new HashSet<>();

    @OneToMany(mappedBy = "specialist")
    private List<SpecialistFacility> specialistFacilities = new ArrayList<>();


    @OneToMany(mappedBy = "specialist")
    private List<Review> reviews = new ArrayList<>();
}

