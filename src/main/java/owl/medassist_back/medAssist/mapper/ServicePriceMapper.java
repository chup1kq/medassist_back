package owl.medassist_back.medAssist.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import owl.medassist_back.medAssist.dto.servicePrice.ServicePriceDto;
import owl.medassist_back.medAssist.entity.servicePrice.ServicePrice;

@Mapper(componentModel = "spring")
public interface ServicePriceMapper {

    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "service.name", target = "serviceName")
    ServicePriceDto toDto(ServicePrice servicePrice);
}
