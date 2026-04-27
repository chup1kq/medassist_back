package owl.medassist_back.medAssist.entity.servicePrice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import owl.medassist_back.medAssist.entity.medicalService.MedicalService;

import java.math.BigDecimal;

@Entity
@Table(name = "service_prices")
@Getter
@Setter
public class ServicePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private MedicalService service;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;
}

