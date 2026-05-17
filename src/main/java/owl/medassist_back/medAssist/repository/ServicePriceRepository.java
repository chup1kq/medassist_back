package owl.medassist_back.medAssist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;

import java.util.List;

public interface ServicePriceRepository extends JpaRepository<ServicePrice, Integer> {

    List<ServicePrice> findAllByOrderById();

    List<ServicePrice> findByServiceIdOrderById(Integer serviceId);

    @Query("""
            select new owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto(
                sp.id,
                sp.misId,
                sp.name,
                sp.price,
                s.id,
                s.name
            )
            from ServicePrice sp
            join sp.service s
            where (:query is null
                or lower(sp.name) like lower(concat('%', :query, '%'))
                or lower(s.name) like lower(concat('%', :query, '%')))
            """)
    Page<ServicePriceDto> search(@Param("query") String query, Pageable pageable);
}


