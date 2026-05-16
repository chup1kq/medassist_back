package owl.medassist_back.medAssist.entity.medicalService;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import owl.medassist_back.medAssist.entity.review.Review;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;
import owl.medassist_back.medAssist.entity.indication.Condition;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "services")
@Getter
@Setter
public class MedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String details;

    private String preparation;

    @Column(unique = true)
    private String url;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "mis_id", nullable = false)
    private Integer misId = 0;

    @OneToMany(
            mappedBy = "service",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ServicePrice> prices = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "service_indications",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "condition_id")
    )
    private Set<Condition> indications = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "service_contraindications",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "condition_id")
    )
    private Set<Condition> contraindications = new HashSet<>();

    @OneToMany(mappedBy = "service")
    private Set<Review> reviews = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "service_specialists",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "specialist_id")
    )
    private Set<Specialist> specialists = new HashSet<>();
}
