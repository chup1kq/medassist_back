package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;

import java.util.List;

public interface ServicePriceRepository extends JpaRepository<ServicePrice, Integer> {

    List<ServicePrice> findAllByOrderById();

    List<ServicePrice> findByServiceIdOrderById(Integer serviceId);
}


