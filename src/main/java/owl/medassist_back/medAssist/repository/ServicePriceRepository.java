package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;

import java.util.List;

public interface ServicePriceRepository extends JpaRepository<ServicePrice, Integer> {

    List<ServicePrice> findAllByOrderById();

    List<ServicePrice> findByServiceIdOrderById(Integer serviceId);

    @Query("""
            select sp from ServicePrice sp
            where (:query is null or lower(sp.name) like lower(concat('%', :query, '%')))
            """)
    Page<ServicePrice> search(@Param("query") String query, Pageable pageable);
}


